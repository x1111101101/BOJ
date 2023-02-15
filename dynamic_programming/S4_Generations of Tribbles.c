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

typedef unsigned long long LL;


/**
9507번: Generations of Tribbles
dp[i] = dp[i-1] + dp[i-2] . . . dp[i-4] 
인데, 이는 dp[i] = dp[i-1]*2 - dp[i-5]로 볼 수도 있다. 이 원리를 이용해서 더 적은 연산으로 값을 구했다.


1
1
2
4
8
15
*/

LL fibo(int n) {
	LL dp[] = {1,1,2,4};
	if(n<4) return dp[n];
	if(n==4) return 8;
	int nxt = 5;
	LL sum = 8;
	int min = 1;
	dp[0] = 8;
	LL lastMin = 1;
	do {
		sum = sum * 2 - lastMin;
		lastMin = dp[min];
		dp[min] = sum;
		min = (min+1)%4;
	} while(nxt++ < n);
	return sum;
}

int main(void) {
	int n;
	scanf("%d", &n);
	FORI(i,0,n) {
		int k;
		scanf("%d", &k);
		printf("%llu\n", fibo(k));
	}
}
