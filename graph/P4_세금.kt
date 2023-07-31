import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

/**
13907번: 세금
최적화 방법에 따라 시간 초과 여부가 갈리는 문제다.
Node 클래스의 dist 변수로 우선순위 큐를 사용했을 때 시간초과가 발생했다.
문제 특성상 삽입과 제거를 할 일이 많은 탓이었다. 배열 구현해서 기존의 제거 연산을 top 변수 값의 조정으로 대체했다.
교훈적인 문제였다.

경로에서 고려할 요소
- 도로의 수
- 거리

기존 경로 = A
새 경로 = B 일때,
B의 거리가 A의 거리 이상이더라도 B의 도로 수가 A의 도로 수보다 적으면 탐색해볼 가치가 있다

도로 개수 * 오른 세금 + 거리

경로 추가가 안될 조건
- 도로수와 거리 모두 도로가 가장 많은 기존 경로에 비해 큼

*/
val INF = 10000000
data class Arrival(val dist: Int, val roads: Int): Comparable<Arrival> {
     override fun compareTo(o: Arrival) = o.roads.compareTo(roads)   
}
data class Edge(val target: Node, val dist: Int, val roads: Int): Comparable<Edge> {
    
    override fun compareTo(o: Edge) = o.dist.compareTo(dist)    
}
class Node(val id: Int) {
    override fun hashCode() = id.hashCode()
    val linked = HashMap<Node, Int>()
    var dist = Array(1000) {INF}
    var top = -1
    
    
    fun addPath(e: Arrival): Boolean {
        if(top == -1) {
            top = e.roads
            dist[top] = e.dist
            return true
        }
        if(e.roads >= top && e.dist >= dist[top]) return false
        if(dist[e.roads] <= e.dist) return false
        dist[e.roads] = e.dist
        if(e.roads > top) {
            top = e.roads
        } else {
            for(i in top downTo e.roads) {
                if(dist[i] <= e.dist) {
                    top = i
                    break
                }
            }
        }
        return true
    }
    
    fun link(t: Node, w: Int) {
        var k = linked[t]
        if(k == null || k > w) {
            linked[t] = w
        }
    }
}

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val (n,m,k) = readLine().split(" ").map {it.toInt()}
    val (s,d) = readLine().split(" ").map {it.toInt()-1}
    val nodes = Array(n) {Node(it)}
    repeat(m) {
        var (a,b,w) = readLine().split(" ").map {it.toInt()}
        a--; b--;
        nodes[a].link(nodes[b], w)
        nodes[b].link(nodes[a], w)
    }
    nodes[s].addPath(Arrival(0,0))
    val bfs = PriorityQueue<Edge>()
    bfs.add(Edge(nodes[s], 0, 0))
    do {
        val edge = bfs.poll()
        val node = edge.target
        if(node.dist[edge.roads] < edge.dist) continue
        for(e in node.linked) {
            val (t,dist) = e
            val newDist = dist + edge.dist
            val newRoad = edge.roads + 1
            val arrival = Arrival(newDist, newRoad)
            if(!t.addPath(arrival)) continue
            bfs.add(Edge(t, newDist, newRoad))
        }
    } while(bfs.isNotEmpty())
    val node = nodes[d]
    var inc = 0
    val incs = ArrayList<Int>(k+1)
    incs.add(0)
    repeat(k) {incs.add(readLine().toInt())}
    System.`out`.bufferedWriter().use {w->
    repeat(k+1) {
        inc += incs[it]
        var ans = INF
        for(roads in 1 until n) {
            ans = min(ans, roads*inc + node.dist[roads])
        }
        w.write("${ans}\n")
    }
    } 
}
