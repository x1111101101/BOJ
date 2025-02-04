/**
 * 13544번: 수열과 쿼리 3
 * 빠른 초기화를 위해 bottom-up 방식 사용
 * query, merge는 top-down 사용
 * 
 * 오프라인 쿼리를 방지하기 위해 쿼리 파라미터를 이전 쿼리 결과와 조합하도록 한 문제
 */

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val a = readLine().split(" ").map { it.toInt() }
    val m = readLine().toInt()
    fun sz(cur: Int = 1): Int {
        return if (cur < n) sz(cur * 2) else cur
    }

    val size = sz()
    val tree = Array(size * 2) { ArrayList<Int>() }

    repeat(n) { tree[size + it] += a[it] }

    fun merge(i: Int, left: Int, right: Int) {
        if (left >= right) return
        val mid = (left + right) / 2
        merge(i * 2, left, mid)
        merge(i * 2 + 1, mid + 1, right)
        var lp = 0
        var rp = 0
        val lc = tree[i * 2]
        val rc = tree[i * 2 + 1]
        while (lp < lc.size && rp < rc.size) {
            if (rc[rp] < lc[lp]) {
                tree[i] += rc[rp++]
            } else {
                tree[i] += lc[lp++]
            }
        }
        while (lp < lc.size) tree[i] += lc[lp++]
        while (rp < rc.size) tree[i] += rc[rp++]
    }
    merge(1, 0, size - 1)
    fun lowerbound(list: List<Int>, k: Int): Int {
        var left = 0
        var right = list.lastIndex
        var result = -1
        while (left <= right) {
            val mid = (left + right) / 2
            if (list[mid] > k) {
                result = mid
                right = mid - 1
            } else left = mid + 1
        }
        return result
    }

    fun query(i: Int, l: Int, r: Int, tl: Int, tr: Int, k: Int): Int {
        if (l > r || l > tr || r < tl) return 0
        if (l >= tl && r <= tr) {
            val idx = lowerbound(tree[i], k)
            if (idx == -1) return 0
            return tree[i].size - idx
        }
        val mid = (l + r) / 2
        return query(i * 2, l, mid, tl, tr, k) + query(i * 2 + 1, mid + 1, r, tl, tr, k)
    }

    var ans = 0
    val bw = System.out.bufferedWriter()
    repeat(m) {
        val (i, j, k) = readLine().split(" ").map { it.toInt() xor ans }
        ans = query(1, 0, size - 1, i - 1, j - 1, k)
        bw.write("${ans}\n")
    }
    bw.close()
}
