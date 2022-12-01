#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
11660번: 구간 합 구하기
시간복잡도가 다른 답안들 보다 3배정도 높았는데 왜 그런지 알 것 같다.
수정해서 다시 커밋할 예정
*/
int n,m;

int main(void) {
	scanf("%d%d", &n, &m);
	int sz = n*n;
	int dp[sz+1];
	dp[0] = 0;
	for(int y = 0; y<n; y++) {
		for(int x = 0; x<n; x++) {
			scanf("%d", dp+y*n+x+1);
		}
	}
	for(int i = 1; i<=sz; i++) {
		dp[i] += dp[i-1];
	}
	int loc[2][2];
	for(int i = 0; i<m; i++) {
		scanf("%d%d%d%d", loc[0]+1, loc[0], loc[1]+1, loc[1]);
		for(int k = 0; k<4; k++) *(loc[0]+k) -= 1;
		int result = 0;
		//printf("%d, %d ~ %d, %d\n", loc[0][0], loc[0][1], loc[1][0], loc[1][1]);
		for(int y = loc[0][1]; y<=loc[1][1]; y++) {
			result += dp[y*n+loc[1][0]+1] - dp[y*n+loc[0][0]];
			//printf("s %d\n", result);
		}
		printf("%d\n", result);
	}
	
}

