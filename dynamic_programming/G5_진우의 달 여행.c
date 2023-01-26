#include <stdio.h>
#include <stdlib.h>

/**
17484, 17485번: 진우의 달 여행
DFS나 BFS로도 해결 가능하지만 중복 연산이 많아지니 각 x좌표, 이전단계에서의 이동 방향별로 총 연료량을 나눠서 저장하고, 
이를 이용해서 다음단계 총 연료량을 갱신하는 DP를 썼다.
현재 단계에서의 계산을 수행하려면 직전 단계의 상태만 알면 되므로 이전단계 정보를 담는 dp, 다음 단계 정보를 담을 nxt의 두 테이블을 swap하면서 계산해 메모리 사용량을 최소화했다.
*/

#define CALLOC(p,q)	(calloc(sizeof(p),(q)))
#define MIN(X,Y)	((X)<(Y)?(X):(Y))

const int OTHER[][2] = {{1,2},{0,2},{0,1}}; // 코드 중복 회피를 위한 오프셋 OTHER[X] 배열에는 인덱스로 쓸 수 있는 값 중 X가 아닌 값이 담긴다. 0: 왼쪽 아래, 1: 아래, 2: 오른쪽 아래
const int INF = 1000000;

int *fuels;
int (*dp)[3]; // dp[x][s] -> s: 오른쪽 아래로 내려왔다면 0, 바로 아래로 내려왔다면 1, 왼쪽 아래로 내려왔다면 2
int (*nxt)[3]; // nxt[x][s] -> s: 오른쪽 아래로 내려왔다면 0, 바로 아래로 내려왔다면 1, 왼쪽 아래로 내려왔다면 2
int ylen, xlen;

int main(void) {
	scanf("%d%d", &ylen, &xlen);
	dp = CALLOC(int, (xlen+2)*3);
	nxt = malloc(sizeof(int)*(xlen+2)*3);
	for(int i = 0; i<3; i++) { // 테두리에 해당하는 인덱스들을 INF값으로 초기화
		dp[0][i] = INF;
		dp[xlen+1][i] = INF;
		nxt[0][i] = INF;
		nxt[xlen+1][i] = INF;
	}
	for(int i = 1; i<=xlen; i++) {
		int fuel;
		scanf("%d", &fuel);
		for(int d = 0; d<3; d++) {
			dp[i][d] = fuel;
		}
	}
	while(--ylen > 0) {
		for(int i = 1; i<=xlen; i++) {
			int fuel;
			scanf("%d", &fuel);
			int xd = -1;
			for(int d = 0; d<3; d++) {
				nxt[i][d] = MIN(dp[i+xd][OTHER[d][0]], dp[i+xd][OTHER[d][1]]) + fuel;
				xd++;
			}
		}
		int (*t)[3] = dp;
		dp = nxt;
		nxt = t;
	}
	int ans = INF;
	for(int i = 1; i<=xlen; i++) {
		for(int d = 0; d<3; d++) {
			ans = MIN(ans, dp[i][d]);
		}
	}
	printf("%d", ans);
}
