/**
1202번: 보석 도둑
가치 높은 순으로 그리디하게 해결, 이분 탐색 활용.
리스트의 중간 값 제거로 인한 연산을 피하기 위해 가방 마다 IntWrap LinkedList 할당
*/

class Item(val weight: Int, val value: Int) : Comparable<Item> {
    override fun compareTo(other: Item): Int {
        val v = value.compareTo(other.value)
        if (v == 0) return weight.compareTo(other.weight)
        return v
    }
}

class IntWrap(var value: Int) {
    var ref: IntWrap? = null

    fun get(): Int {
        return getLRef().value
    }

    fun getLRef(): IntWrap {
        if(this.ref == null) return this
        val r = this.ref!!.getLRef()
        this.ref = r
        return r
    }
}


fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val (n, k) = readLine().split(" ").map { it.toInt() }
    val items = Array(n) { readLine().split(" ").map { it.toInt() }.let { Item(it[0], it[1]) } }
    val bags = Array(k) { readLine().toInt() }
    val jump = Array<IntWrap?>(k) { null }
    items.sort()
    bags.sort()
    fun lowerbound(l: Int, r: Int, p: Int): Int {
        var left = l
        var right = r
        var result = -1
        while (left <= right) {
            val mi = (left + right) / 2
            if(bags[mi] < p) {
                left = mi + 1
            } else {
                result = mi
                right = mi-1
            }
        }
        return result
    }
    var ans = 0L
    var max = bags.lastIndex

    fun bindAt(idx: Int) {
        val wrap = IntWrap(idx+1)
        jump[idx] = wrap
        if(idx > 0) {
            val left = jump[idx-1]
            if(left != null) {
                jump[idx] = left
                left.value = idx+1
            }
        }
        if(idx < jump.lastIndex) {
            val right = jump[idx+1]
            if(right != null) {
                jump[idx]!!.ref = right
            }
        }
    }

    fun tryBind(item: Item, left: Int = 0): Boolean {
        val lb = lowerbound(left, max, item.weight)
        if(lb == -1) return false
        val j = jump[lb]
        if(j != null) {
            if(j.get() >= bags.size) {
                return false
            }
            bindAt(j.get())
            return true
        }
        bindAt(lb)
        return true
    }

    for(i in items.lastIndex downTo 0) {
        val item = items[i]
        if(tryBind(item)) ans += item.value
    }
    println(ans)
}
