/**
2188번: 축사 배정
이분 매칭
*/
fun main() {
    val (n, m) = readln().split(" ").map { it.toInt() }
    val preferences = Array(n) { readln().split(" ").map { it.toInt() - 1 }.let {
        if(it.size == 1) emptyList()
        else it.subList(1, it.size)
    } }

    val assign = Array(n) { -1 }
    val assigned = Array(m) { -1 }
    val done = BooleanArray(m)

    fun dfs(cow: Int): Boolean {
        for(p in preferences[cow]) {
            if(done[p]) continue
            done[p] = true
            if(assigned[p] != -1 && !dfs(assigned[p])) continue
            assign[cow] = p
            assigned[p] = cow
            return true
        }
        return false
    }

    for(cow in 0 until n) {
        dfs(cow)
        done.fill(false)
    }
    val ans = assign.count { it != -1 }
    println(ans)
}
