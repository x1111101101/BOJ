import java.util.LinkedList
import kotlin.math.min

/**
2098번: 외판원 순회

계단수와 유사한 문제

비트필드에 방문 노드들을 기록

n=5, 시작 도시 = start
일때 0부터 4까지 start를 증가시키면서
  W(start, 11111(2진수))
의 최솟값이 정답이 된다.

W(start, 11111) =
  min(W(0, 01111) + dist[0][start], W(1, 10111) + dist[1][start], . . .)

즉 W(c, v) = 
{x는 c와 연결된 모든 도시 | W(x, v-x) + dist[x][c] } 중 최솟값
(v가 공집합일 땐 시작도시로 부터의 거리, v-x는 집합 연산)

계단수 문제를 먼저 풀어봐서 점화식을 쉽게 구할 수 있었다.
 */

fun tob(i: Int): String {
    return (0..3).map { q->
        val it = 3-q
        val and = (1).shl(it).and(i)
        if(and == 0) 0 else 1
    }.joinToString("")
}

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val dist = Array(n) {readLine().split(" ").map { it.toInt() }.map { if(it == 0) -1 else it }}
    val linked = Array(n) {ArrayList<Pair<Int,Int>>()}
    dist.indices.forEach { from->
        dist[from].forEachIndexed { to, w ->
            if(w != -1) linked[to].add(from to w)
        }
    }
    val inf = Int.MAX_VALUE
    var ans = inf
    for(start in 0 until n) {
        val gv = ((1).shl(n) - 1).xor((1).shl(start))
        val cache = Array(n) {Array((1).shl(n)) {-11} }

        fun dp(to: Int, visit: Int): Int {
            if((1).shl(to).and(visit) != 0) {
                return -1
            }
            if(cache[to][visit] != -11) return cache[to][visit]
            if(visit == 0) {
                return dist[start][to]
            }
            var min = inf
            for(p in linked[to]) {
                val (i, d) = p
                val mask = (1).shl(i)
                val nvisit = visit.xor(visit.and(mask))
                if(nvisit == visit) continue
                val w = dp(i, nvisit)
                if(w != -1) min = min(min, w+d)
            }
            val result = if(min == inf) -1 else min
            cache[to][visit] = result
            return result
        }
        val w = dp(start, gv)
        if(w != -1) ans = min(ans, w)
    }
    println(ans)
}
