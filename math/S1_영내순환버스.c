#include <stdio.h>
#include <stdlib.h>

/**
27278번: 영내 순환 버스
*/

#define SCALE	(100000)
#define MAX(X,Y)	((X)>(Y) ? (X) : (Y))

typedef struct Plan {
	int from,to,time;
} Plan;

int nodes, soldiers;
int time[SCALE];
int timeAcc[SCALE+1];

Plan plans[SCALE];

void input() {
	scanf("%d%d", &nodes, &soldiers);
	for(int i = 0; i<nodes; i++) {
		scanf("%d", time+i);
	}
	for(int i = 0; i<soldiers; i++) {
		Plan p;
		scanf("%d%d%d", &p.from, &p.to, &p.time);
		p.from--; p.to--;
		plans[i] = p;
	}
}

int main(void) {
	input();
	int acc = 0;
	for(int i = 1; i<=nodes; i++) {
		acc += time[i-1];
		timeAcc[i] = acc;
	}
	int max = 0;
	for(int i = 0; i<soldiers; i++) {
		Plan p = plans[i];
		int t = acc * (p.time / acc);
		int onboard = t + timeAcc[p.from];
		if(onboard < p.time) {
			onboard += acc;
		}
		int getoff = onboard;
		if(p.from < p.to) {
			getoff += timeAcc[p.to] - timeAcc[p.from];
		} else {
			getoff += acc - (timeAcc[p.from] - timeAcc[p.to]);
		}
		max = MAX(max, getoff);
	}
	printf("%d", max);
}
