/**
3649번: 로봇 프로젝트
간단히 O(N)으로 풀 수 있는 문제
해시맵을 이용해 해결
*/

import kotlin.math.abs

fun main() {
    val br = System.`in`.bufferedReader()
    val bw = System.`out`.bufferedWriter()
    var x: Int? = br.readLine()?.toInt()
    while(x != null) {
        x *= 10000000
        val n = br.readLine().toInt()
        val a = Array(n) { br.readLine().toInt() }
        val map = HashMap<Int, Int>()
        for(len in a) {
            if(len < x) map[len] = (map[len] ?: 0) + 1
        }
        var ansGap = -1
        var ansPair: Pair<Int, Int>? = null
        for(len in a) {
            val f = x-len
            val amount = map[f] ?: continue
            if(f == len && amount < 2) continue
            val gap = abs(f-len)
            if(gap < ansGap) continue
            ansGap = gap
            ansPair = if(len < f) len to f else f to len
        }
        if(ansPair == null) {
            bw.write("danger\n")
        } else {
            bw.write("yes ${ansPair.first} ${ansPair.second}\n")
        }
        x = br.readLine()?.toInt()
    }
    bw.close()
}
