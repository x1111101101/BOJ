package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;


public class Main {
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String sp[] = br.readLine().split(" ");
        long min = Long.parseLong(sp[0]);
        long max = Long.parseLong(sp[1]);
       	
        long minGet = getCount(min);
        long maxGet = getCount(max);
        System.out.printf("%d %d\n", minGet, maxGet);
        
        System.out.println((max-min) - (maxGet - minGet));
		   
    }
    
    // 2~l 중 제곱수로 나누어 떨어지는 수의 개수 리턴
    static long getCount(long l) {
        HashMap<Integer, Integer> factors = new HashMap();
        long sqrt = (long)sqrt((double)l);
        System.out.println("sqrt: " + sqrt);
        long count = 0;
        for(long i = 2; i<= sqrt; i++) {
        	long pow = i*i;
            long div = l/pow;
            long log = calcDupl(div);
            // if(log < 0) log = 0;
            //if(log != 0) log--;
            System.out.printf("i: %d, pow: %d, dup: %d \n", i, pow, log);
            count += log;
        }
        return count;
    }
    
    // 중복 제거 후 제곱수 개수 리턴
    static long calcDupl(long div) {
        long sqrt = (long) sqrt((double) div);
        if(sqrt < 2) return div;
        sqrt--;
        return div-sqrt;
    }
    
    static long log(long base, long x) {
        long log = (long) (log10((double)x) / log10((double)base));
        return log;
    }
    
    static long a(long a, long b) {
        return 0;
    }
    
    public static void main2(String[] args) throws IOException {
        /*
        
        10: 3
        100: 10
        1000: 31
        10000: 100
        100000: 316
        1000000: 1000
        10000000: 3162
        100000000: 10000
        
        짝수로그: a^(로그-1)
        
        1200->
        
        1~1000:
        	1~100
            2~200
            3~300
        
        
        
        */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String sp[] = br.readLine().split(" ");
        long min = Long.parseLong(sp[0]);
        long max = Long.parseLong(sp[1]);
        long minDif = Long.MAX_VALUE;
        
        int last = 0;
        
        long a = (long) Math.sqrt((double)max);
        long b = (long) (a+1)*(a+1);
        // max가 a와 b 사이에 있는지 확인!!!
        
        for(int i = 1; i<100; i++) {
            int pow = i*i;
            System.out.println(pow-last);
            last = pow;
        } // 1 3 5 7 9
        
        /*
        15
        
        
        */
        
        
        for(long l = 2; l<300; l+=2) {
            long log = (long) (log10((double)max) / log10((double)l));
            long pow = (long)pow(l, log);
        	long dif = max - pow;
            if(dif < minDif) {
                minDif = dif;
                System.out.println("pow: " + pow);
            }
        }
        System.out.println("min: " + minDif);
        
    }
    
    
    static long count(long base, long pow) {
        return 0;
    }
    
    static long getMultiplier(long base) {
        return calc(base*base);
    }
    	
    static long calc(long max) {
        long count = 0;
        int b = (int) Math.sqrt(4);
        for(long i = 0; i<=max; i++) {
            long sq = (long)Math.sqrt(i);
        	if(sq*sq == i) {
                //System.out.println(i);
                count++;
            }
        }
        return count;
    }
    
}
