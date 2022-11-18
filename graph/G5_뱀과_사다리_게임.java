#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
16928번: 뱀과 사다리 게임
무조건 가장 높은 칸으로 가는 그리디로 풀면 무한루프에 빠질 수도 있음 -> BFS
효율적으로 틱을 세기 위해 두개의 배열을 생성 후 포인터끼리 스왑하면서 사용
*/
int warp[100] = {-1,};
int visit[100] = {0};

int main(void) {
	int n, m;
	int x, y;
	scanf("%d %d", &n, &m);
	for(int i = 0; i<n+m; i++) {
		scanf("%d %d", &x, &y);
		warp[x-1] = y-1;
	}
	int* st = malloc(sizeof(int)*100);
	int* st2 = malloc(sizeof(int)*100);
	int sz = 1;
	int tick = 0;
	st[0] = 0;
	visit[0] = 1;
	while(sz > 0) {
		int sz2 = 0;
		do {
			int c = st[--sz];
			if(c == 99) {
				printf("%d\n", tick);
				return 0;
			}
			for(int i = 1; i<7; i++) {
				int g = c+i;
				if(g > 99 || visit[g]) continue;
				visit[g] = 1;
				if(warp[g]) {
					g = warp[g];
				}
				st2[sz2++] = g;
			}
		} while(sz > 0);
		tick++;
		sz = sz2;
		int* t = st;
		st = st2;
		st2 = t;
	}
	
	
}

