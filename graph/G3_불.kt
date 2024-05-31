/**
4179번: 불
그냥 BFS랑 구현 문제. 불의 위치를 하나의 리스트에 저장하면서 각 단계마다 확산시킴.
*/

fun main() {
    val br = System.`in`.bufferedReader()
    val len = br.readLine().split(" ").map { it.toInt() }
    val (xlen, ylen) = len
    val offsets = ArrayList<Array<Int>>()
    for(d in arrayOf(1, -1)) for(ax in 0..1) offsets.add(arrayOf(ax, d))
    val map = Array(xlen) {br.readLine().toCharArray()}
    val (fx,fy) = map.indices.map { it to map[it].indexOf('J') }.first { it.second != -1 }
    var bfs = ArrayList<Array<Int>>()
    var temp = ArrayList<Array<Int>>()
    bfs.add(arrayOf(fx, fy))
    val visit = Array(xlen) {Array(ylen) {false} }
    var tick = 1
    var fires = map.indices.flatMap {
            x-> map[x].indices
        .filter { map[x][it] == 'F' }
        .map { arrayOf(x, it) }
    }.toMutableList()
    do {
        val nf = ArrayList<Array<Int>>()
        while(fires.isNotEmpty()) {
            val f = fires.removeLast()
            for(o in offsets) {
                val location = f.copyOf()
                location[o[0]] += o[1]
                if(0 > location[o[0]] || len[o[0]] <= location[o[0]]) continue
                val (x, y) = location
                if(map[x][y] != '#' && map[x][y] != 'F') {
                    map[x][y] = 'F'
                    nf.add(location)
                }
            }
        }
        fires = nf

        do {
            val current = bfs.removeLast()
            for(o in offsets) {
                val location = current.copyOf()
                location[o[0]] += o[1]
                if(0 > location[o[0]] || len[o[0]] <= location[o[0]]) {
                    println(tick)
                    return
                }
                val (x, y) = location
                if(visit[x][y]) continue
                visit[x][y] = true
                if(map[x][y] != '#' && map[x][y] != 'F') {
                    temp.add(location)
                }
            }

        } while(bfs.isNotEmpty())
        val c = bfs
        bfs.clear()
        bfs = temp
        temp = c
        tick++
    } while(bfs.isNotEmpty())
    println("IMPOSSIBLE")
}
