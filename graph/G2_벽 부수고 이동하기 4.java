package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
16946번: 벽 부수고 이동하기 4
Union-Find를 사용했다. 트리의 노드의 개수를 불러오기 위해 Root Node에 트리의 크기를 절댓값으로 갖는 음수를 저장하고
병합시마다 이를 이용해 트리 크기를 업데이트했다.
*/
public class Main {
    
    static final int OFFSETS[][] = new int[][]{{0,1},{0,-1},{1,1},{1,-1}};
    static int len[] = new int[2];
    static boolean map[][]; // true -> 벽
    static int uf[];
    
    
    static int find(int i) {
        LinkedList<Integer> li = new LinkedList<>();
        while(uf[i] > -1) {
            li.add(i);
            i = uf[i];
        }
        for(int k : li) uf[k] = i;
        return i;
    }
    
    static void merge(int a, int b) {
        int pa = find(a);
        int pb = find(b);
        if(pa > pb) {
            uf[pb] += uf[pa];
            uf[pa] = pb;  
        } else if(pa != pb) {
            uf[pa] += uf[pb];
            uf[pb] = pa;
        }
    }
    
    static void tryMerge(int x1, int y1, int x2, int y2) {
        boolean a = map[x1][y1];
        boolean b = map[x2][y2];
        if(a != b || a) {
            return;
        }
        merge(ctoi(x1,y1), ctoi(x2,y2));
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        len[1] = parseInt(st.nextToken());
        len[0] = parseInt(st.nextToken());
        map = new boolean[len[0]][len[1]];
        uf = new int[len[0] * len[1]];
        for(int i = 0; i<uf.length; i++) {
            uf[i] = -1;
        }
        for(int y = 0; y<len[1]; y++) {
            String s = br.readLine();
            for(int x = 0; x<len[0]; x++) {
                if('1' == s.charAt(x)) {
                    map[x][y] = true;
                }
            }
        }
        for(int x = 1; x<len[0]; x++) {
            tryMerge(x-1, 0, x, 0);
        }
        for(int y = 1; y<len[1]; y++) {
            tryMerge(0, y-1, 0, y);
            for(int x = 1; x<len[0]; x++) {
                tryMerge(x, y, x-1, y);
                tryMerge(x, y, x, y-1);
            }
        }
        ArrayList<Integer> linked = new ArrayList<>(4);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for(int y = 0; y<len[1]; y++) {
            for(int x = 0; x<len[0]; x++) {
                if(!map[x][y]) {
                    bw.write('0');
                    continue;
                }
                for(int[] of : OFFSETS) {
                    int[] loc = new int[]{x,y};
                    loc[of[0]] += of[1];
                    if(!validate(loc[of[0]], of[0])) continue;
                    if(map[loc[0]][loc[1]]) continue;
                    int ci = ctoi(loc[0], loc[1]);
                    ci = find(ci);
                    if(linked.contains(ci)) continue;
                    linked.add(ci);
                }
                int result = 1;
                for(int k : linked) {
                    result -= uf[k];
                }
                linked.clear();
                bw.write(String.valueOf(result%10));
            }
            bw.write('\n');
        }
        bw.flush();
        
    }
    
    static boolean validate(int v, int axis) {
        return v > -1 && v < len[axis];
    }
    
    static int ctoi(int x, int y) {
        return x*len[1]+y;
    }
}
