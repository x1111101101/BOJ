import java.util.*;
import java.lang.Math.*
import java.io.*
import kotlin.collections.*

/**
9465번: 스티커
*/

fun max(a: Int, b: Int, c: Int) = max(max(a,b),c)

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val bw = System.`out`.bufferedWriter()
    repeat(readLine().toInt()) {
        val n = readLine().toInt()
        val map = Array<Array<Int>>(2) { readLine().split(" ").map {it.toInt()}.toTypedArray() }
        val dp = Array<IntArray>(n+1){IntArray(3)} // 0: 선택X, 1: 윗쪽 선택, 2: 아랫쪽 선택
        for(i in 1..n) {
            val a = map[0][i-1]
            val b = map[1][i-1]
            val l = dp[i-1]
            dp[i][0] = max(l[0], l[1], l[2])
            dp[i][1] = max(l[0], l[2]) + a
            dp[i][2] = max(l[0], l[1]) + b
        }
        val l = dp[n]
        val ans = max(l[0], l[1], l[2])
        bw.write("$ans\n")
    }
    bw.close()
}
