import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

/**
3176번: 도로 네트워크

LCA를 응용해서 해결할 수 있다.
희소 배열을 만들 때 경로에 포함된 간선 중 거리가 가장 긴 값, 작은 값까지 저장해서 큰 어려움 없이 풀 수 있었다.

*/
class Sparse: Boundary {
    val target: Node
    constructor(target: Node, min: Long, max: Long): super(min, max) {
        this.target = target
    }
    constructor(target: Node, b: Boundary): super(b.min, b.max) {
        this.target = target
    }
}
open class Boundary(val min: Long, val max: Long) {
    fun compound(b: Boundary) = Boundary(min(min, b.min), max(max, b.max))
    override fun toString() = "$min $max"
}
class Node(val id: Int) {
    val linked = HashMap<Node, Long>()
    var parent = this
    var depth = 0
    val sparse = ArrayList<Sparse>(18)
    
    fun goto(depth: Int, boundary: Boundary): Sparse {
        // if(depth > this.depth) throw IllegalArgumentException()
        if(this.depth == depth) return Sparse(this, boundary)
        for(i in sparse.lastIndex downTo 0) {
            val s = sparse[i]
            val t = s.target
            if(t.depth < depth) continue
            return t.goto(depth, boundary.compound(s))
        }
        return parent.goto(depth, boundary.compound(sparse[0]))
    }
    override fun hashCode() = id.hashCode()
}

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val nodes = Array(n) {Node(it)}
    repeat(n-1) {
        var (a,b,d) = readLine().split(" ").map {it.toInt()}
        a--; b--
        var na = nodes[a]
        var nb = nodes[b]
        na.linked[nb] = d.toLong()
        nb.linked[na] = d.toLong()
    }
    fun initNodes(node: Node, depth: Int) {
        for(e in node.linked) {
            val c = e.key
            if(c == node.parent) continue
            c.parent = node
            c.depth = depth+1
            initNodes(c, depth+1)
        }
    }
    initNodes(nodes[0], 0)
    for(i in 1 until n) {
        val node = nodes[i]
        var dist = node.linked[node.parent]!!
        node.sparse.add(Sparse(node.parent, dist, dist))
    }
    for(depth in 1 until 18) {
        for(node in nodes) {
            val s = node.sparse.getOrNull(depth-1) ?: continue
            val s2 = s.target.sparse.getOrNull(depth-1) ?: continue
            val new = Sparse(s2.target, s.compound(s2))
            node.sparse.add(new)
        }
    }
    fun solve(a: Node, b: Node, boundary: Boundary): Boundary { // a.depth == b.depth
        if(a==b) return boundary
        var bo = boundary
        for(i in a.sparse.lastIndex downTo 0) {
            val sa = a.sparse[i]
            val sb = b.sparse[i]
            if(sa.target != sb.target) {
                return solve(sa.target, sb.target, sa.compound(sb).compound(bo))
            }
        }
        return solve(a.parent, b.parent, a.sparse[0].compound(b.sparse[0]).compound(bo))
    }
    System.`out`.bufferedWriter().use {w->
        val k = readLine().toInt()
        repeat(k) {
            var (a,b) = readLine().split(" ").map {it.toInt()-1}
            if(nodes[a].depth < nodes[b].depth) {
                var c = a
                a = b
                b = c
            }
            val nb = nodes[b]
            val na = nodes[a].goto(nb.depth, Boundary(Long.MAX_VALUE, 0L))
            w.write("${solve(na.target, nb, na)}\n")
        }
    }    
}
