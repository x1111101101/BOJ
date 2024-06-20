import java.util.Comparator
import kotlin.math.absoluteValue

/**
2887번: 행성 터널
kruskal 알고리즘으로 MST 만드는 문제.
- kotlin의 sortBy함수의 공간 복잡도가 높으니 적어도 코테에선 사용을 지양하자
- find는 노드 수가 너무 많지만 않으면 그냥 재귀함수 써도 되겠다.
*/

data class Node(val id: Int, val coords: List<Int>)

data class Edge(val a: Int, val b: Int, val w: Long)

data class Union(var next: Int, var count: Int, var weight: Long)

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val nodes = Array(n) {Node(it, readLine().split(" ").map { it.toInt() })}
    val edges = ArrayList<Edge>(n*3)
    repeat(3) { ax->
        nodes.sortWith { a,b-> a.coords[ax] - b.coords[ax]}
        for(i in 0 until nodes.lastIndex) {
            val dist = (nodes[i].coords[ax] - nodes[i+1].coords[ax]).absoluteValue
            edges.add(Edge(nodes[i].id, nodes[i+1].id, dist.toLong()))
        }
    }
    edges.sortWith { a, b -> (b.w - a.w).toInt() }
    val parent = Array(n) {it}

    fun find(i: Int): Int {
        if(parent[i] == i) return i
        parent[i] = find(parent[i])
        return parent[i]
    }

    fun union(a: Int, b: Int): Boolean {
        var pa = find(a)
        var pb = find(b)
        if(pa == pb) return false
        if(pa > pb) {
            val t = pa
            pa = pb
            pb = t
        }
        parent[pb] = pa
        return true
    }
    val included = ArrayList<Edge>(n-1)
    while (edges.isNotEmpty() && included.size != n-1) {
        val edge = edges.removeLast()
        if (union(edge.a, edge.b)) {
            included.add(edge)
        }
    }
    println(included.sumOf { it.w })
}
