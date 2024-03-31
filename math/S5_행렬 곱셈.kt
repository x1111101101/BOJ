import java.io.BufferedReader

/**
2740번: 행렬 곱셈
*/

fun readMatrix(br: BufferedReader): Array<List<Int>> {
    val (n,m) = br.readLine().split(" ").map { it.toInt() }
    return Array(n) { br.readLine().split(" ").map { it.toInt() } }
}

fun main() = with(System.`in`.bufferedReader()) {
    val a = readMatrix(this)
    val b = readMatrix(this)
    if(a.isEmpty() || b.isEmpty() || b[0].isEmpty()) return@with
    val n = a.size
    val k = b.size
    val m = b[0].size
    val matrix = Array(n) {IntArray(m)}
    for(x in 0 until n) {
        for(y in 0 until m) {
            var sum = 0
            for(i in 0 until k) {
                sum += a[x][i] * b[i][y]
            }
            matrix[x][y] = sum
        }
    }
    println(matrix.map { it.joinToString(" ") }.joinToString("\n"))
}
