import java.util.*
import kotlin.math.sqrt

/**
8462번: 배열의 힘
Mo's algorithm을 활용해 해결
Arrays.sort를 사용하지 않고 sorted() 메서드를 사용했더니 메모리 초과가 발생했었다.
*/
fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, t) = readLine().split(" ").map { it.toInt() }
    val sqrt = sqrt(n + 1.0).toInt()
    val count = IntArray(1000_001) { 0 }
    val result = LongArray(t) { 0L }
    var ans = 0L
    val a = IntArray(n)
    StringTokenizer(readLine()).let { st->
        repeat(n) { a[it] = st.nextToken().toInt() }
    }

    data class Query(val id: Int, val start: Int, val end: Int) : Comparable<Query> {
        override fun compareTo(other: Query): Int {
            val cs = start / sqrt - other.start / sqrt
            return if (cs != 0) cs else end - other.end
        }
    }

    val q = Array(t) { idx->
        readLine().split(" ").map { it.toInt() - 1 }
            .let { Query(idx, it[0], it[1]) }
    }
    Arrays.sort(q)

    fun power(value: Int): Long = count[value].toLong() * count[value] * value

    fun increase(value: Int) {
        val prev = power(value)
        count[value]++
        val new = power(value)
        ans += new - prev
    }

    fun decrease(value: Int) {
        val prev = power(value)
        count[value]--
        val new = power(value)
        ans += new - prev
    }
    
    for(i in q[0].start..q[0].end) {
        increase(a[i])
    }
    result[q[0].id] = ans
    var (left, right) = q[0].run { listOf(start, end) }
    for(i in 1 until q.size) {
        val query = q[i]
        while(query.start > left) decrease(a[left++])
        while (query.end < right) decrease(a[right--])
        while (query.start < left) increase(a[--left])
        while (query.end > right) increase(a[++right])
        result[query.id] = ans
    }
    val bw = System.out.bufferedWriter()
    bw.use {
        result.forEach { bw.write("${it}\n") }
    }
}
