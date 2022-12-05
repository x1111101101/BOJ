#include <stdio.h>

/**
1912번: 연속합
음수만으로 이뤄져있는지 먼저 체크한 뒤, 음수만으로 구성되었다면 음수들 중 최대값을 출력해서 끝내고,
그렇지 않다면 양수가 무조건 하나 이상 있다는 뜻이기에 편하게 풀 수 있다.
양수가 하나라도 있다면 수열을 순차적으로 더하다가 더한 결과(변수 c)가 0 미만이 된다면 의미가 없어지기 때문이다.
O(N)의 시간 복잡도로 풀었다.

한번 더 생각해보니 양수가 하나라도 있다는 가정도 필요가 없어졌다.
코드를 더 짧게 수정했다.

수의 범위: -1000~1000
자연 수 n <=100000
*/

int main(void) {
	int n;
	int c = 0;
	int input;
	int result = -2000;
	scanf("%d", &n);
	for(int i = 0; i<n; i++) {
		scanf("%d", &input);
		c += input;
		if(c > result) result = c;
		if(c < 0) {
			c = 0;
		}
	}
	printf("%d", result);
	fflush(stdout);
}
