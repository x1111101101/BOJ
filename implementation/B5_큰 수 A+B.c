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
10757번: 큰 수 A+B
*/

char str[2][1000010];
int len[2];

int main(void) {
	FORI(i,0,2) {
		scanf("%s", str[i]+1);
		len[i] = strlen(str[i]+1);
		str[i][0] = '0';
	}
	int pointer[2] = {len[0], len[1]};
	int max = len[0] > len[1] ? 0 : 1;
	while(pointer[!max] >= 0) {
		int a = str[max][pointer[max]] - '0';
		int b = str[!max][pointer[!max]] - '0';
		str[max][pointer[max]] = (a+b)%10 + '0';
		str[max][pointer[max]-1] += (a+b)/10;
		pointer[0]--;
		pointer[1]--;
	}
	while(pointer[max] > 0) {
		int n = str[max][pointer[max]] - '0';
		str[max][pointer[max]--] = n%10 + '0';
		str[max][pointer[max]] += n/10;
	}
	int start = str[max][0] == '0' ? 1 : 0;
	FORI(i,start,len[max]+1) {
		printf("%c", str[max][i]);
	}
}

