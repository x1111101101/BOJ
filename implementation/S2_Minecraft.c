#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <memory.h>

/**
18111번: 마인크래프트
문제에선 땅을 3차원 구조로 표현했지만, 2차원 구조로 생각해도 문제를 푸는데에 문제가 없다.
*/

int minTime = 1000000000;
int heightAtMinTime = 0;
int n, m, blocks, size;
int amounts[257] = {0,};
int cumulative = 0;

void tryRank(int time, int height) {
	if(time == minTime) return;
	if(time<minTime) {
		minTime = time;
		heightAtMinTime = height;
	}
}

int main(void) {
	scanf("%d %d %d", &n, &m, &blocks);
	int minHeight = 257;
	int maxHeight = 1;
	int minCount = 0;
	int input;
	size = n*m;
	for(int i = 0; i<size; i++) {
		scanf("%d", &input);
		amounts[input]++;
	}
	for(int i = 256; i>=0; i--) {
		if(amounts[i] != 0) {
			maxHeight = i; 
			break;
		}
	}
  	for(int i = 0; i<maxHeight; i++) {
		cumulative += amounts[i]*(maxHeight-i);
	}
	int time = 0;
	for(int h = maxHeight; h>-1; h--) {
		// 설치
		if(blocks >= cumulative) {
			tryRank(time+cumulative, h);
		}
		cumulative -= size-amounts[h];
		
		// 파괴
		if(h<1) break;
		time += amounts[h]*2;
		amounts[h-1] += amounts[h];
		if(amounts[h-1] == size) {
			tryRank(time, h-1);
		}	
		blocks += amounts[h];
	}
	printf("%d %d\n", minTime, heightAtMinTime);
}
