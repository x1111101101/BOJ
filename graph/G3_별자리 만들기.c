#include <stdio.h>
#include <stdlib.h>
#include <math.h>

/**
4386번: 별자리 만들기
유니온 파인드 + 크루스칼 알고리즘으로 구현
*/

#define SQ(x)	((x)*(x))
#define EPSILON	(1e-15)

int INF = 100000000;
int uf[100];

int doubleEquals(double a, double b) {
	double abs = a-b;
	if(abs < 0) abs *= -1;
	return abs < EPSILON;
}

int cmp(const void* a, const void* b) {
	double p = ((const double*) a)[2];
	double q = ((const double*) b)[2];
	return doubleEquals(p,q) ? 0 : (p > q)*2 - 1;
}

int find(int k) {
	if(uf[k] == -1) return k;
	uf[k] = find(uf[k]);
	return uf[k];
}

int merge(int a, int b) {
	int pa = find(a);
	int pb = find(b);
	if(pa > pb) {
		uf[pa] = pb;
	} else if(pa != pb) {
		uf[pb] = pa;
	} else return 0;
	return 1;
}

int main(void) {
	int n;
	scanf("%d", &n);
	double locs[100][2];
	for(int i = 0; i<n; i++) {
		scanf("%lf %lf", locs[i], locs[i]+1);
		uf[i] = -1;
	}
	double g[5050][3]; // 모든 간선의 양 끝 노드, 거리를 저장. 거리가 짧은 순으로 정렬 예정. a,b,distance
	int sz = 0;
	for(int a = 0; a<n; a++) {
		for(int b = a+1; b<n; b++) {
			double* la = locs[a];
			double* lb = locs[b];
			double dist = SQ(la[0]-lb[0]) + SQ(la[1]-lb[1]);
			g[sz][0] = a;
			g[sz][1] = b;
			g[sz++][2] = sqrt(dist);
		}
	}
	qsort(g, sz, sizeof(double)*3, cmp);
	double result = 0;
	int left = n-1;
	int i = 0;
	while(left > 0) {
		double* e = g[i++];
		int a = e[0];
		int b = e[1];
		if(merge(a,b)) {
			left--;
			result += e[2];
		}
	}
	printf("%.2lf", (result));
	fflush(stdout);
}
