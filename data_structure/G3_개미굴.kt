import java.util.*;
import java.lang.Math.abs;

/**
14725번: 개미굴
단어 단위의 트라이를 해시맵을 활용해서 구현했다.
주의할 점은 사전순으로 우선순위를 매겨줘야 하는 것이다.
*/

val bw = System.`out`.bufferedWriter()
val root = Room("")

fun main(args: Array<String>) = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    for(i in 1..n) {
        val st = StringTokenizer(readLine())
        val count = st.nextToken().toInt()
        var target = root
        for(i in 1..count) {
            val name = st.nextToken()
            target = target.getOrFind(name)
        }
    }
    root.printChilds("")
    bw.close()
    
}


class Room(val name: String): Comparable<Room> {
    val rooms = hashMapOf<String, Room>()
    override fun compareTo(r: Room) = name.compareTo(r.name)
    
    fun getOrFind(name: String) = rooms.computeIfAbsent(name) {Room(name)}
    
    fun printChilds(prefix: String) {
        val list = rooms.values.toMutableList()
        list.sort()
        for(r in list) {
            bw.write(prefix)
            bw.write(r.name)
            bw.write("\n")
            r.printChilds(prefix + "--")
        }
    }
    
    
}
