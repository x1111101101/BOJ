import java.util.*;
import java.lang.Math.abs;

/**
2644번: 촌수계산

두 노드간의 최단 거리를 구하는 문제.
BFS로 해결했는데, 거리 값을 구해야 하기 때문에 같은 깊이(같은 누적 탐색 횟수)에 있는 탐색 대상 노드들을 일괄적으로 처리해서 거리를 구함
*/

val graph = Array<MutableList<Int>>(100) {ArrayList()}
var n = 0

fun solve(t1: Int, t2: Int): Int {
    var queue = LinkedList<Int>()
    val visit = BooleanArray(n) {false}
    visit[t1] = true
    queue.add(t1)
    var bfsCount = 0 // BFS에서의 최대 누적 탐색 횟수
    do {
        bfsCount++
        val tempQueue = LinkedList<Int>()
        do {
            val node = queue.removeFirst()
            for(near in graph[node]) {
                if(visit[near]) continue
                if(near == t2) return bfsCount
                visit[near] = true
                tempQueue.add(near)
            }
        } while(queue.isNotEmpty())
        queue = tempQueue
    } while(queue.isNotEmpty())
    return -1
}

fun main(args: Array<String>) = with(System.`in`.bufferedReader()) {
    n = readLine().toInt()
    val tokens = StringTokenizer(readLine())
    val target1 = tokens.nextToken().toInt() - 1
    val target2 = tokens.nextToken().toInt() - 1
    val m = readLine().toInt()
    repeat(m) {
        val st = StringTokenizer(readLine())
        val x = st.nextToken().toInt() - 1
        val y = st.nextToken().toInt() - 1
        graph[x].add(y)
        graph[y].add(x)
    }
    println(solve(target1, target2))
}
