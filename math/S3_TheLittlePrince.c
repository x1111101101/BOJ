#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
1004번: 어린왕자
주어진 원안에 출발점, 도착점 둘 중 하나만 있다면 반드시 원의 경계선을 지나야하고, 둘 다 있거나 둘중 하나도 없다면 지나지 않아도 된다
-> XOR 비트 연산 사용
*/
int sq(int x) {
	return x*x;
}

int isInside(int x, int y, int r, int* l) {
	return r*r < sq(l[0]-x) + sq(l[1]-y);
}

int main(void) {
	int from[2], to[2];
	int cases, planets;
	int x,y,r;
	int loc[2];
	scanf("%d", &cases);
	while(cases -- > 0) {
		scanf("%d %d", from, from+1);
		scanf("%d %d", to, to+1);
		scanf("%d", &planets);
		int result = 0;
		while(planets-- > 0) {
			scanf("%d %d %d", &x, &y, &r);
			int inS = isInside(x,y,r,from);
			int inE = isInside(x,y,r,to);
			int xor = inS^inE;
			if(xor) {
				result++;
			}
		}
		printf("%d\n", result);
	}
}
