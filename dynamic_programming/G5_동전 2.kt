import kotlin.math.min

/**
2294번: 동전 2
*/

val INF = 10001

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    var (n, k) = readLine().split(" ").map { it.toInt() }
    val v = Array(n) {readLine().toInt()}.filter { it <= k }
    n = v.size
    val cache = Array(n) {Array(k+1){-1} }
    for(t in v.indices) {
        cache[t][v[t]] = 1
    }
    fun dp(i: Int, t: Int): Int {
        if(i < 0) return INF
        if(t <= 0) return INF
        if(cache[i][t] != -1) {
            return cache[i][t]
        }
        val resultA = dp(i, t-v[i]) + 1
        val resultB = dp(i-1, t)
        val result = min(resultA, resultB)
        cache[i][t] = result
        return result
    }
    val ans = dp(n-1, k)
    println(if(ans >= INF) -1 else ans)
}
