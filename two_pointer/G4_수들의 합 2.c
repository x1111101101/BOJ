#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>

#define IRNG(i,x,y)	for(int i = x; i<y; i++)
#define RNG(type, name, from, operator, to)	for(type name = from; name operator to; name++)
#define MAX(x,y)	((x>y)?(x):(y));
#define MIN(x,y)	((x<y)?(x):(y));
#define PCAST_C(new, pointer)	((const new*) pointer)	
#define SQUARE(x)	((x)*(x))

/**
2003번: 수들의 합 2
누적합+투포인터로 해결
*/

int main(void) {
	int n,m;
	scanf("%d%d", &n,&m);
	int s[n+1];
	s[0] = 0;
	int acc = 0;
	IRNG(i,1,n+1) {
		scanf("%d", s+i);
		s[i] += s[i-1];
	}
	int ans = 0;
	IRNG(i,1,n+1) {
		IRNG(j,i,n+1) {
			int v = s[j]-s[i-1];
			if(v == m) {
				ans++;
				break;
			}
		}
	}
	printf("%d", ans);
}
