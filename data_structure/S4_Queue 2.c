#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>

#define IRNG(i,x,y)	for(int i = x; i<y; i++)
#define RNG(type, name, from, operator, to)	for(type name = from; name operator to; name++)
#define MAX(x,y)	((x>y)?(x):(y));
#define MIN(x,y)	((x<y)?(x):(y));
#define PCAST_C(new, pointer)	((const new*) pointer)	
#define SQUARE(x)	((x)*(x))

/**
18258번: 큐2
큐를 직접 구현하는 문제.
동적 메모리 할당을 이용해 유동적인 크기를 갖는 큐를 
*/

#define QT	int

typedef struct {
	QT* q;
	int memsize,size;
	int start;
} Queue;

Queue q;

int q_fixIdx(Queue* d, int index) {
	int ri = index + d->start;
	if(ri >= d->memsize) {
		ri -= d->memsize;
	}
	return ri;
}

QT q_get(Queue* d, int index) {
	return d->q[q_fixIdx(d,index)];
}

void q_resize(Queue* d) {
	int newSz = (d->memsize + 1)*2;
	QT* new = malloc(sizeof(QT)*newSz);
	IRNG(i,0,d->size) {
		new[i] = q_get(d, i);
	}
	free(d->q);
	d->q = new;
	d->start = 0;
	d->memsize = newSz;
}

void q_push_back(Queue* d, QT value) {
	int idx = d->size;
	if(d->size == d->memsize) {
		q_resize(d);
	}
	d->q[q_fixIdx(d,idx)] = value;
	d->size++;
}

QT q_pop(Queue* d) {
	QT v = q_get(d,0);
	d->start++;
	if(d->start >= d->memsize) d->start -= d->memsize;
	d->size--;
	return v;
}

int main(void) {
	int t;
	scanf("%d", &t);
	char cmd[10];
	int arg;
	IRNG(z,0,t) {
		scanf("%s", cmd);
		if(strcmp(cmd, "empty") == 0) {
			printf("%d\n", q.size == 0);
			continue;
		}
		if(strcmp(cmd, "size") == 0) {
			printf("%d\n", q.size);
			continue;
		}
		if(strcmp(cmd,"push") == 0) {
			scanf("%d", &arg);
			q_push_back(&q, arg);
			continue;
		}
		if(q.size == 0) {
			printf("-1\n");
			continue;
		}
		if(strcmp(cmd, "pop") == 0) {
			printf("%d\n", q_pop(&q));
			continue;
		}
		if(strcmp(cmd, "front") == 0) {
			printf("%d\n", q_get(&q, 0));
			continue;
		}
		if(strcmp(cmd, "back") == 0) {
			printf("%d\n", q_get(&q, q.size-1));
		}	
	}
}
