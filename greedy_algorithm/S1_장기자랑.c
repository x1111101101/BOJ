#include <stdio.h>
#include <stdlib.h>

/**
27277번: 장기자랑
입력값을 오름차순 정렬하고 뒤에서 부터 위 아래 위 아래 순서로 선택하면서 순서를 정하면 정답이 나오는데, 확신이 들지 않아 부루트포스로 확실한 정답과 비교하며 확인
*/

int sz;
int s[100000];
int max = 0;

int bf[100000];
int best[100000];

int calc() {
	int l = 0;
	int ss = 0;
	for(int i = 0; i<sz; i++) {
		int sc = s[bf[i]] - l;
		if(sc < 0) sc = 0;
		ss+= sc;
		l = s[bf[i]];
		
	}
	return ss;
}

int visit[100000];
void bruteforce(int index) {
	if(index == sz) {
		int v = calc();
		if(v > max) {
			max = v;
			for(int i = 0; i<sz; i++) best[i] = bf[i];
		}
	} else {
		for(int i = 0; i<sz; i++) {
			if(visit[i]) continue;
			visit[i] = 1;
			bf[index] = i;
			bruteforce(index+1);
			visit[i] = 0;
		}
	}
}

int cmp(const void* a, const void* b) {
	int p = *((const int*)a);
	int q = *((const int*)b);
	return p>q ? 1 : (p==q)-1;
}

int main(void) {
	scanf("%d", &sz);
	for(int i = 0; i<sz; i++) scanf("%d", s+i);
	qsort(s, sz, sizeof(int), cmp);
	int top = sz-1;
	int bottom = 0;
	int p = sz-1;
	while(p >= 1) {
		bf[p] = top--;
		bf[p-1] = bottom++;
		p -= 2;
	}
	if(p == 0) { // sz가 홀수일때의 처리
		bf[p] = top--;
	}
	printf("%d\n", calc());
}
