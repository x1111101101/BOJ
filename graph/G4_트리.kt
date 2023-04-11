import java.util.*;
import java.lang.Math.*
import java.io.*
import kotlin.collections.*

/**
4803번: 트리
*/

val br = System.`in`.bufferedReader()

fun main(args: Array<String>) = with(br) {
    val bw = System.`out`.bufferedWriter()
    var caseNum = 1
    do {
        val (n,m) = readLine().split(" ").map { it.toInt() }
        if(n+m == 0) break
        bw.write("Case ${caseNum++}: ${solve(n, m)}\n")
    } while(true)
    bw.close()
}

fun solve(n: Int, m: Int): String = with(br) {
    val parent = Array<Int>(n) { it }
    val type = IntArray(n) {1} // 0: undef, 1: tree, 2: grpah
    
    fun find(i: Int): Int {
        if(parent[i] == i) return i
        parent[i] = find(parent[i])
        return parent[i]
    }
    
    // 사이클 발생 여부 리턴
    fun merge(a: Int, b: Int): Boolean {
        val pa = find(a)
        val pb = find(b)
        if(pa == pb) {
            type[pa] = 2
            return true
        }
        parent[pa] = pb
        type[pb] = max(1, max(type[pb], type[pa]))
        type[pa] = 0
        return false
    }
    
    repeat(m) {
        val (a, b) = readLine().split(" ").map { it.toInt() - 1 }
        merge(a, b)
    }
    var trees = 0
    for(i in 0 until n) {
        if(type[i] == 1) trees++
    }
    
    if(trees == 0) {
        return "No trees."
    } else if(trees == 1) {
        return "There is one tree."
    }
    return "A forest of ${trees} trees."
}
