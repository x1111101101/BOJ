/**
1126번: 같은 탑
*/
import kotlin.math.max

fun main() = with(System.`in`.bufferedReader()){
    val n = readLine().toInt()
    val height = readLine().split(" ").map { it.toInt() }

    val inf = -1000000
    val empty = inf*100
    val memo = Array(50) { Array(500001) {empty} }

    fun dp(i: Int, g: Int): Int {
        if(i == 0) {
            if(height[i] == g) return height[i]
            if(g == 0) return 0
            return inf
        }
        if(memo[i][g] != empty) return memo[i][g]
        val h = height[i]
        var ans = dp(i-1, g)
        ans = max(ans, dp(i-1, g+h))
        if(h <= g) {
            ans = max(ans, dp(i-1, g-h) + h)
        } else {
            ans = max(ans, dp(i-1, h-g) + g)
        }
        if(g == 0) {
            ans = max(0, ans)
        }
        memo[i][g] = max(inf, ans)
        return ans
    }

    val ans = dp(n - 1, 0)
    println(if(ans <= 0) -1 else ans)
}
