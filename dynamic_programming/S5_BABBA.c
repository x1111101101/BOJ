#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
9625번: BABBA

a(n) = b(n-1)
b(n) = b(n-1) + a(n-1)
*/

int main(void) {
	int n;
	scanf("%d", &n);
	int b = 0, a=1;
	for(int i = 0; i<n; i++) {
		int ta = a;
		a=b;
		b+=ta;
	}
	printf("%d %d", a, b);
}
