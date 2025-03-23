/**
2263번: 트리의 순회
포스트오더에서 조상에 대한 힌트를 얻고, 힌트를 기반으로 인오더로 자식 정보를 획득하는 과정을
재귀적으로 반복하면 쉽게 해결할 수 있다.
이게 왜 시간제한 5초인지 의문
*/
fun main(): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val iin = readLine().split(" ").map { it.toInt() }
    val post = readLine().split(" ").map { it.toInt() }
    val child = Array(n+1) { Array(2) { 0 } }
    val idx = Array(n + 1) { Array(2) { -1 } }
    listOf(iin, post).forEachIndexed { index, a ->
        a.forEachIndexed { i, v -> idx[v][index] = i }
    }
    fun rangeOrNull(start: Int, endInclusive: Int): IntRange? {
        if (start <= endInclusive) return start..endInclusive
        return null
    }

    fun IntRange.len() = last - first + 1
    fun proc(inRange: IntRange, postRange: IntRange): Int {
        val parent = post[postRange.last]
        if(postRange.len() == 1) return parent
        val inCenterIdx = idx[parent][0]
        val left = rangeOrNull(inRange.first, inCenterIdx - 1)
            ?.let { it to (postRange.first until (postRange.first + it.len())) }
        val right = rangeOrNull(inCenterIdx + 1, inRange.last)
            ?.let { it to ((postRange.last - it.len()) until postRange.last) }
        left?.apply { child[parent][0] = proc(first, second) }
        right?.apply { child[parent][1] = proc(first, second) }
        return parent
    }
    val root = proc(iin.indices, post.indices)
    val ans = ArrayList<Int>(n)
    fun preOrder(cur: Int) {
        ans += cur
        for(i in 0 until 2) if(child[cur][i] != 0) preOrder(child[cur][i])
    }
    preOrder(root)
    println(ans.joinToString(" "))
}
