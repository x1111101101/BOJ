#include <stdio.h>
#include <stdlib.h>

/**
2568번: 전깃줄 - 2
LIS 응용문제. LIS의 길이 뿐만아니라 LIS의 요소들 까지 파악해서 풀어야 하는 문제다.
*/

#define SCALE	(100000)

int size;
int nums[SCALE][2]; // left, right
int dp[SCALE];
int prev[SCALE];
int len;

int cmp(const void* a, const void* b) {
	int aa = *((const int*) a);
	int bb = *((const int*) b);
	return (aa > bb ? 1 : (aa == bb)-1);
}

// p보다 큰 값 중 최솟값
int lowerbound(int p) {
	int r = -1;
	int a = 0;
	int b = len-1;
	while(a<=b) {
		int m = (a+b)/2;
		if(nums[dp[m]][1] <= p) {
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
	dp[0] = 0;
	len = 1;
	for(int i = 1; i<size; i++) {
		int num = nums[i][1];
		int k = lowerbound(num);
		if(k == -1) {
			k = len++;
		}
		prev[i] = dp[k];
		dp[k] = i;
	}
	printf("%d\n", size-len);
	int count[SCALE] = {0,};
	int pri = dp[len-1];
	for(int i = len-1; i > -1; i--) {
		while(dp[i] > pri || nums[dp[i]][1] > nums[pri][1]) {
			dp[i] = prev[dp[i]];	
		}
		count[dp[i]] = 1;
		pri = dp[i];
	}
	for(int i = 0; i<size; i++) {
		if(!count[i]) printf("%d\n", nums[i][0]);
	}
}
