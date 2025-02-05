import java.io.BufferedReader
import java.util.PriorityQueue

/**
7662번: 이중 우선순위 큐
*/
class Node(val value: Int) {
    var deleted: Boolean = false
}

fun solve(br: BufferedReader): String = with(br) {
    val k = readLine().toInt()
    val pq = PriorityQueue<Node>(k, Comparator<Node> { a,b-> a.value.compareTo(b.value) })
    val pqReversed = PriorityQueue<Node>(k, Comparator<Node> { a,b-> -(a.value.compareTo(b.value)) })
    var size = 0
    repeat(k) {
        val command = readLine().split(" ")
        val value = command[1].toInt()
        if(command[0] == "I") {
            val node=  Node(value)
            pq.add(node)
            pqReversed.add(node)
            size++
        } else {
            if(size == 0) return@repeat
            size--
            val target = if(value == 1) pqReversed else pq
            while(target.peek().deleted) target.poll()
            val node = target.poll()
            node.deleted = true
        }
    }
    if(size == 0) return "EMPTY"
    listOf(pq, pqReversed).forEach { while (it.peek().deleted) it.poll() }
    return "${pqReversed.peek().value} ${pq.peek().value}"
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val t = readLine().toInt()
    val bw = System.out.bufferedWriter()
    repeat(t) {
        val ans = solve(this)
        bw.write("${ans}\n")
    }
    bw.close()
}
