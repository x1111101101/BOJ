import java.util.*
import kotlin.collections.ArrayList

/**
2820번: 자동차 공장
오일러 경로 테크닉 + 세그먼트 트리 with lazy propagation
*/
class Node {
    val children = LinkedList<Int>()
    var euler: Pair<Int, Int> = -1 to -1
}

class Segment {
    var cur = 0
}

fun euler(nodes: Array<Node>) {
    var id = 0
    fun dfs(node: Int): Int {
        val start = id++
        var end = start
        for (c in nodes[node].children) {
            end = dfs(c)
        }
        nodes[node].euler = start to end
        return end
    }
    dfs(0)
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, m) = readLine().split(" ").map { it.toInt() }
    val nodes = Array(n) { Node() }
    val initial = Array(n) { readLine().split(" ").map { it.toInt() } }
    for (i in 1 until initial.size) {
        val parent = initial[i][1] - 1
        nodes[parent].children += i
    }
    euler(nodes)
    val segs = Array(n * 4) { Segment() }
    fun update(i: Int, l: Int, r: Int, tl: Int, tr: Int, value: Int) {
        if (l > r) return
        val seg = segs[i]
        if (tl > r || tr < l) {
            return
        }
        if (tl <= l && r <= tr) {
            seg.cur += value
            return
        }
        val mid = (l + r) / 2
        update(i * 2, l, mid, tl, tr, value)
        update(i * 2 + 1, mid + 1, r, tl, tr, value)
    }

    fun query(i: Int, l: Int, r: Int, t: Int): Int {
        if (t !in l..r) return 0
        val seg = segs[i]
        if (l == r) return seg.cur
        val mid = (l + r) / 2
        val lq = query(i * 2, l, mid, t)
        val rq = query(i * 2 + 1, mid + 1, r, t)
        return lq + rq + seg.cur
    }

    val ans = ArrayList<Int>(m)
    repeat(m) {
        val cmd = readLine().split(" ")
        val args = cmd.subList(1, cmd.size).map { it.toInt() }
        when (cmd[0][0]) {
            'p' -> {
                val (target, wage) = args.let { listOf(it[0] - 1, it[1]) }
                val (s, e) = nodes[target].euler
                update(1, 0, n - 1, s + 1, e, wage)
            }

            'u' -> {
                val target = args[0] - 1
                val targetId = nodes[target].euler.first
                val wage = query(1, 0, n - 1, targetId) + initial[target][0]
                ans += wage
            }
        }
    }
    println(ans.joinToString("\n"))
}
