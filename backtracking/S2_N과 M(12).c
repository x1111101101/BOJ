#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
15666번: N과 M (12)
대소관계를 한번만 파악해서 nxt배열에 저장하고 이를 참조해서 중복을 피하도록 구현했다.
N과 M(9) 문제 보다는 난이도가 쉬었다.
*/
int n,m;
int c[8];
int d[8];
int nxt[8];

int cmp(const void* a, const void* b) {
	int k = *((const int*) a);
	int p = *((const int*) b);
	return (k>p) ? 1 : ((k<p) ? -1 : 0);
}

void go(int i, int min) {
	int k = min;
	if(i == m-1) {
		while(1) {
			d[i] = k;
			for(int a = 0; a<m; a++) {
				printf("%d ", c[d[a]]);
			}
			printf("\n");
			k = nxt[k];
			if(k >= n) return;
		}	
		return;
	}
	while(1) {
		d[i] = k;
		go(i+1, k);
		k = nxt[k];
		if(k >= n) return;
	}
	
}

int main(void) {
	scanf("%d%d", &n, &m);
	for(int i = 0; i<n; i++) {
		scanf("%d", c+i);
	}
	qsort(c, n, sizeof(int), cmp);
	int p = 0;
	int i = 1;
	while(i<n) {
		if(c[i] == c[i-1]) {
			i++;
			continue;
		}
		for(int k = p; k<i; k++) {
			nxt[k] = i;
		}
		p = i;
		i++;
	}
	while(p < n) {
		nxt[p] = n;
		p++;
	}
	go(0, 0);
	
}

