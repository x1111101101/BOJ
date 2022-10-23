#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>
/**
1003번: 피보나치 함수
문제에서 주어진 n의 최댓값 40을 넣어 테스트하면서 오버플로우가 나지 않는지 확인함.
*/
int main(void) {
	int cases, n, max = -1;
	scanf("%d", &cases);
	int nums[cases];
	for(int i = 0; i<cases; i++) {
		scanf("%d", &n);
		if(n > max) max = n;
		nums[i] = n;
	}
	int calls[max+1][2];
	calls[0][1] = 0;
	calls[0][0] = 1;
	calls[1][0] = 0;
	calls[1][1] = 1;
	for(int k = 2; k<=max; k++) {
		calls[k][1] = calls[k-1][1] + calls[k-2][1];
		calls[k][0] = calls[k-1][0] + calls[k-2][0];
	}
	for(int i = 0; i<cases; i++) {
		int n = nums[i];
		printf("%d %d\n", calls[n][0], calls[n][1]);
	}
}
