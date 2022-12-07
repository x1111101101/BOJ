#include <stdio.h>
#include <stdlib.h>

/**
10129번: 작은새
우선순위 큐를 이용해 NLogN 이내로 풀 수 있을걸로 예상했지만 시간초과. 다음에 다시 풀어볼 예정이다.
*/

int h[1000000];
int pq[1000001];
int dp[1000000];
int popped[1000000];
int nxt;

void swap(int a, int b) {
	int temp = pq[a];
	pq[a] = pq[b];
	pq[b] = temp;
}

void push(int idx) {
	pq[nxt] = idx;
	int i = nxt++;
	while(i/2 > 0) {
		if(dp[pq[i/2]] > dp[idx]) {
			swap(i/2, i);
			i /= 2;
		} else {
			break;
		}
	}
}

int pop() {
	int v = pq[1];
	swap(--nxt, 1);
	int i = 1;
	while(i*2 < nxt) {
		int c = i*2;
		if(i*2+1 < nxt && dp[pq[i*2+1]] < dp[pq[i*2]]) {
			c = i*2+1;
		}
		if(dp[pq[i]] > dp[pq[c]]) {
			swap(i, c);
			i = c;
		} else {
			break;
		}
	}
	return v;
}
int get() {
	return pq[1];
}


int main(void) {
	int n, q, k;
	scanf("%d", &n);
	for(int i = 0; i<n; i++) scanf("%d", h+i);
	scanf("%d", &q);
	while(q-- > 0) {
		scanf("%d", &k);
		nxt = 2;
		pq[1] = 0;
		dp[0] = 0;
		for(int i = 1; i<n; i++) {
			int before = -1;
			while(1) {
				int min = get();
				if(i-min > k) {
					pop();
					//printf("C: %d\n", i);
					continue;
				}
				if(h[min] > h[i]) {
					dp[i] = dp[min];
					break;
				}
				int minV = dp[min]+1;
				pop();
				int pc = 1;
				popped[0] = min;
				while(nxt > 1) {
					int idx = pop();
					if(i-idx > k) continue;
					popped[pc++] = idx;
					int v = dp[idx];
					if(h[idx] > h[i]) {
						if(v < minV) {
							minV = v;
						}
						break;
					}
					if(v+1 > min) {
						break;
					}
				}
				for(int p = 0; p<pc; p++) {
					push(popped[p]);
				}
				dp[i] = minV;
				break;
			}
			push(i);
		}
		printf("%d\n", dp[n-1]);
	}
	
}
