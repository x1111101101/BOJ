/**
 * 11375번: 열혈강호
 * 이분매칭 기초 문제
 * dfs로 해결
 */
fun main() = with(System.`in`.bufferedReader()) {
    val (n, m) = readLine().split(" ").map { it.toInt() }
    val jobs = Array(n) { readLine().split(" ").map { it.toInt() - 1 } }
        .map { it.subList(1, it.size) }
    val allocated = Array(m) { -1 }
    val done = Array(m) { false }
    fun dfs(person: Int): Boolean {
        for (job in jobs[person]) {
            if(done[job]) continue
            done[job] = true
            if (allocated[job] == -1 || dfs(allocated[job])) {
                allocated[job] = person
                return true
            }
        }
        return false
    }
    val ans = (0 until n).count { done.fill(false); dfs(it) }
    println(ans)
}
