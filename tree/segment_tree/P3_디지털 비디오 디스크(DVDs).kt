import kotlin.math.max
import kotlin.math.min

/**
9345번: 디지털 비디오 디스크
세그먼트트리 기초
*/

data class Node(
    var min: Int = Int.MAX_VALUE,
    var max: Int = Int.MIN_VALUE
)
data class Q(val min: Int, val max: Int)

val Q_INF = Q(Int.MAX_VALUE, Int.MIN_VALUE)

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val t = readLine().toInt()
    val bw = System.out.bufferedWriter()
    val tree = Array(400_000) { Node() }
    repeat(t) {
        val (n, k) = readLine().split(" ").map { it.toInt() }

        fun update(i: Int, target: Int, l: Int, r: Int, value: Int): Node {
            val node = tree[i]
            if (target !in l..r) return node
            if (l == r) {
                node.min = value
                node.max = value
                return node
            }
            val m = (l + r) / 2
            val left = update(i * 2, target, l, m, value)
            val right = update(i * 2 + 1, target, m + 1, r, value)
            node.max = max(left.max, right.max)
            node.min = min(left.min, right.min)
            return node
        }

        fun query(i: Int, tl: Int, tr: Int, l: Int, r: Int): Q {
            val node = tree[i]
            if (tr < l || tl > r) return Q_INF
            if (tl <= l && r <= tr) return Q(node.min, node.max)
            val m = (l + r) / 2
            val left = query(i * 2, tl, tr, l, m)
            val right = query(i * 2 + 1, tl, tr, m + 1, r)
            return Q(min(left.min, right.min), max(left.max, right.max))
        }

        for (i in 0 until n) update(1, i, 0, n - 1, i)

        repeat(k) {
            val (q, a, b) = readLine().split(" ").map { it.toInt() }
            if (q == 0) {
                val va = query(1, a, a, 0, n-1).min
                val vb = query(1, b, b, 0, n-1).min
                update(1, a, 0, n - 1, vb)
                update(1, b, 0, n - 1, va)
            } else {
                val query = query(1, a, b, 0, n - 1)
                if (query.min == a && query.max == b) {
                    bw.write("YES\n")
                } else {
                    bw.write("NO\n")
                }
            }
        }
    }
    bw.close()
}
