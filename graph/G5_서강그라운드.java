package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
14938번: 서강그라운드
데이크스트라, 플로이드워셜 태그가 붙은 문제인데, 이 알고리즘들은 아직 공부한적이 없지만 일단 풀어봤다.
정답을 제출하고 다른 답안들과 비교해봤는데, 플로이드 워셜을 쓴 답안들은 40% 정도 시간을 덜 썼다.
플로이드 워셜, 데이크스트라를 공부하면 다시 풀어봐야겠다.
*/
public class Main {

    static int maxDistance;
    static int regions[][];
    static int items[];
    
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String sp[] = br.readLine().split(" ");
        int n = parseInt(sp[0]);
        maxDistance = parseInt(sp[1]);
        int r = parseInt(sp[2]);
        items = new int[n];
        regions = new int[n][n];
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        for(int i = 0; i<n; i++) {
            items[i] = parseInt(st.nextToken());
        }
        while(r-- > 0) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = parseInt(st.nextToken()) - 1;
            int b = parseInt(st.nextToken()) - 1;
            int len = parseInt(st.nextToken());
            if(regions[a][b] == 0 || regions[a][b] > len) {
                regions[a][b] = len;
            }
            if(regions[b][a] == 0 || regions[b][a] > len) {
                regions[b][a] = len;
            }
        }
        int maxItems = -1;
        for(int start = 0; start < n; start++) {
            int pickedUp[] = new int[n];
            int minDist[] = new int[n];
            Arrays.fill(minDist, -1);
            List<Integer> regionTable = new LinkedList<>();
            List<Integer> distTable = new LinkedList<>();
            pickedUp[start] = items[start];
            regionTable.add(start);
            distTable.add(0);
            while(!regionTable.isEmpty()) {
                int region = regionTable.remove(0);
                int dist = distTable.remove(0);
                pickedUp[region] = items[region];
                for(int i = 0; i<n; i++) {
                    int d = regions[region][i];
                    if(d <= 0) {
                        continue;
                    }
                    if(d+dist > maxDistance) {
                        continue;
                    }
                    if(minDist[i] >= 0 && minDist[i] < d+dist) {
                        continue;
                    }
                    minDist[i] = d+dist;
                    regionTable.add(i);
                    distTable.add(d+dist);
                }
            }
            int result = 0;
            for(int i : pickedUp) {
                result += i;
            }
            if(maxItems < result) {
                maxItems = result;
            }
        }
        System.out.println(maxItems);   
    }
}
