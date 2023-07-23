import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

/**
25498번: 핸들 뭘로 하지
단순해 보이는 BFS 문제지만 시간 제한이 꽤 빡빡하다.
초기화를 위해 부모 노드에 자식 노드를 저장할 때 단일한 리스트를 사용하지 않고, 자식 노드가 갖는 알파벳 값 별로 분류했다.
이 분류로 자식 노드가 갖는 알파벳 중 사전순으로 가장 마지막으로 오는 알파벳을 26회의 반복문을 돌리는 것만으로 찾을 수 있게 되었다.
이러한 방식을 사용하지 않고, 우선순위 큐나 소트, 완전 탐색으로 사전순으로 가장 뒤쳐지는 값을 찾게 된다면 시간초과가 발생한다.
*/

val codeA = 'a'.toInt()

class Node(val id: Int, val code: Int) {
    override fun hashCode() = id.hashCode()
    var linked = Array(26) {LinkedList<Node>()}
    var parent = this
    var best = -1
    
    fun link(other: Node) {
        linked[other.code].add(other)
    }
    fun findBest() {
        linked[parent.code].remove(parent)
        for(k in 25 downTo 0) {
            if(linked[k].isNotEmpty()) {
                best = k
                break
            }
        }
    }
}

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val chars = readLine().toCharArray()
    val nodes = Array(n) {Node(it, chars[it].toInt() - codeA)}
    repeat(n-1) {
        var (a,b) = readLine().split(" ").map {it.toInt()}
        a--; b--;
        nodes[a].link(nodes[b])
        nodes[b].link(nodes[a])
    }
    var bfs = ArrayList<Node>(50000)
    var new = ArrayList<Node>(50000)
    bfs.add(nodes[0])
    val sb = StringBuilder(500000)
    sb.append((nodes[0].code+codeA).toChar())
    do {
        var best = -1
        for(target in bfs) {
            target.findBest()
            best = max(best, target.best)
        }
        if(best == -1) break
        for(target in bfs) {
            if(target.best != best) continue
            for(l in target.linked[best]) {
                l.parent = target
            }
            new.addAll(target.linked[best])
        }
        val copy = bfs
        bfs = new
        new = copy
        new.clear()
        sb.append((best+codeA).toChar())
    } while(bfs.isNotEmpty())
    println(sb.toString())
}
