package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;

/**
2559번: 수열
S3_RangeSum(11659번)을 풀면서 알게된 누산 방식을 
*/
public class Main {
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] sp = br.readLine().split(" ");
        int n = parseInt(sp[0]);
        int k = parseInt(sp[1]);
        int cache[] = new int[n];
        sp = br.readLine().split(" ");
        cache = new int[n];
        cache[0] = parseInt(sp[0]);
        for(int i = 1; i<sp.length; i++) {
            cache[i] = parseInt(sp[i]) + cache[i-1];
        }
        int max = MIN_VALUE;
        for(int i = 0; i<=sp.length-k; i++) {
            int sum = cache[i+k-1];
            if(i > 0) {
                sum -= cache[i-1];
            }
            if(sum > max) {
                max = sum;
            }
        }
        System.out.println(max);
    }
}
