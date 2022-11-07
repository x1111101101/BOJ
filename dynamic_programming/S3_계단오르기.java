import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
2579번: 계단오르기
*/
public class Main {
    
    public static void main(String[] args) throws IOException {    
        int scores[];
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int steps = parseInt(br.readLine());
        scores = new int[steps];
        int dp[][] = new int[steps][2];
        for(int i = 0; i<steps; i++) {
            scores[i] = parseInt(br.readLine());
        	dp[i][0] = -1;
            dp[i][1] = -1;
        }
        if(steps == 1) {
            System.out.println(scores[0]);
            return;
        }
        dp[0][0] = scores[0];
        dp[1][0] = scores[1];
        dp[1][1] = dp[0][0]+scores[1];
        for(int i = 2; i<steps; i++) {
            int max = max(dp[i-2][0], dp[i-2][1]);
            if(max > -1) {
                dp[i][0] = max+scores[i];
            }
            if(dp[i-1][0] > -1) {
                dp[i][1] = dp[i-1][0] + scores[i];
            }
            
        }
        System.out.println(max(dp[steps-1][0], dp[steps-1][1]));
    }
    
    static int max(int a, int b) {
        return a > b ? a : b;
    }
    
}
