import Counter.decrease
import Counter.increase
import java.util.*
import kotlin.math.sqrt

/**
 * 14897번: 서로 다른 수와 쿼리 1
 * - 특정 구간 내 서로 다른 수의 개수를 구하는 문제.
 * - 도수(frequency)가 0이 아닌 수의 개수를 구하면 됨.
 * - 배열의 크기에 비해 원소가 취할 수 있는 값의 범위가 넓음.
 *   값 대신 정렬되었을 때의 인덱스를 사용.
 */

val SCALE = 1000_001
var sqrt = 1

class Query(val start: Int, val end: Int) : Comparable<Query> {
    var result = 0
    val chunk = start / sqrt
    override fun compareTo(other: Query): Int {
        val q1 = chunk
        val q2 = other.chunk
        if (q1 != q2) return q1 - q2
        return end - other.end
    }

    override fun toString(): String {
        return "$chunk $end"
    }
}

val QUERY_NIL = Query(-1, -1)

val frequency = IntArray(SCALE) { 0 }
val setSize = IntArray(SCALE) { 0 }

object Counter {
    private var unique = 0


    fun segmentUnique(): Int {
        return unique - setSize[0]
    }

    fun increase(number: Int) {
        frequency[number]++
        if (frequency[number] > 0) {
            setSize[frequency[number]]++
            setSize[frequency[number] - 1]--
        }
    }

    fun decrease(number: Int) {
        frequency[number]--
        if (frequency[number] >= 0) {
            setSize[frequency[number]]++
            setSize[frequency[number] + 1]--
        }
    }

    fun init(unique: Int) {
        setSize[0] = unique
        this.unique = unique
    }
}

val idxMap = HashMap<Int, Int>(SCALE * 3)
val a = IntArray(SCALE)

val q = Array<Query>(SCALE) { QUERY_NIL }
val qs = Array<Query>(SCALE) { QUERY_NIL }

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()

    StringTokenizer(readLine()).also { tokenizer ->
        repeat(n) { i ->
            a[i] = tokenizer.nextToken().toInt()
        }
    }

    // scale down
    val pq = PriorityQueue<Int>(SCALE)
    for (i in 0 until n) {
        if (idxMap.containsKey(a[i])) continue
        idxMap[a[i]] = -1
        pq.add(a[i])
    }
    while (pq.isNotEmpty()) {
        val num = pq.poll()
        idxMap[num] = n - pq.size
    }
    for (i in 0 until n) a[i] = idxMap[a[i]]!!
    Counter.init(idxMap.size)

    // sort queries
    sqrt = sqrt(n.toDouble() + 1).toInt()
    val queries = readLine().toInt()
    repeat(queries) { i ->
        q[i] = readLine().split(" ")
            .map { it.toInt() - 1 }
            .let { Query(it[0], it[1]) }
        qs[i] = q[i]
    }
    Arrays.sort(qs, 0, queries)

    // first query
    val first = qs.first()
    for (i in first.start..first.end) {
        increase(a[i])
    }
    first.result = Counter.segmentUnique()
    var left = first.start
    var right = first.end


    // other queries
    for (i in 1 until queries) {
        val query = qs[i]
        while (left > query.start) increase(a[--left])
        while (right < query.end) increase(a[++right])
        while (left < query.start) decrease(a[left++])
        while (right > query.end) decrease(a[right--])
        query.result = Counter.segmentUnique()
    }

    // print answer
    val writer = System.out.buffered(1024 * 50).bufferedWriter()
    repeat(queries) { writer.write("${q[it].result}\n") }
    writer.close()
}
