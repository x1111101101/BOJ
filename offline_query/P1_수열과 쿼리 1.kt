import Counter.decrease
import Counter.increase
import java.util.*
import kotlin.math.sqrt

/**
 * 13548번: 수열과 쿼리 6
 * - 특정 구간 내 최빈값의 도수를 구하는 문제
 * - mo's 알고리즘으로 해결
 * - 최빈값 업데이트 로직을 우선순위 큐 활용해서 비효율적으로 구현함.
 *   증감이 1의 크기로만 발생해 배열만으로도 구현 가능.
 *   추후 보완 예정.
 */

val SCALE = 200_002
var sqrt = 0

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

class Frequency(val frequency: Int) : Comparable<Frequency> {

    var inQueue = false
    var numbers = 0

    override fun compareTo(other: Frequency): Int {
        return other.frequency - frequency
    }
}

object Counter {
    private val heap = PriorityQueue<Frequency>(SCALE)
    private val frequencies = Array(SCALE) { Frequency(it) }
    private val map = Array(SCALE) { frequencies[SCALE / 2] }

    init {
        heap += map[0]
    }

    fun max(): Int {
        while (heap.peek().numbers <= 0) {
            heap.poll().inQueue = false
        }
        return heap.peek().frequency - SCALE / 2
    }

    fun update(number: Int, delta: Int) {
        val freq = map[number]
        freq.numbers--

        val newFreq = frequencies[freq.frequency + delta]
        map[number] = newFreq
        newFreq.numbers++

        if (newFreq.numbers == 1 && !newFreq.inQueue) {
            heap.add(newFreq)
            newFreq.inQueue = true
        }
    }

    fun increase(number: Int) {
        update(number, 1)
    }

    fun decrease(number: Int) {
        update(number, -1)
    }
}

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val a = readLine().split(" ").map { it.toInt() }
    sqrt = sqrt(n.toDouble() + 1).toInt()
    val q = Array(readLine().toInt()) {
        readLine().split(" ")
            .map { it.toInt() - 1 }
            .let { Query(it[0], it[1]) }
    }
    val qs = Array(q.size) { q[it] }.also { it.sort() }
    val first = qs.first()
    for (i in first.start..first.end) {
        increase(a[i])
    }
    first.result = Counter.max()
    var left = first.start
    var right = first.end
    for (i in 1 until qs.size) {
        val query = qs[i]
        while (left < query.start) decrease(a[left++])
        while (right > query.end) decrease(a[right--])
        while (left > query.start) increase(a[--left])
        while (right < query.end) increase(a[++right])
        query.result = Counter.max()
    }
    println(q.joinToString("\n") { it.result.toString() })
}
