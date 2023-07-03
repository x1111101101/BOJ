import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

/**
2473번: 세 용액
수열에서 세 숫자를 골라 합을 구했을 때 그 절댓값이 가장 낮은 조합을 찾는 문제.
수열의 길이가 최대 5000이고, (5000 C 2)가 천만 정도라서 브루트포스로 구현 가능할것으로 판단했다.
두 수를 선택하고 합을 구한 뒤, (구한 합 * -1)에 가장 가까운 수를 이분 탐색을 이용해서 찾았다.
이분 탐색에서 현재 선택한 두 수와 중복되는 수가 탐색될 수 있기 때문에 중복처리를 해줬다.
*/
fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val nums = readLine().split(" ").map { it.toLong() }.sorted()
    val ans = arrayOf(0L, 0L, 0L)
    var min = Long.MAX_VALUE
    
    fun lowerbound(v: Long): Int {
        var a = 0
        var b = n-1
        var r = -1
        while(a <= b) {
            var m = (a+b)/2
            val mv = nums[m]
            if(mv >= v) {
                b = m-1
                r = m
            } else {
                a = m+1
            }
        }
        return r
    }
    
    fun tryUpdate(a: Long, b: Long, c: Long) {
        val k = abs(a+b+c)
        if(k < min) {
            ans[0] = a
            ans[1] = b
            ans[2] = c
            min = k
        }
        
    }
    
    for(a in 0 until n) {
        for(b in a+1 until n) {
            val p = nums[a]+nums[b]
            var c = lowerbound(-p)
            if(c == -1) {
                c = n-1
            }
            for(i in c until n) {
                if(a == i || b == i) continue
                tryUpdate(nums[a], nums[b], nums[i])
                break
            }
            for(k in 1..c) {
                val i = c-k
                if(a == i || b == i) continue
                tryUpdate(nums[a], nums[b], nums[i])
                break
            }
        }
    }
    println(ans.sorted().joinToString(" "))
}
