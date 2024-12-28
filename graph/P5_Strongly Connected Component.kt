import java.util.*
import kotlin.math.min

/**
2150번: Strongly Connected Component
id 값을 탐색 순으로 할당해야함에 유의하자.
*/
val listComparator = Comparator<List<Int>> { a, b ->
    for (i in 0 until min(a.size, b.size)) {
        if (a[i] - b[i] != 0) return@Comparator a[i] - b[i]
    }
    return@Comparator a.size - b.size
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, m) = readLine().split(" ").map { it.toInt() }
    val nodes = Array(n + 1) { HashSet<Int>() }
    repeat(m) {
        val (a, b) = readLine().split(" ").map { it.toInt() }
        nodes[a] += b
    }
    val sccList = LinkedList<List<Int>>()
    val parent = Array(n + 1) { 0 }
    val finish = Array(n + 1) { false }
    val stack = ArrayList<Int>(10000)
    var id = 0
    fun dfs(i: Int): Int {
        parent[i] = ++id
        stack += i
        var curParent = id
        for (out in nodes[i]) {
            if (parent[out] == 0) {
                curParent = min(curParent, dfs(out))
            } else if (!finish[out]) {
                curParent = min(curParent, parent[out])
            }
        }
        if (parent[i] == curParent) {
            val currentScc = LinkedList<Int>()
            while (true) {
                val last = stack.removeLast()
                currentScc += last
                finish[last] = true
                if (last == i) {
                    break
                }
            }
            sccList += currentScc
        }
        return curParent
    }
    for (i in 1..n) {
        if (parent[i] == 0) dfs(i)
    }
    System.out.bufferedWriter().use { bw ->
        bw.write("${sccList.size}\n")
        sccList.map { it.sorted() }.sortedWith(listComparator).forEach {
            bw.write(it.joinToString(" "))
            bw.write(" -1\n")
        }
    }
}
