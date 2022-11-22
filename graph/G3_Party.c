#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
1238번: 파티
조건: 도시 A에서 다른 도시 B로 가는 도로의 개수는 최대 1개이다.

플로이드 워셜(O(N^3))로 풀어서 600ms 정도가 나왔다.
데이크스트라를 쓰면 4~10ms로 풀 수 있는 것 같다. 데이크스트라를 공부한 후 다시 풀어봐야겠다.
*/

const int INF = 1000000;

int main(void) {
	int n,m,x;
	int a,b,t;
	int g[1000][1000];
	for(int i = 0; i<1000000; i++) {
		*(g[0]+i) = INF;
	}
	scanf("%d %d %d", &n, &m, &x);
	x -= 1;
	for(int i = 0; i<m; i++) {
		scanf("%d %d %d", &a, &b, &t);
		a--; b--;
		g[a][b] = t;
	}
	for(int via = 0; via<n; via++) {
		for(int a = 0; a<n; a++) {
			for(int b = 0; b<n; b++) {
				int d = g[a][via] + g[via][b];
				if(d >= g[a][b]) {
					continue;
				}
				g[a][b] = d;
			}
		}
	}
	int max = 0;
	for(int f = 0; f<n; f++) {
		g[f][f] = 0;
		int time = g[f][x] + g[x][f];
		if(max < time) {
			max = time;
		}
	}
	printf("%d\n", max);
}

