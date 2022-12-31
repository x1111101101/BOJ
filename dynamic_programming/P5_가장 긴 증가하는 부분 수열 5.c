#include <stdio.h>
#include <stdlib.h>

#define SCALE	(1000000)

/**
14003번: 가장 긴 증가하는 부분 수열 5
*/

int dp[SCALE];
int nums[SCALE];
int *indexes[SCALE];
int size[SCALE];

int upper(int* arr, int start, int endExclusive, int t) {
	int result = -1;
	int r = endExclusive-1;
	while(start <= r) {
		int i = (start+r)/2;
		if(arr[i] >= t) {
			result = i;
			r = i-1;
		} else {
			start = i+1;
		}
	}
	return result;
}

int lower(int* arr, int start, int endExclusive, int t) {
	int result = -1;
	int r = endExclusive-1;
	while(start <= r) {
		int i = (start+r)/2;
		if(arr[i] >= t) {
			r = i-1;
		} else {
			start = i+1;
			result = i;
		}
	}
	return result;
}

int main(void) {
	int n;
	int sz = 0;
	scanf("%d", &n);
	int num;
	for(int i = 0; i<n; i++) {
		scanf("%d", &num);
		nums[i] = num;
		int u = upper(dp, 0, sz, num);
		if(u == -1) {
			dp[sz++] = num;
			u = sz-1;
		} else {
			dp[u] = num;
		}
		if(size[u] %2 == 0) {
			indexes[u] = realloc(indexes[u], sizeof(int) * (size[u]+2));
		}
		indexes[u][size[u]++] = i;
	}
	int out[sz];
	int idx = indexes[sz-1][size[sz-1]-1];
	
	out[sz-1] = nums[idx];
	for(int i = 2; i<=sz; i++) {
		int l = sz-i;
		int p = lower(indexes[l], 0, size[l], idx);
		idx = indexes[l][p];
		out[l] = nums[idx];
	}
	
	printf("%d\n", sz);
	for(int i = 0; i<sz; i++) {
		printf("%d ", out[i]);
	}
	
}
