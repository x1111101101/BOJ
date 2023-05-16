import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

/**
13537번: 수열과 쿼리 1
세그먼트 트리의 각 노드에 리스트를 하나씩 할당하고 그 노드가 나타내는 범위에 해당하는 값들을  방식으로 병합정렬했다.
*/

val a = ArrayList<Int>(100000)
val tree = Array(500000) {ArrayList<Int>()}

fun msort(i: Int, left: Int, right: Int) {
    if(left==right) {
        tree[i].add(a[left])
        return
    }
    if(left > right) return
    val m = (left+right)/2
    msort(i*2, left, m)
    msort(i*2+1, m+1, right)
    var l = 0
    var r = 0
    while(l < tree[i*2].size && r < tree[i*2+1].size) {
        val a = tree[i*2][l]
        val b = tree[i*2+1][r]
        if(a<b) {
            l++
            tree[i].add(a)
        } else {
            r++
            tree[i].add(b)
        }
    }
    while(l < tree[i*2].size) tree[i].add(tree[i*2][l++])
    while(r < tree[i*2+1].size) tree[i].add(tree[i*2+1][r++])
}

fun query(i: Int, left: Int, right: Int, tl: Int, tr: Int, k: Int): Int {
    if(left > right || tl > right || tr < left) return 0
    if(!(tl <= left && tr >= right)) {
        val m = (left+right)/2
        return query(i*2, left, m, tl, tr, k) + query(i*2+1, m+1, right, tl, tr, k)
    }
    val lb = lowerbound(tree[i], k)
    if(lb == -1) return 0
    return tree[i].size - lb
}

fun lowerbound(l: List<Int>, k: Int): Int {
    var left = 0
    var right = l.lastIndex
    var result = -1
    while(left <= right) {
        val m = (left+right)/2
        if(l[m] <= k) {
            left = m+1
        } else {
            result = m
            right = m-1
        }
    }
    return result
}

fun trv(i: Int, p: String) {
    if(tree[i].isEmpty()) return
    print(p)
    tree[i].forEach {
        print("${it} ")
    }
    println()
    trv(i*2, p + "  ")
    trv(i*2+1, p + "  ")
}

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    readLine().split(" ").map {it.toInt()}.forEach { a.add(it) }
    msort(1, 0, n-1)
    //trv(1, "")
    val bw = System.`out`.bufferedWriter()
    val m = readLine().toInt()
    repeat(m) {
        val (i,j,k) = readLine().split(" ").map {it.toInt()}
        val query = query(1, 0, n-1, i-1, j-1, k)
        bw.write("${query}\n")
    }
    bw.close()
}
