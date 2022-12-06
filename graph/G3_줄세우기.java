import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
2252번: 줄세우기
위상 정렬로 간단히 풀 수 있는 문제지만 위상정렬을 학습하지 않은 상태로 풀어서 메모리 초과가 발생했다.
*/
public class Main {
    
    final static int MULTIPLIER = 100000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String nums[] = br.readLine().split(" ");
        int n = parseInt(nums[0]);
        int m = parseInt(nums[1]);
        HashSet<Integer> smaller = new HashSet<>(n);
        boolean def[] = new boolean[n+1];
        while(m-- > 0) {
            String sp[] = br.readLine().split(" ");
            int a = parseInt(sp[0]);
            int b = parseInt(sp[1]);
            smaller.add(a*MULTIPLIER+b);
            def[a] = true;
            def[b] = true;
        }
        LinkedList<Integer> li = new LinkedList<>();
        LinkedList<Integer> toAdd = new LinkedList();
        for(int i = 1; i<=n; i++) {
            if(!def[i]) continue;
            int idx = -1;
            int k = 0;
            for(int b : li) {
                if(smaller.contains(i*MULTIPLIER+b)) {
                    idx = k;
                    break;
                }
                k++;
            }
            if(idx == -1) {
                li.add(i);
                continue;
            }
            li.add(idx, i);
            Iterator<Integer> iter = li.iterator();
            for(int p = 0; p<=idx; p++) {
                iter.next();
            }
            k = idx+1;
            // 8 5 6 7
            toAdd.clear();
            while(iter.hasNext()) {
                int b = iter.next();
                if(smaller.contains(b*MULTIPLIER+i)) {
                    iter.remove();
                    toAdd.addFirst(b);
                }
                k++;
            }
            for(int r : toAdd) {
                li.add(idx, r);
            }
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for(int v : li) {
            bw.write(String.valueOf(v));
            bw.write(' ');
        }
        for(int i = 1; i<=n; i++) {
            if(!def[i]) {
                bw.write(String.valueOf(i));
                bw.write(' ');
            }
        }
        bw.close();
    }
    

    
    
}
