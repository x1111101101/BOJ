#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>

int n;
int dp[15][2];

int solve() {
	if (n % 2 == 1) return 0;
	dp[0][0] = 3;
	dp[0][1] = 2;
	dp[1][0] = 11;
	dp[1][1] = 8;
	int max = n / 2 - 1;
	for (int x = 2; x <= max; x++) {
		int arch = dp[x - 2][0] * 2 + dp[x - 2][1];
		int combo = dp[x - 1][0] * 3 + arch;
		dp[x - 1][1] = arch;
		dp[x][0] = combo;
	}
	return dp[max][0];
}

int main(void) {
	scanf("%d", &n);
	printf("%d", solve());
}
