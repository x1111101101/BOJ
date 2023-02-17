import java.util.*;
import java.lang.Math.abs;

/**
11286번: 절댓값 힙
Priority Queue를 직접 구현해서 해결해봤다.
*/

class PQ {
    // note: 절댓값이 가장 작은 값이 여러개일 때는, 가장 작은 수를 출력하고, 그 값을 배열에서 제거한다.
    companion object {
        // b가 우선순위가 높을 경우 1 리턴
        fun compare(a: Long, b: Long): Int {
            val va = abs(a)
            val vb = abs(b)
            if(va > vb) return 1
            if(va < vb) return -1
            return if(a>b) 1 else if(a==b) 0 else -1
        }
    }
    
    val table = LongArray(100001) {0}
    var nxt = 1
    val isEmpty get() = nxt == 1
    
    fun swap(a: Int, b: Int) {
        val t = table[a]
        table[a] = table[b]
        table[b] = t
    }
    
    fun push(num: Long) {
        table[nxt] = num
        var i = nxt++
        while(i > 1) {
            val n = i/2
            if(compare(table[n], table[i]) == 1) {
                swap(i, n)
                i = n
            } else break
        }
    }
    
    fun pop(): Long {
        val result = table[1]
        swap(1, --nxt)
        var i = 1
        while(i*2 < nxt) {
            var k = i*2
            if(k+1 < nxt && compare(table[k], table[k+1]) == 1) {
                k++
            }
            if(compare(table[i], table[k]) == 1) {
                swap(i, k)
                i = k
            } else {
                break
            }
        }
        return result
    }
    
}


fun main(args: Array<String>) = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val pq = PQ()
    System.`out`.bufferedWriter().use { w->
        repeat(n) {
            val num = readLine().toLong()
            if(num == 0L) {
                if(pq.isEmpty) {
                    w.write("0\n")
                } else {
                    w.write("${pq.pop()}\n")
                }
            } else {
                pq.push(num)
            }
        }
    }
    
}
