package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
1766번: 문제집
위상정렬을 이용해 풀었다.

1. 풀 수 있는 문제들 중 쉬운 문제를 먼저 푼다. 단 그 문제 보다 먼저 풀면 더 좋은 문제가 없어야한다.
2. 문제를 다 풀때까지 1을 반복
*/
public class Main {

    final static BufferedWriter bw;
    
    static {
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
    }
    
    static void print(int num) throws IOException {
        bw.write(String.valueOf(num+1));
        bw.write(' ');
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = parseInt(st.nextToken());
        int m = parseInt(st.nextToken());
        boolean solved[] = new boolean[n];
        HashSet<Integer> af[] = new HashSet[n];
        List<Integer> bef[] = new List[n];
        for(int i = 0; i<n; i++) {
            af[i] = new HashSet<>();
            bef[i] = new LinkedList<>();
        }
        int degree[] = new int[n];
        for(int i = 0; i<m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = parseInt(st.nextToken())-1; // b 이전에 a를 풀어야함
            int b = parseInt(st.nextToken())-1;
            if(!af[a].contains(b)) {
                bef[a].add(b);
                af[b].add(a);
                degree[b]++;
            }
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int j = 0; j<n; j++) {
            if(degree[j] <= 0) {
                pq.add(j);
            }
        }
        while(!pq.isEmpty()) {
            int f = pq.poll();
            if(degree[f] <= 0) {
                solved[f] = true;
                print(f);
                for(int b : bef[f]) {
                    degree[b]--;
                    if(degree[b] <= 0 && !solved[b]) {
                        pq.add(b);
                    }
                }
                continue;
            }
        }
        bw.close();
    }
}

/*
6 7
5 6
5 2
2 4
4 3
2 1
6 1
1 3
ans: 5 2 4 6 1 3
*/
