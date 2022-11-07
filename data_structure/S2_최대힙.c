#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
11279번: 최대힙
https://velog.io/@jaenny/%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0-%ED%9E%99-%EC%B5%9C%EC%86%8C%ED%9E%99-%EC%B5%9C%EB%8C%80%ED%9E%99
를 참고해서 이진 트리 구조를 처음으로 학습했다.

pop 시킬때 swap하는 과정에서 정렬이 어긋나지 않게 신경써야한다.
*/

int h[100001];
int xt = 1;

void swap(int a, int b) {
	int t = h[a];
	h[a] = h[b];
	h[b] = t;
}

int pop() {
	int value = h[1];
	if(xt == 1) return 0;
	swap(1, --xt);
	int i = 1;
	while(i*2 < xt) {
		if(i*2+1 < xt) {
			int max;
			if(h[i*2] < h[i*2+1]) {
				max = i*2+1;
			} else {
				max = i*2;
			}
			if(h[max] > h[i]) {
				swap(max, i);
				i = max;
				continue;
			}
		} else {
			if(h[i*2] > h[i]) {
				swap(i*2, i);
				i = i*2;
				continue;
			}
		}
		break;
	}
	return value;
}

void push(int v) {
	h[xt] = v;
	int i = xt;
	while(i/2 > 0) {
		if(h[i/2] < h[i]) {
			swap(i/2, i);
			i /= 2;
		} else {
			break;
		}
	}
	xt++;
}


int main(void) {
	int ops, in;
	scanf("%d", &ops);
	while(ops-- > 0) {
		scanf("%d", &in);
		if(in == 0) {
			printf("%d\n", pop());
		} else {
			push(in);
		}
	}
}
