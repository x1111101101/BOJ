#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
2638번: 치즈
별다른 어려움 없이 푼 문제다.
2차원 배열 포인터를 코딩 테스트 문제에서 처음 적용해보았다.
치즈들을 먼저 녹이고 공기에 대한 연산을 해야 함에 유의하여 풀었다.

*/

int s[2];
int map[100][100]; // 2: 공기, 1: 치즈, 0: 치즈 내부 공기
int offsets[4][3] = {{1,0,0}, {-1,0,0}, {0,1,1}, {0,-1,1}}; // +x, +y, axis

int rc(int* loc, int axis) {
	return -1 < loc[axis] && loc[axis] < s[axis];
}

int get(int* loc) {
	return map[loc[0]][loc[1]];
}
int airBfs(int x, int y) {
	int st[10001][2]; // x,y
	int stc = 1;
	st[0][0] = x;
	st[0][1] = y;
	map[x][y] = 2;
	while(stc > 0) {
		--stc;
		int l[2];
		l[0] = st[stc][0];
		l[1] = st[stc][1];
		for(int o = 0; o<4; o++) {
			st[stc][0] = l[0] + offsets[o][0];
			st[stc][1] = l[1] + offsets[o][1];
			if(rc(st[stc], offsets[o][2])) {
				if(get(st[stc]) == 0) {
					map[st[stc][0]][st[stc][1]] = 2;
					stc++;
				}
			}
		}
	}
}
int canMelt(int* loc) {
	int air = 0;
	int al[2];
	for(int i = 0; i<4; i++) {
		al[0] = offsets[i][0] + loc[0];
		al[1] = offsets[i][1] + loc[1];
		if(rc(al, offsets[i][2]) && get(al) == 2)
			air++;
	}
	return air > 1;
}

void swapPtr(int (**a)[2], int (**b)[2]) {
	int (*temp)[2] = *a;
	*a = *b;
	*b = temp;
}

void pr() {
	for(int y = 0; y<s[1]; y++) {
		for(int x = 0; x<s[0]; x++) {
			printf("%d",map[x][y]);
		}
		printf("\n");
	}
}

int main(void) {
	scanf("%d %d", s+1, s);
	int (*cses)[2] = malloc(sizeof(int)*2*10000);
	int csesc = 0;
	for(int i = 0; i<s[1]; i++)
		for(int k = 0; k<s[0]; k++)
			scanf("%1d", map[k]+i);
	for(int i = 0; i<s[1]; i++) {
		for(int k = 0; k<s[0]; k++) {
			if(map[k][i] == 1) {
				cses[csesc][0] = k;
				cses[csesc][1] = i;
				csesc++;
			}
		}
	}
	airBfs(0,0);
	int tick = 0;
	int melted[10000][2];
	int sm, mc;
	int (*cses2)[2] = malloc(sizeof(int)*2*10000);
	//pr();
	
	while(csesc > 0) {
		sm = 0;
		mc = 0;
		for(int i = 0; i < csesc; i++) {
			int* c = cses[i];
			if(canMelt(c)) {
				melted[mc][0] = c[0];
				melted[mc][1] = c[1];
				mc++;
			} else {
				cses2[sm][0] = c[0];
				cses2[sm][1] = c[1];
				sm++;
			}
		}
		for(int i = 0; i < mc; i++) {
			airBfs(melted[i][0], melted[i][1]);
		}
		swapPtr(&cses2, &cses);
		tick++;
		csesc = sm;
	}
	printf("%d", tick);
	
}
