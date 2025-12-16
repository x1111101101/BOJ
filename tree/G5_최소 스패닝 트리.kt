/**
1197번: 최소 스패닝 트리
프림 알고리즘으로 해결
*/
import java.util.*

fun main() {
    val (v, e) = readln().split(" ").map { it.toInt() }
    val edges = Array(e) { readln().split(" ").map { it.toInt() } }
    val edgeMap = Array(v + 1) { ArrayList<List<Int>>(400) }
    edges.forEach { edgeMap[it[0]] += it; edgeMap[it[1]] += it }
    edgeMap.forEach { it.sortBy { it[2] } }
    val included = Array(v + 1) { false }
    included[1] = true
    var includedCount = 1
    var length = 0
    val pq = PriorityQueue<List<Int>>(e, compareBy { it[2] })
    edgeMap[1].forEach { pq += it }
    edgeMap[1].clear()
    while (includedCount < v) {
        val edge = pq.poll()
        val (a, b, weight) = edge
        if (included[a] and included[b]) continue
        included[a] = true
        included[b] = true
        length += weight
        edgeMap[a].forEach { pq += it }
        edgeMap[b].forEach { pq += it }
        edgeMap[a].clear()
        edgeMap[b].clear()
        includedCount++
    }
    println(length)
}
