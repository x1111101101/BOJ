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
25501번: 재귀의 귀재
문제에 제시된 재귀함수 예시를 복붙해서 쉽게 풀 수도 있지만, 반복문으로 재귀함수 로직을 구현해봤다.
*/

int main(void) {
	int n;
	scanf("%d", &n);
	char str[1001];
	while(n-- > 0) {
		scanf("%s", str);
		int l = 0;
		int r = strlen(str)-1;
		int cmp = 0;
		int flag = 1;
		while(l < r) {
			cmp++;
			if(str[l] != str[r]) {
				flag = 0;
				break;
			}
			l++;
			r--;
		}
		printf("%d %d\n", flag, cmp+flag);
	}
}
