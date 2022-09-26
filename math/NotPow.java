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
        //System.out.println("sqrt: " + sqrt);
        ArrayList<Long> list = new ArrayList<>();
        long count = 0;
        
        loop1:
        for(long i = 2; i<= sqrt; i++) {
        	long pow = i*i;
            for(long k:list) {
                if(pow%k == 0) {
                    continue loop1;
                }
            }
            
            long toSum = l/pow;
            for(long k: list) {
                long div2 = l/(k*pow);
                if(div2 > 0) {
                    toSum -= div2;
                }
            }
            list.add(pow);
            count += toSum;
            System.out.printf("%d: %d개\n", pow, toSum);
        }
        return count;
    }
    
}
