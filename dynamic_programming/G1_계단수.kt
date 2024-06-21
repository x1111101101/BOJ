/**
1562번: 계단수
비트마스킹 + DP로 간단히 해결
*/

val MOD = 1_000_000_000

val cache = Array(101) {Array(10){Array(1024){-1} } }
fun dp(len: Int, start: Int, visit: Int): Int {
    if(start !in 0..9 || len <= 0) return 0
    if(len == 1 && (1).shl(start) == visit) {
        return 1
    }
    if(cache[len][start][visit] != -1) return cache[len][start][visit]
    if((1).shl(start).and(visit) == 0) return 0
    val v = visit.xor((1).shl(start))
    var r = (dp(len-1, start-1, visit) + dp(len-1, start+1, visit))%MOD + (dp(len-1, start-1, v) + dp(len-1, start+1, v))%MOD
    r %= MOD
    cache[len][start][visit] = r
    return r
}

fun solve(len: Int): Int {
    var gv = 1
    for(i in 1..9) {
        gv = gv.or((1).shl(i))
    }
    var ans = 0
    for(start in 1..9) {
        ans += dp(len, start, gv)
        ans %= MOD
    }
    return ans
}

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val ans = solve(n)
    println(ans)
}
