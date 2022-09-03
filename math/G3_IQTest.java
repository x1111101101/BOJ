import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

/**
1111번 - IQ Test

*/
public class Main {
    
    static int[] nums;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	int size = Integer.parseInt(br.readLine());
        String splited[] = br.readLine().split("\\s");
        if(size != splited.length) throw new IllegalStateException();
        if(splited.length < 2) {
            System.out.println("A");
            return;
        }
        nums = new int[splited.length];
        for(int i = 0; i<nums.length; i++) {
            nums[i] = Integer.parseInt(splited[i]);
        }
        
        int f = nums[0];
        int s = nums[1];
        
        HashSet set = new HashSet();
        
        // (last)*a + b
        for(int a = -200; a <= 200; a++) {
            Exp ex = new Exp();
            ex.a = a;
            int v = f*a;
            ex.b = s-v;
            // 1 2 3
            if(ex.test()) {
                int next = ex.next();
                set.add(next);
                if(set.size() > 1) {
                    System.out.println("A");
                    return;
                }
            }
        }
        
        if(set.isEmpty()) System.out.println("B");
        else System.out.println(set.stream().findFirst().get());
        
        
    }
    
    static class Exp {
        
        int a;
        int b;
        
        boolean test() {
            for(int i = 1; i<nums.length; i++) {
                int v = nums[i-1] * a + b;
                if(v != nums[i]) return false;
            }
            return true;
        }
        
        int next() {
            int last = nums[nums.length-1];
            return last*a+b;
        }
    }
    

}
