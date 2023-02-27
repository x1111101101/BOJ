import java.util.*;
import java.lang.Math.abs;

/**
2533번: 사회망 서비스
후위탐색을 이용해서 노드 별 얼리어댑터 선정 여부를 결정
문제에서 입력이 트리로만 주어지고, 트리 중 어느 노드를 시작점으로 해도 동일한 위상이기에 유니온 파인드를 적용할 필요는 없었다.

채점을 받고 문제 태그를 확인해보니 트리에서의 다이나믹프로그래밍으로 분류되어있었다.
후위 연산을 하며 자식 노드 중 얼리어댑터 수를 이용해 노드의 얼리어댑터로의 선정 여부를 결정했는데 이게 DP로 분류되는 이유인 것 같다.
풀 당시에는 DP 문제인지의 여부를 크게 신경쓰지 않고 풀었다.
*/

val SCALE = 1000000
val parent = IntArray(SCALE) {-1}
val graph = Array<MutableList<Int>>(SCALE) {ArrayList()}

fun merge(a: Int, b: Int) {
    val pa = findParent(a)
    val pb = findParent(b)
    if(pa < pb) {
        parent[pa] += parent[pb]
        parent[pb] = pa
    } else {
        parent[pb] += parent[pa]
        parent[pa] = pb
    }
}

val st = ArrayList<Int>(1000000)
fun findParent(t: Int): Int {
    var r = t
    while(parent[r] >= 0) {
        st.add(r)
        r = parent[r]
    }
    for(a in st) parent[a] = r
    st.clear()
    return r
}

var rec = 0
fun post(visit: BooleanArray, current: Int): Int {
    val li = LinkedList<Int>()
    for(c in graph[current]) {
        if(visit[c]) continue
        visit[c] = true
        li.add(c)
    }
    if(li.isEmpty()) return 1
    var count = 0
    for(c in li) {
        count += post(visit, c)
    }
    if(count == 0) {
        return 1
    }
    rec++
    return 0
}

fun main(args: Array<String>) = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    repeat(n-1) {
        val st = StringTokenizer(readLine())
        val a = st.nextToken().toInt() - 1
        val b = st.nextToken().toInt() - 1
        graph[a].add(b)
        graph[b].add(a)
        merge(a, b)
    }
    var start = -1
    for(i in 0 until n) {
        if(-parent[i] == n) {
            start = i
            break
        }
    }
    var visit = BooleanArray(n)
    visit[start] = true
    post(visit, start)
    println(rec)
}
