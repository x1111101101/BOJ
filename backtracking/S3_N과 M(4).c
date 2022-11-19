#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
15652번: N과 M
재귀함수 버전과 스택 버전 둘 다 구현해봤다.

*/
int n,m;
int nums[8];

void print(int* st) {
	for(int i = 0; i<m; i++) {
		printf("%d ", nums[i]);
	}
	printf("\n");
}

// 스택으로 구현
void printWithStack() {
	int i = m-1;
	while(1) {
		print(nums);
		nums[i]++;
		if(nums[i] <= n) {
			continue;
		}
		while(1) {
			if(i == 0) {
				return;
			}
			if(nums[--i] < n) {
				break;
			}
			
		}
		nums[i]++;
		for(int k = i; k<m; k++) {
			nums[k] = nums[i];
		}
		i = m-1;
	}
}

// 재귀 함수로 구현
void printWithRecursiveFun(int i, int min) {
	if(i < m-1) {
		for(int num = min; num<=n; num++) {
			nums[i] = num;
			printWithRecursiveFun(i+1, nums[i]);
		}
		return;
	}
	for(int num = min; num<=n; num++) {
		nums[i] = num;
		print(nums);
	}
}

int main(void) {
	scanf("%d %d", &n, &m);
	for(int i = 0; i<m; i++) {
		nums[i] = 1;
	}
	//printWithStack();
	printWithRecursiveFun(0, 1);
}

