import java.util.*

/**
16404번: 주식회사 승범이네
오일러 경로 테크닉 + Lazy Propagation SegTree를 사용해서 해결

- segTree는 연속적인 엑세스에서 효율적으로 작동한다
- 한 노드의 자손 노드들의 id를 연속된 형태로 할당해서 Segment Tree에 활용할 수 있도록 만들었다.


*/

class Node {
    var parent = -1
    var children = LinkedList<Int>()
}

class Segment {
    var lazy: Int = 0
    var current: Int = 0
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, m) = readLine().split(" ").map { it.toInt() }
    val nodes = Array(n) { Node() }
    readLine().split(" ").map { it.toInt() }.forEachIndexed { index, i ->
        nodes[index].parent = i - 1
        if (i != -1) nodes[i - 1].children += index
    }
    val ids = Array(n) { -1 to -1 }
    var id = 0
    fun allocateId(idx: Int): Int {
        val start = id++
        var end = start
        for (i in nodes[idx].children) {
            end = allocateId(i)
        }
        ids[idx] = start to end
        return end
    }
    allocateId(0)
    val segs = Array(n*4) { Segment() }

    fun update(i: Int, left: Int, right: Int, tl: Int, tr: Int, value: Int, lazy: Int = 0): Int {
        if(left > right) return 0
        val cur = segs[i]
        val len = right - left + 1
        cur.lazy += lazy
        if(tl > right || tr < left) return cur.lazy*len + cur.current
        if(left >= tl && right <= tr) {
            // 부분 집합
            cur.lazy += value
            return cur.lazy * len + cur.current
        }
        val mid = (left + right) / 2
        val lv = update(i*2, left, mid, tl, tr, value, cur.lazy)
        val rv = update(i*2 + 1, mid+1, right, tl, tr, value, cur.lazy)
        cur.lazy = 0
        cur.current = lv + rv
        return cur.current
    }

    fun query(i: Int, left: Int, right: Int, target: Int, lazy: Int): Int {
        if(left > right) return 0
        if(target !in left..right) return 0
        val cur = segs[i]
        val mid = (left + right) / 2
        if(left == right) {
            return cur.current + cur.lazy + lazy
        }
        val lv = query(i*2, left, mid, target, lazy + cur.lazy)
        val rv = query(i*2 + 1, mid+1, right, target, lazy + cur.lazy)
        return lv + rv
    }
    System.out.bufferedWriter().use { writer->
        repeat(m) {
            val op = readLine().split(" ").map { it.toInt() }
            when(op[0]) {
                1-> {
                    val (start, end) = ids[op[1] - 1]
                    update(1, 0, n - 1, start, end, op[2])
                }
                2-> {
                    val target = ids[op[1] - 1].first
                    val value = query(1, 0, n-1, target, 0)
                    writer.write("${value}\n")
                }
                else-> throw IllegalStateException()
            }
        }
    }
}
