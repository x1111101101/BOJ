#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/**
9935번: 문자열 폭발

문자열을 차례대로 탐색하면서
폭발 문자열에 포함되는 문자열이라면:
	스택 맨 뒤에 문자를 추가
	스택 뒷쪽 문자들로 폭발 문자열이 만들어졌다면 그 문자들에 해당하는 str의 값을 지우고 스택 사이즈를 폭발 문자열의 길이만큼 줄임
else:
	스택을 비움
	
대략적으로 (폭발문자열의 길이 * 문자열의 길이)의 시간복잡도를 가짐
*/

char str[1000001];
char explode[40];
int length, lengthEx;
int left;
int contains[123];

int main(void) {
	scanf("%s", str);
	scanf("%s", explode);
	length = strlen(str);
	lengthEx = strlen(explode);
	left = length;
	for(int i = 0; i < lengthEx; i++) {
		contains[explode[i]] = 1;
	}
	int st[1000000][2]; // 문자, 인덱스
	int sz = 0;
	int i = 0;
	while(i < length) {
		char c = str[i++];
		if(!contains[c]) {
			sz = 0;
			continue;
		}
		st[sz][0] = c;
		st[sz++][1] = i-1;
		if(sz < lengthEx) continue;
		int flag = 1;
		for(int v = 1; v<=lengthEx; v++) {
			if(st[sz-v][0] != explode[lengthEx-v]) {
				flag = 0;
				break;
			}
		}
		if(!flag) continue;
		for(int v = 1; v<=lengthEx; v++) {
			str[st[sz-v][1]] = '#';
		}
		sz -= lengthEx;
		left -= lengthEx;
	}
	if(left == 0) {
		printf("FRULA");
		return 0;
	}
	for(int k = 0; k<length; k++) {
		int c = str[k];
		if(c == '#') continue;
		fputc(c, stdout);
	}
	fflush(stdout);
	
}
