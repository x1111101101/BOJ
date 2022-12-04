#include <stdio.h>

/**
12015번: 가장 긴 증가하는 부분 수열
현재 단계에서 고려해야하는 이전 단계의 값의 범위가 넓어 이진 탐색을 사용
O(NLogN)
*/

// g보다 크거나 같은 값 중 가장 작은 값의 인덱스
int binarySearch(int* arr, int g, int size) {
	int result = -1;
	int left = 0;
	int right = size-1;
	while(left <= right) {
		int k = (right+left)/2;
		if(arr[k] >= g) {
			result = k;
			right = k-1;
		} else {
			left = k+1;
		}
	}
	return result;
}

int main(void) {
	int n;
	scanf("%d", &n);
	int dp[1000000];
	int num;
	scanf("%d", &num);
	dp[0] = num;
	int size = 1;
	for(int i = 1; i<n; i++) {
		scanf("%d", &num);
		if(num > dp[size-1]) {
			dp[size++] = num;
			continue;
		}
		// num보다 작거나 같은 수 중 가장 큰 값의 인덱스 구하기
		int k = binarySearch(dp, num, size);
		dp[k] = num;
	}
	printf("%d\n", size);
	fflush(stdout);
}
