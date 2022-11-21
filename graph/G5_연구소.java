package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
14502번: 
최적의 위치를 찾는 알고리즘을 생각해보려 했지만, 부루트 포스 밖에 답이 안보였다. 부루트 포스 문제가 맞았다.
2차원 평면을 1차원의 확장으로 생각해서 하나의 좌표쌍을 하나의 수로 취급해 부루트 포스를 구현했다.
*/
public class Main {
    
    static int l[] = new int[2];
    static int map[][] = new int[8][8];
    static int viruses[][] = new int[64][2];
    static int virusCount = 0;
    static int min = 10000;
    static int cmb[] = new int[3];
    static int sz;
    static int st[][] = new int[64][2];

    static int visit(int axis, int[] o) {
        int v = o[axis];
        if(v > -1 && v < l[axis] && map[o[0]][o[1]] == 0) {
            st[sz][0] = o[0];
            st[sz++][1] = o[1];
            map[o[0]][o[1]] = 3;
            return 1;
        }
        return 0;
    }

    // 0: empty, 1: wall, 2: virus, 3: virtual wall
    static void check() {
        for(int i = 0; i<3; i++) {
            int c = cmb[i];
            int x = c%l[0];
            int y = c/l[0];
            if(map[x][y] != 0) return;
        }
        for(int i = 0; i<3; i++) {
            int c = cmb[i];
            int x = c%l[0];
            int y = c/l[0];
            map[x][y] = 3;
        }
        int count = 3;
        sz = virusCount;
        for(int i = 0; i<virusCount; i++) {
            st[i][0] = viruses[i][0];
            st[i][1] = viruses[i][1];
        }

        while(sz > 0) {
            sz--;
            int loc[] = new int[]{st[sz][0], st[sz][1]};
            loc[0]++;
            count += visit(0, loc);

            loc[0] -= 2;
            count += visit(0, loc);

            loc[0]++;
            loc[1]++;
            count += visit(1, loc);

            loc[1] -= 2;
            count += visit(1, loc);
        }
        for(int x = 0; x<l[0]; x++)
            for(int y = 0; y<l[1]; y++)
                if(map[x][y] == 3) map[x][y] = 0;
        if(count < min) min = count;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        l[1] = parseInt(st.nextToken());
        l[0] = parseInt(st.nextToken());
        int emptyCount = 0;
        for(int y = 0; y<l[1]; y++) {
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x<l[0]; x++) {
                map[x][y] = parseInt(st.nextToken());
                if(map[x][y] == 2) {
                    viruses[virusCount][0] = x;
                    viruses[virusCount][1] = y;
                    virusCount++;
                } else if(map[x][y] == 0) {
                    emptyCount++;
                }
            }
        }
        int sz = l[0] * l[1];
        for(cmb[0] = 0; cmb[0]<sz; cmb[0]++) {
            for(cmb[1] = cmb[0]+1; cmb[1]<sz; cmb[1]++) {
                for(cmb[2] = cmb[1]+1; cmb[2]<sz; cmb[2]++) {
                    check();
                }
            }
        }
        System.out.println(emptyCount - min);
    }


    
    
}
