import kotlin.math.min
/**
1311번: 할 일 정하기 1
*/
fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val cost = Array(n) {readLine().split(" ").map { it.toInt() }}
    val scale = (1).shl(n)
    val memo = Array(n) {Array(scale) {-1} }
    fun dp(i: Int, jobs: Int): Int {
        if(i == n) return 0
        if(memo[i][jobs] != -1) return memo[i][jobs]
        var result = 100000000
        for(j in 0 until n) {
            val mask = (1).shl(j)
            if(mask.and(jobs) != 0) continue
            val c = cost[i][j]
            result = min(result, dp(i+1, jobs.xor(mask)) + c)
        }
        memo[i][jobs] = result
        return result
    }
    println(dp(0,0))
}
