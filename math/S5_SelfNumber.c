#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
4673번: 셀프 넘버
부루트포스로 
*/
int d(int n) {
	int r = n;
	while(n>0) {
		r += n%10;
		n /= 10;
	}
	return r;
}

int main(void) {
	int arr[10001] = {0,};
	for(int i = 1; i<10001; i++) {
		int q = d(i);
		if(q <= 10000)
			arr[q]++;	
	}
	for(int i = 1; i<10001; i++) {
		if(arr[i] == 0) printf("%d\n", i);
	}
}
