#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
1012번: 유기농 배추
간단한 BFS 문제다.

정적인 크기의 탐색용 배열을 이용해 너비 우선 탐색으로 풀었다.
그래프의 최대 크기가 50x50인점, 탐색용 배열에 중복 삽입을 하지 않는 점을 이용해 배열의 필요한 최대 크기를 구했다.
*/

int cases, plants;
int mapSize[2];
int map[50][50] = {0,};
int size;
int ts[2500][2];

void add(int* v) {
	ts[size][0] = v[0];
	ts[size][1] = v[1];
	size++;
}

int rem(int* loc) {
	map[loc[0]][loc[1]] = 0;
}

int has(int* loc) {
	return map[loc[0]][loc[1]] == 1;
}

int check(int* i, int axis) {
	return -1 < i[axis] && i[axis] < mapSize[axis] && has(i);
}

int main(void) {
	scanf("%d", &cases);
	while(cases-- > 0) {
		scanf("%d %d %d", mapSize, mapSize+1, &plants);
		int inputs[plants][2];
		for(int x = 0; x<mapSize[0]; x++)
			for(int y = 0; y<mapSize[1]; y++)
				map[x][y] = 0;
		for(int i = 0; i<plants; i++) {
			scanf("%d %d", inputs[i], inputs[i]+1);
			map[inputs[i][0]][inputs[i][1]] = 1;
		}
		int result = 0;
		for(int i = 0; i<plants; i++) {
			if(!has(inputs[i])) continue;
			result++;
			add(inputs[i]);
			do {
				size--;
				int * loc = ts[size];
				rem(loc);
				loc[0]++;
				if(check(loc, 0)) {
					add(loc);
				}
				loc[0] -= 2;
				if(check(loc, 0)) {
					add(loc);
				}
				loc[0]++;
				loc[1]++;
				if(check(loc, 1)) {
					add(loc);
				}
				loc[1] -= 2;
				if(check(loc, 1)) {
					add(loc);
				}
			} while(size > 0);
			size = 0;
		}
		printf("%d\n", result);
	}
}

