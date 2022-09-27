package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;

/**
11659번: 구간 합 구하기4
시간 초과 났던 코드. 두 수씩 더해나가서 연산 횟수를 줄이는 방식을 사용했으나 o(n)의 시간복잡도가 필요함
*/
public class Main {
    
    static int n,m;
    static int nums[];
    static int cache[];
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] sp = br.readLine().split(" ");
        n = Integer.parseInt(sp[0]);
        m = Integer.parseInt(sp[1]);
        
        sp = br.readLine().split(" ");
        nums = new int[n];
        cache = new int[n];
        for(int i = 0; i<sp.length; i++) {
            nums[i] = Integer.parseInt(sp[i]);
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        while(m-->0) {
            sp = br.readLine().split(" ");
            int i = Integer.parseInt(sp[0])-1;
            int j = Integer.parseInt(sp[1])-1;
        	
            int length = j-i+1;
            length = sum(nums, cache, length, i);
            while(length > 1) {
                length = sum(cache, cache, length, 0);
            }
            bw.write(String.valueOf(cache[0]));
            bw.newLine();
        }
        bw.close();
    }
    
    // return new length
    static int sum(int[] arr, int[] target, int length, int fix) {
        int loop = length/2;
        for(int i = 0; i<loop; i++) {
            int sum = arr[i*2+fix] + arr[i*2+1+fix];
            target[i] = sum;
        }
        if(length%2 != 0) {
            target[loop] = arr[length-1+fix];
            loop++;
        }
        return loop;
    }
    
    
    static class Range {
        int index, sum;
    }
    
}
