import kotlin.math.min

/**
13392번: 방법을 출력하지 않는 숫자 맞추기
*/

fun dist(current: Int, target: Int): Int {
    if (current - target >= 0) {
        return current - target
    } else {
        return current + 10 - target
    }
}

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val cur = readLine().map { it.toString().toInt() }
    val target = readLine().map { it.toString().toInt() }
    val memo = Array(n) { Array(10) { -1 } }

    fun dp(i: Int, o: Int): Int {
        if(i == n) return 0
        if(memo[i][o] != -1) {
            return memo[i][o]
        }
        // 오른쪽으로 돌릴 경우
        val current = (cur[i] + o) % 10
        val dist = dist(current, target[i])
        var result = dp(i+1, o) + dist
        // 왼쪽으로 돌릴 경우
        for(t in 1..9) {
            val no = (o+t)%10
            val d = dist((cur[i] + no) % 10, target[i])
            result = min(result, dp(i+1, no) + d + t)
        }
        memo[i][o] = result
        return result
    }
    println(dp(0, 0))

}
