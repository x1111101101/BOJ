/**
1074번: Z
기초적인 분할정복 문제
*/

val OFFSETS = arrayOf(
    0 to 0, 1 to 0, 0 to 1, 1 to 1
)

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, ty, tx) = readLine().split(" ").map { it.toInt() }
    val len = 1 shl n

    fun find(x: Int, y: Int, len: Int): Int {
        if (len == 1) return 0
        val half = len shr 1
        val pow = half * half
        for (i in 0 until 4) {
            val (dx, dy) = OFFSETS[i]
            val cx = x + half * dx
            val cy = y + half * dy
            if (tx !in cx until (cx + half)) continue
            if (ty !in cy until (cy + half)) continue
            return pow * i + find(cx, cy, half)
        }
        throw IllegalStateException()
    }
    println(find(0, 0, len))
}
