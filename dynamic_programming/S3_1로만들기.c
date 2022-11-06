#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
1463번: 1로 만들기

조건들을 역으로 생각하면
1. 3의 배수거나 1일 때: 곱하기 3
2. 2의 배수거나 2일 때: 곱하기 2
3. 더하기 1

이를 이용해서 1에서 출발하는 bottom-up 방식 사용
*/

int min(int a, int b) {
	return a < b ? a : b;
}

int main(void) {
	int n;
	scanf("%d", &n);
	int dp[1000001]; // times
	dp[1] = 0;
	dp[2] = 1;
	dp[3] = 1;
	for(int i = 4; i<=n; i++) {
		dp[i] = dp[i-1] + 1;
		if(i%2 == 0) {
			dp[i] = min(dp[i/2]+1, dp[i]);
		}
		if(i%3 == 0) {
			dp[i] = min(dp[i/3]+1, dp[i]);
		}
	}
	printf("%d\n", dp[n]);
}
