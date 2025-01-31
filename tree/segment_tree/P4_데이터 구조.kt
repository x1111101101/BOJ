/**
12899번: 데이터 구조
*/

class Node {
    var count = 0
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val SCALE = 200_0000
    val tree = Array(SCALE * 4) { Node() }
    fun insert(i: Int, left: Int, right: Int, value: Int) {
        if (value !in left..right) return
        if (left == right) {
            tree[i].count++
            return
        }
        val mid = (left + right) / 2
        insert(i * 2, left, mid, value)
        insert(i * 2 + 1, mid + 1, right, value)
        tree[i].count = tree[i * 2].count + tree[i * 2 + 1].count
    }

    fun queryAndRemove(i: Int, left: Int, right: Int, rank: Int, count: Int = 0): Int {
        tree[i].count--
        if (left == right) {
            return left
        }
        val leftCount = tree[i * 2].count
        val mid = (left + right) / 2
        if (count + leftCount >= rank)
            return queryAndRemove(i * 2, left, mid, rank, count)
        return queryAndRemove(i * 2 + 1, mid+1, right, rank, count + leftCount)
    }
    val bw = System.out.bufferedWriter()
    repeat(n) {
        val q = readLine().split(" ").map { it.toInt() }
        if(q[0] == 1) {
            insert(1, 0, SCALE, q[1])
        } else if(q[0] == 2) {
            val ans = queryAndRemove(1, 0, SCALE, q[1])
            bw.write("${ans}\n")
        }
    }
    bw.close()
}
