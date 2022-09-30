#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <memory.h>

/**
2805번: 나무자르기
오버플로우를 고려하지 않고 sum을 int로 설정해서 틀렸었다.
*/
int trees[1000000];
int n,m;
int max = -1;
int maxTreeHeight = -1;

long sum(int h) {
	long sum = 0;
	for(int i = n-1; i>-1; i--) {
		if(trees[i] > h) {
			sum += trees[i] - h;
		} else {
			break;
		}
	}
	return sum;
}

void binary(int start, int end) {
	if(end < start) return;
	int h = (start+end)/2;
	long v = sum(h);
	if(v < m) {
		binary(start, h-1);
	} else if(v == m) {
		max = h;
		return;
	} else {
		if(h>max) max = h;
		binary(h+1, end);
	}
}

int intcmp(const void* a, const void* b) {
	int aa = *((const int*)a);
	int bb = *((const int*)b);
	if(aa < bb) return -1;
	if(aa == bb) return 0;
	return 1;
}
int main(void) {
	scanf("%d %d", &n, &m);
	for(int i = 0; i<n; i++) {
		scanf("%d", &trees[i]);
		if(trees[i] > maxTreeHeight) maxTreeHeight = trees[i];
	}
	
	qsort(trees, n, sizeof(int), intcmp);
	binary(0, maxTreeHeight);
	printf("%d", max);
}

