import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
3015번: 오아시스 재결합
같은 키에 대해서만 잘 고려해주면 쉽게 푸는 문제
*/
fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    var ans = 0L
    val st = ArrayList<Int>()
    val hmap = HashMap<Int, Int>()
    repeat(readLine().toInt()) {
        val h = readLine().toInt()
        val prev = hmap[h] ?: 0
        hmap[h] = prev + 1
        var d = 0
        if(st.isNotEmpty() && st.last() < h) {
            do {
                val rem = st.removeLast()
                hmap[rem] = hmap[rem]!! - 1
                d++
            } while (st.isNotEmpty() && st.last() < h)
        }
        d += prev
        if(st.isNotEmpty() && st.first() > h) {
            d += 1
        }
        ans += d
        st.add(h)
    }
    println(ans)
}
