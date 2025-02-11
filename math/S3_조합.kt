import java.math.BigDecimal
/**
2407번: 조합
*/
fun comb(n: Int, m: Int): BigDecimal {
    val none = BigDecimal(-1)
    val memo = Array(m+1) { none }
    fun dp(c: Int): BigDecimal {
        if(c == 1) return BigDecimal(n)
        if(memo[c] != none) return memo[c]
        memo[c] = dp(c-1) * BigDecimal(n - c + 1) / BigDecimal(c)
        return memo[c]
    }
    return dp(m)
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, m) = readLine().split(" ").map { it.toInt() }
    println(comb(n, m).toPlainString())
}
