#include <stdio.h>
#include <stdlib.h>

/**
11723번: 집합
요소의 범위가 1~20이므로 unsigned int 대상의 비트 마스킹을 이용해서 구현
*/

unsigned int set = 0;
unsigned int full = 1;

void initFull() {
	unsigned two = 2;
	for(int i = 0; i<20; i++) {
		full += two;
		two *= 2;
	}
}

int main(void) {
	int n;
	initFull();
	scanf("%d", &n);
	char str[8];
	while(n-- > 0) {
		int t = 1;
		int p;
		scanf("%s", str);
		if(str[0] == 'a') {
			if(str[1] == 'l') {
				set = full;
				continue;
			}
			scanf("%d", &p);
			t <<= p;
			set |= t;
			continue;
		}
		if(str[0] == 'e') {
			set = 0;
			continue;
		}
		scanf("%d", &p);
		t <<= p;
		unsigned int tcmp;
		switch(str[0]) {
			case 'r':
				tcmp = set ^ t;
				if(tcmp < set) set = tcmp;
				break;
			case 'c':
				tcmp = set ^ t;
				printf("%d\n", tcmp < set);
				break;
			case 't':
				set ^= t;
				break;
		}
	}
}
