#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
1022번: 소용돌이 예쁘게 출력하기
계산 영역 중 프린트를 해야하는 영역에 해당하는 배열을 만든 뒤, 프린트 영역에 해당하면 1씩 증감하면서 배열에 넣어주고, 프린트 영역에 해당하지 않으면, 다음 프린트
영역까지의 거리만큼 한번에 건너뛰는 방식으로 최적화.
*/

int r1,r2,c1,c2;
int sizeX, sizeY;
int x = 0,y = 0;
int num = 1;
int maxP;
int* arr;

int cmax(int a, int b) {
	if(a > b) return a; return b;
}
int cmin(int a, int b) {
	if(a < b) return a; return b;
}

// 좌표 포인터, 변화량
void move(int* t, int times, int min, int max, int check(void)) {
	if(!(*t+times < min || *t > max) && check()) {
		int goal = *t+times;
		if(*t < min-1) {
			int distance = min-1-*t;
			num += distance;
			*t = min-1;
		}
		int maxT = cmin(max, goal);
		while(*t < maxT) {
			*t += 1;
			num++;
			int fx = x-c1;
			int fy = y-r1;
			*(arr+sizeX*fy+fx) = num;
		}
		maxP = num;
		if(maxT == max) {
			int distance = goal - *t;
			num += distance;
			*t += distance;
		}
		
	} else {
		*t += times;
		num += times;
	}
}

void moveNegative(int *t, int times, int min, int max, int check(void)) {
	if(!(*t<min || *t-times > max) && check()) {
		int goal = *t-times;
		if(*t > max+1) {
			int distance = *t-(max+1);
			num += distance;
			*t = max+1;
		}
		
		int maxT = cmax(min, goal);
		while(maxT < *t) {
			*t -= 1;
			num++;
			int fx = x-c1;
			int fy = y-r1;
			*(arr+sizeX*fy+fx) = num;
		}
		maxP = num;
		if(maxT == min) {
			int distance = *t-goal;
			num += distance;
			*t -= distance;
		}
	} else {
		*t -= times;
		num += times;
	}
}

void printAll(void) {
	int l10 = log10((double) maxP)+1;
	//printf("l10 = %d, max=%d\n", l10, maxP);
	char str[50];
	if(maxP > l10) {
		sprintf(str, "%%%dd ", l10);
	} else {
		if(maxP > 1) {
			int l10s = log10((double) maxP-1)+1;
			if(l10s == l10) {
				l10 += 1;
			}
		}
		sprintf(str, "%%-%dd", l10);
	}
	for(int q = 0; q<sizeY; q++) {
		for(int p = 0; p<sizeX; p++) {
			printf(str, *(arr + p + q*sizeX));
		}
		printf("\n");
	}
}

int cy(void) {
	return y >= r1 && y <= r2;
}
int cx(void) {
	return x >= c1 && x <= c2;
}

int main(void) {
	scanf("%d %d %d %d", &r1, &c1, &r2, &c2);
	sizeX = c2-c1+1;
	sizeY = r2-r1+1;
	int toPrint[sizeX][sizeY];
	for(int y = 0; y<sizeY; y++) {
		for(int x = 0; x<sizeX; x++) {
			toPrint[x][y] =-1;
		}
	}
	arr = toPrint[0];
	if(c1 <= 0 && 0 <= c2 && r1 <= 0 && 0 <= r2) {
		*(arr+(x-c1) + (y-r1)*sizeX) = 1;
	}
	int del = 1;
	do {
		move(&x, del, c1, c2, cy);
		moveNegative(&y, del, r1, r2, cx);
		del++;
		moveNegative(&x, del, c1, c2, cy);
		move(&y, del, r1, r2, cx);
		del++;
	} while(toPrint[0][0] == -1 || toPrint[sizeX-1][sizeY-1] == -1 || toPrint[sizeX-2][sizeY-1] == -1);
	printAll();
}
