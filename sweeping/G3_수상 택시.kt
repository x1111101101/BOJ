import kotlin.math.max
import kotlin.math.min
/**
2836번: 수상택시
*/

data class Node(val loc: Int, val id: Int) : Comparable<Node> {
    override fun compareTo(other: Node): Int {
        return loc.compareTo(other.loc)
    }
}

data class Boundary(val start: Int, val end: Int) {
    fun merge(other: Boundary): Boundary? {
        if (other.start !in start..end && other.end !in start..end) return null
        return Boundary(min(start, other.start), max(end, other.end))
    }

    fun reversed() = Boundary(end, start)
}

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val (n, m) = readLine().split(" ").map { it.toInt() }
    val passengers = Array(n) {
        readLine()
            .split(" ")
            .map { it.toInt() }
            .let { Boundary(it[0], it[1]).reversed() }
    }.filter { it.end > it.start }
    if (passengers.isEmpty()) {
        println(m)
        return@with
    }
    var nodes = passengers.mapIndexed { index, it -> Node(it.start, index) }
        .sorted()
    val boundaries = ArrayList<Boundary>()
    boundaries.add(passengers[nodes[0].id])
    for (i in 1 until nodes.size) {
        val cur = nodes[i]
        val last = boundaries.last()
        val merged = last.merge(passengers[cur.id])
        if (merged == null) {
            boundaries.add(passengers[cur.id])
        } else {
            boundaries[boundaries.lastIndex] = merged
        }
    }
    val sum: Long = m + boundaries.sumOf { (it.end - it.start).toLong() } * 2L
    println(sum)
}
