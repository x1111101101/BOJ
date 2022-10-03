package project;

import java.io.*;
import java.util.*;

import java.lang.annotation.*;

import static java.lang.Math.*;
import static java.lang.Character.*;
import static java.lang.Integer.*;

/**
9228번: 열쇠
bfs 방식에서 계속 메모리 초과가 발생되서 dfs 탐색으로 해결했다.
방문한 노드는 제거하고 재방문 해야될 노드들을 따로 추려놔서 탐색 횟수를 최소화

배운 점:
	1. DFS/BFS 중 무엇을 선택하느냐에 따라 공간복잡도가 달라진다는걸 유념해야한다.
  	2. visited 해시셋을 사용하는 대신 노드를 없애버려도 된다! 이 문제에선 *로 바꾼 것이 노드를 없애는 것과 같다.
	3. Object 클래스의 hashCode 메서드에서 C는 대문자였다! hashcode로 작성해서 중복처리가 안되어 애를 좀 먹었다.

*/
public class Main {
    
    static char cells[][] = new char[100][100];
    static int sizeX, sizeZ;
    static List<Point> points = new ArrayList<>();
    static int papers = 0;
    static HashSet<Character> keys = new HashSet(); // 대문자만 저장
    static HashMap<Character, Set<Point>> locks = new HashMap();
    
    public static void main(String[] args) throws IOException {    
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int cases = parseInt(br.readLine());
        while(cases-- > 0) {
            points = new ArrayList();
            keys.clear();
            locks.clear();
            papers = 0;
            
            HashSet<Character> existingKeys = new HashSet<>(); 
            String[] sp = br.readLine().split(" ");
            sizeZ = parseInt(sp[0]);
            sizeX = parseInt(sp[1]);
            for(int z = 0; z<sizeZ; z++) {
                String in = br.readLine();
            	for(int x = 0; x< sizeX; x++) {
                    char c = in.charAt(x);
                    cells[x][z] = c;
                	if(isAlphabetic(c)) {
                        if(isLowerCase(c)) {
                            existingKeys.add(toUpperCase(c));
                        }
                    }
                }
            }
            
            String keysStr = br.readLine();
            if(!keysStr.equals("0")) {
            	for(int i = 0; i<keysStr.length(); i++) {
                	keys.add(toUpperCase(keysStr.charAt(i)));
            	}
            }
            existingKeys.addAll(keys);
            
           
            for(int x = 0; x< sizeX; x++) {
                for(int z = 0; z<sizeZ; z++) {
                    char c = cells[x][z];
                    if(!isAlphabetic(c) || !isUpperCase(c)) continue;
                    if(!existingKeys.contains(c)) {
                        cells[x][z] = '*';
                    } else if(keys.contains(c)) {
                        cells[x][z] = '.';
                    }
                }
            }
            keys.clear();
            
            // 입구 찾기: 테두리 칸들 중 벽을 제외한 모든 칸은 입구가 될 수 있음
            for(int z = 0; z<sizeZ; z+= sizeZ-1) {
                for(int i = 0; i<sizeX; i++) {
                    char c = cells[i][z];
                    if(c != '*') points.add(new Point(i, z));
                }
            }
            for(int z = 1; z<sizeZ-1; z++) {
                for(int x = 0; x<sizeX; x+=sizeX-1) {
                    char c = cells[x][z];
                    if(c != '*') points.add(new Point(x, z));
                }
            }
            
            
            while(!points.isEmpty()) {
                int moved = 0;
                while(!points.isEmpty()) {
                    Point p = points.remove(points.size()-1);
                    moved += tryVisit(p.x, p.z, points);
                }
                for(char key : keys) {
                    Set<Point> set = locks.get(key);
                    if(set == null) continue;
                    List<Point> toRem = new LinkedList();
                    for(Point p : set) {
                        cells[p.x][p.z] = '*';
                        addNearby(p.x, p.z, points);
                        toRem.add(p);
                        moved++;
                    }
                    for(Point rem: toRem) set.remove(rem);
                }
                
                if(moved == 0) {
                    break;
                }
            }
            System.out.println(papers);
        }
    }
    
    static int tryVisit(int x, int z, List<Point> toAdd) {
        char c = cells[x][z];
        if(c=='.') {
            cells[x][z] = '*';
            addNearby(x,z,toAdd);
            return 1;
        }
    	if(c == '$') {
            papers++;
            cells[x][z] = '*';
            addNearby(x,z,toAdd);
            return 1;
        } 
        if(isUpperCase(c)) {
            locks.computeIfAbsent(c, k->new HashSet()).add(new Point(x,z));
            cells[x][z] = '*';
            return 1;
        }
        keys.add(toUpperCase(c));
        cells[x][z] = '*';
        addNearby(x,z,toAdd);
        return 1;
    }
    
    static void addNearby(int x, int z, List<Point> dest) {
        if(check(x+1, z)) dest.add(new Point(x+1, z));
        if(check(x-1, z)) dest.add(new Point(x-1, z));
        if(check(x, z+1)) dest.add(new Point(x, z+1));
        if(check(x, z-1)) dest.add(new Point(x, z-1));
    }
    
    static boolean check(int x, int z) {
        return x > -1 && x < sizeX
            && z > -1 && z < sizeZ 
            && cells[x][z] != '*';
    }
    
    static class Point {
        final int x,z;
        Point(int x,int z) {
            this.x = x;
            this.z = z;
        }
        @Override
        public int hashCode() {
            return Objects.hash(x,z);
        }
        @Override
        public boolean equals(Object o) {
            if(o instanceof Point) {
                Point p = (Point) o;
            	return p.x==x && p.z==z;
        	} return false;
        }
    }
}
