#include <stdio.h>
#include <stdlib.h>

#define SCALE	(100)

/**
2565번: 전깃줄
가장 긴 증가하는 부분 수열 문제의 풀이를 응용해서 풀 수 있는 문제다.
*/

int size;
int nums[SCALE][2]; // left, right
int dp[SCALE];
int len;

int cmp(const void* a, const void* b) {
	int aa = *((const int*) a);
	int bb = *((const int*) b);
	return (aa > bb ? 1 : (aa == bb)-1);
}

// p보다 큰 값 중 최솟값
int bSearch(int p) {
	int r = -1;
	int a = 0;
	int b = len-1;
	while(a<=b) {
		int m = (a+b)/2;
		if(dp[m] <= p) {
			a = m+1;
		} else {
			r = m;
			b = m-1;
		}
	}
	return r;
}

int main(void) {
	scanf("%d", &size);
	for(int i = 0; i<size; i++) {
		scanf("%d%d", nums[i], nums[i]+1);
	}
	qsort(nums, size, sizeof(int)*2, cmp);
	dp[0] = nums[0][1];
	len = 1;
	for(int i = 1; i<size; i++) {
		int num = nums[i][1];
		int k = bSearch(num);
		if(k == -1) {
			dp[len++] = num;
		} else {
			dp[k] = num;
		}
	}
	printf("%d", size-len);
	
}
