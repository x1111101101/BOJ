#include <stdio.h>
#include <stdlib.h>

/**
2623번: 음악프로그램
위상 정렬을 활용하여 풀었다.
진입차수가 0인게 더 이상 발생하지 않는데 아직 모든 가수를 내보내지 않았다면 순서를 정하는게 불가능하다

1. 진입차수가 0인 노드를 큐에 담는다.
2. 큐에 담긴 노드를 하나 꺼내고 그 노드에 해당하는 가수를 스케쥴에 포함시킨다.
3. 꺼낸 노드와 직접적으로 연결된, 노드들의 진입차수를 1씩 낮추고 진입차수가 0이라면 큐에 담는다.
4. 큐가 빌때까지 2를 반복한다.
5. 스케쥴의 크기가 가수의 수와 동일하다면 스케쥴을 출력하고 그렇지 않다면 0을 출력한다.

처음에 realloc 함수를 사용했었는데 메모리 초과가 발생했다. 원인은 realloc한 메모리에 free를 호출해서였다. realloc 시 메모리는 반납해야할  자동으로 반납된다.
*/

int n;
int degree[1000] = {0,}; // 가수 별 진입 차수
int* after[1000]; // index번 가수가 공연 시 진입차수가 줄어드는 가수들
int size[1000] = {0,};

int q[1000];
int qsz = 0;
int qst = 0;

int contains(int i, int k) {
	for(int j = 0; j<size[i]; j++) {
		if(after[i][j] == k) return 1;
	}
	return 0;
}

int pop() {
	int v = q[qst++];
	qsz--;
	if(qst > 999) {
		qst = qst%1000;
	}
	return v;
}

void push(int v) {
	int idx = qst + qsz++;
	if(idx > 999) {
		idx = idx%1000;
	}
	q[idx] = v;
}

int main(void) {
	int m;
	scanf("%d%d", &n, &m);
	while(m-- > 0) {
		int count, prev, in;
		scanf("%d", &count);
		if(count-- == 0) continue;
		scanf("%d", &prev);
		prev--;
		while(count-- > 0) {
			scanf("%d", &in);
			in--;
			if(!contains(prev, in)) {
				degree[in]++;
				if(size[prev] % 2 == 0) {
					int* new = malloc((size[prev]+2)*sizeof(int));
					for(int j = 0; j<size[prev]; j++) {
						new[j] = after[prev][j];
					}
					free(after[prev]);
					after[prev] = new;
				}
				
				after[prev][size[prev]++] = in;
			}
			prev = in;
		}
	}
	for(int i = 0; i<n; i++) {
		if(degree[i] == 0) {
			push(i);
		}
	}
	int left = n;
	int out[n];
	while(qsz > 0) {
		int s = pop();
		out[n-left--] = s;
		for(int k = 0; k<size[s]; k++) {
			degree[after[s][k]]--;
			if(degree[after[s][k]] == 0) {
				push(after[s][k]);
			}
		}
	}
	if(left != 0) {
		printf("0");
	} else {
		for(int i = 0; i<n; i++) {
			printf("%d\n", out[i]+1);
		}
	}
}
