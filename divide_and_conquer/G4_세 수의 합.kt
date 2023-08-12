import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

/**
2295번: 세 수의 합

원소 a,b,c,k 가 있다.
a + b + c - k = 0인 경우가 있으면 된다.

중간에서 만나기(Meet in the middle) 기법을 사용하기 위해
- 두 원소의 합을 저장하는 집합 A
- 두 원소의 차를 저장하는 집합 B
가 필요하다.

단 B 집합의 원소는 모두 음수여야 한다.
A 집합은 모두 자연수기 때문이다.

중복 선택이 가능하다.


*/
class V(val a: Int, val b: Int) {
    fun isVisited(v: Int): Boolean {
        return a == v || b == v
    }
    fun isVisited(v: V) = isVisited(v.a) || isVisited(v.b)
}
fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val l = Array(n) {readLine().toInt()}
    l.sort()
    val sumMap = HashMap<Int, LinkedList<V>>()
    val subtMap = HashMap<Int, LinkedList<V>>()
    for(a in 0 until n) for(b in a+1 until n) {
        subtMap.computeIfAbsent(l[b]-l[a]) {LinkedList()}.add(V(a,b)) // a-b의 절댓값을 저장. b가 k에 해당
        sumMap.computeIfAbsent(l[b]+l[a]) {LinkedList()}.add(V(a,b))
    }
    for(a in 0 until n) sumMap.computeIfAbsent(l[a]*2) {LinkedList()}.add(V(a,a))
    var ans = 0
    for(sum in sumMap) {
        val need = sum.key
        val li = subtMap[need] ?: continue
        for(v in li) {
            ans = max(ans, l[v.b])
        }
    }
    println(ans)
}
