#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

#define MAX(a,b) (((a)>(b))?(a):(b))

/**
2579번: 계단오르기
풀었던 문제지만 알고리즘 스터디에서 다시 풀게 되어 C로도 풀어봤다.
*/
int main(void) {
	int n;
	int s[300];
	int dp[300][2] = {0,};
	scanf("%d", &n);
	for(int i = 0; i<n; i++) {
		scanf("%d", s+i);
	}
	dp[0][0] = s[0];
	if(n>1) {
		dp[1][0] = s[1]; // 처음부터 2칸을 한번에 올라갈 수 있다면 성립됨
		dp[1][1] = s[1]+s[0];
	}
	for(int f = 2; f<n; f++) {
		dp[f][1] = dp[f-1][0] + s[f]; // dp[f][1]에 이전층 1콤보 값 + 현재 층 값 대입
		int bigger = MAX(dp[f-2][0], dp[f-2][1]); // 전전층 1콤보 값과 2콤보 값 중 더 큰 값
		dp[f][0] = bigger + s[f];
	}
	printf("%d\n", MAX(dp[n-1][0], dp[n-1][1]));
}
