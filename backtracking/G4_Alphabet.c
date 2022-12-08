#include <stdio.h>
#include <stdlib.h>

/**
1987번: 알파벳
오버헤드를 고려해서 스택 기반으로 DFS를 했다.
max depth가 적을땐 재귀함수 기반으로 DFS를 하는것도 나쁘지 않을 것 같다.
*/
int l[2];
int OFFSET[][2] = {{0,-1}, {0,1}, {1,-1}, {1,1}}; // axis, 증가 값

int checkRange(int v, int axis) {
	return v > -1 && v < l[axis];
}

int main(void) {
	char map[20][20];
	scanf("%d%d", l+1, l);
	getchar();
	for(int y = 0; y<l[1]; y++) {
		for(int x = 0; x<l[0]; x++) {
			map[x][y] = getchar();
		}
		getchar();
	}
	int vsz = 'Z'-'A'+1;
	int visit[vsz]; // 알파벳 별 방문 확인 테이블
	for(int i = 0; i<vsz; i++) visit[i] = 0;
	
	int maxLv = 1;
	int st[400][3]; // 레벨 별 x, y, offset 탐색 진전도
	int sz = 1;
	visit[map[0][0]-'A'] = 1;
	st[0][0] = 0;
	st[0][1] = 0;
	st[0][2] = 0;
	while(sz > 0) { // 스택 기반 DFS with Backtracking
		int* last = st[sz-1];
		int loc[2];
		int o;
		for(o = last[2]; o<4; o++) { // 탐색가능한 4가지 경우 중 이전에 탐색하지 않은 경우를 선택(last[2])
			int* os = OFFSET[o];
			loc[0] = last[0];
			loc[1] = last[1];
			loc[os[0]] += os[1];
			if(!checkRange(loc[os[0]], os[0]) || visit[map[loc[0]][loc[1]]-'A']) {
				continue;
			}
			break;
		}
		if(o >= 4) {
			if(sz > maxLv) maxLv = sz;
			sz--;
			visit[map[last[0]][last[1]]-'A'] = 0;
			continue;
		}
		last[2] = o+1;
		visit[map[loc[0]][loc[1]]-'A'] = 1;
		st[sz][0] = loc[0];
		st[sz][1] = loc[1];
		st[sz][2] = 0;
		sz++;
	}
	printf("%d", maxLv);
	fflush(stdout);
}
