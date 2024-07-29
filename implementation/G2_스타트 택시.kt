import java.util.LinkedList

/**
19238번: 스타트 택시
*/
val OFFSETS = listOf(1, -1, 1, -1)

class BfsState(var loc: List<Int>, var moved: Int)
fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    var (n, passengerCount, fuel) = readLine().split(" ").map { it.toInt() }
    val wall = Array(n) { readLine().split(" ").map { it.toInt() == 1 } }
    var loc = readLine().split(" ").map { it.toInt() - 1 }
    val passengers = Array(passengerCount) { readLine().split(" ").map { it.toInt() - 1 } }
    val passengersMap = Array(n) { Array(n) { -1 } }
    passengers.forEachIndexed { idx, v ->
        passengersMap[v[0]][v[1]] = idx
    }

    fun bfs(initial: List<Int>, con: (BfsState) -> Boolean): BfsState {
        var bfs = LinkedList<List<Int>>()
        val visit = Array(n) { Array(n) { false } }
        visit[initial[0]][initial[1]] = true
        val state = BfsState(initial, 0)
        var terminated = false
        bfs.add(initial)
        if(con(state)) return state
        do {
            val newBfs = LinkedList<List<Int>>()
            state.moved++
            do {
                val (x, y) = bfs.removeLast()
                for (o in 0..3) {
                    val new = mutableListOf(x, y)
                    new[o / 2] += OFFSETS[o]
                    val (nx, ny) = new
                    if (nx !in 0 until n || ny !in 0 until n || visit[nx][ny] || wall[nx][ny]) continue
                    visit[nx][ny] = true
                    state.loc = new
                    newBfs.add(new)
                    if(con(state)) terminated = true
                }
            } while (bfs.isNotEmpty())
            bfs = newBfs
        } while (bfs.isNotEmpty() && !terminated)
        return state
    }
    
    fun pickPassenger(): Pair<Int, List<Int>>? {
        val candidates = ArrayList<List<Int>>()
        var moved = 0
        bfs(loc) {
            val (x, y) = it.loc
            if (passengersMap[x][y] != -1) {
                candidates.add(it.loc)
                moved = it.moved
                return@bfs true
            }
            return@bfs false
        }
        if(candidates.isEmpty()) return null
        val q =  candidates
            .minWith { a,b->
                val k = a[0].compareTo(b[0])
                if(k != 0) k else a[1].compareTo(b[1])
            }
        return moved to q
    }

    var complete = 0
    while (complete < passengerCount) {
        val board = pickPassenger()
        if(board == null || board.first > fuel) {
            println(-1)
            return@with
        }
        val passengerId = passengersMap[board.second[0]][board.second[1]]
        val passenger = passengers[passengerId]
        passengersMap[board.second[0]][board.second[1]] = -1
        loc = board.second
        fuel -= board.first
        var moved = -1
        bfs(loc) {
            val (x,y) = it.loc
            if(passenger[2] == x && passenger[3] == y) {
                moved = it.moved
                return@bfs true
            }
            return@bfs false
        }
        if(moved == -1 || moved > fuel) {
            println(-1)
            return@with
        }
        loc = passengers[passengerId].run { listOf(this[2], this[3]) }
        fuel += moved
        complete++
    }
    println(fuel)
}
