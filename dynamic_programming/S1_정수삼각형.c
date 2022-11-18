#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
1932번: 정수 삼각형
다 풀고 보니 배열 하나만으로도 풀 수 있을 것 같다.
하나로 줄인 코드를 다시 커밋하겠다.
*/
int main(void) {
	int n;
	int* dp = malloc(sizeof(int) * 500);
	int* dp2 = malloc(sizeof(int) * 500);
	dp[0] = 0;
	dp2[0] = 0;
	scanf("%d", &n);
	for(int i = 1; i<=n; i++) {
		for(int k = 0; k<i-1; k++) {
			dp2[k] = dp[k];
		}
		dp2[i-1] = dp[i-2];
		for(int k = 1; k<i-1; k++) {
			if(dp2[k] < dp[k-1]) {
				dp2[k] = dp[k-1];
			}
		}
		for(int k = 0; k<i; k++) {
			int in;
			scanf("%d", &in);
			dp2[k] += in;
		}
		int* t = dp;
		dp = dp2;
		dp2 = t;
	}
	int max = dp[0];
	for(int i = 1; i<n; i++) {
		if(dp[i] > max) max = dp[i];
	}
	printf("%d\n", max);
}

