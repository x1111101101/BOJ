import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

/**
17472번: 다리 만들기 2

NOTE
- 다리 길이 >= 2

MST를 구하는 문제인데, 독특한 방식으로 노드간 거리가 결정된다.

*/

class Edge(val a: Int, val b: Int, val dist: Int): Comparable<Edge> {
    override fun compareTo(k: Edge): Int {
        return dist.compareTo(k.dist)
    }
}

val INF = 3000
val OFFSETS = arrayOf(0 to 1, 0 to -1, 1 to 1, 1 to -1)

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val len = readLine().split(" ").map {it.toInt()}
    val map = Array(len[0]) {readLine().split(" ").map {it.toInt()}}
    val group = Array(len[0]) {IntArray(len[1]) {-1}}
    val min = Array(6) {IntArray(6) {INF}}
    val visit = Array(len[0]) {BooleanArray(len[1])}
    fun dfs(x: Int, y: Int, i: Int) {
        group[x][y] = i
        for(o in OFFSETS) {
            val loc = arrayOf(x, y)
            loc[o.first] += o.second
            if(loc[o.first] < 0 || loc[o.first] >= len[o.first]) continue
            val (p,q) = loc
            if(map[p][q] == 0 || visit[p][q]) continue
            visit[p][q] = true
            dfs(p,q,i)
        }
    }
    var size = 0
    for(x in 0 until len[0]) {
        for(y in 0 until len[1]) {
            if(map[x][y] == 0 || group[x][y] > -1) continue
            dfs(x,y,size++)
        }
    }
    fun checkBridge(x: Int, y: Int, axis: Int, delta: Int, dist: Int, og: Int) {
        val loc = arrayOf(x,y)
        loc[axis] += delta
        if(loc[axis] < 0 || loc[axis] >= len[axis]) return
        val (p,q) = loc
        if(map[p][q] == 1) {
            if(og == group[p][q] || dist < 2) return
            val a = og
            val b = group[p][q]
            if(dist < min[a][b]) {
                min[a][b] = dist
                min[b][a] = dist
            }
            return
        }
        checkBridge(p,q,axis,delta,dist+1, og)
    }
    for(x in 0 until len[0]) {
        for(y in 0 until len[1]) {
            if(group[x][y] == -1) continue
            for(axis in 0..1) {
                for(m in 0..1) {
                    val delta = 1 - 2*m
                    checkBridge(x,y,axis,delta,0,group[x][y])
                }
            }
        }
    }
    val pq = PriorityQueue<Edge>()
    val parent = Array(size) {it}
    var leftEdges = size-1
    var length = 0
    for(a in 0 until size) {
        for(b in a+1 until size) {
            if(min[a][b] == INF) continue
            pq.add(Edge(a,b,min[a][b]))
        }
    }
    
    fun find(i: Int): Int {
        var p = i
        while(parent[p] != p) {
            p = parent[p]
        }
        return p
    }
    
    fun merge(a: Int, b: Int): Boolean {
        val pa = find(a)
        val pb = find(b)
        if(pa == pb) return false
        parent[pb] = pa
        return true
    }
    
    while(pq.isNotEmpty()) {
        val e = pq.poll()
        if(merge(e.a, e.b)) {
            length += e.dist
            leftEdges--
        }
    }
    if(leftEdges > 0) println("-1")
    else println(length)
}
