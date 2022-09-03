import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

/**
1019번 - 책 페이지
소감: 어려운 문제일 수록 실시간적이고 즉흥적인 발상에 의존하지 말고 생각먼저 하고 코드를 쓰자
그리고 endInclusive와 endExclusive 파라미터에 주의하자.
*/
public class Main {
    
    static int[] counts = new int[10];
    static ArrayList<CalcHelper> helpers = new ArrayList<>(); // key: 10->0, 100->1, 1000->2
    
    /*

    1200

    1~100, 100~200 . . . 1100~1200

    1100 ~ 1200
    1100 ~ 1110, 1100 ~ 1120, 1130 ~ 1140 . . . 1190~1200

    1190 ~ 1200

    */
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	int pages = Integer.parseInt(br.readLine());
        init(pages);
        
        calc(1, pages+1, new ArrayList<Integer>());
        StringJoiner sj = new StringJoiner(" ");
        for(int i : counts) {
            sj.add(Integer.toString(i));
        }
        System.out.println(sj.toString());
    }
    
    static void init(int pages) {
        int log10 = (int) Math.log10(pages);
        CalcHelper helper10 = new CalcHelper();
        for(int i = 0; i<10; i++) {
            helper10.consts[i]++;
        }
        helper10.countX = 10;
        helpers.add(helper10);
        if(log10 < 2) {
            return;
        }
        
        int countX = 100;
        for(int i = 1; i<log10; i++) {
            CalcHelper h = new CalcHelper();
            CalcHelper prev = helpers.get(i-1);
           	h.countX = countX;
            countX *= 10;
            for(int x = 0; x < 10; x++) {
                h.consts[x] += prev.countX;
            }
            sum(prev.consts, h.consts, 10);
            helpers.add(h);
        }
        
        /* 테스트용 코드
        for(int i = 0; i<helpers.size(); i++) {
        	String debug = Arrays.stream(helpers.get(i).consts)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", "));
        	// System.out.println(i+": " + debug);
        }
        */
    }
    
    static void calc(int start, int endExclusive, ArrayList<Integer> consts) {
        d("calc(%d, %d)", start, endExclusive);
        if(endExclusive <= 10) {
            for(int i = start; i<endExclusive; i++) {
                counts[i] += 1;
                for(int con : consts) {
                    counts[con]++;
                }
            }
            return;
        }
        
        int log10 = (int) Math.log10((double)(endExclusive-1));
        int x10 = 1;
        for(int p = 0; p<log10; p++) {
            x10 *= 10;
        }
        CalcHelper helper = helpers.get(log10-1);
        int newEndEx = start;
        for(int i = 1; i<=10; i++) {
            int newEndExTest = x10*i;
            if(newEndExTest <= endExclusive) {
                d("%d ~ %d", newEndEx, newEndExTest);
                if(newEndEx != 1) {
                    sum(helper.consts, counts);
                    int mul = newEndExTest-newEndEx;
                    for(int con : consts) {
                        counts[con] += mul;
                    }
                    counts[getFirstNum(newEndEx)] += helper.countX;
                } else {
                    calc(1, newEndExTest, consts);
                }
                newEndEx = newEndExTest;
            } else {
                ArrayList<Integer> clone = (ArrayList<Integer>) consts.clone();
                int endEx = endExclusive - newEndEx;
                int newLog10 = (int) Math.log10((double) endEx);
                int distance = log10-newLog10;
                for(int k = 0; k<distance; k++) {
                    int num = getNthNum(endExclusive, k);
                    clone.add(num);
                    d("num: %d", num);
                }
                calc(0, endEx, clone);
                break;
            }
            
        }
        
        
        
    }
    
    static void d(String format, Object... obj) {
        //System.out.printf(format, obj);
        //System.out.println();
    }

    
    static void sum(int[] from, int[] to) {
        for(int i = 0; i < from.length; i++) {
            to[i] += from[i];
        }
    }
    static void sum(int[] from, int[] to, int mul) {
        for(int i = 0; i < from.length; i++) {
            to[i] += from[i]*mul;
        }
    }
    
    
    static int[] copy(int[] arr) {
        int[] clone = new int[arr.length];
        for(int i = 0; i<arr.length; i++) {
            clone[i] = arr[i];
        }
        return clone;
    }
    
    static int getFirstNum(int i) {
        int log10 = (int) Math.log10((double)i);
        int k = 1;
        for(int p = 0; p<log10; p++) {
            k *= 10;
        }
        return i / k;
    }
    
    // 19
    static int getNthNum(int target, int n) {
        int log10 = (int) Math.log10((double)target); //1
        double num = target/10;
        for(int i = 0; i < log10-n; i++) {
            num /= 10;
        }
        num -= (int) num;
        return (int)(num*10);
    }
    
    static class CalcHelper {
        int[] consts = new int[10];
        int countX;
        
        /*
        01 02 03 04 05
        11 12 13 14 15
        
        100: 100 101 102 103
        
        */
        
        
    }
    

}
