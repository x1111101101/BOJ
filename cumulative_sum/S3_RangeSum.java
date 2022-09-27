package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;

/**
11659번: 구간 합 구하기4
처음엔 두 수씩 더해나가서 점점 연산 횟수를 줄이는 방식을 썼으나 시간초과.
1시간 정도 고민해봤는데, 발상이 안되서 검색한 결과:
계산을 초기 1회만 수행하고 계산값을 바탕으로 한 연산으로 o(n)의 시간복잡도로 해결 가능.
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
        cache[0] = nums[0];
        for(int i = 1; i<sp.length; i++) {
            cache[i] = cache[i-1] + cache[i];
        }
        
        while(m-->0) {
            sp = br.readLine().split(" ");
            int i = Integer.parseInt(sp[0])-1;
            int j = Integer.parseInt(sp[1])-1;
        	
            int sum = cache[j];
            int toWd = cache[i-1];
            System.out.println(cache[j] - toWd);
        }
    }
    

}

