#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

int len;
int map[2187][2187];
int count[3] = {0,};
int offsets[9][2];

/**
x,y는 left top 좌표
모두 일치하지 않을 시 2 리턴

더 낮은 크기의 스택 영역으로 문제를 해결할 수 있는데 오버헤드로 인해 다른 사람들의 답안보다 시간이 200ms 정도 더 걸리는 것 같다. 오버헤드도 고려해서 다시 짜보자.
*/
int reculsive(int x, int y, int l) {
	if(l == 1) {
		return map[x][y];
	}
	int u = l/3;
	int results[9];
	for(int i = 0; i<9; i++) {
		int cx = x + u * offsets[i][0];
		int cy = y + u * offsets[i][1];
		results[i] = reculsive(cx,cy,u);
	}
	int equal = 1;
	if(results[0] == 2) {
		equal = 0;
	} else {
		for(int i = 1; i<9; i++) {
			if(results[i-1] != results[i]) {
				equal = 0;
				break;
			}
		}
	}
	if(equal) {
		return results[0];
	} else {
		for(int i = 0; i<9; i++) {
			int r = results[i];
			if(r != 2) {
				count[r+1]++;
			}
		}
		return 2;
	}
}

int main(void) {
	for(int i = 0; i<3; i++) {
		for(int j = 0; j<3; j++) {
			offsets[i*3+j][0] = j;
			offsets[i*3+j][1] = i;
		}
	}
	scanf("%d", &len);
	for(int y = 0; y<len; y++) {
		for(int x = 0; x<len; x++) {
			scanf("%d", map[x]+y);
		}
	}
	if(len == 1) {
		count[map[0][0]+1]++;
	} else {
		int r = reculsive(0,0,len);
		if(r != 2) {
			count[r+1]++;
		}
	}
	printf("%d\n%d\n%d\n", count[0], count[1], count[2]);
}
