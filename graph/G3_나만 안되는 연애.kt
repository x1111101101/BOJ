/**
14621번: 나만 안되는 연애
문제 조건과 일치하지 않는 간선들을 필터링한 뒤
필터링된 간선들에 대해서 크루스칼 돌려서 간단히 해결
*/

fun main() = with(System.`in`.bufferedReader()) {
	val (n, m) = readLine().split(" ").map {it.toInt()}
    val type = readLine().split(" ").map {if(it == "W") 0 else 1}
	  val edges = Array(m) {
        val (a,b,w) = readLine().split(" ").map {it.toInt()}
        arrayOf(a-1, b-1, w)
    }.filter { type[it[0]] != type[it[1]] }.sortedBy { it[2] }
    
    val parent = Array(n) { it }
    fun find(i: Int): Int {
      if(parent[i] == i) return i
      parent[i] = find(parent[i])
      return parent[i]
    }
    fun union(a: Int, b: Int): Boolean {
      val pa = find(a)
      val pb = find(b)
      if(pa == pb) return false
      parent[pb] = pa
      return true
    }
    val ans = ArrayList<Array<Int>>()
    for(e in edges) {
      val (a,b,w) = e
      if(!union(a, b)) continue
      ans += e
      if(ans.size == n-1) break
    }
    if(ans.size != n-1) {
      println(-1)
    } else {
      println(ans.map {it[2]}.sum())
    }
    
}
