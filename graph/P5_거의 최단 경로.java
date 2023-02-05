package project;

import java.util.*;
import java.io.*;

import static java.lang.Integer.*;

/**
5719번: 거의 최단 경로
플레티넘 5의 난이도인 만큼 10번 이상의 시도 끝에 맞춘 문제다.

오답들의 문제점을 나열하자면 아래와 같다.
- 다익스트라로 구한 최단 경로에서 재귀함수를 통해 하나씩 간선을 배제하면서 다익스트라를 계속해서 돌리는 방법을 썼다. -> 시간 초과 -> BFS를 써야한다.
- BFS로 다익스트라 중복 경로들을 파악했지만, 그 중복 경로들을 배제하는 루프를 돌리는 과정에서 무한 루프 발생 -> 배제 여부 체크를 하는 배열을 활용
- 노드별로 배제 여부를 저장하는 배열을 활용해서 구현했는데 오답 발생. 배제 여부 확인 시 그 간선은 배제를 안시킨게 원인(실수) -> 그 노드에 대응되는 간선을 여러개가 있을 수도 있으므로
다른 간선에서 배제 체크 배열 값을 변경했을 수도 있음 -> 그 노드를 기준으로 추가적인 루프는 돌지 않되 그 간선은 배제 시켜야 한다.
*/

public class Main {

    static int nodes;
    static int start,end;
    static ArrayList<Edge> edges[] = new ArrayList[500];
    static int minDist[] = new int[500];
    static int INF = 5000010;
    static int blocked[][];
    
    static {
        for(int i = 0; i<edges.length; i++) {
            edges[i] = new ArrayList<>();
        }
    }
    
    static int calcMinDist() {
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
        for(int i = 0; i<nodes; i++) {
            minDist[i] = INF;
        }
        pq.add(new Edge(start,0));
        minDist[start] = 0;
        while(!pq.isEmpty()) {
            Edge edge = pq.poll();
            if(edge.dist > minDist[edge.to]) {
                continue;                
            }
            for(Edge e : edges[edge.to]) {
                int nd = edge.dist + e.dist + blocked[edge.to][e.to]*INF;
                if(nd >= minDist[e.to]) {
                    continue;
                }
                minDist[e.to] = nd;
                pq.add(new Edge(e.to, nd));
            }
        }
       
        return minDist[end];
    }
    
    static void trace() {
        LinkedList<Integer> t = new LinkedList<>();
        t.add(start);
        List<Integer> visit[] = new List[nodes];
        visit[start] = new ArrayList<>(5);
        while(!t.isEmpty()) {
            int node = t.removeFirst();
            int accDist = minDist[node];
            for(Edge e : edges[node]) {
                int nd = accDist + e.dist;
                if(nd != minDist[e.to]) continue;
                if(visit[e.to] == null) {
                    visit[e.to] = new ArrayList<>(5);
                    visit[e.to].add(node);
                    t.add(e.to);
                } else {
                    visit[e.to].add(node);
                }
            }
        }
        boolean complete[] = new boolean[nodes];
        complete[end] = true;
        t.add(end);
        while(!t.isEmpty()) {
            int cur = t.removeFirst();
            if(visit[cur] == null) continue;
            for(int prev : visit[cur]) {
                blocked[prev][cur] = 1;
                if(complete[prev]) {
                    continue;
                }
                complete[prev] = true;
                t.add(prev);
            }
        }
        
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        do {
            StringTokenizer st = new StringTokenizer(br.readLine());
            nodes = parseInt(st.nextToken());
            int m = parseInt(st.nextToken());
            if(nodes == 0 && m == 0) break;
            blocked = new int[nodes][nodes];
            st = new StringTokenizer(br.readLine());
            start = parseInt(st.nextToken());
            end = parseInt(st.nextToken());
            for(int i = 0; i<m; i++) {
                st = new StringTokenizer(br.readLine());
                int from = parseInt(st.nextToken());
                Edge e = new Edge(parseInt(st.nextToken()), parseInt(st.nextToken()));
                edges[from].add(e);
            }
            int md = calcMinDist();
            int d;
            if(md >= INF) {
                d = -1;
            } else {
                trace();
                d = calcMinDist();
                if(d >= INF) d = -1;
            }
            bw.append(String.valueOf(d));
            bw.append("\n");
            // last
            for(int i = 0; i<nodes; i++) {
                edges[i].clear();
            }
        } while(true);
        bw.close();
    }
    
    static class Edge implements Comparable<Edge> {
        final int to,dist;
        Edge(int t, int d) {
            to=t;dist=d;
        }
        
        public int compareTo(Edge e) {
            return Integer.compare(dist,e.dist);
        }
    }

}
