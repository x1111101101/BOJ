/**
12865번: 평범한 배낭
*/
fun main() {
    val (n, k) = readln().split(" ").map { it.toInt() }
    val items = Array(n) { readln().split(" ").map { it.toInt() } } // weight, value

    val memo = Array(n) { Array(k+1) { -1 } }

    fun dp(itemIdx: Int, capacity: Int): Int {
        if(itemIdx < 0 || capacity < 0) return 0
        if(memo[itemIdx][capacity] != -1) return memo[itemIdx][capacity]
        val (curWeight, curValue) = items[itemIdx]
        if(curWeight > capacity) return dp(itemIdx-1, capacity)
        val onInclude = dp(itemIdx-1, capacity - curWeight) + curValue
        val onExclude = dp(itemIdx-1, capacity)
        return maxOf(onInclude, onExclude).also { memo[itemIdx][capacity] = it }
    }

    println(dp(n-1, k))
}
