import java.util.*;
import java.lang.Math.*
import java.io.*
import kotlin.collections.*

/**
2239번: 스도쿠
스도쿠의 빈칸을 사전순으로 가장 앞서도록 채우는 문제
재귀 함수를 이용한 백트래킹으로 구현
*/

val map = ArrayList<Array<Int>>()
val banX = Array<BooleanArray>(9) {BooleanArray(9)}
val banY = Array<BooleanArray>(9) {BooleanArray(9)}
val banSec = Array<Array<BooleanArray>>(3) {Array<BooleanArray>(3){BooleanArray(9)}}
val targets = ArrayList<Pair<Int, Int>>(81)

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    repeat(9) {
        map.add(readLine().map {it.toString().toInt()}.toTypedArray())
    }
    for(x in 0..8) {
        for(y in 0..8) {
            if(map[x][y] == 0) {
                targets.add(x to y)
                continue
            }
            toggle(x,y,map[x][y])
        }
    }
    bf(0)
}

fun print() {
    map.forEach {
        println(it.joinToString(""))
    }
}

fun bf(target: Int): Boolean {
    if(target == targets.size) {
        print()
        return true
    }
    val (x,y) = targets[target]
    for(num in 1..9) {
        if(banX[x][num-1] || banY[y][num-1] || banSec[x/3][y/3][num-1]) continue
        toggle(x,y,num)
        map[x][y] = num
        if(bf(target+1)) return true
        toggle(x,y,num)
    }
    return false
}

fun toggle(x: Int, y: Int, num: Int) {
    val i = num-1
    banX[x][i] = !banX[x][i]
    banY[y][i] = !banY[y][i]
    banSec[x/3][y/3][i] = !banSec[x/3][y/3][i] 
}

