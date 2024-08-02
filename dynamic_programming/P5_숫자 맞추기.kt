import java.util.StringJoiner
import kotlin.math.min

/**
2494번: 숫자 맞추기

*/

fun dist(current: Int, target: Int): Int {
    if (current - target >= 0) {
        return current - target
    } else {
        return current + 10 - target
    }
}

fun ldist(current: Int, target: Int): Int {
    if(current <= target) return target-current
    return 10 - current + target
}

const val EMPTY = -1000

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val cur = readLine().map { it.toString().toInt() }
    val target = readLine().map { it.toString().toInt() }
    val memo = Array(n) { Array(10) { EMPTY } }
    val opmemo = Array(n) { Array(10) { Array(3) {EMPTY} } } // i, o, rotation

    fun dp(i: Int, o: Int): Int {
        if(i == n) return 0
        if(memo[i][o] != EMPTY) {
            return memo[i][o]
        }
        // 오른쪽으로 돌릴 경우
        val current = (cur[i] + o) % 10
        val dist = dist(current, target[i])
        var result = dp(i+1, o) + dist
        opmemo[i][o].also {
            it[0] = i+1
            it[1] = o
            it[2] = -dist
        }

        // 왼쪽으로 돌릴 경우
        val ldist = ldist(current, target[i])
        val no = (o+ldist) % 10
        val temp = dp(i+1, no) + ldist
        if(temp < result) {
            result = temp
            opmemo[i][o].also {
                it[0] = i+1
                it[1] = no
                it[2] = ldist
            }
        }
        memo[i][o] = result
        return result
    }
    val ans = dp(0, 0)
    val sj = StringJoiner("\n")
    val last = arrayOf(0,0)
    repeat(n) {
        val op = opmemo[last[0]][last[1]]
        sj.add("${it+1} ${op[2]}")
        last[0] = op[0]
        last[1] = op[1]
    }
    println(ans)
    println(sj)
}
