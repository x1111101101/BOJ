package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
17144번: 미세먼지 안녕!
이런류의 문제들이 게임 만드는 느낌이 들어서 재미있다.

해시셋으로 불필요한 탐색을 줄여보려 했는데, 오버헤드가 더 컸다.
해시셋 사용-> 미사용으로 바꾸니 500ms 단축

해시셋을 사용하지 않도록 변경함에 따라
Location 클래스에 hashcode, equals 함수 구현 부분은 삭제했다.
*/
public class Main {
    
    static int len[] = new int[2];
    static int map[][];
    static int machines[][] = new int[2][2];
    static int left;
    static int offsets[][] = new int[][]{{0,1}, {0,-1}, {1,1}, {1,-1}}; // axis, d
    static int machineOffsets[][][] = new int[][][]{{{1,-1}, {0,1}, {1,1}, {0,-1}}, {{1,1}, {0,1}, {1,-1}, {0,-1}}}; // axis, d
    
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String sp[] = br.readLine().split(" ");
        len[1] = parseInt(sp[0]); // y length
        len[0] = parseInt(sp[1]); // x length
        map = new int[len[0]][len[1]];
        int mCount = 0;
        int goalTick = parseInt(sp[2]);
        for(int y = 0; y<len[1]; y++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            for(int x = 0; x<len[0]; x++) {
                map[x][y] = parseInt(st.nextToken());
                if(map[x][y] == -1) {
                    machines[mCount][0] = x;
                    machines[mCount++][1] = y;
                }
            }
        }
        while(goalTick-- > 0) {
            diffuse();
            clean();
        }
        
        int result = 0;
        for(int y = 0; y<len[1]; y++)
            for(int x = 0; x<len[0]; x++)
                if(map[x][y] > 0) 
                    result += map[x][y];
        System.out.println(result);
    }
    
    // v > 0
    static void addDust(Location l, int v) {
        map[l.loc[0]][l.loc[1]] += v;
    }
    
    // v > 0
    static void removeDust(Location l, int v) {
        map[l.loc[0]][l.loc[1]] -= v;
    }
    
    static int getDust(Location l) {
        return map[l.loc[0]][l.loc[1]];
    }
    
    static boolean checkRange(int[] l, int axis) {
        int loc = l[axis];
        return loc > -1 && loc < len[axis];
    }
    
    static List<Location> toAdd = new ArrayList<>(1000);
    static List<Integer> toAddDusts = new ArrayList<>(1000);
    static List<Location> toSpread = new ArrayList<>(8);
    static void diffuse() {
        for(int x = 0; x<len[0]; x++) {
            for(int y = 0; y<len[1]; y++) {
                Location l = new Location(new int[]{x,y});
                if(getDust(l) < 5) continue;
                for(int[] offset : offsets) {
                    int locs[] = new int[] {l.loc[0], l.loc[1]};
                    locs[offset[0]] += offset[1];
                    if(!checkRange(locs, offset[0]))
                        continue;
                    Location nl = new Location(locs);
                    if(getDust(nl) == -1)
                        continue;
                    toSpread.add(nl);
                }
                int toSum = getDust(l)/5;
                removeDust(l, toSum*toSpread.size());
                for(Location sl : toSpread) {
                    toAdd.add(sl);
                    toAddDusts.add(toSum);
                }
                toSpread.clear();
            }
        }
        for(int i = 0; i < toAdd.size(); i++) {
            addDust(toAdd.get(i), toAddDusts.get(i));
        }
        toAdd.clear();
        toAddDusts.clear();
        toSpread.clear();
    }
    
    static void clean() {
        int range[][] = new int[][]{{-1, len[0]}, {-1, machines[0][1]+1}};
        int offset[][] = machineOffsets[0];
        clean(0, range, offset);
        range = new int[][]{{-1, len[0]}, {machines[1][1]-1, len[1]}};
        offset = machineOffsets[1];
        clean(1, range, offset);
    }
    
    static void clean(int m, int[][] ranges, int[][] offsets) {
        int[] loc = new int[]{machines[m][0], machines[m][1]};
        int[] offset = offsets[0];
        loc[offset[0]] += offset[1];
        Location fl = new Location(loc);
        removeDust(fl, getDust(fl));
        int offsetNum = 0;
        do {
            offset = offsets[offsetNum];
            int[] range = ranges[offset[0]];
            int[] nextLoc = new int[]{fl.loc[0], fl.loc[1]};
            nextLoc[offset[0]] += offset[1];
            if(range[0] >= nextLoc[offset[0]] || range[1] <= nextLoc[offset[0]]) {
                offsetNum++;
                continue;
            }
            Location next = new Location(nextLoc);
            if(getDust(next) == -1) {
                break;
            }
            addDust(fl, getDust(next));
            removeDust(next, getDust(next));
            fl = next;
            
        } while(offsetNum < 4);
    }
    
    static class Location {
        final int[] loc = new int[2];
        
        Location(int x, int y) {
            loc[0] = x;
            loc[1] = y;
        }
        
        Location(int[] l) {
            loc[0] = l[0];
            loc[1] = l[1];
        }
    }
    

}
