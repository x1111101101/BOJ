package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
1197번: 최소 스패닝 트리
크루스칼 알고리즘과 Union-Find로 풀었다.
*/
public class Main {

    static int V, E;
    static int cTable[];
    static int nodes[][]; // a,b,c
    static int result = 0;
    static int linked = 0;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        V = parseInt(st.nextToken());
        E = parseInt(st.nextToken());
        cTable = new int[V];
        nodes = new int[E][3];
        for(int i = 0; i<V; i++) {
            cTable[i] = i;
        }
        for(int i = 0; i<E; i++) {
            st = new StringTokenizer(br.readLine());
            nodes[i][0] = parseInt(st.nextToken())-1;
            nodes[i][1] = parseInt(st.nextToken())-1;
            nodes[i][2] = parseInt(st.nextToken());
        }
        Iterator<int[]> iter = Arrays.stream(nodes).sorted((a,b)-> {
            if(a[2] > b[2]) return 1;
            return (a[2] == b[2]) ? 0 : -1;
        }).iterator();
        
        while(iter.hasNext()) {
            int[] eg = iter.next();
            if(cTable[eg[0]] == cTable[eg[1]]) continue;
            int a, b;
            if(cTable[eg[0]] > cTable[eg[1]]) {
                a = eg[1];
                b = eg[0];
            } else {
                a = eg[0];
                b = eg[1];
            }
            result += eg[2];
            int k = cTable[b];
            for(int i = 0; i<V; i++) {
                if(cTable[i] == k) {
                    cTable[i] = cTable[a];
                }
            }
            if(++linked == V-1) {
                break;
            }
        }
        System.out.println(result);
    }
}
