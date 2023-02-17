#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#include <stdint.h>

#define FORI(i,x,y)	for(int i = x; i<y; i++)
#define RNG(type, name, from, operator, to)	for(type name = from; name operator to; name++)
#define MAX(x,y)	((x>y)?(x):(y))
#define MIN(x,y)	((x<y)?(x):(y))
#define PCAST_C(new, pointer)	((const new*) pointer)	
#define SQUARE(x)	((x)*(x))

/**
2292번: 벌집
반복문 없이 방정식을 세워 풀었다.
큰 수를 나타내는 int형간 곱셈으로 인한 오버플로우를 주의해야한다.


1: 1
7(+6): 1+6
19(+12): 1+6+12
37(+18): 1+6+12+18
61(+24)

a=0
d=6
S(n) = 3n^2-3n

n = 1+S(x)


*/



/**
3x^2 -3x + (1-n) = 0

x = (3+sqrt(9-4*3*(1-n)))/6.0
*/
int solveEq(int n) {
	double x = (3+sqrt(9-12.0*(1-n)))/6.0;
	return ceil(x);
}

int main(void) {
	int n;
	scanf("%d", &n);
	printf("%d", solveEq(n));
}

