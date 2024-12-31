import java.util.*
import kotlin.collections.HashSet
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

/**
 * 11280번: 2-SAT - 3
 * 위상정렬에 대한 활용력, 이해도를 묻는 문제
 * 0. 노드 구성 - 각 변수에 대해 T/F에 대응되는 두개 노드를 구성
 * 1. SCC를 하나의 노드로 병합해 그래프 재구성
 * 2. 어떤 하나의 변수의 모순된 값들에 대응되는 노드 두개가 SCC라면 0 출력
 * 한 변수에 대한 두 값을 대변하는 두 노드가 SCC라면 그 변수는 모순된 상태를 가질 수 밖에 없다.
 * SCC가 아니라면 모순되지 않은 상태를 가질 수 있는 길이 하나 이상 있다.
 */
class Node(val id: Int) {
    val outEdges = HashSet<Node>() // to
    val inEdges = LinkedList<Node>() // from

    override fun hashCode(): Int = id
}

class Graph(val nodeSize: Int) {
    val nodes = Array(nodeSize) { Node(it) }


    fun mergeSCC(): Pair<Graph, Array<Node>> {
        val sccList = calcSCC()
        var id = 0
        val newNodes = ArrayList<Node>()
        val sccNodes = Array<Node?>(nodeSize) { null }
        for (group in sccList) {
            val node = Node(id++)
            group.forEach {
                sccNodes[it.id] = node
            }
            newNodes += node
        }
        for (i in nodes.indices) {
            val node = sccNodes[i]!!
            val prevNode = nodes[i]
            prevNode.outEdges.forEach {
                val tnode = sccNodes[it.id]!!
                if (tnode == node) return@forEach
                node.outEdges += tnode
                tnode.inEdges += node
            }
        }
        val newGraph = Graph(newNodes.size)
        for (i in newNodes.indices) {
            newGraph.nodes[i] = newNodes[i]
        }
        return newGraph to sccNodes as Array<Node>
    }

    fun calcSCC(): List<List<Node>> {
        val sccList = LinkedList<List<Node>>()
        val stack = ArrayList<Node>(nodeSize)
        val parent = Array(nodeSize) { 0 }
        val finish = Array(nodeSize) { false }
        var id = 0
        fun dfs(i: Int): Int {
            parent[i] = ++id
            stack += nodes[i]
            var curParent = id
            for (out in nodes[i].outEdges) {
                if (parent[out.id] == 0) {
                    curParent = min(curParent, dfs(out.id))
                } else if (!finish[out.id]) {
                    curParent = min(curParent, parent[out.id])
                }
            }
            if (parent[i] == curParent) {
                val currentScc = LinkedList<Node>()
                while (true) {
                    val last = stack.removeLast()
                    currentScc += last
                    finish[last.id] = true
                    if (last.id == i) {
                        break
                    }
                }
                sccList += currentScc
            }
            return curParent
        }
        for (node in nodes) {
            if (parent[node.id] == 0) dfs(node.id)
        }
        return sccList
    }
}

fun List<String>.f() = this.filter { it.isNotEmpty() && it != " " }

fun idx(i: Int) = i.absoluteValue * 2 + (if (i < 0) 1 else 0)


fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, m) = readLine().split(" ").map { it.toInt() }
    val graph = Graph(n * 2 + 2)
    for (i in 0 until m) with(graph) {
        val (a, b) = readLine().split(" ").map { it.toInt() }
        val (sa, sb) = listOf(a, b).map { it / it.absoluteValue }
        nodes[idx(-a)].outEdges += graph.nodes[idx(b)]
        nodes[idx(-b)].outEdges += graph.nodes[idx(a)]
    }
    val (newGraph, newNodes) = graph.mergeSCC()
    var ans = 1
    for(i in 1..n) {
        if(newNodes[idx(i)] == newNodes[idx(-i)]) {
            ans = 0
            break
        }
    }
    println(ans)
}
