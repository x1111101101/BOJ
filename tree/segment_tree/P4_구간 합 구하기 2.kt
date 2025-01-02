/**
 * 10999번: 구간 합 구하기 2
 * Segment Tree with Lazy Propagation 문제다.
 * 자료구조 개념 공부 없이 직접 생각해서 구현해봤다.
 * 업데이트 발생할 때 마다 lazy 값을 전파하면서 갱신한다.
 */

data class Node(var current: Long, var lazy: Long)

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, m, k) = readLine().split(" ").map { it.toInt() }
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
    fun query(i: Int, left: Int, right: Int, tl: Int, tr: Int): Pair<Long, Int>{
        if(left > tr || right < tl || left > right) return 0L to 0
        val len = right - left + 1
        if(left >= tl && right <= tr) {
            // 현재 구간이 target 구간의 부분집합
            return tree[i].let { it.current + it.lazy * len } to len
        }
        val mid = (left+right)/2
        val leftSum = query(i*2, left, mid, tl, tr)
        val rightSum = query(i*2+1, mid+1, right, tl, tr)
        val subLen = leftSum.second + rightSum.second
        return (leftSum.first + rightSum.first + tree[i].lazy * subLen) to subLen
    }
    repeat(n) {
        val value = readLine().toLong()
        add(1, 0, n-1, it, it, value)
    }
    System.out.bufferedWriter().use { w->
        repeat(m + k) {
            val input = readLine().split(" ").map { it.toLong() }
            val (start, end) = listOf(input[1], input[2]).map { it.toInt() - 1 }
            if(input[0] == 1L) {
                val value = input[3]
                add(1, 0, n-1, start, end, value)
            } else {
                val result = query(1 , 0, n-1, start, end)
                w.write("${result.first}\n")
            }
        }
    }
}
