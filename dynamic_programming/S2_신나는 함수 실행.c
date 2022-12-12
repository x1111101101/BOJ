#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/**
9184번: 신나는 함수 실행
*/
int unknown = -10000000;
int memo[51][51][51];

int w(int a, int b, int c) {
	if(a<=0 || b<=0 || c<= 0) {
		return 1;
	} 
	if(memo[a][b][c] != unknown) {
		return memo[a][b][c];
	}
	int result;
	if(a>20 || b>20 || c>20) {
		result = w(20,20,20);
	} else if(a<b && b<c) {
		result = w(a, b, c-1) + w(a, b-1, c-1) - w(a, b-1, c);
	} else {
		result = w(a-1, b, c) + w(a-1, b-1, c) + w(a-1, b, c-1) - w(a-1, b-1, c-1);
	}
	memo[a][b][c] = result;
	return result;
}

int main(void) {
	for(int i = 0; i<51; i++) {
		for(int k = 0; k<51; k++) {
			for(int j=0; j<51; j++) {
				memo[i][k][j] = unknown;
			}
		}
	}
	while(1) {
		int a,b,c;
		scanf("%d%d%d", &a,&b,&c);
		if(a==b && b==c && c==-1) break;
		printf("w(%d, %d, %d) = %d\n", a,b,c, w(a,b,c));
	}
}
