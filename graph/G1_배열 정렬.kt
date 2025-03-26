/**
28707번: 배열 정렬
원소 개수의 범위가 짧아 비트마스킹을 이용할 수 있다.
순열 하나가 하나의 노드에 해당한다.
이에 대해 다익스트라로 최단거리를 구했다.
*/

@JvmInline
value class P(val value: Long) {

    operator fun get(index: Int) = MASK.and(value shr (BITS * index)).toInt()
    fun changed(index: Int, newValue: Int): P {
        val erased = (MASK shl (index*BITS)).inv().and(value)
        return P((newValue.toLong() shl (BITS * index)).or(erased))
    }
    // fun toList(size: Int) = (0 until size).map { this[it] }
    fun swapped(index1: Int, index2: Int): P = changed(index1, this[index2])
                                                .changed(index2, this[index1])

    companion object {
        const val BITS = 5
        const val MASK = 32L-1
        val EMPTY = P(0L)
        fun fromList(list: List<Int>): P {
            var v = EMPTY
            list.forEachIndexed { index, i -> v = v.changed(index, i) }
            return v
        }
    }
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val inf = Long.MAX_VALUE
    val n = readLine().toInt()
    val a = readLine().split(" ").map { it.toInt() }
    val operations = Array(readLine().toInt()) { readLine().split(" ").map { it.toInt() } }
    val goal = a.sorted().let { P.fromList(it) }
    val min = HashMap<P, Long>()
    val first = P.fromList(a)
    min[first] = 0
    data class Trav(val current: P, val cost: Long)
    val queue = ArrayDeque<Trav>(500)
        .also { it.add(Trav(first, 0)) }
    fun dist(target: P) = min[target] ?: inf
    while(queue.isNotEmpty()) {
        val (current, cost) = queue.removeFirst()
        if(dist(current) < cost) continue
        for((i,j,c) in operations) {
            val newCost = cost + c
            val next = current.swapped(i-1, j-1)
            if(dist(next) <= newCost) continue
            min[next] = newCost
            queue.add(Trav(next, newCost))
        }
    }
    val ans = dist(goal)
    println(if(ans == inf) -1 else ans)
}
