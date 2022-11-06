package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
1315번: RPG
첫 시도 후 두달이 지난뒤 다시 풀어보았다.
훨씬 간결한 코드로 구현이 되었지만, 여전히 메모리 초과가 난다.
이제 다이나믹 프로그래밍으로 풀기만 성공하면 완벽하다.
*/
public class Main {
    
    final static int STR = 0, INT = 1, PNT = 2;
    static int quests[][];
    static int questAmount;
    static int max = 0;
    
    public static void main(String[] args) throws IOException {    
        int scores[];
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        questAmount = parseInt(br.readLine());
        quests = new int[questAmount][3];
        for(int i = 0; i<questAmount; i++) {
            String[] sp = br.readLine().split(" ");
            quests[i][STR] = parseInt(sp[0]);
            quests[i][INT] = parseInt(sp[1]);
            quests[i][PNT] = parseInt(sp[2]);
        }
        new DFS(new int[]{1,1,0}, new boolean[questAmount], 0);
        System.out.println(max);
    }
    
    static int[] copy(int[] t) {
        int arr[] = new int[t.length];
        for(int i = 0; i<t.length; i++) arr[i] = t[i];
        return arr;
    }
    static boolean[] copy(boolean[] t) {
        boolean arr[] = new boolean[t.length];
        for(int i = 0; i<t.length; i++) arr[i] = t[i];
        return arr;
    }
    
    static class DFS {
        int stats[];
        int cleared = 0;
        boolean visited[];
        
        DFS(int s[], boolean[] v, int c) {
            this.cleared = c;
            this.visited = v;
            this.stats = s;
            for(int i = 0; i <questAmount; i++) {
                if(visited[i]) continue;
                if(quests[i][STR] <= stats[STR] || quests[i][INT] <= stats[INT]) {
                    visited[i] = true;
                    stats[PNT] += quests[i][PNT];
                    cleared++;
                }
            }
            for(int i = 0; i <questAmount; i++) {
                if(visited[i]) continue;
                int strDif = quests[i][STR] - stats[STR];
                int intDif = quests[i][INT] - stats[INT];
                
                if(strDif <= stats[PNT]) {
                    int newStats[] = copy(stats);
                    newStats[STR] += strDif;
                    newStats[PNT] -= strDif - quests[i][PNT];
                    boolean newVisited[] = copy(visited);
                	newVisited[i] = true;
                    new DFS(newStats, newVisited, cleared+1);
                }
                if(intDif <= stats[PNT]) {
                    int newStats[] = copy(stats);
                    newStats[INT] += intDif;
                    newStats[PNT] -= intDif - quests[i][PNT];
                    boolean newVisited[] = copy(visited);
                    newVisited[i] = true;
                    new DFS(newStats, newVisited, cleared+1);
                }
            }
            if(cleared > max) max = cleared;
        }
    }
}
