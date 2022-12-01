#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
11660번: 구간 합 구하기
줄 단위로 저장하는 코드에서 면 단위로 저장하는 코드로 개선
소요시간 3배 줄음
*/

int main(void) {
	int n,m;
	scanf("%d%d", &n, &m);
	//int g[1025][1025];
	int dp[1025][1025] = {0,};
	int num;
	for(int y = 1; y<=n; y++) {
		int sum = 0;
		for(int x = 1; x<=n; x++) {
			scanf("%d", &num);
			sum += num;
			dp[x][y] = sum + dp[x][y-1];
		}
	}
	while(m-- > 0) {
		int x1,y1,x2,y2;
		scanf("%d%d%d%d",&y1,&x1,&y2,&x2);
		int result = dp[x2][y2] - dp[x2][y1-1] - dp[x1-1][y2];
		result += dp[x1-1][y1-1];
		printf("%d\n", result);
	}
}

