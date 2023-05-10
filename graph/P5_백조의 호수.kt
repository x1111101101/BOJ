import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

/**
3197번: 백조의 호수
disjoint set 알고리즘을 이용해서 풀었다. 처음엔 find 연산 시 스택을 이용해 뿌리 노드와 자식 노드 사잇값들도 부모로 뿌리 노드를 가리키도록 했는데, 노드의 집합이 한번 조회되면 재조회가 얼음 근처에서만 일어나기 때문에 이러한 최적화는 불필요했고,
오히려 오버헤드 때문에 시간초과가 난 듯 하다. 이 로직을 없애니 AC가 떴다.
*/

val OFFSETS = arrayOf(0 to 1, 0 to -1, 1 to 1, 1 to -1)

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val len = readLine().split(" ").map {it.toInt()}
    
    val map = Array(len[0]) {readLine().toCharArray()}
    val parent = Array(len[0]*len[1]) {it}
    val swans = ArrayList<Int>()
    
    fun p() {
        for(x in 0 until len[0]) {
            for(y in 0 until len[1]) {
                print(map[x][y])
            }
            println()
        }
        println()
    }
    
    
    fun id(x: Int, y: Int) = x*len[1] + y
    
    fun find(id: Int): Int {
        var k = id
        while(parent[k] != k) {
            k = parent[k]
        }
        parent[id] = k
        return k
    }
    
    fun merge(a: Int, b: Int): Boolean {
        val pa = find(a)
        val pb = find(b)
        if(pa == pb) return false
        if(pa > pb) {
            parent[pa] = pb;
        } else {
            parent[pb] = pa;
        }
        return true
    }
    
    var bfs = LinkedList<Pair<Int, Int>>()
    
    for(x in 0 until len[0]) {
        for(y in 0 until len[1]) {
            if(map[x][y] == 'X') {
                continue
            }
            bfs.add(x to y)
            if(map[x][y] == 'L') {
                swans.add(id(x,y))
            }
        }
    }
    
    var time = 0
    val visit = Array(len[0]) {BooleanArray(len[1])}
    do {
        for(pair in bfs) {
            val (x,y) = pair
            map[x][y] = '.'
            for(o in OFFSETS) {
                val newLoc = arrayOf(x,y)
                newLoc[o.first] += o.second
                if(newLoc[o.first] !in 0 until len[o.first]) continue
                val (nx, ny) = newLoc
                if(map[nx][ny] != 'X') merge(id(x,y), id(nx,ny))
            }
        }
        val ice = LinkedList<Pair<Int, Int>>()
        while(bfs.isNotEmpty()) {
            val (x, y) = bfs.removeAt(0)
            for(o in OFFSETS) {
                val newLoc = arrayOf(x,y)
                newLoc[o.first] += o.second
                if(newLoc[o.first] !in 0 until len[o.first]) continue
                val (nx, ny) = newLoc
                if(visit[nx][ny]) continue
                visit[nx][ny] = true
                if(map[nx][ny] == 'X') {
                    ice.add(nx to ny)
                    continue
                }
                merge(id(nx, ny), id(x,y))
            }
        }
        if(find(swans[0]) == find(swans[1])) {
            println(time)
            return
        }
        bfs = ice
        time++
        
    } while(ice.isNotEmpty())
    println(time)
}
