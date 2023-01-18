#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/**
11779번: 최소비용 구하기 2
다익스트라를 이용해 풀었다. 
구체적인 경로를 구하기 위해 출발점으로 부터의 최단경로 테이블에 해당 목적지 까지의 최소 누적 비용 뿐만아니라 직전에 경유했던 노드번호까지 저장해서 이를 역으로 추적했다. 
*/

typedef struct {
	int to, fee;
} Edge;

const int INF = 1000000000;
int cities;
int (*buses[1000])[2]; // 목적지, 비용
int bSizes[1000];

Edge q[100001];
int nxt = 1;

void swap(int a, int b) {
	Edge t = q[a];
	q[a] = q[b];
	q[b] = t;
}

void push(Edge e) {
	q[nxt] = e;
	int i = nxt++;
	while(i > 1) {
		int k = i/2;
		if(q[k].fee > e.fee) {
			swap(i, k);
			i = k;
		} else break;
	}
}

Edge poll() {
	Edge r = q[1];
	swap(1, --nxt);
	int i = 1;
	while(i*2 < nxt) {
		int k = i*2;
		if(k+1 < nxt && q[k+1].fee < q[k].fee) {
			k++;
		}
		if(q[i].fee > q[k].fee) {
			swap(i, k);
			i = k;
		} else break;
	}
	return r;
}

void input() {
	scanf("%d", &cities);
	int busCount;
	scanf("%d", &busCount);
	do {
		int from,to,fee;
		scanf("%d%d%d", &from, &to, &fee);
		to--; from--;
		int duplicated = 0; // 출발 노드, 도착 노드가 동일한 버스가 있을 경우를 처리
		for(int i = 0; i<bSizes[from]; i++) {
			if(buses[from][i][0] != to) continue;
			duplicated = 1;
			if(buses[from][i][1] > fee) buses[from][i][1] = fee;
			break;
		}
		if(duplicated) continue;
		if(bSizes[from]%2 == 0) {
			buses[from] = realloc(buses[from], sizeof(int)*2*(bSizes[from]+2));
		}
		buses[from][bSizes[from]][0] = to;
		buses[from][bSizes[from]++][1] = fee;
	} while(--busCount > 0);
}

int main(void) {
	input();
	int start,end;
	scanf("%d%d", &start, &end);
	start--; end--;
	int g[1000][2]; // 총 비용, 직전 경유 도시
	for(int i = 0; i<cities; i++) g[i][0] = INF;
	g[start][0] = 0;
	g[start][1] = start;
	Edge fe = {start, 0};
	push(fe);
	do { // 다익스트라
		Edge edge = poll();
		int accFee = edge.fee;
		if(accFee > g[edge.to][0]) continue;
		for(int i = 0; i<bSizes[edge.to]; i++) {
			int to = buses[edge.to][i][0];
			int fee = buses[edge.to][i][1];
			int totalFee = accFee + fee;
			if(g[to][0] <= totalFee) continue;
			g[to][0] = totalFee; // 누적된 비용
			g[to][1] = edge.to; // 직전 경유지
			Edge e;
			e.to = to;
			e.fee = totalFee;
			push(e);
		}
	} while(nxt > 1);
	printf("%d\n", g[end][0]);
	int out[1000];
	int count = 0;
	int last = end;
	do { // 경로 역추적
		count++;
		out[1000 - count] = last;
		if(last == start) {
			break;
		}
		last = g[last][1];
	} while(1);
	printf("%d\n", count);
	for(int i = 1000-count; i<1000; i++) {
		printf("%d ", out[i]+1);
	}
}
