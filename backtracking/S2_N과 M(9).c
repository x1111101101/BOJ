#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

int n,m;
int d[8] = {-1,};
int c[9];
int v[8] = {0,};

/**
15663번: N과 M (9)
다른 N과 M 문제들보다 난이도가 조금 높았던 문제다.
*/
int cmp(const void* a, const void* b) {
	int k = *((const int*) a);
	int p = *((const int*) b);
	return (k>p) - (k<p);
}

void go(int i) {
	if(i == m-1) {
		d[i] = 8;
		for(int k = 0; k<n; k++) {
			if(v[k]) continue;
			if(c[k] == c[d[i]]) continue;
			d[i] = k;
			for(int p = 0; p<m; p++) {
				printf("%d ", c[d[p]]);
			}
			printf("\n");
		}
		return;
	}
	d[i] = 8;
	for(int k = 0; k<n; k++) {
		if(v[k]) continue;
		if(c[k] == c[d[i]]) continue;
		d[i] = k;
		v[k] = 1;
		go(i+1);
		v[k] = 0;
	}
}

int main(void) {
	c[8] = 123123123;
	scanf("%d%d", &n, &m);
	for(int i = 0; i<n; i++) {
		scanf("%d", c+i);
	}
	qsort(c, n, sizeof(int), cmp);
	go(0);
}

