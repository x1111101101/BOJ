/**
1759번: 암호 만들기
*/
fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (l, c) = readLine().split(" ").map { it.toInt() }
    val chars = readLine().split(" ").map { it[0] }.sortedBy { it.code }
    val isVowels = Array(c) { "aeiou".contains(chars[it]) }
    val arr = Array(l) { -1 }
    val bw = System.out.bufferedWriter()
    val count = Array(2) { 0 }
    fun comb(i: Int) {
        if (i == l) {
            if(count[0] < 1 || count[1] < 2) return
            bw.write(arr.map { chars[it] }.joinToString("") + "\n")
            return
        }
        val start = if (i == 0) 0 else arr[i - 1] + 1
        for (k in start until c) {
            val idx = if(isVowels[k]) 0 else 1
            arr[i] = k
            count[idx]++
            comb(i+1)
            count[idx]--
        }
    }
    comb(0)
    bw.close()
}
