import java.util.StringTokenizer


/**
 * 17411번: 가장 긴 증가하는 부분 수열 6
 *
 * 1.
 * 좌표 압축: 원소, N 범위 조건에 따라 원소를 정렬하여 압축하면 원소 범위를 줄일 수 있다. (0 <= 원소 < N)
 * 이렇게 했을 때, 원소 값을 인덱스(또는 Key)로 활용할 수 있다.
 *
 * 2.
 * 가장 긴 부분 수열의 길이만 구할 것이라면, 부분 수열의 길이가 같다면 마지막 원소가 더 작은 수열만 남겨도 된다.
 * 하지만, 이 문제에선 개수도 구해야 한다.
 *
 * 3.
 * 부분 수열의 마지막 원소 값에 대해 부분 수열의 최대 길이를 업데이트해 나가면 해를 찾을 수 있다.
 *
 * 4.
 * 부분 수열의 끝에 원소를 하나씩 더하는 방식으로 찾아나가자.
 *
 * 5.
 * 어떤 부분 수열이 같은 끝 값을 같는 부분 수열 중 최장 길이를 갖지 않는다면, 미래에 가장 긴 부분 수열이 될 수 없다.
 *
 * 6.
 * 어떤 값 x가 주어졌을 때, x 보다 작은 끝 값을 지닌 부분수열 중 가장 긴 것들을 찾아내야 한다.
 * 끝값과 개수가 가장 중요한 정보일 것이다.
 * N 범위로 인해 bottom-up이 적합해 보인다. (N*N 배열 생성 불가, HashMap?)
 *
 * 7.
 * 어떤 끝 값에 대해 최장 길이인 부분 수열 정보만 유지하면 된다.
 * Query: x 보다 작은 끝 값을 가진 최장 부분 수열의 길이와 경우의 수
 * Update: 끝값, 길이, 경우의 수
 *
 * Segment tree 활용하면 O(N * log(N)) 시간 복잡도 갖는다.
 *
 */

const val MOD = 1_000_000_007
const val SCALE = 1_000_000
val maxLen = IntArray(SCALE)
val cases = IntArray(SCALE)
val NIL = Entry(0, 1)
val tree = Array(SCALE * 4) { Entry() }

data class Entry(
    var maxLen: Int = 0,
    var case: Int = 0
) {
    fun merge(a: Entry, b: Entry) {
        if (a.maxLen == b.maxLen) {
            maxLen = a.maxLen; case = (a.case + b.case) % MOD
        } else if (a.maxLen > b.maxLen) {
            maxLen = a.maxLen; case = a.case
        } else {
            maxLen = b.maxLen; case = b.case
        }
    }
}

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val rawA = StringTokenizer(readLine()).let { t -> Array(n) { t.nextToken().toInt() } }
    val a = Array(rawA.size) { it }
        .also { it.sortWith(Comparator { a, b -> rawA[a] - rawA[b] }) }
        .let { sortedIdx ->
            val order =
                sortedIdx.runningReduceIndexed { i, acc, v -> acc + if (rawA[sortedIdx[i - 1]] == rawA[v]) 0 else 1 }
            val compressed = IntArray(n)
            sortedIdx.forEachIndexed { i, v -> compressed[v] = order[i] - sortedIdx[0] }
            compressed
        }


    fun query(i: Int, l: Int, r: Int, x: Int): Entry {
        if (l > r || l >= x) return NIL
        val node = tree[i]
        if (r < x) return node
        val mid = (l + r) / 2
        return Entry().also { it.merge(query(i * 2, l, mid, x), query(i * 2 + 1, mid + 1, r, x)) }
    }

    fun update(i: Int, l: Int, r: Int, x: Int, len: Int, case: Int) {
        if (l > r || x !in l..r) return
        val node = tree[i]
        if (l == r) {
            node.maxLen = len
            node.case = case
            return
        }
        val mid = (l + r) / 2
        update(i * 2, l, mid, x, len, case)
        update(i * 2 + 1, mid + 1, r, x, len, case)
        node.merge(tree[i * 2], tree[i * 2 + 1])
    }

    for (x in a) {
        val (len, case) = query(1, 0, n, x).let { if (it.maxLen == 0) NIL else it }
        val newLen = len + 1
        if (newLen < maxLen[x]) continue
        val newCase = if (newLen == maxLen[x]) (case + cases[x]) % MOD else case
        update(1, 0, n, x, newLen, newCase)
        cases[x] = newCase
        maxLen[x] = newLen
    }
    val root = tree[1]
    println("${root.maxLen} ${root.case}")
}
