import java.util.*;
import java.lang.Math.*
import java.io.*
import kotlin.collections.*

/**
1167번: 트리의 지름
*/

class Node(val num: Int) {
    val linkedNodes = ArrayList<Pair<Node,Int>>()
    val farthest = IntArray(2) {0}
    
    fun tryRenew(dist: Int) {
        if(farthest[0] < dist) {
            farthest[1] = farthest[0]
            farthest[0] = dist
        } else if(farthest[1] < dist) {
            farthest[1] = dist
        }
    }
}

fun main(args: Array<String>) = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val nodes = Array<Node>(n) {Node(it)}
    for(t in 0 until n) {
        val sp = readLine().split(" ")
        val i = sp[0].toInt() - 1
        for(k in 0 until sp.size/2 - 1) {
            val node = sp[1+2*k].toInt() - 1
            val dist = sp[1+2*k + 1].toInt()
            nodes[i].linkedNodes.add(Pair(nodes[node], dist))
        }
    }
    fun dfs(node: Node, acc: Int, last: Int): Int {
        node.linkedNodes.forEach { (k,v) -> 
            if(k.num != last) {
                val d = dfs(k, v+acc, node.num)
                node.tryRenew(d+v)
            }
        }
        val farthest = node.farthest[0]
        node.tryRenew(acc)
        return farthest
    }
    dfs(nodes[0], 0, -1)
    var ans = 0
    nodes.forEach {
        val d = it.farthest[0]+it.farthest[1]
        if(d>ans) ans = d
    }
    println(ans)
}
