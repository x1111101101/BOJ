#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
25644번: 최대 상승
*/

int main(void) {
	int n;
	scanf("%d", &n);
	int arr[n];
	for(int i = 0; i<n; i++) {
		scanf("%d", arr+i);
	}
	int maxAfter[n];
	maxAfter[n-1] = arr[n-1];
	for(int i = n-2; i>-1; i--) {
		int k = arr[i];
		if(k > maxAfter[i+1]) {
			maxAfter[i] = k; 
		} else {
			maxAfter[i] = maxAfter[i+1];
		}
	}
	int max = 0;
	for(int i = 0; i<n; i++) {
		int k = arr[i];
		int p = maxAfter[i];
		int q = p - k;
		if(q > max) max = q;
	}
	printf("%d\n", max);
}
