#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
1541번: 잃어버린 괄호
-를 만나면 다시 -를 만나기 전까지 괄호로 묶어버리면 최솟값을 구할 수 있다는 점을 이용해 쉽게 푼 문제다.
*/
int main(void) {
	char e[51];
	scanf("%s", e);
	int num = 0;
	int rg = 0;
	int ao = 1;
	int i = 0;
	while(1) {		
		if(e[i] >= '0' && e[i] <= '9') {
			rg = rg * 10 + e[i]-'0';
			i++;
			continue;
		}
		num += rg*ao;
		rg = 0;
		if(e[i] == '-') {
			ao = -1;
		}
		if(e[i++] == '\0') break;
	}
	printf("%d", num);
}
