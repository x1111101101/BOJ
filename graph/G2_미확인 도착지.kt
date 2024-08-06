import java.util.*

/**
9370번: 미확인 도착지
목적지들 중 최단 거리 경로상 g-h를 포함하는 목적지들을 찾는 문제
다익스트라 + 비트마스킹으로 해결
 */


data class D(val node: Int, val dist: Int): Comparable<D> {
    override fun compareTo(other: D): Int = dist.compareTo(other.dist)
}


fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val cases = readLine().toInt()
    repeat(cases) {
        val (nodeCount, edgeCount, destCount) = readLine().split(" ").map { it.toInt() }
        val (start, g, h) = readLine().split(" ").map { it.toInt() }
        val distance = Array(nodeCount + 1) { Array(nodeCount + 1) { 0 } }
        val linked = Array(nodeCount + 1) {LinkedList<Int>()}
        repeat(edgeCount) {
            val (a, b, d) = readLine().split(" ").map { it.toInt() }
            distance[a][b] = d; distance[b][a] = d
            linked[a] += b
            linked[b] += a
        }
        val destinations = Array(destCount) { readLine().toInt() }
        fun find() {
            val INF = 100_000_000
            val minDist = Array(nodeCount+1) {INF}
            val mark = Array(nodeCount + 1) { 0 }
            mark[g] = 1
            mark[h] = 2
            minDist[start] = 0
            val bfs = PriorityQueue<D>()
            bfs.add(D(start, 0))
            while(bfs.isNotEmpty()) {
                val (node, dist) = bfs.poll()
                if(minDist[node] < dist) continue
                for (i in linked[node]) {
                    if(distance[node][i] == -1) continue
                    val newDist = distance[node][i] + dist
                    if(minDist[i] != -1 && newDist > minDist[i]) continue
                    val prevMark = mark[i]
                    if(mark[node] != 0) {
                        if(newDist < minDist[i]) {
                            val init = if(i == g) 1 else if(i==h) 2 else 0
                            mark[i] = init.or(mark[node])
                        } else {
                            mark[i] = mark[i].or(mark[node])
                        }
                    } else {
                        if(newDist < minDist[i]) {
                            val init = if(i == g) 1 else if(i==h) 2 else 0
                            mark[i] = init
                        }
                    }
                    if(prevMark != mark[i] || minDist[i] > newDist) {
                        minDist[i] = newDist
                        bfs.add(D(i, minDist[i]))
                    }
                }
            }
            val candidates = destinations
                .filter { mark[it] == 3 }
            println(candidates.sorted().joinToString(" "))
        }
        find()
    }
}
