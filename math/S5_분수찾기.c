#include <stdio.h>
#include <stdlib.h>
#include <math.h>

/**
1193번: 분수찾기
입력값을 x라고 했을 때,
n번째 대각선에 대응하는 x중 최댓값은 일반항이 (an = n)인 등차수열의 Sn이다.

an = n
sn = 0.5n^2 + 0.5n

s(n) = x
0.5n^2 + 0.5n - x = 0
n = -0.5 + sqrt(0.25+2x)		(근의 공식)

1			1
2~3			2
4~6			3
7~10		4
11~15		5
16~21		6
*/
int calcN(int x) {
	double n = -0.5 + sqrt(0.25 + 2*x);
	return ceil(n);
}

void swap(int* a, int *b) {
	int t = *a;
	*a = *b;
	*b = t;
}

int main(void) {
	int x;
	scanf("%d", &x);
	int n = calcN(x);
	int sn = 0.5*(n*n) + 0.5*n;
	int dif = sn-x;
	int c = n-dif;
	int p = dif+1;
	if(n%2 != 0) swap(&c, &p);
	printf("%d/%d", c,p);
}
