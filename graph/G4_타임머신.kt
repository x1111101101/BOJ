/**
11657번: 타임머신
밸만포드 알고리즘 활용 음수가중치 반영한 최단거리 찾기, 음수가중치 사이클 확인
*/
class Edge(val from: Int, val to: Int, val cost: Int)
val INF = Long.MAX_VALUE

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, m) = readLine().split(" ").map { it.toInt() }
    val edges = Array(m) { readLine().split(" ").map { it.toInt() }.let { Edge(it[0] - 1, it[1] - 1, it[2]) } }
    val dist = Array(n) { INF }
    dist[0] = 0
    fun findDists(): Boolean {
        var changed = false
        for(i in 1 until n) {
            for(edge in edges) {
                if(dist[edge.from] == INF) continue
                val newCost = edge.cost + dist[edge.from]
                if(newCost >= dist[edge.to]) continue
                dist[edge.to] = newCost
                changed = true
            }
        }
        return changed
    }
    findDists()
    if(findDists()) println(-1)
    else {
        val ans = (1 until dist.size).map { dist[it] }.map { if(it==INF) "-1" else it.toString() }.joinToString("\n")
        println(ans)
    }
}
