#include <stdio.h>
#include <stdlib.h>

/**
2342번: Dance Dance Revolution
BFS나 DFS로 풀게되면 시간복잡도가 O(N^2)이 된다. 
최대 입력 개수가 100000개라서 이 방식으론 시간 초과가 발생한다. -> DP

각 단계에서 왼발과 오른발의 위치는 (0,0), (0,1), (1,0), (0,2) . . . 의 25가지 경우로 한정되어있다.
가능한 왼발, 오른발 위치에서 최소 비용을 기억해서 풀게 했다.

*/
int INF = 200000000;
int (*dp)[5]; // 이전 단계에서 왼발, 오른발 위치 별 최소 비용
int (*tdp)[5]; // 현재 단계에서의 최소 비용 저장용 배열
int pw[5][5]; // x에서 y로 가는 비용

int min(int a, int b) {
	return a<b ? a : b;
}

void erase(int (*arr)[5]) {
	for(int x = 0; x<5; x++) for(int y = 0; y<5; y++) arr[x][y] = INF;
}

int main(void) {
	dp = malloc(25*sizeof(int));
	tdp = malloc(25*sizeof(int));
	erase(dp);
    for(int i = 0; i<5; i++)
        for(int k = 0; k<5; k++)
            pw[i][k] = 3;
	for(int i = 1; i<5; i++) {
        pw[i][i] = 1;
        pw[0][i] = 2;
		if(i-2 > 0)
			pw[i-2][i] = 4;
		if(i+2 < 5)
			pw[i+2][i] = 4;
	}
	int g;
	scanf("%d", &g);
	if(g == 0) {
		printf("0");
		return 0;
	}
	dp[0][g] = 2;
	while(1) {
		int lg = g;
		scanf("%d", &g);
		if(g == 0) {
			g = lg;
			break;
		}
		erase(tdp);
		for(int k = 0; k<5; k++) {
			// 이전 단계에선 당연히 오른발이나 왼발이 lg를 밟고 있어야만 한다. 따라서 오른발이나 왼발이 lg를 밟고 있는 케이스에 대해서만 계산한다.
			if(dp[k][lg] < INF) {
				tdp[g][lg] = min(tdp[g][lg], dp[k][lg] + pw[k][g]);
				tdp[k][g] = min(tdp[k][g], dp[k][lg] + pw[lg][g]);
			}
			if(dp[lg][k] < INF) {
				tdp[lg][g] = min(tdp[lg][g], dp[lg][k] + pw[k][g]);
				tdp[g][k] = min(tdp[g][k], dp[lg][k] + pw[lg][g]);
			}
		}
		int (*t)[5] = dp;
		dp = tdp;
		tdp = t;
	}
	int ans = INF;
	for(int h = 0; h<5; h++) {
		ans = min(ans, min(dp[g][h], dp[h][g]));
	}
	printf("%d", ans);
}
