#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#include <stdint.h>

#define IRNG(i,x,y)	for(int i = x; i<y; i++)
#define RNG(type, name, from, operator, to)	for(type name = from; name operator to; name++)
#define MAX(x,y)	((x>y)?(x):(y));
#define MIN(x,y)	((x<y)?(x):(y));
#define PCAST_C(new, pointer)	((const new*) pointer)	
#define SQUARE(x)	((x)*(x))

/**
1644번: 소수의 연속합
비트마스킹을 이용한 에라토스테네스의 채로 소수를 필터링하고, 누적합과 투 포인터를 이용해서 구간 별 연속합을 O(1)의 시간 복잡도로 체크
+ 같은 길이의 구간에 한해 오른쪽으로 갈 수록 구간합이 커지는 성질을 이용해 루프 중단점을 설정해서 시간을 단축함
*/

typedef uint32_t Int;
int acc[4000001];

Int check(Int t, int idx) {
	Int k = 1;
	k <<= idx;
	return k | t;
}

int isChecked(Int t, int idx) {
	Int k = 1;
	k <<= idx;
	return k & t;
}

int convert(int n) {
	return n/(sizeof(Int)*8);
}

int convertMod(int n) {
	return n%(sizeof(Int)*8);
}

int main(void) {
	int n;
	scanf("%d", &n);
	int tsz = (n/(sizeof(Int)*8))+1;
	Int *table = calloc(sizeof(Int), tsz);
	int min = 2;
	int size = 0;
	while(min <= n) {
		int x = n+1;
		IRNG(i,min,n+2) {
			int k = convert(i);
			int t = table[k];
			int mod = convertMod(i);
			if(isChecked(t,mod)) {
				continue;
			}
			x = i;
			min = i+1;
			break;
		}
		int p = x;
		if(p > n) continue;
		size++;
		acc[size] = p + acc[size-1];
		do {
			int k = convert(p);
			int t = table[k];
			int mod = convertMod(p);
			if(isChecked(t, mod)) {
				p += x;
				continue;
			}
			table[k] = check(t, mod);
			p += x;
		} while(p <= n);
	}
	int ans = 0;
	IRNG(a,1,size+1) {
		IRNG(b,a,size+1) {
			int k = acc[b]-acc[a-1];
			if(k == n) {
				ans++;
			} else if(k < n) continue;
			break;
		}
	}
	printf("%d", ans);
}
