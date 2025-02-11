/**
3273번: 두 수의 합
*/
fun main(): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val a = readLine().split(" ").map { it.toInt() }.sorted()
    val x = readLine().toInt()
    var l = 0
    var r = n-1
    var ans = 0L
    while(l < r) {
        val sum = a[l] + a[r]
        if(sum == x) {
            ans++
            l++
        } else if(sum > x) {
            r--
        } else {
            l++
        }
    }
    println(ans)
}
