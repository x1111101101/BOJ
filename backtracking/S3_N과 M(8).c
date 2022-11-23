#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
15657번: N과 M (8)
*/
int n,m;
int num[8];
int idx[8];

int cmp(const void* a, const void* b) {
	int aa = *((const int*) a);
	int bb = *((const int*) b);
	return (aa > bb) - (aa < bb);
}

void go(int i, int min) {
	if(i == m-1) {
		for(int k = min; k<n; k++) {
			idx[i] = k;
			for(int d = 0; d<m; d++) {
				printf("%d ", num[idx[d]]);
			}
			printf("\n");
		}	
		return;
	}
	for(int k = min; k<n; k++) {
		idx[i] = k;
		go(i+1, k);
	}
}

int main(void) {
	scanf("%d %d", &n, &m);
	for(int i = 0; i<n; i++) {
		scanf("%d", num+i);
	}
	qsort(num, n, sizeof(int), cmp);
	go(0, 0);
}

