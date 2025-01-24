/**
 * 1017번: 소수 쌍
 * - 수열은 중복 없이 주어진다
 * - N <= 50
 *
 * valid한 쌍들을 모두 구하고, 이분매칭
 */

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val a = readLine().split(" ").map { it.toInt() }
    // find primes
    val isPrime = Array(2001) { true }
    for (base in 2..1000) for (mul in 2..(isPrime.size - 1) / base)
        isPrime[mul * base] = false
    // find valid pairs
    val valid = Array(n) { Array(n) { true } }
    for (i in 0 until n) for (j in i + 1 until n)
        valid[i][j] = isPrime[a[i] + a[j]].also { valid[j][i] = it }
    for (i in 0 until n) valid[i][i] = false
    fun simulate(t: Int): Boolean {
        val assign = Array(n) { -1 }
        val done = Array(n) { false }
        fun dfs(left: Int): Boolean {
            for(right in 1 until n) {
                if(done[right] || !valid[left][right]) continue
                done[right] = true
                if(assign[right] == -1|| dfs(assign[right])) {
                    assign[right] = left
                    return true
                }
            }
            return false
        }
        assign[t] = 0
        for(i in 1 until n) {
            done[t] = true
            done[0] = true
            dfs(i)
            done.fill(false)
        }
        assign[0] = t
        return assign.count { it != -1 } == n
    }
    val ans = (1 until n).filter { valid[0][it] }.filter { simulate(it) }
    if (ans.isEmpty()) {
        println(-1)
    } else {
        println(ans.map { a[it] }.sorted().joinToString(" "))
    }

}
