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

#define SCALE	(100000)

/**
2357번: 최솟값과 최댓값

세그먼트 트리 자료구조의 개념만 이해하고 구현된 코드를 한번도 보지 않은채로 재귀함수를 이용해 직접 구현해봤다.
세그먼트 트리를 이용해 문제를 해결하는 것은 Top-down 방식의 DP로 볼 수도 있겠다.
세그먼트 트리의 개념은 https://m.blog.naver.com/ndb796/221282210534 포스트를 통해 이해했다.
*/

int n, m;

typedef struct {
	int min, max;
} Pair;

int INF = 1000000001;
int datas[SCALE];
Pair tree[SCALE*4];

void fill(int idx, int left, int right) {
	if(left == right) {
		tree[idx].min = datas[left];
		tree[idx].max = datas[left];
		return;
	}
	if(left > right) return;
	int min = INF;
	int max = -INF;
	FORI(i, left, right+1) {
		int v = datas[i];
		if(v < min) min = v;
		if(v > max) max = v;
	}
	int mid = (left+right)/2;
	tree[idx].min = min;
	tree[idx].max = max;
	fill(idx*2, left, mid);
	fill(idx*2 + 1, mid+1, right);
}

Pair query(int idx, int left, int right, int cl, int cr) {
	Pair p;
	p.min = INF;
	p.max = -INF;
	if(cl > right || cr < left) {
		return p;
	}
	if(cl >= left && cr <= right) return tree[idx];
	int mid = (cl+cr)/2;
	Pair ra = query(idx*2, left, right, cl, mid);
	Pair rb = query(idx*2 + 1, left, right, mid+1, cr);
	p.min = MIN(ra.min, rb.min);
	p.max = MAX(ra.max, rb.max);
	return p;
}

int main(void) {
	scanf("%d%d", &n, &m);
	FORI(i,0,n) scanf("%d", datas+i);
	fill(1, 0, n-1);
	FORI(i,0,m) {
		int a,b;
		scanf("%d%d", &a,&b);
		a--; b--;
		Pair p = query(1, a, b, 0, n-1);
		printf("%d %d\n", p.min, p.max);
	}
}
