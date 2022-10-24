#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <math.h>
/**
17202번: 핸드폰 번호 궁합
*/
int main(void) {
	int arr[16];
	for(int i = 0; i<8; i++) {
		scanf("%1d", arr+i*2);
	}
	for(int i = 0; i<8; i++) {
		scanf("%1d", arr+i*2+1);
	}
	int loop = 15;
	while(loop >= 2) {
		for(int i = 0; i<loop; i++) {
			arr[i] = arr[i] + arr[i+1];
			arr[i] %= 10;
		}
		loop--;
	}
	printf("%d%d", arr[0], arr[1]);
}
