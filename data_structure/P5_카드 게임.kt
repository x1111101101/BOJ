import java.util.*
import kotlin.math.sqrt

/**
16566번: 카드 게임
Sqrt Decomposition을 사용해서 해결
정석 풀이는 union-find와 관련된 듯
*/
fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, m, k) = readLine().split(" ").map { it.toInt() }
    val nums = readLine().split(" ").map { it.toInt() }
    val kcards = readLine().split(" ").map { it.toInt() }
    val scale = sqrt(n.toDouble()).toInt()
    val table = Array((n + n - 1) / scale) { PriorityQueue<Int>() }
    nums.forEach { num->
        table[num/scale].add(num)
    }
    val bw = System.out.bufferedWriter()
    loop@ for(c in kcards)  {
        val ts = c/scale
        for(i in ts until table.size) {
            val removeAtLeast = table[i].removeAtLeast(c+1)
            if(removeAtLeast == -1) continue
            bw.write("${removeAtLeast}\n")
            continue@loop
        }
        for(i in 0 until table.size) {
            if(table[i].isNotEmpty()) {
                bw.write("${table[i].poll()}\n")
                break
            }
        }
    }
    bw.close()
}

fun PriorityQueue<Int>.removeAtLeast(i: Int): Int {
    val toAdd = ArrayList<Int>(size/2 + 10)
    var result = -1
    while(isNotEmpty() && peek() < i) toAdd.add(poll())
    if(isNotEmpty()) {
        result = poll()
    }
    addAll(toAdd)
    return result
}
