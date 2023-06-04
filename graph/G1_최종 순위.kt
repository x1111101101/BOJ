import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

/**
3665번: 최종 순위

위상 정렬 문제인데, 입력 값에 따라 간선을 재정의 해야 하고, 사이클 발생 여부도 파악해야 한다.

?: 사이클 발생 X
IMPOSSIBLE: 사이클 발생 O

*/

val br = System.`in`.bufferedReader()
val bw = System.`out`.bufferedWriter()

fun tc() = with(br) {
    val n = readLine().toInt()
    val nodes = Array(n) {Node(it)}
    val prev = readLine().split(" ").map {it.toInt()-1}
    val m = readLine().toInt()
    val indeg = Array(n) {0}
    val reversed = Array(n) {Array(n) {0}}
    repeat(m) {
        val (a,b) = readLine().split(" ").map {it.toInt()-1}
        reversed[a][b] = 1
        reversed[b][a] = 1
    }
    for(i in 0 until n) {
        val a = prev[i]
        for(k in 0 until i) {
            val b = prev[k]
            if(reversed[a][b] == 1) {
                nodes[b].linked.add(a)
                indeg[a]++
            } else {
                nodes[a].linked.add(b)
                indeg[b]++
            }
        }
    }
    val visit = Array(n) {0} // 1: in stack, 2: visited
    var hasCycle = false
    fun dfs(node: Int) {
        for(num in nodes[node].linked) {
            if(visit[num] == 1) {
                hasCycle = true
                visit[node] = 2
                return
            }
            if(visit[num] == 2) continue
            visit[num] = 1
            dfs(num)
        }
        visit[node] = 2
    }

    for(i in 0 until n) {
        if(visit[i] == 2) continue
        visit[i] = 1
        dfs(i)
        if(hasCycle) {
            bw.write("IMPOSSIBLE\n")
            return
        }
    }

    val rank = ArrayList<Int>(n)
    do {
        var count = 0
        var found = -1
        for(i in 0 until n) {
            if(indeg[i] != 0) continue
            count++
            found = i
        }
        if(count != 1) {
            bw.write("?\n")
            return
        }
        indeg[found] = -1
        for(c in nodes[found].linked) {
            indeg[c]--
        }
        rank.add(found)
    } while(rank.size < n)
    for(i in 0 until n) {
        val k = n-i-1
        bw.write("${rank[k]+1} ")
    }
    bw.write("\n")
}

class Node(val num: Int) {
    val linked = HashSet<Int>() // 자신보다 순위가 낮은 노드들
}

fun main(args: Array<String>): Unit = with(br) {
    repeat(readLine().toInt()) {
        tc()
    }
    bw.close()
}
