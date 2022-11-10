#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
15650번: N과 M (2)
처음 풀어보는 백트래킹 문제. 백트래킹이 뭔지 모르는데 일단 풀어봤다.

중복체크를 사용하지 않고 풀었다.

4가 수열의 최댓값일 때 1 2 4 뒤에 1 3 1이 아닌
1 3 4가 온다는걸 이용했다.
*/
int n,m;
int nums[8] = {0,};

void print() {
	for(int i = 0; i<m; i++) {
		printf("%d ", nums[i]);
	}
	putchar('\n');
}

int main(void) {
	scanf("%d %d", &n, &m);
	for(int i = 1; i<=m; i++) {
		nums[i-1] = i;
	}
	int index = m-1;
	while(nums[0] <= n) {
		if(nums[index] <= n) {
			print();
		}
		if(nums[index]+1 <= n) {
			nums[index]++;
			continue;
		}
		int p = index;
		while(1) {
			p -= 1;
			if(p == -1) return 0;
			if(nums[p] < n) break;
		}
		nums[p]++;
		for(int i = p+1; i <m; i++) {
			nums[i] = nums[i-1]+1;
		}
		index = m-1;
	}
	
}
