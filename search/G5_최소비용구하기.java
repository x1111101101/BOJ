package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
1916번: 최소 
데이크스트라 알고리즘 문제라는데 처음 듣는 알고리즘이지만 일단 풀어봤다. 

다른 사람들은 이 문제에서 우선순위 큐를 많이 사용하는 것 같다.
우선순위 큐가 뭔지 학습하고 데이크스트라도 학습해야겠다.
BFS 로직을 짜다 실수를 했는데, 계속 다른곳에서 문제를 찾느라 시간을 허비했다. 30분만에 풀었을 문제를 1시간을 써서 풀었다.

최적화:
- 같은 출발 노드와 같은 목적지 노드가 여러개 있을 때 비용이 적은 노드만을 선별해서 탐색
- minFees 테이블을 이용해 탐색 중 어떤 노드에 도달했을 때 다른 case보다 높은 누적 비용을 사용했을 때 해당 case는 탐색을 종료시키게 함
- 탐색용 리스트를 두개를 쓰면서 변수 swap을 이용해 arraycopy가 발생하지 않도록 했다.
*/
public class Main {
    
    static long minFees[];
    static int cities[][][];
    static int sizes[];
    
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int cityCount = parseInt(br.readLine());
        int busCount = parseInt(br.readLine());
        cities = new int[cityCount][cityCount][2];
        sizes = new int[cityCount];
        minFees = new long[cityCount];
        for(int i = 0; i<cityCount; i++) {
            minFees[i] = Long.MAX_VALUE;
        }
        for(int i = 0; i<busCount; i++) {
           	String sp[] = br.readLine().split(" ");
       		int from = parseInt(sp[0]) - 1;
            int to = parseInt(sp[1]) - 1;
            if(from == to) continue;
            int fee = parseInt(sp[2]);
            int index = sizes[from];
            int b = 10000000;
            for(int k = 0; k<sizes[from]; k++) {
                int t = cities[from][k][0];
                if(t != to) continue;
                b = cities[from][k][1];
                index = k;
                break;
            }
            if(b < fee) {
                continue;
            }
            if(b == 10000000) {
                index = sizes[from]++;
            }
            cities[from][index][0] = to;
            cities[from][index][1] = fee;
        }
        String sp[] = br.readLine().split(" ");
    	  int from = parseInt(sp[0]) - 1;
        int dest = parseInt(sp[1]) - 1;
        minFees[from] = 0;
        ArrayList<Integer> cityTable = new ArrayList<>(cityCount);
        ArrayList<Long> feeTable = new ArrayList<>(cityCount);
        ArrayList<Integer> cityTable2 = new ArrayList<>(cityCount);
        ArrayList<Long> feeTable2 = new ArrayList<>(cityCount);
        cityTable.add(from);
        feeTable.add(0L);
    	  while(!cityTable.isEmpty()) {
            feeTable2.clear();
            cityTable2.clear();
            do {
                int city = cityTable.remove(cityTable.size()-1);
                long totalFee = feeTable.remove(feeTable.size()-1);
                for(int i = 0; i<sizes[city]; i++) {
                    int to = cities[city][i][0];
                    int fee = cities[city][i][1];
                    if(fee + totalFee < minFees[to]) {
                        minFees[to] = fee + totalFee;
                        if(to == dest) {
                            continue;
                        }
                        cityTable2.add(to);
                        feeTable2.add(fee + totalFee);
                    }
           		}
            } while(!cityTable.isEmpty());
            ArrayList temp = cityTable2;
            cityTable2 = cityTable;
            cityTable = temp;
            temp = feeTable2;
            feeTable2 = feeTable;
            feeTable = temp;
        }
        System.out.println(minFees[dest]);
    }
    

}
