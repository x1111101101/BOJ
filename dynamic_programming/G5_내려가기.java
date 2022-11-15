package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
2096번: 내려가기
연산량을 더 줄일 수 있을 것 같다.
다음에 다시 한번 풀어봐야겠다.
*/
public class Main {
    
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int size = parseInt(br.readLine());
        int nums[][] = new int[size][3];
        int dp[][][] = new int[size][3][2];
        int sidx[] = {0,0,1};
        int eidx[] = {1,2,2};
        
        for(int i = 0; i<size; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            nums[i][0] = parseInt(st.nextToken());
            nums[i][1] = parseInt(st.nextToken());
            nums[i][2] = parseInt(st.nextToken());
        }
        for(int i = 0; i<3; i++){
            dp[0][i][0] = nums[0][i];
            dp[0][i][1] = nums[0][i];
        }
        for(int i = 1; i<size; i++) {
            for(int k = 0; k<3; k++) {
                int max = dp[i-1][sidx[k]][1] + nums[i][k];
                int min = dp[i-1][sidx[k]][0] + nums[i][k];
                for(int p = sidx[k]+1; p<=eidx[k]; p++) {
                    int n = dp[i-1][p][0] + nums[i][k];
                    int x = dp[i-1][p][1] + nums[i][k];
                    if(n < min) min = n;
                    if(x > max) max = x;
                }
                dp[i][k][1] = max;
                dp[i][k][0] = min;
            }
        }
        int max = dp[size-1][0][1];
        int min = dp[size-1][0][0];
        for(int i = 1; i<3; i++) {
            int n = dp[size-1][i][0];
            int x = dp[size-1][i][1];
            if(n < min) min = n;
            if(x > max) max = x;
        }
        System.out.println(max + " " + min);
        
    }
}
