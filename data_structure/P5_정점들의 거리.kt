import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

/**
1761번: 정점들의 거리
희소배열을 활용해 LCA를 찾아 거리를 구했다.
*/

class Sparse(val target: Node, val distance: Int)
class Node(val id: Int) {
    var parent = this
    val linked = HashMap<Node, Int>()
    val sparse = ArrayList<Sparse>(20)
    var depth = 0
    
    override fun hashCode() = id.hashCode()
    fun link(o: Node, distance: Int) {
        linked[o] = distance
    }
    fun goto(depth: Int, distance: Int): Sparse {
        if(this.depth == depth) return Sparse(this, distance)
        for(i in sparse.lastIndex downTo 0) {
            val s = sparse[i]
            if(s.target.depth < depth) continue
            return s.target.goto(depth, s.distance + distance)
        }
        throw IllegalArgumentException()
    }

}

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val nodes = Array(n) {Node(it)}
    repeat(n-1) {
        var (a,b,d) = readLine().split(" ").map {it.toInt()}
        a--; b--;
        nodes[a].link(nodes[b], d)
        nodes[b].link(nodes[a], d)
    }
    fun fp(node: Node, depth: Int) {
        node.depth = depth
        for(e in node.linked) {
            var c = e.key
            if(c == node.parent) continue
            c.parent = node
            fp(c, depth+1)
        }
    }
    fp(nodes[0], 0)
    for(id in 1 until n) {
        val node = nodes[id]
        node.sparse.add(Sparse(node.parent, node.linked[node.parent]!!))
    }
    for(i in 1..17) {
        for(id in 0 until n) {
            var node = nodes[id]
            val s = node.sparse.getOrNull(i-1) ?: continue
            val s2 = s.target.sparse?.getOrNull(i-1) ?: continue
            node.sparse.add(Sparse(s2.target, s2.distance + s.distance))
        }
    }
    fun solve(a: Node, b: Node, distance: Int): Int { // a.depth == b.depth
        if(a == b) return distance
        for(i in a.sparse.lastIndex downTo 0) {
            var na = a.sparse[i]
            var nb = b.sparse[i]
            if(na.target == nb.target) continue
            return solve(na.target, nb.target, distance + na.distance + nb.distance)
        }
        return a.sparse[0].distance + b.sparse[0].distance + distance
    }
    val m = readLine().toInt()
    System.`out`.bufferedWriter().use { w->   
        for(i in 0 until m) {
            var (a,b) = readLine().split(" ").map {it.toInt()}
            if(a==b) {
                w.write("0\n")
                continue
            }
            a--; b--;
            if(nodes[a].depth < nodes[b].depth) {
                val c = a
                a = b
                b = c
            }
            // depth: a > b
            val na = nodes[a]
            val nb = nodes[b]
            val ac = na.goto(nb.depth, 0)
            w.write("${solve(ac.target, nb, ac.distance)}\n")
        }                              
    }
    
}
