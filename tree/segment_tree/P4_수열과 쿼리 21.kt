/**
16975번: 수열과 쿼리 21
수열과 쿼리 1 문제와 마찬가지로 느리게 갱신되는 세그먼트 트리 응용
*/

data class Node(var current: Long, var lazy: Long)

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val tree = Array(n * 4) { Node(0, 0) }
    fun add(i: Int, left: Int, right: Int, tl: Int, tr: Int, value: Long, lazy: Long = 0L): Long {
        if(left > right) return 0L
        val len = right - left + 1
        tree[i].lazy += lazy
        if(left > tr || right < tl) return tree[i].current + tree[i].lazy * len
        if(left >= tl && right <= tr) {
            // 현재 구간이 변경 구간의 부분집합
            tree[i].lazy += value
            return tree[i].lazy * len + tree[i].current
        }
        val lazyValue = tree[i].lazy
        val mid = (left+right)/2
        val leftR = add(i*2, left, mid, tl, tr, value, lazyValue)
        val rightR = add(i*2+1, mid+1, right, tl, tr, value, lazyValue)
        tree[i].current = leftR + rightR
        tree[i].lazy = 0
        return tree[i].current + tree[i].lazy * len
    }
    fun query(i: Int, left: Int, right: Int, target: Int): Long {
        if(left > right || target !in left..right) return 0L
        if(left == right) {
            // 현재 구간이 target 구간의 부분집합
            return tree[i].let { it.current + it.lazy }
        }
        val mid = (left+right)/2
        val leftSum = query(i*2, left, mid, target)
        val rightSum = query(i*2+1, mid+1, right, target)
        return (leftSum + rightSum + tree[i].lazy)
    }
    readLine().split(" ").map { it.toLong() }.forEachIndexed { index, v ->
        add(1, 0, n-1, index, index, v)
    }

    System.out.bufferedWriter().use { w->
        repeat(readLine().toInt()) {
            val input = readLine().split(" ").map { it.toLong() }
            if(input[0] == 1L) {
                val value = input[3]
                val (start, end) = listOf(input[1], input[2]).map { it.toInt() - 1 }
                add(1, 0, n-1, start, end, value)
            } else {
                val target = input[1].toInt() - 1
                val result = query(1 , 0, n-1, target)
                w.write("${result}\n")
            }
        }
    }
}
