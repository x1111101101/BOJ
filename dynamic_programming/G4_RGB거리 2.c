#include <stdio.h>

#define MIN(a,b)	(((a)<(b))?(a):(b))

/**
17404번: RGB 거리 2
문제를 보자마자 크게 어렵지 않은 문제라는걸 느꼈다.
dp[1][x][x]를 큰 값으로 초기화해서 간단히 풀었다.
*/
int OFFSET[][2] = {{1,2},{0,2},{0,1}};

int main(void) {
	int n;
	int dp[1000][3][3]; // dp[n][x][y] -> x색으로 출발, y색의 최댓값
	scanf("%d", &n);
	for(int s = 0; s<n; s++)
		for(int x = 0; x<3; x++)
			for(int y = 0; y<3; y++)
				dp[s][x][y] = 0;
	int fee[3];
	scanf("%d%d%d", dp[0][0], dp[0][1]+1, dp[0][2]+2);
	for(int x = 0; x<3; x++) {
		for(int y = 0; y<3; y++) {
			dp[0][x][y] = dp[0][x][x];
		}
		dp[1][x][x] = 10000000;
	}
	for(int s = 1; s<n; s++) {
		scanf("%d%d%d", fee, fee+1, fee+2);
		for(int x = 0; x<3; x++) {
			for(int y = 0; y<3; y++) {
				int* os = OFFSET[y];
				dp[s][x][y] += MIN(dp[s-1][x][os[0]], dp[s-1][x][os[1]]) + fee[y];
			}
		}
	}
	int result = 100000000;
	for(int x = 0; x<3; x++) {
		int* os = OFFSET[x];
		int k = MIN(dp[n-1][x][os[0]], dp[n-1][x][os[1]]);
		if(result > k) result = k;
	}
	printf("%d\n", result);
	fflush(stdout);
}
