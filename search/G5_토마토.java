import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;

/**
7569번: 토마토
시도 횟수: 1
리스트 rottenTomatos에 탐색 대상을 담고 소거해나가는 방식으로 최적화
*/
public class Main {
    
    static int boxes[][][];
    static int sizeX, sizeY, sizeZ;
    static ArrayList<Point3D> rottenTomatos;
    
    public static void main(String[] args) throws IOException {    
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String sp[] = br.readLine().split(" ");
        sizeX = parseInt(sp[0]);
        sizeZ = parseInt(sp[1]);
        sizeY = parseInt(sp[2]);
        boxes = new int[sizeX][sizeY][sizeZ];
        int notEmpty = 0;
        rottenTomatos = new ArrayList<>(sizeX*sizeY*sizeZ);
        for(int y = 0; y<sizeY; y++) {
            for(int z = 0; z<sizeZ; z++) {
                StringTokenizer st = new StringTokenizer(br.readLine(), " ");
                for(int x = 0; x<sizeX; x++) {
                    int v = parseInt(st.nextToken());
                    boxes[x][y][z] = v;
                    if(v == 1) {
                        rottenTomatos.add(new Point3D(x,y,z));
                		    notEmpty++;
                    } else if(v==0) 
                        notEmpty++;
                }
            }
        }
        int rotten = rottenTomatos.size();
        int time = 0;
        while(!rottenTomatos.isEmpty() && rotten < notEmpty) {
            time++;
            ArrayList<Point3D> toAdd = new ArrayList<>();
            while(!rottenTomatos.isEmpty()) {
                Point3D p = rottenTomatos.remove(rottenTomatos.size()-1);
                if(p.y+1 < sizeY) rotten += check(p.x, p.y+1, p.z, toAdd);
                if(p.y-1 > -1) rotten += check(p.x, p.y-1, p.z, toAdd);
                if(p.x+1 < sizeX) rotten += check(p.x+1, p.y, p.z, toAdd);
            	  if(p.x-1 > -1) rotten += check(p.x-1, p.y, p.z, toAdd);
            	  if(p.z+1 < sizeZ) rotten += check(p.x, p.y, p.z+1, toAdd);
                if(p.z-1 > -1) rotten += check(p.x, p.y, p.z-1, toAdd);
            }
            rottenTomatos.addAll(toAdd);
        }
        if(rotten < notEmpty) {
            System.out.println("-1");
        } else {
            System.out.println(time);
        }
    }
    
    static int check(int x, int y, int z, ArrayList<Point3D> addingTarget) {
        if(boxes[x][y][z] == 0) {
            addingTarget.add(new Point3D(x,y,z));
            boxes[x][y][z] = 1;
        	return 1;
        }
        return 0;
    }
    
    
    static class Point3D {
        final int x,y,z;
        Point3D(int x,int y,int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
