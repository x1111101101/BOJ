#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
16953번: A->B
*/
int min = 200000000;
int a,b;

void go(long long c, int t) {
	if(c == b) {
		if(min > t) min = t;
		return;
	}
	if(c > b) return;
	go(c*10+1,t+1);
	go(c*2, t+1);
}

int main(void) {
	scanf("%d%d", &a, &b);
	go(a, 0);
	if(min == 200000000) {
		min = -2;	
	}
	printf("%d\n", min+1);
	fflush(stdout);
}
