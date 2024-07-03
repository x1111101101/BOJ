/**
DFS + 비트마스킹으로 간단히 해결
*/
class Solution {

    fun solution(users: Array<String>, banned: Array<String>): Int {
        val visit = Array(users.size) {false}
        val comb = Array(512) {0}
        fun dfs(i: Int) {
            if(i == banned.size) {
                var mask = 0
                for(v in visit.indices) {
                    if(!visit[v]) continue
                    mask = mask.xor((1).shl(v))
                }
                comb[mask] = 1
                return
            }
            val ban = banned[i]
            for(u in 0..users.lastIndex) {
                val user = users[u]
                if(visit[u] || !match(ban, user)) continue
                visit[u] = true
                dfs(i+1)
                visit[u] = false
            }
        }
        dfs(0)
        return comb.sum()
    }
}

fun match(ban: String, user: String): Boolean {
    if(user.length != ban.length) return false
    for(i in ban.indices) {
        if(ban[i] == '*') continue
        if(ban[i] != user[i]) return false
    }
    return true
}
