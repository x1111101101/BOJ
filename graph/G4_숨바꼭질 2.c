#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#include <stdint.h>

#define FORI(i,x,y)	for(int i = x; i<y; i++)
#define RNG(type, name, from, operator, to)	for(type name = from; name operator to; name++)
#define MAX(x,y)	((x>y)?(x):(y))
#define MIN(x,y)	((x<y)?(x):(y))
#define PCAST_C(new, pointer)	((const new*) pointer)	
#define SQUARE(x)	((x)*(x))

/**
12851번: 숨바꼭질 2
BFS로 간단히 해결 가능한 문제인데, Q_MEM_SIZE를 너무 낮게 잡는 바람에 오답을 몇번 맞았다.
중복 탐색 대신 탐색 횟수 메모를 통해 불필요한 탐색의 발생을 최대한 제한하면서 답을 구했다.
*/
#define Q_MEM_SIZE	(1000000)

typedef struct {
	int offset;
	int table[Q_MEM_SIZE];
	int size;
} Q;

void push(Q* q, int value) {
	int i = (q->offset+q->size)%(Q_MEM_SIZE+1);
	q->size++;
	q->table[i] = value;
}

int poll(Q* q) {
	int v = q->table[q->offset++];
	q->offset %= Q_MEM_SIZE + 1;
	q->size--;
	return v;
}


int n, m; // n->m
int nodes[200001][2]; // min time, count
Q q[2];

int check(int target, int prev, int time) {
	if(target > 200000) return 0;
	if(nodes[m][0] < time) return 0;
	if(nodes[target][0] < time) return 0;
	if(nodes[target][0] == time) {
		nodes[target][1] += nodes[prev][1];
		return 0;
	}
	nodes[target][0] = time;
	nodes[target][1] = nodes[prev][1];
	return target != m;
}


int main(void) {
	FORI(i, 0, 200001) nodes[i][0] = 100000;
	scanf("%d %d", &n, &m);
	if(n >= m) {
		printf("%d\n1", n-m);
		return 0;
	}
	nodes[n][0] = 0;
	nodes[n][1] = 1;
	int qidx = 0;
	push(&q[0], n);
	int time = 0;
	while(q[qidx].size > 0) {
		Q* nq = &q[!qidx];
		do {
			int node = poll(&q[qidx]);
			if(node > 0) {
				if(check(node*2, node, time+1)) push(nq, node*2);
				if(check(node-1, node, time+1)) push(nq, node-1);
			}
			if(check(node+1, node, time+1)) push(nq, node+1);
		} while(q[qidx].size > 0);
		if(nodes[m][0] == time) break;
		qidx = !qidx;
		time++;
	}
	int minTime = nodes[m][0];
	int count = nodes[m][1];
	printf("%d\n%d", minTime, count);
}
