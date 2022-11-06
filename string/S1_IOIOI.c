#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
5525번: IOIOI
항상 OI로 패턴이 끝남을 이용해 최적화해서 100점 획득
*/

int k,l;
char* str;
int result = 0;

int check(int i) {
	if(str[i+2*k] != 'I') return i;
	for(int p = 0; p<k; p++) {
		if(str[i+2*p] != 'I') return i;
		if(str[i+2*p+1] != 'O') return i;
	}
	result++;
	int ns = i+2*k+1;
	while(ns < l-1) {
		if(str[ns] == 'I') return ns-1;
		if(str[ns+1] == 'O') return ns;
		result++;
		ns+=2;
	}
	return ns-1;
}

int main(void) {
	scanf("%d %d", &k, &l);
	str = malloc(sizeof(char) * l+1);
	scanf("%s", str);
	int plen = 1+k*2;
	for(int i = 0; i<=l-plen; i++) {
		i = check(i);
	}
	printf("%d", result);
}
