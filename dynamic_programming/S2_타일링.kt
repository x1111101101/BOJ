import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

/**
1793번: 타일링

타일들은 아래 세 패턴으로 한정할 수 있다
A: 세로로 긴 1x2 타일
B: 2x2 텅 빈 타일
C: 가로로 긴 1x2 타일 두개를 위아래로 겹쳐놓은 것

n이 1 증가할 때 마다 타일들의 변화
A:
    1. 오른쪽에 A타일을 하나 붙인다
    2. 맨 오른쪽 A타일을 하나 제거하고 B타일을 붙인다
    3. 맨 오른쪽 A타일을 하나 제거하고 C타일을 붙인다
B, C: 오른쪽에 A타일을 하나 붙인다

B,C에서 맨 오른쪽 2개를 제거하는 경우를 고려하는건 무의미하다. 2 단계 전에서 해당 모양은 이미 처리했다.

결과적으로 A타일의 개수는 B타일과 C타일의 개수의 합 만큼 늘어나고
B, C타일의 개수는 A타일의 개수와 같게 된다.
B, C타일의 개수를 뭉뚱그려서 처리할 수 있다.
*/

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val ans = ArrayList<BigInteger>(251)
    ans.add(BigInteger.ONE) // 0일 때 경우의 수가 1임에 주의
    ans.add(BigInteger.ONE)
    val t = arrayOf(BigInteger.ZERO,BigInteger.ONE) // B,C 타일 개수 / A 타일 개수
    repeat(249) {
        var t0 = t[1].add(t[1])
        var t1 = t[1].add(t[0])
        t[0] = t0
        t[1] = t1
        val value = t[0].add(t[1])
        ans.add(value)
    }
    var k: String? = readLine()
    while(k != null) {
        val n = k!!.toInt()
        println(ans[n])
        k = readLine()
    }
}
