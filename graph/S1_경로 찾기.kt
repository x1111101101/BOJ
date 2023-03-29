import java.util.*;
import java.lang.Math.*
import java.io.*
import kotlin.collections.*

/**
11403번: 경로 찾기
최대 정점의 개수가 100개 밖에 안되서 플로이드 워셜로 해결
*/
fun main(args: Array<String>) = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val g = Array<Array<Int>>(n) { readLine().split(" ").map {it.toInt()}.toTypedArray() }
    val bw = System.`out`.bufferedWriter()
    for(m in 0 until n) {
        for(a in 0 until n) {
            for(b in 0 until n) {
                if(g[a][m] + g[m][b] < 2) continue
                g[a][b] = 1
            }
        }
    }
    for(a in 0 until n) {
        for(b in 0 until n) {
            bw.write(g[a][b].toString())
            bw.write(" ")
        }
        bw.write("\n")
    }
    bw.close()
}
