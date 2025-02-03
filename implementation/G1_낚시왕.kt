/**
17143번: 낚시왕
*/

class Shark(var direction: Int, val speed: Int, val size: Int)
class Offset(val axis: Int, val direction: Int)

val OFFSETS = arrayOf(
    Offset(0, -1), Offset(0, 1), Offset(1, 1), Offset(1, -1)
)

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (r, c, m) = readLine().split(" ").map { it.toInt() }
    val len = arrayOf(r, c)
    var map = Array(r) { Array<Shark?>(c) { null } }
    repeat(m) {
        val (x, y, s, d, z) = readLine().split(" ").map { it.toInt() }
        val speed = s % ((len[OFFSETS[d - 1].axis] - 1) * 2)
        val shark = Shark(d - 1, speed, z)
        map[x - 1][y - 1] = shark
    }
    fun nextDestination(shark: Shark, x: Int, y: Int): Pair<Int, Int> {
        val locs = arrayOf(x, y)
        val offset = OFFSETS[shark.direction]
        val axis = offset.axis
        locs[axis] += offset.direction * shark.speed
        while(true) {
            val oppositeDirection = (shark.direction / 2) * 2 + 1 - shark.direction % 2
            if (locs[axis] < 0) {
                shark.direction = oppositeDirection
                locs[axis] *= -1
            } else if (locs[axis] >= len[axis]) {
                shark.direction = oppositeDirection
                val left = locs[axis] - len[axis] + 2
                locs[axis] = len[axis] - left
            } else break
        }
        return locs[0] to locs[1]
    }

    fun move() {
        val dest = Array(r) { Array<Shark?>(c) { null } }
        for(x in 0 until r) {
            for(y in 0 until c) {
                val shark = map[x][y] ?: continue
                val (nx, ny) = nextDestination(shark, x, y)
                val prev = dest[nx][ny]
                if(prev != null && prev.size > shark.size) continue
                dest[nx][ny] = shark
            }
        }
        map = dest
    }

    fun catch(y: Int): Int {
        val x = (0 until r).firstOrNull { map[it][y] != null } ?: return 0
        val shark = map[x][y]!!
        map[x][y] = null
        return shark.size
    }

    var ans = 0
    repeat(c) { x ->
        ans += catch(x)
        move()
    }
    println(ans)
}
