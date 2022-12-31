#include <stdio.h>
#include <stdlib.h>

#define SCALE	(1001)

/**
11054번: 가장 긴 바이토닉 부분 수열

이전에 풀었던 가장 긴 증가하는 부분 수열 문제와 겹치는 부분이 있어 쉽게 풀 수 있었던 문제다.

1. 정방향으로 증가 수열을 만들 때 해당 인덱스(x)를 마지막 항으로 했을 때 최대 크기를 memo[x]에 저장
2. 역방향 증가 수열의 최대 길이를 구하면서(n, n-1, n-2 . . . 1 순으로 탐색) 항 x를 탐색할 때 x-1을 (n~x) 증가 수열의 일부와 결합 했을 때 최대 길이를 구함.
3. 2에서 구한 최대 길이 중 최댓값을 출력

시간 복잡도: O(N x log(N))
*/

int ans = 0;
int dp[SCALE];
int memo[SCALE];
int nums[SCALE];

void try(int new) {
	if(new > ans) ans = new;
}

// direction -> 1: 오름차순, 0: 내림차순
int lowerbound(int* arr, int t, int start, int size, int direction) {
	int r = -1;
	int idx[2] = {start, size-1};
	int delta = 1 - (2*(!direction));
	while(idx[0] <= idx[1]) {
		int m = (idx[0]+idx[1])/2;
		if(arr[m] >= t) {
			idx[direction] = m-delta;
			r = m;
		} else {
			idx[!direction] = m+delta;
		}
	}
	return r;
}

int upperbound(int* arr, int t, int start, int size, int direction) {
	int r = -1;
	int idx[2] = {start, size-1};
	int delta = 1 - (2*(!direction));
	while(idx[0] <= idx[1]) {
		int m = (idx[0]+idx[1])/2;
		if(arr[m] < t) {
			idx[!direction] = m+delta;
			r = m;
		} else {
			idx[direction] = m-delta;
		}
	}
	return r;
}

int main(void) {
	int n;
	scanf("%d", &n);
	int sz = 0;
	for(int i = 1; i<=n; i++) {
		scanf("%d", nums+i);
		int u = lowerbound(dp, nums[i], 1, sz+1, 1);
		if(u == -1) {
			dp[++sz] = nums[i];
			memo[i] = sz;
		} else {
			dp[u] = nums[i];
			memo[i] = u;
		}
	}
	try(sz);
	sz = 0;
	for(int i = n; i>0; i--) {
		int u = lowerbound(dp, nums[i], 1, sz+1, 1);
		if(u == -1) {
			dp[++sz] = nums[i];
		} else {
			dp[u] = nums[i];
		}
		int k = upperbound(dp, nums[i-1], 1, sz+1, 1);
		//printf("U: %d, K : %d\n", u, k);
		if(k == -1) continue;
		
		try(k+memo[i-1]);
	}
	try(sz);
	printf("%d", ans);
}
