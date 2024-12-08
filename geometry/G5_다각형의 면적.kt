import kotlin.math.absoluteValue

/**
2166번: 다각형의 면적
오목 다각형일 수도 있어서 외적 값들을 모두 더하고 마지막에 절댓값을 취함.
소숫점 첫째 자리는 0 또는 5인 경우만 존재해서 나머지 연산을 통해 처리.
*/
data class Point(val x: Long, val y: Long)

fun ccw(p1: Point, p2: Point, p3: Point): Long {
    return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x)
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val points = Array(n) {
        readLine().split(" ").map { it.toLong() }.let { Point(it[0], it[1]) }
    }
    var ans = 0L
    for(secIdx in 1 until n-1) {
        val sec = points[secIdx]
        val thr = points[secIdx+1]
        val ccw = ccw(points[0], sec, thr)
        ans += ccw
    }
    ans = ans.absoluteValue
    val suffix = if(ans%2L == 0L) ".0" else ".5"
    println("${ans/2}${suffix}")
}
