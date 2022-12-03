#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
18870번: 좌표 압축
*/
int cmp(const void* a, const void* b) {
	const int p = *((const int* ) a);
	const int q = *((const int* ) b);
	return p > q ? 1 : (p==q)-1;
}

int main(void) {
	int n;
	scanf("%d", &n);
	int l[n][2];
	for(int i = 0; i<n; i++) {
		scanf("%d", l[i]);
		l[i][1] = i;
	}
	qsort(l, n, sizeof(int)*2, cmp);
	int d[n];
	int i = 0;
	int count = 0;
	while(i < n) {
		int k;
		for(k = i+1; k<n; k++) {
			if(l[k][0] != l[i][0]) {
				break;
			}
		}
		for(int p = i; p<k; p++) {
			d[l[p][1]] = count;
		}
		count++;
		i = k;
	}
	for(int i = 0; i<n; i++) {
		printf("%d ", d[i]);
	}
	fflush(stdout);
}
