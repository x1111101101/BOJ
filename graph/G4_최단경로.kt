import java.util.*;

/**
1753번: 최단경로
코틀린으로 푼 첫 문제. 다익스트라로 해결
*/

val INF = 2000000

fun main(args: Array<String>) = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine())
    val v = st.nextToken().toInt()
    val e = st.nextToken().toInt()
    val start = readLine().toInt() - 1
    val edges = Array<HashMap<Int,Int>>(v) {HashMap()}
    loop@ for(a in 1..e) {
        st = StringTokenizer(readLine())
        val from = st.nextToken().toInt() - 1
        val to = st.nextToken().toInt() - 1
        val dist = st.nextToken().toInt()
        val prev = edges[from][to]
        if(prev == null || prev > dist) edges[from][to] = dist
    }
    val min = IntArray(v) {INF}
    val q = PriorityQueue<Edge>()
    min[start] = 0
    q.add(Edge(start, 0))
    while(q.isNotEmpty()) {
        val edge = q.poll()
        if(edge.dist > min[edge.to]) continue
        for(p in edges[edge.to].entries) {
            val to = p.key
            val dist = p.value
            val newdist = edge.dist + dist
            if(min[to] <= newdist) continue
            min[to] = newdist
            q.add(Edge(to, newdist))
        }
    }
    
    with(System.`out`.bufferedWriter()) {
        for(i in 0 until v) {
            val dist = min[i]
            if(dist == INF) {
                write("INF")
            } else {
                write("${dist}")
            }
            write("\n")
        }
        close()
    }
    close()
    
}

class Edge(val to: Int, var dist: Int): Comparable<Edge> {
    override fun compareTo(that: Edge): Int {
        if(that.dist > dist) return -1
        if(that.dist == dist) return 0
        return 1
    }
}
