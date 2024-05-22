/**
 * 2021 KAKAO RECRUITMENT - 광고 삽입
 * O(N)의 시간복잡도로 풀이.
 *
 * - 주어진 시간 구간들을 초 ~ 초 형태로 바꿈. (최대가 100시간이라면 36만초가 최대)
 * - 36만의 크기를 갖는 배열 arr의 각 인덱스에 0초 ~ 36만초를 대응 시킴
 * - arr의 각 인덱스 별로 해당 초에서의 시청자 수를 저장
 * - arr의 누적합을 구함
 * - 0초 부터 (36만 - 광고 시간)초까지를 순회하면서 해당 초를 시작으로 정한 구간의 누적 시청 시간을 누적합을 이용해 O(1)로 구함.
 * - 누적 시청 시간 중 최초로 구한 최댓값을 정답값으로 선택
 * 
 * 시간 단축 방법
 * - 모든 구간을 순회하면서 arr에 1씩 증가시키는 로직은 최악의 경우 1000억회의 연산 발생
 * - 재생 시간을 여는 괄호와 닫는 괄호로 해석
 *      (  (  ) ( ) )등의 꼴로 해석 가능
 * - 위치 별 괄호의 중첩 횟수를 구하면서 arr에 한번에 0~30만개의 값을 한번에 더함 - O(N)
 * - 시작 시간과 끝 시간 끼리의 중복이 있을 수 있음에 유의
 * 
 */


class Solution {
    
    data class L(val time: Int, val count: Int, val isEnd: Boolean): Comparable<L> {
        override fun compareTo(other: L): Int {
            val c = this.time.compareTo(other.time)
            if(c == 0) {
                if(this.isEnd) return -1
                return 1
            }
            return c
        }
    }

    fun getSeconds(time: String): Int {
        val (h,m,s) = time.split(":").map { it.toInt() }
        return h*3600 + m * 60 + s
    }

    fun toTimeString(t: Int): String {
        val h = t/3600
        val m = t%3600/60
        val s = t%60
        val result = arrayOf(h,m,s).map { String.format("%02d", it) }
        return result.joinToString(":")
    }

    fun solution(play_time: String, adv_time: String, logList: Array<String>): String {
        val playTime = getSeconds(play_time)
        val adTime = getSeconds(adv_time)
        val sorted = ArrayList<L>(600000)
        val endCount = HashMap<Int, Int>()
        val startCount = HashMap<Int, Int>()
        logList.forEach { // 중복 제거
            val (start,end) = it.split("-").map { getSeconds(it) }
            startCount[start] = (startCount[start] ?: 0) + 1
            endCount[end] = (endCount[end] ?: 0) + 1 // endExclusive
        }
        endCount.forEach { t, d -> sorted.add(L(t, d, true)) }
        startCount.forEach { t, d -> sorted.add(L(t, d, false)) }
        sorted.sort()
        val arr = Array(playTime) {0}
        var dup = 0 // 중첩 횟수
        var left = 0
        for(s in sorted) {
            if(s.isEnd) {
                for(i in left until s.time) {
                    arr[i] += dup
                }
                dup -= s.count
                left = s.time
                continue
            }
            for(i in left..s.time) {
                arr[i] += dup
            }
            arr[s.time] += s.count
            left = s.time+1
            dup += s.count

        }
        val acc = Array(arr.size+1) {0L}
        acc[0] = arr[0].toLong()
        for(i in 1 until acc.size) {
            acc[i] = acc[i-1] + arr[i-1]
        }
        var ans = 0L
        var startAns = -60
        for(start in 1 ..acc.size-adTime) {
            val end = start + adTime-1
            val sum = acc[end] - (acc[start-1])
            if(ans < sum) {
                startAns = start
                ans = sum
            }
        }
        return toTimeString(startAns-1)
    }
}
