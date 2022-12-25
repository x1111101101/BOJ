package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
4195번: 친구 네트워크
기본적인 Union-Find 문제.
노드 이름이 String으로 주어져 이름 별로 id를 부여하고 해시맵을 이용해 id를 불러옴
Root Node에 트리의 크기를 절댓값으로 갖는 음수를 저장
*/
public class Main {
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int cases = parseInt(br.readLine());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        while(cases-- > 0) {
            int edges = parseInt(br.readLine());
            TestCase t = new TestCase(edges);
            while(edges-- > 0) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                bw.write(String.valueOf(t.addEdge(st.nextToken(), st.nextToken())));
                bw.write('\n');
            }
        }
        bw.close();
    }
    
    static class TestCase {
        List<Integer> parents;
        HashMap<String, Integer> ids = new HashMap<>();
        int nxt = 0;
        
        TestCase(int edges) {
            parents = new ArrayList<Integer>(edges);
        }
        
        int getId(String name) {
            return ids.computeIfAbsent(name, k->{
                parents.add(-1);
                return nxt++;
            });
        }
        
        final static ArrayList<Integer> stack = new ArrayList<>(200000);
        int find(int id) {
            if(id < 0) return id;
            while(parents.get(id) > -1) {
                stack.add(id);
                id = parents.get(id);
            }
            for(int c : stack) {
                parents.set(c, id);
            }
            stack.clear();
            return id;
        }
        
        int merge(int a, int b) {
            int pa = find(a);
            int pb = find(b);
            if(pa > pb) {
                parents.set(pb, parents.get(pb) + parents.get(pa));
                parents.set(pa, pb);
                return parents.get(pb) * -1;
            } else if (pa < pb) {
                parents.set(pa, parents.get(pa) + parents.get(pb));
                parents.set(pb, pa);
                return parents.get(pa) * -1;
            }
            return parents.get(pa) * -1;
        }
        
        int addEdge(String a, String b) {
            int ia = getId(a);
            int ib = getId(b);
            return merge(ia, ib);
        }
        
    }
    
}
