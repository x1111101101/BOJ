#include <stdio.h>
#include <stdlib.h>

#define MAX_DOTS	(500000)

/**
20040번: 사이클 게임
노드의 최대 갯수가 50만개라 부모를 찾는 find 함수에서 부모를 업데이트 할 때 재귀로 업데이트를 하지 않고, 스택으로 업데이트를 해서 스택 오버플로우를 피했다.
*/

int n,m;
int parent[MAX_DOTS];

int find(int a) {
	static int st[MAX_DOTS];
	int sz = 0;
	while(parent[a] > -1) {
		st[sz++] = a;
		a = parent[a];
	}
	for(int i = 0; i<sz; i++) {
		parent[st[i]] = a;
	}
	return a;
}

int merge(int a, int b) {
	int pa = find(a);
	int pb = find(b);
	if(pa > pb) {
		parent[pa] = pb;
	} else if(pa < pb) {
		parent[pb] = pa;
	} else return 0;
	return 1;
}

int main(void) {
	scanf("%d %d", &n, &m);
	for(int i = 0; i<n; i++) parent[i] = -1;
	int d1, d2;
	for(int i = 0; i<m; i++) {
		scanf("%d %d", &d1, &d2);
		if(!merge(d1,d2)) {
			printf("%d", i+1);
			return 0;
		}	
	}
	printf("0");
}
