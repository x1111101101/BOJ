/**
16093번: 수열과 쿼리 20
각 자리의 비트 값을 기준으로 trie 구성
모든 연산 O(1)
*/

class Node {
    val children = arrayOfNulls<Node>(2)
    var count = 0
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val m = readLine().toInt()
    val root = Node()
    fun insert(cur: Node, value: Int, idx: Int = 31) {
        cur.count++
        if(idx == -1) return
        val bit = (value ushr idx) and 1
        val node = cur.children[bit] ?: Node().also { cur.children[bit] = it }
        insert(node, value, idx - 1)
    }
    fun remove(cur: Node, value: Int, idx: Int = 31) {
        cur.count--
        if(idx == -1) return
        val bit = (value ushr idx) and 1
        val node = cur.children[bit]!!
        remove(node, value, idx-1)
        if(node.count <= 0) {
            cur.children[bit] = null
        }
    }
    fun queryMax(cur: Node, pivot: Int, idx: Int = 31): Int {
        if(idx == -1) {
            return 0
        }
        val pbit = (pivot ushr idx) and 1
        val opt = 1 - pbit
        val target = if(cur.children[opt] == null) 1 - opt else opt
        val result = (target shl idx) or queryMax(cur.children[target]!!, pivot, idx-1)
        return result
    }
    insert(root, 0)
    val bw = System.out.bufferedWriter()
    Array(m) {
        val (op, x) = readLine().split(" ").map { it.toInt() }
        when(op) {
            1-> insert(root, x)
            2-> remove(root, x)
            3-> {
                val result = queryMax(root, x)
                bw.write("${result xor x}\n")
            }
        }
    }
    bw.close()
}
