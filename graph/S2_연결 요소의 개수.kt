#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

/**
11724번: 연결 요소의 개수
DFS로 노드 별로 연결 요소 id를 할당함으로써 서로 다른 연결요소를 구분
*/
int group[1000];
int n, m;
int graph[1000][1000];
int id = 0;

void dfs(int node) {
	for (int i = 0; i < n; i++) {
		if (group[i] || !graph[node][i]) continue;
		group[i] = group[node];
		dfs(i);
	}
}

int main(void) {
	scanf("%d %d", &n, &m);
	while (m-- > 0) {
		int a, b;
		scanf("%d%d", &a, &b);
		a--; b--;
		graph[a][b] = graph[b][a] = 1;
	}
	for (int i = 0; i < n; i++) {
		if (group[i]) continue;
		group[i] = ++id;
		dfs(i);
	}
	printf("%d", id);
}
