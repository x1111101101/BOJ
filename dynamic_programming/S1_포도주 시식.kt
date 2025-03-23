/**
2156번: 포도주 시식
*/
fun main(): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val value = Array(n) { readLine().toInt() }
    val memo = Array(n) { Array(3) { -1 } }
    val combos = 0..2
    fun dp(i: Int, combo: Int): Int {
        if(i < 0) return 0
        if(memo[i][combo] != -1) return memo[i][combo]
        val ans =  when(combo) {
            0-> combos.maxOf { dp(i-1, it) }
            1-> dp(i-1, 0) + value[i]
            else-> dp(i-1, 1) + value[i]
        }
        memo[i][combo] = ans
        return ans
    }
    println(combos.maxOf { dp(n-1, it) })
}
