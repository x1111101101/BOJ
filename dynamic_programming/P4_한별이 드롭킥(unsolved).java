package project;

import java.util.*;
import java.io.*;
import java.util.Map.Entry;

import static java.lang.Integer.*;
import static java.lang.Math.*;

/**
27449번: 한별이 드롭킥!
아직 시간초과로 해결 못한 문제다.
*/

/**
그리디로는 못품

DP

5000^2 = 25000000

해시?

각 단계에서:
- 왼쪽 상승 기류로 이동
- 오른쪽 상승 기류로 이동
- 어떠한 상승 기류로도 이동하지 않음

셋 중 하나의 옵션을 취할 수 있다.
*/
public class Main {
    
    static int n,m;
    static HashMap<VisitId, VisitResult> dp[] = new HashMap[2];
    static int revIndex[] = new int[]{1,0}; 
    static ArrayList<Boost> boosts = new ArrayList<>();
    static long ans = 0;
    
    static {
        for(int i = 0; i<dp.length; i++) dp[i] = new HashMap<>();
    }
    
    // return -1
    static long move(int currentX, long height, Boost goal) {
        int dist = abs(goal.x - currentX);
        if(dist >= height) {
            return -1;
        }
        return height - dist + goal.boost;
    }
    
    
    static void tryRecord(VisitId visit, State current, int direction, HashMap<VisitId, VisitResult> ndp) {
        int delta = -1 + 2*direction;
        VisitId newVisit = new VisitId();
        newVisit.visit[0] = visit.visit[0];
        newVisit.visit[1] = visit.visit[1];
        newVisit.current = direction;
        newVisit.visit[direction] += delta;
        int currentX = boosts.get(visit.visit[visit.current]).x;
        int goal = newVisit.visit[direction];
        if(goal < 0 || goal >= n) return;
        Boost g = boosts.get(goal);
        long tAlt = move(currentX, current.height, g);
        //System.out.printf("curFt: %d, curx: %d, curH: %d, tAlt: %d\n", current.flyTime, currentX, current.height, tAlt);
        if(tAlt == -1) {
            return;
        }
        State s = new State(current.flyTime + abs(currentX - g.x), tAlt);
        VisitResult recordTarget = ndp.get(newVisit);
        if(recordTarget == null) {
            recordTarget = new VisitResult(s);
            ndp.put(newVisit, recordTarget);
        } else {
            if(recordTarget.tryAdd(s)) {
                
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        init();
        int dpidx = 0;
        while(!dp[dpidx].isEmpty()) {
            HashMap<VisitId, VisitResult> cdp = dp[dpidx];
            HashMap<VisitId, VisitResult> ndp = dp[revIndex[dpidx]];
            for(Entry<VisitId, VisitResult> e : cdp.entrySet()) {
                VisitId visit = e.getKey();
                VisitResult result = e.getValue();
                for(State s : result.states) {
                    long nf = s.flyTime + s.height;
                    ans = nf > ans ? nf : ans;
                    // left
                    tryRecord(visit, s, 0, ndp);
                    // right
                    tryRecord(visit, s, 1, ndp); 
                }
                
            }
            cdp.clear();
            dpidx = revIndex[dpidx];
        }
        System.out.println(ans);
    }
    
    static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = parseInt(st.nextToken());
        m = parseInt(st.nextToken());
        ans = m;
        for(int i = 0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            Boost b = new Boost();
            b.x = parseInt(st.nextToken());
            b.boost = parseInt(st.nextToken());
            boosts.add(b);
        }

        // dp 테이블 초기화
        Collections.sort(boosts);
        int closet = -1;
        for(int i = n-1; i>-1; i--) {
            int x = boosts.get(i).x;
            if(x > 0) continue;
            closet = i;
            VisitId fvi = new VisitId();
            fvi.visit[0] = closet;
            fvi.visit[1] = closet;
            fvi.current = 0;
            long alt = move(0, m, boosts.get(closet));
            if(alt != -1) {
                State s = new State(-x,alt);
                dp[0].put(fvi, new VisitResult(s));
            }
            break;
        }
        if(closet+1 < n) {
            VisitId fvi = new VisitId();
            fvi.visit[0] = closet+1;
            fvi.visit[1] = closet+1;
            fvi.current = 1;
            Boost b = boosts.get(closet+1);
            long alt = move(0, m, b);
            if(alt != -1) {
                State s = new State(b.x, alt);
                dp[0].put(fvi, new VisitResult(s));
            }
        }
    }
    static class Boost implements Comparable<Boost> {
        int x, boost;
        
        @Override
        public int compareTo(Boost b) {
            return x > b.x ? 1 : -1;
        }
    }
    
    static class VisitResult {
        List<State> states = new ArrayList<>();
        long minTime, minHeight;
        
        VisitResult(State s) {
            minTime = s.flyTime;
            minHeight = s.height;
            states.add(s);
        }
        
        final static List<Integer> toRemove = new ArrayList<>(5000);
        boolean tryAdd(State s) {
            for(int i = 0; i<states.size(); i++) {
                State g = states.get(i);
                if(g.flyTime >= s.flyTime && g.height >= s.height) {
                    return false;
                }
            }
            states.add(s);
            if(s.flyTime <= minTime) {
                minTime = s.flyTime;
            } else if(s.height <= minHeight) {
                minHeight = s.height;
            }
            for(int i = 0; i<states.size(); i++) {
                State g = states.get(i);
                if(g.flyTime <= s.flyTime && g.height <= s.height) {
                    toRemove.add(i);
                }
            }
            for(int i = 0; i<toRemove.size(); i++) {
                int idx = toRemove.get(i);
                states.remove((int)(idx-i));
            }
            toRemove.clear();
            return true;
        }
        
    }
    
    static class State {
        long flyTime;
        long height;
        
        State(){}
        State(long t, long h) {
            flyTime = t; height = h;
        }
        public String toString() {
            return "ft: " + flyTime + ", height: " + height;
        }
    }
    
    static class VisitId {
        
        int visit[] = new int[2]; // left, right
        int current; // visit field's index
        
        @Override
        public int hashCode() {
            return Objects.hash(visit[0], current, visit[1]);
        }
        @Override
        public boolean equals(Object o) {
            if(!(o instanceof VisitId)) return false;
            VisitId v = (VisitId) o;
            return visit[0] == v.visit[0] && 
                visit[1] == v.visit[1] && 
                current == v.current;
        }
        @Override
        public String toString() {
            return visit[0] + "~" + visit[1] + ", " + current;
        }
        
    }

}
