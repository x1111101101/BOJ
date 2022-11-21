package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
9376번: 탈옥
현재 해결책이 그래프 단순화 후 부루트 포스 밖에 생각나지 않는다.
그래프 단순화까진 구현 했으나 부루트 포스를 쓰면 시간 복잡도가 매우 커지니 시간초과가 날 것이다.
그래프에 대해 더 학습한 뒤 계속해서 풀어볼 예정이다.
*/
public class Main {
    
    final static char DOOR = '#';
    final static char EMPTY = '.';
    final static char WALL = '*';
    final static char TARGET = '$';
    final static int INF = 1000;
    final static int OFFSETS[][] = new int[][]{{0,1}, {0,-1}, {1,1}, {1,-1}}; 

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int cases = parseInt(br.readLine());
        while(cases-- > 0) {
            int min = new TestCase(br).getMin();
            System.out.println(min);
        }
    }
    
    static class TestCase {
        
        final boolean visit[];
        final int len[] = new int[2];
        final char map[][];
        final List<Integer> startPoints = new ArrayList(36);
        private int min = 100;
        
        
        TestCase(BufferedReader br) throws IOException {
            StringTokenizer st = new StringTokenizer(br.readLine());
            len[1] = parseInt(st.nextToken());
            len[0] = parseInt(st.nextToken());
            map = new char[len[0]][len[1]];
            for(int y = 0; y<len[1]; y++) {
                String line = br.readLine();
                for(int x = 0; x<len[0]; x++) {
                    map[x][y] = line.charAt(x);
                }
            }
            visit = new boolean[len[0]*len[1]];
            for(int i = 0; i<len[0]; i++) {
                if(map[i][0] != WALL) {
                    startPoints.add(locToInt(i, 0));
                }
                if(map[i][len[1]-1] != WALL) {
                    startPoints.add(locToInt(i, len[1]-1));
                }
            }
            for(int i = 1; i<len[1]-1; i++) {
                if(map[0][i] != WALL) {
                    startPoints.add(locToInt(0, i));
                }
                if(map[len[0]-1][i] != WALL) {
                    startPoints.add(locToInt(len[0]-1, i));
                }
            }
        }
        
        // range check
        boolean rc(int[] loc, int axis) {
            int v = loc[axis];
            return -1 < v && v < len[axis];
        }
        
        boolean rc(int v, int axis) {
            return -1 < v && v < len[axis];
        }
    
        int getMin() {
            int size = len[0] * len[1];
            
            // BFS로 그래프 단순화 -> 빈공간을 고려해 그래프 재구성
            int table[][] = new int[size][size];
            int ids[][] = new int[size][2]; // loc->id, id->loc
            for(int i = 0; i<size; i++) {
                Arrays.fill(ids[i], -1);
                Arrays.fill(table[i], INF);
            }
            int nodes = 0;
            
            boolean visit[][] = new boolean[len[0]][len[1]];
            List<Integer> idBfs = new ArrayList<>(100);
            List<Integer> idBfsSts = new ArrayList<>(100);
            for(int x = 0; x<len[0]; x++) {
                for(int y = 0; y<len[1]; y++) {
                    if(visit[x][y]) continue;
                    if(map[x][y] == WALL) continue;
                    visit[x][y] = true;
                    idBfs.clear();
                    idBfsSts.clear();
                    int locToI = locToInt(x,y);
                    idBfs.add(locToI);
                    while(!idBfs.isEmpty()) {
                        int r[] = intToLoc(idBfs.remove(idBfs.size()-1));
                        for(int[] offset : OFFSETS) {
                            int l[] = new int[] {r[0], r[1]};
                            l[offset[0]] += offset[1];
                            if(!rc(l, offset[0]) || visit[l[0]][l[1]]) continue;
                            int c = map[l[0]][l[1]];
                            if(c == WALL) continue;
                            int lti = locToInt(l[0], l[1]);
                            visit[l[0]][l[1]] = true;
                            idBfs.add(lti);
                            if(c == EMPTY && !startPoints.contains(lti)) continue;
                            idBfsSts.add(lti);
                        }
                    }
                    for(int il : idBfsSts) {
                        int id = ids[il][0];
                        if(id == -1) {
                            id = nodes++;
                            ids[il][0] = id;
                            ids[id][1] = il;
                        }
                    }
                    for(int a : idBfsSts) {
                        for(int b : idBfsSts) {
                            table[ids[a][0]][ids[b][0]] = 0;
                            table[ids[b][0]][ids[a][0]] = 0;
                        }
                    }
                }
            }
            // 단순화 끝
            List<Integer> targetIds = new ArrayList<>(2);
            List<Integer> startingIds = new ArrayList<>(startPoints.size());
            for(int id = 0; id<nodes; id++) {
                int lti = ids[id][1];
                int loc[] = intToLoc(lti);
                if(map[loc[0]][loc[1]] == TARGET) {
                    targetIds.add(id);
                    if(targetIds.size() == 2) break;
                }
            }
            
            return -1;
        }
        
        int[] intToLoc(int i) {
            return new int[] {i%len[0], i/len[0]};
        }
        
        int locToInt(int x, int y) {
            return len[0]*y+x;
        }
    
    }

    
    
}
