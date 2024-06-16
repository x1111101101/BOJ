/**
1516번: 게임 개발
1년 전에 여러번 시도해도 맞추지 못했던 문제다.
subproblem들로 나누는 것에 주목해서 쉽게 해결했다.
top-down 방식에 좀 더 익숙해져간다.
*/
fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val time = Array(n) {0}
    val req = Array(n) {ArrayList<Int>(n-1)}
    repeat(n) {
        val tokens = readLine().split(" ").let { it.subList(0, it.lastIndex) }.map { it.toInt() }
        time[it] = tokens[0]
        for(i in 1 until tokens.size) req[it].add(tokens[i]-1)
    }
    val memo = Array(n) {-1}
    fun dp(id: Int): Int {
        if(memo[id] != -1) return memo[id]
        val r = (req[id].map { dp(it) }.maxOrNull() ?: 0) + time[id]
        memo[id] = r
        return r
    }
    println(
        (0 until n).map { dp(it) }.joinToString("\n")
    )
}
