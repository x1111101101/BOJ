import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

/**
17435번: 합성함수와 쿼리
Sparse Table(희소 배열)을 활용해서 해결했다.
*/
fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val m = readLine().toInt()
    val sparse = Array(m) { Array(20){0} }
    readLine().split(" ").map {it.toInt()-1}.toList().forEachIndexed { idx,v-> sparse[idx][0] = v }
    val q = readLine().toInt()
    for(k in 1 until 20) {
        for(i in 0 until m) {
            sparse[i][k] = sparse[sparse[i][k-1]][k-1]
        }
    }
    System.`out`.bufferedWriter().use {bw->
        repeat(q) {
            var (n,x) = readLine().split(" ").map {it.toInt()}
            x--
            var i = 0
            while(n > 0) {
                if(n%2 == 1) {
                    x = sparse[x][i]
                }
                n /= 2
                i++
            }
            bw.write("${x+1}\n")
        }
    }    
}
