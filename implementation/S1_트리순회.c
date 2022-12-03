#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
1991번: 트리 
*/
char t[26][2] = {{-1,-1},};

void pre(char x) {
	if(x < 0) return;
	putchar(x+'A');
	pre(t[x][0]);
	pre(t[x][1]);
}

void in(char x) {
	if(x < 0) return;
	in(t[x][0]);
	putchar(x+'A');
	in(t[x][1]);
}

void post(char x) {
	if(x < 0) return;
	post(t[x][0]);
	post(t[x][1]);
	putchar(x+'A');
}

int main(void) {
	int inp;
	scanf("%d", &inp);
	char p, l, r;
	getchar();
	while(inp-- > 0) {
		scanf("%c %c %c", &p, &l, &r);
		if(l == '.') l = 'A'-1;
		if(r == '.') r = 'A'-1;
		r -= 'A'; l -= 'A'; p -= 'A';
		t[p][0] = l;
		t[p][1] = r;
		fflush(stdout);
		getchar();
	}
	pre(0);
	putchar('\n');
	in(0);
	putchar('\n');
	post(0);
	fflush(stdout);
}
