import kotlin.math.min
/**
1806번: 부분합
*/
fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, s) = readLine().split(" ").map { it.toInt() }
    val a = readLine().split(" ").map { it.toInt() }
    val acc = Array(n) { a[it] }
    for (i in 1 until n) acc[i] += acc[i - 1]
    fun lowerbound(v: Int, start: Int = 0): Int {
        var left = start
        var right = n - 1
        var result = -1
        while (left <= right) {
            val mid = (left + right) / 2
            if (acc[mid] < v) {
                left = mid + 1
            } else {
                right = mid - 1
                result = mid
            }
        }
        return result
    }

    val inf = Int.MAX_VALUE
    var ans = inf
    for (left in 0 until n) {
        val min = acc[left] + s - a[left]
        val right = lowerbound(min, left)
        if (right == -1) continue
        ans = min(ans, right - left + 1)
    }
    println(
        if(ans == inf) 0 else ans
    )
}
