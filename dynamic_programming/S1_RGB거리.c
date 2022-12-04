#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

#define MIN(a, b) (((a)<(b))?(a):(b))

/**
1149번: RGB 거리
*/
int main(void) {
	int n;
	scanf("%d", &n);
	int dp[3] = {0,};
	for(int i = 0; i<n; i++) {
		int r,g,b;
		scanf("%d%d%d", &r,&g,&b);
		int x = r + MIN(dp[1], dp[2]);
		int y = g + MIN(dp[0], dp[2]);
		dp[2] = b + MIN(dp[0], dp[1]);
		dp[0] = x;
		dp[1] = y;
	}
	int r = MIN(MIN(dp[0],dp[1]),dp[2]);
	printf("%d\n", r);
	fflush(stdout);
}
