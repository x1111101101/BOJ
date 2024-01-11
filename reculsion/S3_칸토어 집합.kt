/**
 *
4779번: 칸토어 집합
(L)(E)(R)의 패턴이 프랙탈 처럼 반복됨
R 값은 L값과 동일하므로 반복해서 계산할 필요가 없음

recursive:
  1. L값 출력, 저장
  2. E 출력
  3. 저장했던 L값 출력

 */

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val w = System.out.bufferedWriter()
    val emptyStrings = ArrayList<String>()
    emptyStrings.add(" ")
    for(i in 1..13) {
        val s = emptyStrings[i-1].let { "$it$it$it" }
        emptyStrings.add(s)
    }
    var input: String? = null
    while(readLine().also { input = it } != null) {
        val n = input!!.toInt()
        val sb = StringBuilder()
        fun rec(depth: Int) {
            if(depth == 0) {
                sb.append('-')
                return
            }
            val u = depth-1
            val empty = emptyStrings[u]
            rec(depth-1)
            val left = sb.toString()
            sb.append(empty)
            sb.append(left)
        }
        rec(n)
        w.write("${sb}\n")
    }
    w.close()
    close()
}
