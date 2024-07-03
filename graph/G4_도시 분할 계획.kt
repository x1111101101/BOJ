/**
1647번: 도시 분할 계획
크루스칼 알고리즘에서 마지막 간선을 빼버린게 정답과 같다.
*/

data class Edge(val a: Int, val b: Int, val w: Int): Comparable<Edge> {
    override fun compareTo(other: Edge): Int {
        return -(w.compareTo(other.w))
    }

}

fun main() = with(System.`in`.bufferedReader()){
    val (n, m) = readLine().split(" ").map { it.toInt() }
    val nodes = Array(n+1) {HashMap<Int,Int>()}
    repeat(m) {
        val (a,b,c) = readLine().split(" ").map { it.toInt() }
        val prev = nodes[a][b]
        if(prev == null || c < prev) {
            nodes[a][b] = c
            nodes[b][a] = c
        }
    }
    val edges = ArrayList<Edge>()
    for(a in 1..n) {
        nodes[a].forEach { k, v ->
            if(k > a) edges.add(Edge(a,k,v))
        }
    }
    edges.sort()
    val parent = Array(n+1) {it}
    fun find(i: Int): Int{
        if(parent[i] == i) return i
        parent[i] = find(parent[i])
        return parent[i]
    }

    fun union(a: Int, b: Int): Boolean {
        val pa = find(a)
        val pb = find(b)
        if(pa == pb) return false
        if(pa > pb) parent[pa] = pb
        else parent[pb] = pa
        return true
    }
    val included = ArrayList<Int>()
    while(edges.isNotEmpty()) {
        val edge = edges.removeLast()
        if(union(edge.a, edge.b)) {
            included.add(edge.w)
            if(included.size == n-1) break
        }
    }
    println(included.sum() - included.last())


}
