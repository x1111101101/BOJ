import java.util.*;
import java.lang.Math.*
import java.io.*
import kotlin.collections.*

/**
5670번: 휴대폰 자판
트라이 자료구조로 해결
*/

fun main(args: Array<String>) = with(System.`in`.bufferedReader()) {
    var ts = readLine()
    val bw = System.`out`.bufferedWriter()
    while(ts != null && !ts.isEmpty()) {
        val n = ts.toInt()
        val t = TestCase()
        repeat(n) { t.addToDictionary(readLine()) }
        bw.write(String.format("%.2f", t.calcAvr()))
        bw.write("\n")
        ts = readLine()
    }
    bw.close()
}

open class Node {
    val childs = HashMap<Char, Node>()
    var exist = false
}

class TestCase: Node() {
    
    val dset = ArrayList<String>()
    
    fun addToDictionary(s: String) {
        dset.add(s)
        var node: Node = this
        s.toCharArray().forEach {
            node = node.childs.computeIfAbsent(it) {Node()}
        }
        node.exist = true
    }
    
    fun calcAvr(): Double {
        var touch = 0
        dset.forEach { touch += simulate(it) }
        return touch.toDouble()/dset.size
    }
    
    fun simulate(s: String): Int {
        var count = 1
        var node = childs[s[0]]!!
        var i = 0
        
        fun skip() {
            while(!node.exist) {
                if(node.childs.size == 1) {
                    node = node.childs[s[++i]]!!
                } else break
            }
        }
        skip()
        while(i < s.lastIndex) {
            count++
            node = node.childs[s[++i]]!!
            skip()
        }
        return count
    }
    
    
}
