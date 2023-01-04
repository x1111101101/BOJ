#include <stdio.h>
#include <stdlib.h>

/**
1967번: 트리의 지름

A의 자식들 중 한쪽으로만 뻗었을 때의 최장길이는 'A의 부모가 A로 뻗었을 때의 최장길이 - (부모와 A간의 거리)'와 같다.
동일한 구조의 반복으로 한 노드를 선택했을 때 최장길이가 구해진다.

두 방향으로의 최장길이 뿐만 아니라 한 방향으로의 최장길이도 생각해줘야한다.
*/

typedef struct node {
	int (*childs)[2]; // 자식의 노드 번호, 거리
	int size;
	int max;
} Node;

Node nodes[10000];
int max[10000];
int ans = 0;

void append(Node *n, int child, int dist) {
	if(n->size % 2 == 0) {
		n->childs = realloc(n->childs, sizeof(int)*(n->size*2+4));
	}
	n->childs[n->size][0] = child;
	n->childs[n->size++][1] = dist;
}

void try(int v) {
	if(ans < v) ans = v;
}

void dfs(int p) {
	Node *n = nodes+p;
	if(n->size == 0) {
		max[p] = 0;
		return;
	}
	int first = 0, second = 0;
	for(int i = 0; i<n->size; i++) {
		int child = n->childs[i][0];
		int dist = n->childs[i][1];
		dfs(child);
		int v = max[child]+dist;
		if(v > first) {
			second = first;
			first = v;
		} else if(v > second) {
			second = v;
		}
	}
	max[p] = first;
	if(n->size == 1) {
		try(first);
		return;
	}
	try(first+second);
}

int main(void) {
	int n;
	int p,c,d;
	scanf("%d", &n);
	for(int i = 1; i<n; i++) {
		scanf("%d%d%d", &p, &c, &d);
		append(nodes+p-1, c-1, d);
	}
	dfs(0);
	printf("%d", ans);
}
