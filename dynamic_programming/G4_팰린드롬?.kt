/**
10942번: 팰린드롬
*/
fun main(): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val a = readLine().split(" ").map { it.toInt() }
    val memo = Array(n) { Array(n) { -1 } }
    for (i in 0 until n) memo[i][i] = 1
    fun dp(s: Int, e: Int): Int {
        if (s > e) return 0
        if (memo[s][e] != -1) return memo[s][e]
        fun calc(): Int {
            if (a[s] != a[e]) return 0
            if ((s - e + 1) % 2 == 1) { // 홀수 길이
                return dp(s + 1, e - 1)
            }
            if (e - s == 1) return 1
            return dp(s + 1, e - 1)
        }
        val ret = calc()
        memo[s][e] = ret
        return ret
    }
    val ans = Array(readLine().toInt()) {
        val (s, e) = readLine().split(" ").map { it.toInt() - 1 }
        dp(s, e)
    }.joinToString("\n")
    println(ans)
}
