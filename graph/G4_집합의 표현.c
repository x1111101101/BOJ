#include <stdio.h>
#include <stdlib.h>

/**
1717번: 집합의 표현
유니온 파인드의 핵심은 루트 노드끼리 잇는 것인듯 하다.
*/
int main(void) {
	int mode,a,b,n,m;
	scanf("%d%d", &n,&m);
	int t[n+1];
	int st[n+1];
	for(int i = 0; i<=n; i++) {
		t[i] = i;
	}
	while(m-- > 0) {
		scanf("%d%d%d", &mode, &a, &b);
		int pa = a;
		int pb = b;
		int sz = 0;
		while(pa > t[pa]) {
			st[sz++] = pa;
			pa = t[pa];
		}
		while(--sz > -1) t[st[sz]] = pa;
		sz = 0;
		while(pb > t[pb]) {
			st[sz++] = pb;
			pb = t[pb];
		}
		while(--sz > -1) t[st[sz]] = pb;
		if(!mode) {
			if(pa > pb) {
				t[pa] = pb;
			} else if(pa != pb) {
				t[pb] = pa;
			}
			continue;
		}
		if(t[pa] == t[pb]) {
			printf("YES\n");
		} else {
			printf("NO\n");
		}
	}
}
