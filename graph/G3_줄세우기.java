package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
2252번: 줄 세우기
위상정렬을 학습해서 다시 풀었다. 큐의 사이즈가 필요 이상으로 커지지 않아 메모리 초과가 발생하지 않았다.
위상정렬 알고리즘은 매우 직관적이라서 이해하는데 크게 어려움이 없었다.

degree <= 0이 아닌 degree == 0로 진입차수 체크를 해서 중복 출력을 피했다.

https://www.youtube.com/watch?v=qzfeVeajuyc
위상정렬 알고리즘은 이 영상을 보며 학습했다.
*/
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String nums[] = br.readLine().split(" ");
        int n = parseInt(nums[0]);
        int m = parseInt(nums[1]);
        HashMap<Integer, List<Integer>> bigger = new HashMap<>(n);
        int degree[] = new int[n+1];
        while(m-- > 0) {
            String sp[] = br.readLine().split(" ");
            int a = parseInt(sp[0]);
            int b = parseInt(sp[1]);
            degree[b]++;
            bigger.computeIfAbsent(a, k->new LinkedList<>()).add(b);
        }
        LinkedList<Integer> li = new LinkedList<>();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for(int i = 1; i<=n; i++) {
            if(degree[i] == 0) li.add(i);
        }
        while(!li.isEmpty()) {
            int k = li.removeFirst();
            bw.write(String.valueOf(k));
            bw.write(' ');
            List<Integer> talls = bigger.get(k);
            if(talls == null) continue;
            for(int t : talls) {
                degree[t]--;
                if(degree[t] == 0) {
                    li.add(t);
                }
            }
        }
        bw.close();
    }
}
