/**
17353번: 하늘에서 떨어지는 1, 2, ..., R-L+1개의 별
느리게 갱신되는 세그먼트 트리로 해결
*/
import java.util.ArrayList
import java.util.StringTokenizer

lateinit var tree: Array<Node>

class Node(val idx: Int) {
    var left: Int = 0
    var right: Int = 0
    var weight = 0L
    var drop: Int = 0

    val leftChild get() = tree[idx * 2]
    val rightChild get() = tree[idx * 2 + 1]

    fun drop(starLeft: Int, starRight: Int) {
        val isOutside = left > starRight || right < starLeft
        if(isOutside) return

        val fullyAssociated = starLeft <= left && right <= starRight
        if(fullyAssociated) {
            drop++
            weight += left - starLeft + 1
            return
        }

        leftChild.drop(starLeft, starRight)
        rightChild.drop(starLeft, starRight)
    }

    fun count(location: Int): Long {
        if(location !in left..right) return 0L
        if(left == right) return weight

        var count = if(weight == 0L) 0L else weight + drop * (location - left)
        count += leftChild.count(location) + rightChild.count(location)

        return count
    }

}

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val initial = StringTokenizer(readLine()).let { st -> Array(n) { st.nextToken().toInt() } }
    tree = Array(n * 4) { Node(it) }
    fun initNodes(cur: Node, left: Int, right: Int) {
        if(left > right) return
        cur.left = left
        cur.right = right
        if(left == right) return
        val mid = (left + right) / 2
        initNodes(cur.leftChild, left, mid)
        initNodes(cur.rightChild, mid + 1, right)
    }
    val root = tree[1]
    initNodes(root, 1, n)
    val queries = readLine().toInt()
    val answer = ArrayList<Long>(queries)
    repeat(queries) {
        val query = readLine().split(" ").map { it.toInt() }
        if(query[0] == 1) {
            root.drop(query[1], query[2])
        } else {
            answer += root.count(query[1]) + initial[query[1] - 1]
        }
    }
    println(answer.joinToString("\n"))

}
