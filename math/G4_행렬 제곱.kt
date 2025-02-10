/**
10830번: 행렬 제곱
분할 정복을 이용해서 거듭 제곱 연산 수행
*/

typealias Mat = Array<Array<Int>>

fun Mat.print() {
    for (x in indices) {
        println(this[x].joinToString(" "))
    }
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, b) = readLine().split(" ").let { it[0].toInt() to it[1].toLong() }
    val mod = 1000
    val mat: Mat = Array(n) { readLine().split(" ").map { it.toInt()%mod }.toTypedArray() }
    val scale = 1000_001
    val memo = arrayOfNulls<Mat>(scale+1)

    fun multiply(a: Mat, b: Mat): Mat {
        val result = Array(n) { Array(n) { 0 } }
        for (x in 0 until n) {
            for (ty in 0 until n) {
                var sum = 0
                for (y in 0 until n) {
                    sum = (sum + a[x][y] * b[y][ty]) % mod
                }
                result[x][ty] = sum
            }
        }
        return result
    }

    fun dp(e: Long): Mat {
        if(e == 1L) return mat
        if(e <= scale && memo[e.toInt()] != null) return memo[e.toInt()]!!
        val k = e/2
        val left = e%2
        val kmat = dp(k)
        val result = multiply(kmat, kmat).let { if(left == 0L) it else multiply(it, mat) }
        if(e <= scale) {
            memo[e.toInt()] = result
        }
        return result
    }
    dp(b).print()
}
