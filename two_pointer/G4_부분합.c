#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
1806번: 부분합
내 답변의 시간복잡도가 약 O(N^2)이다.
그런데 투 포인터를 제대로 사용하면 훨씬 빠르게 풀 수 있는 것 같다.
다음에 다시 풀어봐야겠다.
*/
int acs[100001];

int main(void) {
	int n,s;
	scanf("%d %d", &n, &s);
	for(int i = 0; i<n; i++) {
		scanf("%d", acs+i+1);
	}
	acs[0] = 0;
	for(int i = 1; i<=n; i++) {
		acs[i] += acs[i-1];
	}
	int sz = s/10000.0 + 0.5;
	if(sz == 0) sz = 1;
	while(sz <= n) {
		for(int i = 0; i<=n-sz; i++) {
			if(acs[i+sz] - acs[i] >= s) {
				printf("%d", sz);
				return 0;
			}
		}
		sz++;
	}
	printf("0");
}

