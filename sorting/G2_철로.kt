import java.util.*;
import java.lang.Math.*;
import java.io.*


/**
13334번: 

- 길은 왼쪽 끝과 오른쪽 끝으로 구성

1. 왼쪽 끝이 가장 작은순으로 정렬하는 pq -> pqLeft, 오른쪽 끝이 가장 작은 순으로 정렬하는 pq -> pqRight
2. 모든 길을 pqLeft와 pqRight로 push
3. pqLeft가 빌 때 까지:
    road = pqLeft에서 pop한 것
    lastRoad = 이전 루프에서의 road
    road와 lastRoad 간의 중첩이 조금이라도 없다면:
        카운트 = 0
    rMax = (road의 왼쪽 끝 + d)
    // (pqRight에 들어 있는 값들의 left) >= (road.left)
    pqRight를 하나씩 peek 하면서 (right 값 <= rMax)라면:
        pqRight.pop()
        카운트++
    카운트--
*/

var n = 0
var d = 0
val roads = ArrayList<Road>(100000)

fun main(args: Array<String>) = with(System.`in`.bufferedReader()) {
    n = readLine().toInt()
    val pqLeft = PriorityQueue(n, CompareByLeft)
    val pqRight = PriorityQueue(n, CompareByRight)
    repeat(n) {
        val st = StringTokenizer(readLine())
        val a = LongArray(2) {st.nextToken().toLong()}
        a.sort()
        val road = Road(a[0], a[1])
        roads.add(road)
    }
    var ans = 0
    d = readLine().toInt()
    for(r in roads) {
        if(r.right-r.left <= d) {
            pqLeft.add(r)
            pqRight.add(r)
        }
    }
    var duplicated = 0
    var lastRoad = Road(-2000000000001L, -2000000000001L)
    while(pqLeft.isNotEmpty()) {
        val road = pqLeft.poll()
        val rightMax = road.left + d
        if(road.left >= lastRoad.left + d) {
            duplicated = 0
        }
        while(pqRight.isNotEmpty()) {
            val peek = pqRight.peek()
            if(peek.right <= rightMax) {
                pqRight.poll()
                duplicated++
            } else {
                break
            }
        }
        if(duplicated > ans) ans = duplicated
        duplicated--
        lastRoad = road
    }
    println(ans)
}


object CompareByLeft: Comparator<Road> {
    override fun compare(a: Road, b: Road): Int {
        return if(a.left < b.left) -1 else if(a.left == b.left) 0 else 1
    }
}
object CompareByRight: Comparator<Road> {
    override fun compare(a: Road, b: Road): Int {
        return if(a.right < b.right) -1 else if(a.right == b.right) 0 else 1
    }
}

data class Road(val left: Long, val right: Long)
