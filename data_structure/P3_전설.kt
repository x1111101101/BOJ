import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

/**
19585번: 전설
색깔 이름과 닉네임 목록이 주어진 후
입력 문자열들에 대해 색깔 이름+닉네임인지 출력하는 문제

트라이 + 해시셋으로 해결했다.
*/

var A = 'a'.toInt()
class Node() {
    var isExist = false
    var childs = Array<Node?>(26) { null }
}

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val (n,m) = readLine().split(" ").map {it.toInt()}
    val nicks = HashSet<String>()
    val tri = Node()
    repeat(n) {
        val color = readLine()
        var cur = tri
        for(i in 0 until color.length-1) {
            val idx = color[i].toInt()-A
            var c = cur.childs[idx]
            if(c == null) {
                c = Node()
                cur.childs[idx] = c
            }
            cur = c
        }
        val idx = color[color.length-1].toInt()-A
        var c = cur.childs[idx]
        if(c == null) {
            c = Node()
            cur.childs[idx] = c
        }
        c.isExist = true
    }
    repeat(m) {nicks.add(readLine())}
    val q = readLine().toInt()
    System.`out`.bufferedWriter().use {w->
    repeat(q) {
        var ans = "No"
        val input = readLine()
        
        fun dfs(prev: Node, idx: Int) {
            val code = input[idx].toInt()-A
            val c = prev.childs[code]
            if(c == null) return
            if(c.isExist && idx+1 < input.length) {
                val sub = input.substring(idx+1, input.length)
                if(nicks.contains(sub)) {
                    ans = "Yes"
                    return
                }
            }
            if(idx+1 >= input.length) return
            dfs(c, idx+1)
        }
        dfs(tri, 0)
        w.write("$ans\n")
    }}
}
