#include <stdio.h>

/**
17298번: 오큰수
문제의 구조에 따라 스택이 내림차순 정렬된다는 점을 이용해서 연산량을 최소화했다.
*/

typedef struct {
	int value;
	int index;
} Term;

Term st[1000001];
int sz;

int main(void) {
	int result[1000000];
	int length;
	scanf("%d", &length);
	int max = 0;
	for(int i = 0; i<length; i++) {
		int in;
		scanf("%d", &in);
		while(sz > 0) {
			if(st[sz-1].value >= in) break;
			result[st[--sz].index] = in;
		}
		st[sz].value = in;
		st[sz++].index = i;
	}
	for(int i = 0; i<sz; i++) result[st[i].index] = -1;
	for(int i = 0; i<length; i++) {
		printf("%d ", result[i]);
	}
}
