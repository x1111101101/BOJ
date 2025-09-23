import java.util.*
import kotlin.math.max
import kotlin.math.sqrt

/**
 * 13546번: 수열과 쿼리 4
 * Mo's + 우선순위 큐로 해결
 * O(N * sqrt(N) + M * sqrt(N) * log(N^0.5) )
 * 100000 * 333 * 8 = 266,400,000
 *
 */

val SCALE = 100001
var SQRT = sqrt(SCALE + 1.0).toInt() + 1

class Query(val start: Int, val end: Int) : Comparable<Query> {
    var result = 0
    val chunk = start / SQRT
    override fun compareTo(other: Query): Int {
        val c = chunk - other.chunk
        return if (c != 0) c else end - other.end
    }
}

val QUERY_NIL = Query(-1, -1)

val a = IntArray(SCALE)
val q = Array<Query>(SCALE) { QUERY_NIL }
val sortedQ = Array<Query>(SCALE) { QUERY_NIL }

class State(val number: Int) {
    var log = LinkedList<Int>()

    fun value(): Int {
        if (log.size < 2) return 0
        return log.last - log.first
    }
}


val COUNTER_BKT_LEN = 20000

class Counter(var left: Int) {
    var right: Int = left - 1
    private val answers = Array(SCALE / COUNTER_BKT_LEN) { PriorityQueue<Int>(COUNTER_BKT_LEN) }
    private val bucketMap = Array(SCALE / COUNTER_BKT_LEN) { BooleanArray(SCALE) }
    private val states = Array(SCALE) { State(it) }
    private val groups = Array(SCALE) { 0 }

    init {
        addRight()
    }

    fun answer(): Int {
        var max = 0
        for (bucket in left / COUNTER_BKT_LEN..right / COUNTER_BKT_LEN) {
            while (answers[bucket].isNotEmpty()) {
                val candidate = -answers[bucket].peek()
                if (candidate <= max) break
                val isValid = groups[candidate] > 0
                if (isValid) {
                    max = max(max, candidate)
                    break
                }
                answers[bucket].poll()
                bucketMap[bucket][candidate] = false
            }
        }
        return max
    }

    fun addRight() {
        val state = states[a[++right]] // new
        state.invalidate()
        state.log.addLast(right)
        state.validate()
    }

    fun addLeft() {
        val state = states[a[--left]]
        state.invalidate()
        state.log.addFirst(left)
        state.validate()
    }

    fun removeLeft() {
        val state = states[a[left++]]
        state.invalidate()
        state.log.removeFirst()
        state.validate()
    }

    fun removeRight() {
        val state = states[a[right--]]
        state.invalidate()
        state.log.removeLast()
        state.validate()
    }

    private fun State.invalidate() {
        val len = this.value()
        if (len > 0) groups[len]--
    }

    private fun State.validate() {
        val len = this.value()
        if (len == 0) return
        groups[len]++
        val bkt = log.first / COUNTER_BKT_LEN
        if(bucketMap[bkt][len]) return
        answers[bkt] += -len
        bucketMap[bkt][len] = true
    }

}

fun main() = with(System.`in`.bufferedReader()) {
    val (n, _) = readLine().split(" ").map { it.toInt() }
    StringTokenizer(readLine()).also { t ->
        repeat(n) { i ->
            a[i] = t.nextToken().toInt()
        }
    }

    // sort queries
    val queries = readLine().toInt()
    repeat(queries) { i ->
        q[i] = readLine().split(" ")
            .map { it.toInt() - 1 }
            .let { Query(it[0], it[1]) }
        sortedQ[i] = q[i]
    }
    Arrays.sort(sortedQ, 0, queries)

    // first query
    val first = sortedQ.first()
    val counter = Counter(first.start)
    while (counter.right < first.end) {
        counter.addRight()
    }
    first.result = counter.answer()


    // other queries
    for (i in 1 until queries) {
        val query = sortedQ[i]
        while (counter.left > query.start) counter.addLeft()
        while (counter.right < query.end) counter.addRight()
        while (counter.left < query.start) counter.removeLeft()
        while (counter.right > query.end) counter.removeRight()
        query.result = counter.answer()
    }

    // print answer
    val writer = System.out.bufferedWriter()
    repeat(queries) { writer.write("${q[it].result}\n") }
    writer.close()
}
