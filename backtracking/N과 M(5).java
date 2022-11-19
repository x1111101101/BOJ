package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
15654번: N과 M (5)
함수의 매커니즘을 이용해 하나의 배열로 모든 dfs의 방문 인덱스를 처리할 수 있다.
다시한번 풀어볼만한 문제다
*/
public class Main {
    
    static int n, m;
    static int nums[];
    static int d[] = new int[8];
    static boolean visit[] = new boolean[8];
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String sp[] = br.readLine().split(" ");
        n = parseInt(sp[0]);
        m = parseInt(sp[1]);
        StringTokenizer st = new StringTokenizer(br.readLine());
        nums = new int[n];
        for(int i = 0; i<n; i++) {
            nums[i] = parseInt(st.nextToken());
        }
        Arrays.sort(nums);
        reculsive(0);
    }
    
    static void reculsive(int i) {
        if(i == m-1) {
            for(int k = 0; k<n; k++) {
                if(visit[k]) continue;
                d[i] = nums[k];
                StringBuilder sb = new StringBuilder();
                for(int p = 0; p<m; p++) {
                    sb.append(d[p]);
                    sb.append(' ');
                }
                System.out.println(sb);
            }
            return;
        }
        for(int k = 0; k<n; k++) {
            if(visit[k]) continue;
            d[i] = nums[k];
            visit[k] = true;
            reculsive(i+1);
            visit[k] = false;
        }
    }
}
