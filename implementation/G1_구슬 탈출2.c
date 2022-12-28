#include <stdio.h>
#include <stdlib.h>


/**
그래프 최대 크기가 10*10이라 재귀로 돌려도 될줄 알았는데
*/

#define INF	(11)

const char WALL = '#';
const char HOLE = 'O';
const int OFFSETS[][2] = {{0,-1},{0,1},{1,-1},{1,1}};

int minDepth = INF;
int len[2];
char map[10][10];
int visit[10][10][10][10]; // 빨강 위치, 파랑 위치
int record[10][10][10][10];

struct blocs {
	int locs[2][2];
};


int hasvisit(int (*locs)[2], int depth) {
	if(record[locs[0][0]][locs[0][1]][locs[1][0]][locs[1][1]] < depth) return 1;
	return visit[locs[0][0]][locs[0][1]][locs[1][0]][locs[1][1]];
}
int setvisit(int (*locs)[2], int v, int depth) {
	if(record[locs[0][0]][locs[0][1]][locs[1][0]][locs[1][1]] > depth) {
		record[locs[0][0]][locs[0][1]][locs[1][0]][locs[1][1]] = depth;
	}
	visit[locs[0][0]][locs[0][1]][locs[1][0]][locs[1][1]] = v;
}

int result;
// result - 1: 빨강이 구멍 통과, 2: 파랑이 구멍 통과, 0: 구멍 통과 X 
struct blocs simulate(const int * o, int (*locs)[2]) {
	struct blocs bl;
	result = 0;
	int (*l)[2] = bl.locs;
	for(int i = 0; i!=2;i++) {
		l[i][0] = locs[i][0];
		l[i][1] = locs[i][1];
	}
	int x = o[0];
	int d = o[1];
	int first;
	if(locs[0][x]*d < locs[1][x]*d) {
		first = 1;
	} else {
		first = 0;
	}
	int move[2] = {1,};
	int cc = 0;
	int opposit[2] = {1,0};
	int hole = -1;
	do {
		first = !first;
		move[first] = 0;
		int* sl = l[first];
		sl[x] += d;
		if(map[sl[0]][sl[1]] == WALL) {
			sl[x] -= d;
			continue;
		}
		int *nl = l[!first];
		if(nl[0] == sl[0] && nl[1] == sl[1]) {
			sl[x] -= d;
			continue;
		}
		if(map[sl[0]][sl[1]] == HOLE) {
			hole = first;
			result = 2;
			break;
		}
		move[first] = 1;
	} while(move[0] || move[1]);
	if(hole == 0) {
		int t = !hole;
		int *ll = l[t];
		int flag = 0;
		while(1) {
			ll[x] += d;
			if(map[ll[0]][ll[1]] == WALL) break;
			if(map[ll[0]][ll[1]] == HOLE) {
				flag = 1;
				break;
			}
		}
		if(!flag) {
			result = 1;
		}
	}
	return bl;
}

int main(void) {
	int * pr = record;
	for(int i = 0; i<10000; i++) {
		*(pr+i) = INF;
	}
	scanf("%d%d", len+1, len);
	getchar();
	int st[10000][2][2];
	int (*locs)[2] = st[0];
	for(int y = 0; y<len[1]; y++) {
		for(int x = 0; x<len[0]; x++) {
			scanf("%c", map[x]+y);
			if(map[x][y] == 'R') {
				map[x][y] = '.';
				locs[0][0] = x;
				locs[0][1] = y;
			} else if(map[x][y] == 'B') {
				map[x][y] = '.';
				locs[1][0] = x;
				locs[1][1] = y;
			}
		}
		getchar();
	}
	setvisit(locs, 1, 0);
	
	int os[10000];
	os[0] = -1;
	int sz = 1;
	while(sz > 0) {
		int (*l)[2] = st[sz-1];
		int o = ++os[sz-1];
		if(o >= 4) {
			setvisit(l, 0, INF);
			sz--;
			continue;
		}
		int (*s)[2] = simulate(OFFSETS[o], l).locs;
		if(!result) {
			if(hasvisit(s, sz)) {
				continue;
			}
			setvisit(s, 1, sz);
			os[sz] = -1;
			for(int i = 0; i<2; i++) {
				st[sz][i][0] = s[i][0];
				st[sz][i][1] = s[i][1];
			}
			sz++;
			continue;
		}
		if(result == 1) {
			if(sz < minDepth) {
				minDepth = sz;
			}
			setvisit(l, 0, INF);
			sz--;
		}
		
	}
	if(minDepth >= INF) minDepth = -1;
	printf("%d", minDepth);
}
