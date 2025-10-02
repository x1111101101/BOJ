/**
2912번: 백설공주와 난쟁이
- mo's algorithm 사용.
- 연산 횟수 최소화하는 bucket 크기 3000을 사용.
- 연결리스트 두개를 사용해서 frequency 카운팅 함.
  - 브루트포스 안쓰려고 연결리스트 구현한건데, 다른 사람들 풀이 보니 다 브루트포스 씀. 심지어 더 빠름.
*/
val COLOR_SCALE = 10000
var sqrt = 3000

data class Query(val start: Int, val end: Int) : Comparable<Query> {
    var result = 0
    val chunk = start / sqrt
    override fun compareTo(other: Query): Int {
        val q1 = chunk
        val q2 = other.chunk
        if (q1 != q2) return q1 - q2
        return end - other.end
    }
}



data class NodeGroup(val frequency: Int, var size: Int = 0) {
    var prev: NodeGroup = this
    var next: NodeGroup = this
    var firstNode: Node? = null


    fun addNode(node: Node) {
        node.group = this
        if (size++ == 0) {
            firstNode = node
            node.prev = node
            node.next = node
            return
        }
        node.next = firstNode!!.also { it.prev = node }
        node.prev = node
        firstNode = node
    }

    fun removeNode(node: Node) {
        if (--size == 0) {
            firstNode = null
            return
        }
        if (firstNode == node) {
            firstNode = node.next.also { it.prev = it }
            return
        }
        if (node.next == node) {
            node.prev.next = node.prev
            return
        }
        node.prev.next = node.next.also { it.prev = node.prev }
    }
}

class Node(val color: Int, var group: NodeGroup) {
    var prev: Node = this
    var next: Node = this
}

class Counter(private val unique: Int) {

    private val nodes = arrayOfNulls<Node>(COLOR_SCALE)
    private var firstGroup = NodeGroup(0)
    private var lastGroup = firstGroup

    fun printGroups() {
        var g = firstGroup
        val li = mutableListOf<NodeGroup>()
        while(true) {
            li += g
            if(g.next == g) break
            g = g.next
        }
        println(li.joinToString(" "))
    }

    fun increase(color: Int) {
        val node = getNode(color)
        val currentGroup = node.group
        val nextGroupExists = currentGroup.next.frequency == currentGroup.frequency + 1
        val nextGroup = if (!nextGroupExists) {
            NodeGroup(currentGroup.frequency + 1)
                .also { newGroup ->
                    if(currentGroup === lastGroup) lastGroup = newGroup
                    else {
                        currentGroup.next.prev = newGroup
                        newGroup.next = currentGroup.next
                    }
                    currentGroup.next = newGroup
                    newGroup.prev = currentGroup
                }
        } else currentGroup.next
        currentGroup.removeNode(node)
        nextGroup.addNode(node)
        if(currentGroup.size == 0 && currentGroup.frequency != 0) {
            nextGroup.prev = currentGroup.prev
            currentGroup.prev.next = nextGroup
        }
    }

    fun decrease(color: Int) {
        val node = getNode(color)
        val currentGroup = node.group
        val nextGroupExists = currentGroup.prev.frequency == currentGroup.frequency - 1
        val nextGroup = if (!nextGroupExists) {
            NodeGroup(currentGroup.frequency - 1)
                .also { newGroup ->
                    if(currentGroup.prev != currentGroup) {
                        currentGroup.prev.next = newGroup
                        newGroup.prev = currentGroup.prev
                    } else {
                        newGroup.prev = currentGroup
                    }
                    currentGroup.prev = newGroup
                    newGroup.next = currentGroup
                }
        } else currentGroup.prev
        currentGroup.removeNode(node)
        nextGroup.addNode(node)
        if(currentGroup.size == 0) {
            if(currentGroup === lastGroup) {
                lastGroup = nextGroup
                lastGroup.next = lastGroup
                return
            }

            nextGroup.next = currentGroup.next
            currentGroup.next.prev = nextGroup

        }
    }

    fun answer(range: IntRange): Int {
        val maxFrequency = lastGroup.frequency
        val count = range.last - range.first + 1
        val isPretty = maxFrequency > count / 2
        if (!isPretty) return 0
        return lastGroup.firstNode!!.color + 1
    }

    private fun getNode(color: Int): Node {
        val node = nodes[color] ?: Node(color, firstGroup).also { newNode ->
            nodes[color] = newNode
            firstGroup.addNode(newNode)
        }
        return node
    }
}


fun main() {
    val (n, c) = readln().split(" ").map { it.toInt() }
    val caps = readln().split(" ").map { it.toInt() - 1 }
    val m = readln().toInt()
    val queries = Array(m) { readln().split(" ").map { it.toInt() - 1 }.let { (a, b) -> Query(a, b) } }

    // sort queries
    val queriesSorted = queries.copyOf().also { it.sort() }

    // first query
    var counter = Counter(c)
    var left = 0
    var right = 0
    var lastBucketIdx = -1
    // other queries
    for (query in queriesSorted) {
        if (lastBucketIdx != query.chunk) {
            lastBucketIdx = query.chunk
            counter = Counter(c)
            for (i in query.start..query.end) {
                counter.increase(caps[i])
            }
            left = query.start
            right = query.end
            query.result = counter.answer(left..right)
            continue
        }

        while (left > query.start) counter.increase(caps[--left])
        while (right < query.end) counter.increase(caps[++right])
        while (left < query.start) counter.decrease(caps[left++])
        while (right > query.end) counter.decrease(caps[right--])
        query.result = counter.answer(left..right)
    }

    // print answer
    println(queries.joinToString("\n") { if (it.result == 0) "no" else "yes ${it.result}" })
}
