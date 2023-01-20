#include <stdio.h>
#include <stdlib.h>

#define SCALE	(1002)

/**
17144번: 미세먼지 안녕!
이전에 자바로 풀었던 문제지만 알고리즘 스터디에서 이 문제가 선택되서 C로 다시 풀어봤다.

(가로+2) x (세로+2) 크기의 2차원 배열 map을 사용했고, 테두리 부분을 -1로 초기화해줘서 -1의 값을 갖는 테두리와 공기청정기 영역에는 확산이 일어나지 않게 했다.
OFFSET을 이용해 코드 중복을 피했는데, 반시계 방향 루프에서의 y 오프셋은 시계 방향 y오프셋 * -1 임을 이용해서 1개의 OFFSET 집합으로 해결했다.
*/

int OFFSETS[][2] = {{0,1}, {1,-1}, {0,-1}, {1,1}}; // 축, 증가량. (반시계 방향)
int (*map)[SCALE];
int len[2];
int fresherY; // 공기 청정기의 윗 부분 y 좌표

void spread(int (*targetMap)[SCALE], int x, int y) {
	int unit = map[x][y] / 5;
	int loc[2];
	for(int i = 0; i<4; i++) {
		loc[0] = x;
		loc[1] = y;
		int* offset = OFFSETS[i];
		loc[offset[0]] += offset[1];
		if(map[loc[0]][loc[1]] == -1) continue;
		targetMap[loc[0]][loc[1]] += unit;
		targetMap[x][y] -= unit;
	}
}

void spreadAll(int (*tempMap)[SCALE]) {
	for(int x = 2; x<=len[0]; x++) {
		for(int y = 1; y<=len[1]; y++) {
			spread(tempMap, x, y);
		}
	}
	for(int y = 1; y<fresherY; y++) {
		spread(tempMap, 1, y);
	}
	for(int y = fresherY+2; y<=len[1]; y++) {
		spread(tempMap, 1, y);
	}
}

int sweep(int top) {
	int loc[2] = {1, fresherY + (!top)};
	int yOffset = top - !top;
	int prev = 0;
	int offsetIndex = 0;
	int* offset = OFFSETS[0];
	while(1) {
		int nxt[2];
		nxt[0] = loc[0];
		nxt[1] = loc[1];
		int delta[2];
		delta[0] = 0; delta[1] = 0;
		delta[offset[0]] += offset[1];
		delta[1] *= yOffset;
		int x = loc[0]+delta[0];
		int y = loc[1]+delta[1];
		if(map[x][y] == -1) {
			offsetIndex++;
			if(offsetIndex == 4) {
				break;
			}
			offset = OFFSETS[offsetIndex];
			continue;
		}
		loc[0] = x;
		loc[1] = y;
		int temp = map[x][y];
		map[x][y] = prev;
		prev = temp;
	}
	
}

void init() {
	map = malloc(sizeof(int)*SCALE*SCALE);
	int input;
	for(int y = 1; y<=len[1]; y++) {
		for(int x = 1; x<=len[0]; x++) {
			scanf("%d", map[x]+y);
		}
	}
	for(int y = 1; y<len[1]; y++) {
		if(map[1][y] == -1) {
			fresherY = y;
			break;
		}
	}
	for(int x = 0; x<=len[0]+1; x++) {
		map[x][0] = -1;
		map[x][len[1]+1] = -1;
	}
	for(int y = 0; y<=len[1]+1; y++) {
		map[0][y] = -1;
		map[len[0]+1][y] = -1;
	}
}

int main(void) {
	int tickToSimulate;
	scanf("%d%d%d", len+1, len, &tickToSimulate);
	init();
	int (*tempMap)[SCALE] = malloc(sizeof(int)*SCALE*SCALE);
	while(tickToSimulate-- > 0) {
		for(int x = 0; x<=len[0]+1; x++) {
			for(int y = 0; y<=len[1]+1; y++) {
				tempMap[x][y] = map[x][y];
			}
		}
		spreadAll(tempMap);
		int (*tempPointer)[SCALE] = tempMap;
		tempMap = map;
		map = tempPointer;
		sweep(1);
		sweep(0);
		
	}
	int leftDusts = 0;
	for(int x = 1; x<=len[0]; x++) {
		for(int y = 1; y<=len[1]; y++) {
			leftDusts += map[x][y];
		}
	}
	printf("%d", leftDusts+2);
}
