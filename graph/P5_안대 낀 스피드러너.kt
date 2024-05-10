
/**
 * 14451: 안대 낀 스피드러너
 *
 * 순진한 BFS를 돌리면 3^400의 연산 횟수가 나올 수 있다.
 * 각 노드가 가질 수 있는 개수 만큼 노드를 분할하면 최대 20*20*4의 그래프가 된다.
 * 그러면 1600개의 노드만을 BFS로 탐색하면 된다.
 *
 * 그런데 두개의 출발점 모든 곳에서 동일한 움직임을 줬을 때의 최솟값을 구해야된다.
 * DFS를 돌린다면 고려해야할 최소한의 유의미한 상태는 (첫번째 출발의 상태 + 두번째 출발의 상태)이다.
 * 즉 첫번째 출발과 두번째 출발 각각의 좌표와 방향이다.
 * 무의미한 탐색은 첫번째 출발과 두번째 출발의 상태의 변화가 없는 탐색이니 visit에서 고려할 요소는 각 출발의 상태 뿐이다.
 *
 */

data class Point(val x: Int, val y: Int)
data class B(val x: Int, val y: Int, val d: Int) {
    fun isFinished(n: Int): Boolean {
        return x == n-1 && y == 0
    }
    fun step(n: Int, map: Array<Array<Boolean>>): B {
        val offset = OFFSETS[d]
        val next = B(x+offset.x, y+offset.y, d)
        return if(isFinished(n) || !canMove(n, map, next.x, next.y)) this else next
    }
    fun turn(nearbyIndex: Int): B {
        val nearby = NEARBY[d][nearbyIndex]
        return B(x,y,nearby)
    }
}
data class D(val a: B, val b: B) {
    fun isFinished(n: Int) = a.isFinished(n) && b.isFinished(n)
    fun step(n: Int, map: Array<Array<Boolean>>): D {
        return D(a.step(n, map), b.step(n, map))
    }
    fun turn(nearbyIndex: Int): D {
        return D(a.turn(nearbyIndex), b.turn(nearbyIndex))
    }
}

infix fun Int.c(y: Int) = Point(this, y)

val OFFSETS = arrayOf(
    0 c 1, 0 c -1, 1 c 0, -1 c 0 // 하 상 우 좌
)
val NEARBY = arrayOf(arrayOf(2, 3), arrayOf(3, 2), arrayOf(1, 0), arrayOf(0, 1)) // 좌회전, 우회전
val MAX_SCALE = 1600*1600
val CAPACITY = (MAX_SCALE * 1.7).toInt()

fun isInside(n: Int, x: Int, y: Int): Boolean {
    return x in 0 until n && y in 0 until n
}
fun canMove(n: Int, map: Array<Array<Boolean>>, x: Int, y: Int): Boolean {
    return isInside(n, x, y) && map[x][y]
}

fun main() {
    val br = System.`in`.bufferedReader()
    val n = br.readLine().toInt()
    val map = Array(n) {Array(n) {false} }
    for(y in 0 until n) {
        val line = br.readLine()
        for(x in 0 until n) {
            map[x][y] = line[x] == 'E'
        }
    }
    val visit = Array(n) {Array(n) {Array(4) {Array(n) {Array(n) {Array(4) {false} } } } } }

    fun isVisited(d: D): Boolean {
        return visit[d.a.x][d.a.y][d.a.d][d.b.x][d.b.y][d.b.d]
    }
    fun visit(d: D) {
        visit[d.a.x][d.a.y][d.a.d][d.b.x][d.b.y][d.b.d] = true
    }
    var bfs = ArrayList<D>(CAPACITY)
    var nextBfs = ArrayList<D>(CAPACITY)
    bfs.add(D(B(0, n-1, 1), B(0, n-1, 2)))
    var tick = 0
    do {
        do {
            val cur = bfs.removeLast()
            if(cur.isFinished(n)) {
                println(tick)
                return
            }
            for(next in arrayOf(cur.step(n, map), cur.turn(0), cur.turn(1))) {
                if(isVisited(next)) continue
                visit(next)
                nextBfs.add(next)
            }
        } while(bfs.isNotEmpty())
        val t = bfs
        bfs = nextBfs
        nextBfs = t
        nextBfs.clear()
        tick++
    } while(bfs.isNotEmpty())
    throw IllegalArgumentException()
}
