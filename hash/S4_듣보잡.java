package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
1764번: 듣보잡
아스키 코드 값을 이용해 사전 순 정렬 
*/
public class Main {
    
    static int min(int a, int b) {
        return a<b ? a : b;
    }
    
    public static void main(String[] args) throws IOException {    
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       	String[] sp = br.readLine().split(" ");
        int n = parseInt(sp[0]);
        int k = parseInt(sp[1]);
        HashSet<String> set = new HashSet<>();
        while(n-- > 0) {
            set.add(br.readLine());
        }
        List<String> results = new ArrayList<>();
        while(k-- > 0) {
            String s = br.readLine();
            if(set.contains(s)) {
                results.add(s);
            }
        }
        results.sort((a,b)-> {
           	int x = min(a.length(), b.length());
            for(int i = 0; i<x; i++) {
                if(a.charAt(i) > b.charAt(i)) {
                    return 1;
                }
                if(a.charAt(i) < b.charAt(i)) {
                    return -1;
                }
            }
            if(a.length() == b.length()) {
                return 0;
            }
            if(a.length() > b.length()) {
                return 1;
            }
            return -1; 
        });
        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(System.out));
        w.write(String.valueOf(results.size()));
        w.write('\n');
        for(String s: results) {
            w.write(s);
            w.write('\n');
        }
        w.flush();
    }
    
}
