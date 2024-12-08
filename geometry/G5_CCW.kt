import kotlin.math.absoluteValue

data class Point(val x: Long, val y: Long)

fun ccw(p1: Point, p2: Point, p3: Point): Long {
    return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x)
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val n = 3
    val points = Array(n) {
        readLine().split(" ").map { it.toLong() }.let { Point(it[0], it[1]) }
    }
    val ccw = ccw(points[0], points[1], points[2])
    val ans = if (ccw == 0L) 0L else ccw / ccw.absoluteValue
    println(ans)
}
