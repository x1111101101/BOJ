#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
1312번: 소수
*/
int main(void) {
	int a, b, n;
	scanf("%d %d %d", &a, &b, &n);
	int m = a%b;
	int d;
	if(m == 0) {
		printf("0");
		return 0;
	}
	while(n-- > 0) {
		m*= 10;
		d = m/b;
		m%= b;
	}
	printf("%d",d);
}
