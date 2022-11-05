#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
2606번: 바이러스
BFS로 간단히 해결한 문제
재귀 함수를 이용한 탐색, 스택을 이용한 탐색 둘 다 구현 해봤다.
*/
int g[100][99] = {-1,};
int s[100] = {0,};
int r = 0;
int d[100] = {0,};

// 재귀함수로 탐색
void reculsive(int p) {
	d[p] = 1;
	for(int i = 0; i<s[p]; i++) {
		int c = g[p][i];
		if(d[c] == 1) continue;
		r++;
		d[c] = 1;
		reculsive(c);
	}
}

int main(void) {
	int n, k;
	scanf("%d %d", &n, &k);
	for(int i = 0; i<k; i++) {
		int a,b;
		scanf("%d %d", &a,&b);
		a--; b--;
		g[a][s[a]++] = b;
		g[b][s[b]++] = a;
	}
	
	// 스택으로 탐색
	int st[100];
	int sz = 1;
	st[0] = 0;
	while(sz > 0) {
		int c = st[--sz];
		d[c] = 1;
		for(int i = 0; i<s[c]; i++) {
			int q = g[c][i];
			if(d[q]) continue;
			d[q] = 1;
			st[sz++] = q;
			r++;
		}
	}
	//reculsive(0);
	printf("%d", r);
}
