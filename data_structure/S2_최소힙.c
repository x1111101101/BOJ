#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
1927번: 최소힙
배열로  이진트리로 구현
*/
typedef unsigned int Uint;

int l = 1;
Uint h[100001];

/*
void swap(int a, int b) {
	Uint t = h[a];
	h[a] = h[b];
	h[b] = t;
}
*/

Uint pop() {
	if(l == 1) {
		return 0;
	}
	Uint result = h[1];
	int i = 1;
	int v = h[--l];
	h[1] = v;
	while(i*2+1 < l) {
		int s = i*2;
		if(s+1 < l) {
			if(h[s] > h[s+1]) {
				s = s+1;
			}
		}
		if(h[i] > h[s]) {
			h[i] = h[s];
			h[s] = v;
			i = s;
		} else {
			return result;
		}
	}
	if(i*2 < l) {
		if(h[i] > h[i*2]) {
			h[i] = h[i*2];
			h[i*2] = v;
		}
	}
	return result;
}

void push(Uint n) {
	h[l] = n;
	int i = l++;
	while(i/2 > 0) {
		if(h[i/2] > n) {
			h[i] = h[i/2];
			h[i/2] = n;
			// swap(i/2, i);
			i /= 2;
		} else {
			break;
		}
	}
}

int main(void) {
	int ops;
	Uint op;
	scanf("%d", &ops);
	while(ops-- > 0) {
		scanf("%u", &op);
		if(op == 0) {
			printf("%u\n", pop());
		} else {
			push(op);
		}
	}
}
