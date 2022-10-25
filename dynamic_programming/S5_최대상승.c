#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>

/**
25644번: 최대 상승

<순진한 방식>
a1과 a2, a3, a4 . . . an까지 비교
a2와 a2 . . . an 까지 비교
.
.
.
an
-> O(n^2)
<푼 방식>
1) ax일 후까지의 주가의 최댓값들만 구함-> O(n)
2) 그 후 1 부터 n까지의 최대 수익을 1에서 구한 구간 별 최댓값을 이용해서 구함 -> O(n)

-> O(n)
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
