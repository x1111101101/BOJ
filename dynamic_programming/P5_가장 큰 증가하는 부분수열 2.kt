import java.util.*;
import java.lang.Math.*
import java.io.*
import java.math.*
import kotlin.collections.*

/**
27989번: 가장 큰 증가하는 부분 수열 2

간 단계의 구성 요소:
- 수열의 길이
- 수열의 합
- 마지막 수

{1, 9, 3, 6, 6, 7, 2, 4}
1. 1 (1)
2. 1 9 (10)
3. 1 3 (3)
4. 1 6 (7)
5.
6. 1 3 6 7 (17) -> 2번 삭제
7. 1 2 (3)
8. 1 3 4 (8) -> 4번 삭제

- 마지막 수가 다른 수열보다 큰데 전체 합이 더 작다면 그 수열은 쓸모가 없다

DP 구성:
입력 수열의 첫번째 항 부터 마지막 항까지 순서대로 탐색

각 단계에 필요한 계산
입력 수열 항이 a일 때:
- 마지막 값이 a보다 작은 이전 단계들에서 만들어진 수열 중 합이 가장 큰 수열 -> 세그먼트트리

세그먼트트리에서 탐색 효율을 높이기 위해
- 입력 수열 상 인덱스와 트리에서의 인덱스를 달리함. 항의 값이 작을 수록 낮은 트리 인덱스를 갖게 해 정렬시킴.
- 트리의 각 노드에 해당 구간에서의 최댓값, 최솟값을 저장해서 불필요한 탐색을 줄임.

처음에는 노드에 최솟값을 저장하지 않고 최댓값만 고려해서 쿼리를 돌려 비효율이 발생해 시간초과가 났었다.
*/

class Node {
    var biggest = 0L // last값의 최댓값
    var smallest = 0L // last값의 최솟값
    var sumMax = 0L // 합의 최댓값
}

class Term(val index: Int, val value: Long): Comparable<Term> {
    var treeIdx = 0
    
    override fun compareTo(other: Term) = value.compareTo(other.value)
}

class Sequence(val last: Long, val total: Long)

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val nums = readLine().split(" ").map {it.toLong()}
    val terms = Array(n) {Term(it, nums[it])}
    val sorted = terms.sorted()
    for(i in 0 until n) {
        val t = sorted[i]
        t.treeIdx = i
    }
    val tree = Array(n*4) {Node()}
    
    // 지정된 구간 내에서 (upperbound > 수열의 마지막 값) 조건을 만족시키는 수열 중 시그마의 최댓값 
    fun query(i: Int, left: Int, right: Int, upperbound: Long): Long {
        if(left > right) return 0
        val node = tree[i]
        if(node.biggest < upperbound) return node.sumMax
        if(node.smallest >= upperbound) return 0
        if(left == right) return 0
        val m = (left+right)/2
        val x = i*2
        return max(query(x, left, m, upperbound), query(x+1, m+1, right, upperbound))
    }
    
    fun insert(i: Int, left: Int, right: Int, idx: Int, seq: Sequence) {
        if(left > right) return
        val node = tree[i]
        if(left==right) {
            if(left == idx) {
                node.biggest = seq.last
                node.smallest = seq.last
                node.sumMax = seq.total
            }
            return
        }
        val m = (left+right)/2
        if(idx <= m) {
            insert(i*2, left, m, idx, seq)
        } else {
            insert(i*2+1, m+1, right, idx, seq)
        }
        node.biggest = max(tree[i*2].biggest, tree[i*2+1].biggest)
        node.sumMax = max(tree[i*2].sumMax, tree[i*2+1].sumMax)
        node.smallest = min(tree[i*2].smallest, tree[i*2+1].smallest)
    }
    
    fun travel(i: Int, left: Int, right: Int, space: String) {
        if(left > right) return
        val node = tree[i]
        println("$space$left~$right ${node.biggest}, ${node.sumMax}")
        if(left == right) return
        val new = space + " "
        val m = (left+right)/2
        travel(i*2, left, m, new)
        travel(i*2+1, m+1, right, new)
    }
    
    for(i in 0 until n) { // DP 세그먼트 트리 초기화
        insert(1, 0, n-1, terms[i].treeIdx, Sequence(nums[i], 0L))
    }
    for(i in 0 until n) {
        val a = nums[i]
        val q = query(1, 0, n-1, a)
        val newSeq = Sequence(a, q+a)
        insert(1, 0, n-1, terms[i].treeIdx, newSeq)
    }
    println(tree[1].sumMax)
}
