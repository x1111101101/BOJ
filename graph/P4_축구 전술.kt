import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.math.min

/**
 * 3977번: 축구 전술
 * 1. SCC를 하나의 노드로 병합해 그래프 재구성
 * 2. indegree가 1인 노드 탐색
 */
class Node(val id: Int) {
    val outEdges = HashSet<Node>() // to
    val inEdges = HashSet<Node>() // from
    override fun hashCode(): Int = id
    val sccNodes = ArrayList<Int>()
}

class Graph(val nodeSize: Int) {
    val nodes = Array(nodeSize) { Node(it) }


    fun mergeSCC(): Graph {
        val sccList = calcSCC()
        var id = 0
        val newNodes = ArrayList<Node>()
        val sccNodes = Array<Node?>(nodeSize) { null }
        for(group in sccList) {
            val node = Node(id++)
            group.forEach {
                sccNodes[it.id] = node
                node.sccNodes += it.id
            }
            newNodes += node
        }
        for(i in nodes.indices) {
            val node = sccNodes[i]!!
            val prevNode = nodes[i]
            prevNode.outEdges.forEach {
                val tnode = sccNodes[it.id]!!
                if(tnode == node) return@forEach
                node.outEdges += tnode
                tnode.inEdges += node
            }
        }
        val newGraph = Graph(newNodes.size)
        for(i in newNodes.indices) {
            newGraph.nodes[i] = newNodes[i]
        }
        return newGraph
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


fun main(): Unit = with(System.`in`.bufferedReader()) {
    val t = readLine().toInt()
    repeat(t) {
        val (n, m) = readLine().split(" ").map { it.toInt() }
        val graph = Graph(n)
        repeat(m) {
            val (a, b) = readLine().split(" ").map { it.toInt() }
            graph.nodes[a].outEdges += graph.nodes[b]
            graph.nodes[b].inEdges += graph.nodes[a]
        }
        val ans = graph.mergeSCC().nodes.filter { it.inEdges.isEmpty() }
        if(ans.size != 1) {
            println("Confused")
        } else {
            println(ans.first().sccNodes.sorted().joinToString("\n"))
        }
        if(it != t-1) {
            readLine()
            println()
        }
    }
}
