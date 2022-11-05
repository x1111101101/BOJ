#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
11399번: ATM
*/

int n;
int v[1000];
int k[1000];

int cmp(const void* t, const void* q) {
	int a = *((const int*) t);
	int b = *((const int*) q);
	return (v[a] > v[b]) - (v[b] > v[a]);
}

int main(void) {
	scanf("%d", &n);
	for(int i = 0; i<n; i++) {
		scanf("%d", v+i);
		k[i] = i;
	}
	qsort(k,n,sizeof(int),cmp);
	int r = 0;
	int total = 0;
	for(int i = 0; i<n; i++) {
		int w = k[i];
		int t = v[w];
		r += total + t;
		total += t;
	}
	printf("%d", r);
}
