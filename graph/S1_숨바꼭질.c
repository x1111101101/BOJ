#include <stdio.h>
#include <stdlib.h>

/**
1697번: 숨바꼭질
탐색하는 깊이를 세가면서 스택으로 bfs
*/
int main(void) {
	int visit[200001] = {0,};
	int a,b;
	int tick = 0;
	scanf("%d%d", &a, &b);
	int* bfs = malloc(100000*sizeof(int));
	int* bfsSwap = malloc(100000*sizeof(int));
	int sz = 1;
	bfs[0] = a;
	visit[a] = 1;
	while(sz > 0) {
		int ssz = 0;
		do {
			int c = bfs[--sz];
			if(c == b) {
				ssz = 0;
				break;
			}
			if(c > 0 && !visit[c-1]) {
				bfsSwap[ssz++] = c-1;
				visit[c-1] = 1;
			}
			if(c < 200000 && !visit[c+1]) {
				bfsSwap[ssz++] = c+1;
				visit[c+1] = 1;
			}
			if(c <= 100000 && c > 0 && !visit[c*2]) {
				bfsSwap[ssz++] = c*2;
				visit[c*2] = 1;
			}
		} while(sz > 0);
		int* t = bfs;
		bfs = bfsSwap;
		bfsSwap = t;
		sz = ssz;
		tick++;
	}
	printf("%d", tick-1);
}
