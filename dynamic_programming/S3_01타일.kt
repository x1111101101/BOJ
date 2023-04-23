import java.util.*;
import java.lang.Math.*
import java.io.*
import kotlin.collections.*

/**
1904번: 01타일

0으로 끝나는 타일을 0타일, 1로 끝나는 타일을 1타일이라 하자.

n이 1씩 증가 할 때마다
0타일: 1을 뒤에 추가한다 -> 1 타일이 된다
1타일: 
    A. 맨 끝 1을 빼고 0을 두개 추가한다
    B. 1을 뒤에 추가한다.

결국 0타일의 개수는 1타일의 개수와 같아지고
1타일의 개수는 0타일의 개수와 1타일의 개수의 합과 같아지는 것이다.

*/

val MOD = 15746

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) { 
    val n = readLine().toInt()
    val t = arrayOf(0,1) // 0 타일 개수, 1 타일 개수
    repeat(n-1) {
        var t0 = t[1]
        var t1 = t[0] + t[1]
        t[0] = t0 % MOD
        t[1] = t1 % MOD
    }
    val ans = (t[0] + t[1]) % MOD
    println(ans)
}
