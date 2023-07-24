import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

/**
11438번: LCA 2
트리의 각 노드에 depth 값을 할당해주고 Sparse Table과 재귀함수를 활용해서 공통 조상을 찾았다. 
*/

class Node(val id: Int) {
    var parent = this
    val linked = LinkedList<Node>()
    val sparse = ArrayList<Node>(20)
    var depth = 0
    
    fun goto(depth: Int): Node { // depth <= this.depth
        if(depth == this.depth) return this
        var last = this
        for(i in 0 until sparse.size) {
            var k = sparse[i]
            if(k.depth == depth) return k
            if(k.depth < depth) break
            last = k
        }
        return last.parent.goto(depth)
    }
}

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val nodes = Array(n) {Node(it)}
    repeat(n-1) {
        var (a,b) = readLine().split(" ").map {it.toInt()}
        a--; b--;
        nodes[a].linked.add(nodes[b])
        nodes[b].linked.add(nodes[a])
    }
    fun fp(node: Node, depth: Int) {
        node.depth = depth
        for(c in node.linked) {
            if(c == node.parent) continue
            c.parent = node
            fp(c, depth+1)
        }
    }
    fp(nodes[0], 0)
    for(id in 1 until n) {
        val node = nodes[id]
        node.sparse.add(node.parent)
    }
    for(i in 1..17) {
        for(id in 0 until n) {
            var node = nodes[id]
            var sp = node.sparse.getOrNull(i-1)?.sparse?.getOrNull(i-1)
            if(sp != null) node.sparse.add(sp)
        }
    }
    fun findCA(a: Node, b: Node): Node { // a.depth == b.depth
        if(a == b) return a
        var lastA = a
        var lastB = b
        for(i in 0 until a.sparse.size) {
            var na = a.sparse[i]
            var nb = b.sparse[i]
            if(na == nb) {
                if(i == 0) return na
                break
            }
            lastA = na
            lastB = nb
        }
        return findCA(lastA, lastB)
    }
    val m = readLine().toInt()
    System.`out`.bufferedWriter().use { w->   
        for(i in 0 until m) {
            var (a,b) = readLine().split(" ").map {it.toInt()}
            a--; b--;
            if(nodes[a].depth < nodes[b].depth) {
                val c = a
                a = b
                b = c
            }
            // depth: a < b
            val na = nodes[a]
            val nb = nodes[b]
            val ac = na.goto(nb.depth)
            val ca = findCA(ac, nb)
            w.write("${ca.id+1}\n")
        }                              
    }
    
}
