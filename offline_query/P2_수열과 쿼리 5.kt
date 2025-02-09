import kotlin.math.sqrt
/**
13547번: 수열과 쿼리 5
mo's algorithm을 처음 활용해본 문제다.
*/

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val sqrt = sqrt(n.toDouble() + 1).toInt()

    data class Query(val id: Int, val start: Int, val end: Int) : Comparable<Query> {
        override fun compareTo(other: Query): Int {
            if (start / sqrt == other.start / sqrt) return end - other.end
            return start / sqrt - other.start / sqrt
        }
    }

    val a = readLine().split(" ").map { it.toInt() }
    val m = readLine().toInt()
    val count = Array(1000_001) { 0 }
    val queries = Array(m) {
        val q = readLine().split(" ").map { it.toInt() - 1 }
        Query(it, q[0], q[1])
    }.also { it.sort() }
    var sum = 0
    fun increase(v: Int) {
        if(count[v]++ == 0) sum++
    }
    fun decrease(v: Int) {
        if(count[v]-- == 1) sum--
    }
    for (i in queries[0].start..queries[0].end) {
        increase(a[i])
    }
    val result = Array(m) { -1 }
    result[queries[0].id] = sum
    var left = queries[0].start
    var right = queries[0].end
    for (i in 1 until queries.size) {
        val query = queries[i]
        while (query.start < left) increase(a[--left])
        while (query.end > right) increase(a[++right])
        while (query.start > left) decrease(a[left++])
        while (query.end < right) decrease(a[right--])
        result[query.id] = sum
    }
    println(result.joinToString("\n"))
}
