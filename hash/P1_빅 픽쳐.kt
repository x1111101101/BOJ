/**
 * 10538번: 빅 픽쳐
 * hashing으로 차원 축소하고, KMP로 패턴을 빠르게 탐색
 */
class KMPPattern<T>(val pattern: Array<T>) {
    val failure = Array(pattern.size) { -1 }

    init {
        var i = 1
        var k = 0
        while (i < pattern.size) {
            if (pattern[i] == pattern[k]) {
                failure[i] = k++
                i++
            } else if (k != 0) {
                k = failure[k - 1] + 1
            } else i++
        }
    }

    fun findAll(target: Array<T>): List<Int> {
        val match = ArrayList<Int>()
        var i = -1
        var k = 0
        while (++i < target.size) {
            if (pattern[k] == target[i]) {
                if (k != pattern.lastIndex) {
                    k++
                    continue
                }
                match += i
                k = failure[k] + 1
            } else {
                if (k == 0) continue
                i--
                k = failure[k - 1] + 1
            }
        }
        return match
    }
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (hp, wp, hm, wm) = readLine().split(" ").map { it.toInt() }
    val pmap = Array(hp) { readLine().map { if (it == 'o') 1 else 0 }.toTypedArray() }
    val mmap = Array(hm) { readLine().map { if (it == 'o') 1 else 0 }.toTypedArray() }

    val sftV = (((64 + hp - 1) / hp).coerceAtLeast(1) + 1).coerceAtMost(63)
    val sftH = ((((64 + wp - 1) / wp).coerceAtLeast(2) + 1).let { if (sftV == it) it + 1 else it }).coerceAtMost(62)
    val mV = if(hp == 1) 0 else 3
    val mH = if(wp == 1) 0 else 1

    fun checkMatch(row: Int, col: Int): Boolean {
        val r = row - hp + 1
        val c = col - wp + 1
        for (x in 0 until wp) for (y in 0 until hp) {
            if (pmap[y][x] != mmap[r + y][c + x]) return false
        }
        return true
    }

    fun calcVHash(map: Array<Array<Int>>): Array<Array<Long>> {
        val hashes = Array(map.size) { Array(map[0].size) { 0L } }
        for (col in map[0].indices) {
            var hash = 0L
            var count = 0
            for (row in map.indices) {
                if (row >= hp) count -= map[row - hp][col]
                count += map[row][col]
                hash = (hash shl sftV) * mV + map[row][col] * 7
                hashes[row][col] = hash + count * 7
            }
        }
        return hashes
    }

    fun calcHHash(map: Array<Array<Int>>): Array<Array<Long>> {
        val hashes = Array(map.size) { Array(map[0].size) { 0L } }
        for (row in map.indices) {
            var hash = 0L
            var count = 0
            for (col in map[0].indices) {
                if (col >= wp) count -= map[row][col - wp]
                count += map[row][col]
                hash = (hash shl sftH) * mH + 1 - map[row][col]
                hashes[row][col] = hash + count * 31
            }
        }
        return hashes
    }

    val hashPV = calcVHash(pmap).let { map -> (0 until wp).map { map[hp - 1][it] } }
    val hashPH = calcHHash(pmap).let { it.map { it.last() } }
    val hashMV = calcVHash(mmap)
    val hashMH = calcHHash(mmap)
    val hit = Array(hm) { Array(wm) { 0 } }
    val kmpV = KMPPattern(hashPV.toTypedArray())
    val kmpH = KMPPattern(hashPH.toTypedArray())
    for (row in 0 until hm) {
        val sliced = hashMV[row]
        kmpV.findAll(sliced).forEach { hit[row][it]++ }
    }
    for (col in 0 until wm) {
        val sliced = (0 until hm).map { row -> hashMH[row][col] }.toTypedArray()
        kmpH.findAll(sliced).forEach { hit[it][col]++ }
    }
    var ans = 0
    for (row in hp - 1 until hm) {
        for (col in wp - 1 until wm) {
            if (hit[row][col] != 2) continue
            ans++
        }
    }
    println(ans)
}
