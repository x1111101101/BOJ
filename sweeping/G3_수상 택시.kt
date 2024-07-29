import kotlin.math.max
import kotlin.math.min
/**
2836번: 수상택시
*/

data class Boundary(val start: Int, val end: Int): Comparable<Boundary> {
    fun merge(other: Boundary): Boundary? {
        if (other.start !in start..end && other.end !in start..end) return null
        return Boundary(min(start, other.start), max(end, other.end))
    }
    fun reversed() = Boundary(end, start)
    override fun compareTo(other: Boundary): Int = start.compareTo(other.start)
}

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val (n, m) = readLine().split(" ").map { it.toInt() }
    val passengers = Array(n) {
        readLine()
            .split(" ")
            .map { it.toInt() }
            .let { Boundary(it[0], it[1]).reversed() }
        }
        .filter { it.end > it.start }
        .sorted()
    val boundaries = ArrayList<Boundary>()
    boundaries.add(passengers[0])
    for (i in 1 until passengers.size) {
        val cur = passengers[i]
        val last = boundaries.last()
        val merged = last.merge(cur)
        if (merged == null) {
            boundaries.add(cur)
        } else {
            boundaries[boundaries.lastIndex] = merged
        }
    }
    val sum: Long = m + boundaries.sumOf { (it.end - it.start).toLong() } * 2L
    println(sum)
}
