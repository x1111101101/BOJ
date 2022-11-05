#include <stdio.h>

/**
1389번: 케빈 베이컨의 6단계 법칙
BFS에서 탐색에 단계를 부여하는 간단한 문제

3과 1간의 캐빈베이컨 수는 1과 3간의 그것과 같다는 점을 이용해 캐싱을 할 수 있을까?
*/

int n, k;
int g[100][99];
int s[100] = {0,};
int d[100];
int min = 10000;
int minP = -1;

int st[100];
int sz = 1;

int calcNum(int p) {
	for(int i = 0; i<100; i++) d[i] = 0;
	int nums[100] = {0,};
	int st2[100];
	st[0] = p;
	sz = 1;
	int lv = 0;
	while(sz > 0) {
		lv++;
		for(int i = 0; i<sz; i++) {
			st2[i] = st[i];
		}
		int sz2 = sz;
		sz = 0;
		while(sz2 > 0) {
			int c = st2[--sz2];
			for(int i = 0; i<s[c]; i++) {
				int q = g[c][i];
				if(d[q]) continue;
				nums[q] = lv;
				d[q] = 1;
				st[sz++] = q;
			}
		}
	}
	int result = 0;
	for(int i = 0; i<n; i++) {
		result += nums[i];
	}
	return result;
}

int main(void) {
	scanf("%d %d", &n, &k);
	for(int i = 0; i<k; i++) {
		int a,b;
		scanf("%d %d", &a,&b);
		a--; b--;
		g[a][s[a]++] = b;
		g[b][s[b]++] = a;
	}
	for(int i = 0; i<n; i++) {
		int cb = calcNum(i);
		if(min > cb) {
			min = cb;
			minP = i;
		}
	}
	printf("%d", minP+1);
	
}
