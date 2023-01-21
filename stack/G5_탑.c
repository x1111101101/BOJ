#include <stdio.h>

/**
2493번: 탑
항 a의 결과값은 a보다 왼쪽에 위치하면서 a보다 큰 항중 가장 가까운 항이다.
*/

#define SCALE	(500000)

int input[SCALE];
int result[SCALE];
int length;
int st[SCALE];
int sz;

int main(void) {
	scanf("%d", &length);
	for(int i = 0; i<length; i++) {
		scanf("%d", input+i);
	}
	for(int i = length-1; i>-1; i--) {
		int num = input[i];
		// 스택은 내림차순 정렬된다
		while(sz > 0) {
			int idx = st[sz-1];
			if(input[idx] > num) break;
			result[idx] = i+1;
			sz--;
		}
		st[sz++] = i;
	}
	for(int i = 0; i<length; i++) {
		printf("%d ", result[i]);
	}
}
