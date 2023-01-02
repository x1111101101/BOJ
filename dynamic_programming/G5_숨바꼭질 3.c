#include <stdio.h>
#include <stdlib.h>

#define MIN(a,b) (((a)<(b))?(a):(b))
#define SCALE	(200001)

/**
13549번: 숨바꼭질 3
다익스트라로도 풀 수 있는 문제였다. 다익스트라도 DP로 분류되지만, 문제의 구조를 이용한 DP를 썼다.
*/

int INF = 100000000;
int dp[SCALE+1];
int from,to;

void fill(int p, int v) {
	p *= 2;
	while(p < SCALE) {
		dp[p] = MIN(v, dp[p]);
		p *= 2;
	}
}

int main(void) {
	scanf("%d%d", &from, &to);
	if(from >= to) {
		printf("%d", from-to);
		return 0;
	}
	for(int i = 0; i<SCALE+1; i++) dp[i] = INF;
	dp[0] = from;
	for(int i = from; i>0; i--) {
		dp[i] = from - i;
		fill(i, dp[i]);
	}
	for(int i = from+1; i<SCALE; i++) {
		dp[i] = MIN(dp[i], MIN(dp[i-1], dp[i+1])+1);
		if(i%2 == 0) {
			dp[i] = MIN(dp[i/2], dp[i]);
		}
		fill(i, dp[i]);
	}
	printf("%d", dp[to]);
}
