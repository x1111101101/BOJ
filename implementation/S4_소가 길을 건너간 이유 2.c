#include <stdio.h>
#include <stdlib.h>

/**
14468번: 소가 길을 건너간 이유 2
원 안의 점들 중 같은 알파벳끼리 이은 선들 끼리 교차하면 카운트 하는 문제
소 x와 소 y의 경로가 교차할 조건: 원을 소 x의 경로로 갈랐을 때, 갈라진 두곳 모두에 y의 점이 하나씩 위치해야 한다.
시계 방향으로 순서대로 점마다 번호를 부여하고 그 점들의 번호를 이용해 range check를 해서 풀었다.
*/

#define CALLOC(p,q)	(calloc(sizeof(p),(q)))
#define MIN(X,Y)	(((X)<(Y))?(X):(Y))

typedef struct Cow {
	int small,big;
} Cow;

Cow cows[26];

int range(int t, int lower, int upper) {
	return lower < t && t < upper;
}

int main(void) {
	for(int i = 1; i<=52; i++) {
		char in;
		scanf("%c", &in);
		int idx = in - 'A';
		if(cows[idx].small == 0) {
			cows[idx].small = i;
			continue;
		}
		if(cows[idx].small > i) {
			cows[idx].big = cows[idx].small;
			cows[idx].small = i;
		} else {
			cows[idx].big = i;
		}
	}
	int ans = 0;
	for(int i = 0; i<26; i++) {
		for(int k = 0; k<26; k++) {
			if(range(cows[k].small, cows[i].small, cows[i].big) != range(cows[k].big, cows[i].small, cows[i].big)) ans++;
		}
	}
	printf("%d", ans/2);
}
