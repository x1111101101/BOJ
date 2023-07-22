import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

/**
14942번: 개미

트리 구조에서 각 노드에서 루트 노드까지 일정 거리 이하만큼만 이동했을 때 각 노드가 어떤 노드에 도달하는지 묻는 문제다. 이동 가능한 거리는 노드별로 다르게 설정된다.
희소 배열을 활용해 어렵지 않게 풀었다.

- 노드 정보를 입력받아 HashMap으로 연결시킨 후, 뿌리 노드로부터 dfs로 부모를 할당.
- 1부터 2씩 곱한 만큼 부모 방향으로 이동했을 때의 도착 노드와 누적된 거리를 sparse table에 저장.
- 재귀 함수를 이용해 sparse table의 마지막 쪽 값들을 우선적으로 조회하며 도착 노드를 계산.
*/

class SparseData(val target: Int, val energy: Int)

class Edge(val target: Ant, val distance: Int)

class Ant(val id: Int, val energy: Int): HashMap<Int, Int>() {
    var parent = this
    
    fun link(id: Int, distance: Int) {
        this[id] = distance
    }
}

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val ants = Array(n) {Ant(it, readLine().toInt())}
    repeat(n-1) {
        var (a,b,c) = readLine().split(" ").map {it.toInt()}
        a--; b--;
        ants[a].link(b, c)
        ants[b].link(a, c)
    }
    fun findParent(a: Ant) {
        for(entry in a) {
            val target = ants[entry.key]
            if(target == a.parent) continue
            target.parent = a
            findParent(target)
        }
    }
    ants[0].link(0, -1)
    findParent(ants[0])
    val sparse = Array(n) {ArrayList<SparseData>(20)}
    for(i in 1 until n) {
        val energy = ants[i][ants[i].parent.id]!!
        sparse[i].add(SparseData(ants[i].parent.id, energy))
    }
    for(depth in 1..17) {
        for(i in 0 until n) {
            if(sparse[i].size < depth) continue
            val k = sparse[i][depth-1]
            if(k.energy == -1 || sparse[k.target].size < depth) continue
            val composit = sparse[k.target][depth-1]
            sparse[i].add(SparseData(composit.target, composit.energy + k.energy))
        }
    }
    fun find(id: Int, energy: Int): Int {
        for(k in 1..sparse[id].size) {
            val i = sparse[id].size-k
            val sp = sparse[id][i]
            if(sp.energy <= energy) return find(sp.target, energy - sp.energy)
        }
        return id
    }
    
    System.`out`.bufferedWriter().use { w->
        w.write("1\n")
        for(i in 1 until n) {
            w.write("${find(i, ants[i].energy)+1}\n")
        }
    }
}
