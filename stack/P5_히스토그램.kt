/**
1725번: 히스토그램
우선순위 큐를 안썼어도 될 것 같다.
추후 수정 예정
*/


import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

data class H(val idx: Int, val height: Int): Comparable<H> {
    override fun compareTo(other: H): Int {
        return other.height.compareTo(height);
    }
}

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val h = Array(n+1) {0}
    repeat(n) {h[it] = readLine().toInt()}
    val pq = PriorityQueue<H>()
    var max = 0L
    for(i in 0..n) {
        val height = h[i]
        var co = i
        while(pq.isNotEmpty() && pq.peek().height > height) {
            val removed = pq.poll()
            co = min(co, removed.idx)
            val width = i-co
            val area: Long = width.toLong() * removed.height.toLong()
            max = max(max, area)
        }
        pq.add(H(co, height))
    }
    println(max)
    
}
