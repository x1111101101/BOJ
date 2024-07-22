/**
14719번: 빗물
DFS로 간단히 구현
*/

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val (h, w) = readLine().split(" ").map { it.toInt() }
    val bh = readLine().split(" ").map { it.toInt() }
    val map = Array(w) {Array(h) {1} }
    for(i in 0 until w) {
        repeat(bh[i]) {
            map[i][it] = 0
        }
    }
    val offsets = listOf(
        0 to 1, 1 to 0, -1 to 0
    )
    fun dfs(x: Int, y: Int) {
        if(x !in 0 until w || y !in 0 until h) return
        if(map[x][y] == 0) return
        map[x][y] = 0
        for(o in offsets) {
            val nx = x + o.first
            val ny = y + o.second
            dfs(nx, ny)
        }
    }
    for(y in 0 until h) {
        dfs(0, y)
        dfs(w-1, y)
    }
    println(map.sumOf { it.sum() })
}
