#include <stdio.h>
#include <math.h>
#include <stdlib.h>

/**
백준 2108: 통계학
이분탐색, qsort 활용
*/

int compare(const void *a, const void *b)
{
    int num1 = *(int *)a;
    int num2 = *(int *)b;
    if (num1 < num2)    
        return -1;
    if (num1 > num2)   
        return 1;
    return 0;
}

void quickSort(int arr[], int start, int end) {
	qsort(arr, end-start+1, sizeof(int), compare);
}

/**
이분 탐색으로 value+x를 만족하는 인덱스 최솟값 리턴
*/
int findDifPoint(int arr[], int value, int start, int end) {
	int index = (start+end)/2;
	if(end-start < 2) {
		if(arr[end] == value) return end+1;
		if(arr[start] != value) return start;
		return end;
	}
	if(arr[index] > value) {
		if(arr[index-1] == value) {
			return index;
		}
		return findDifPoint(arr, value, start,index);
	} else {
		return findDifPoint(arr, value, index, end);
	}
}

int main(void) {
	int size, in, sum=0;
	scanf("%d", &size);
	int arr[size];
	int freq[size];
	
	for(int i = 0; i<size; i++) {
		scanf("%d", &in);
		arr[i] = in;
		freq[i] = 0;
		sum += in;
	}
	quickSort(arr, 0, size-1);
	int freqIndex = 0;
	int freqCount[size];
	int maxFreq = 0;
	int duplication = 0;
	for(int i = 0; i<size; i++) {
		int j = findDifPoint(arr, arr[i], i, size-1);
		freq[freqIndex] = arr[i];
		freqCount[freqIndex] = j-i;
		freqIndex++;
		if(j-i > maxFreq) {
			maxFreq = j-i;
			duplication = 0;
		} else if(j-i == maxFreq) {
			duplication++;
		}
		i = j-1;
	}
	int freqToSort[duplication+1];
	int ftsIndex = 0;
	for(int i = 0; i<=freqIndex; i++) {
		if(freqCount[i] == maxFreq) {
			freqToSort[ftsIndex++] = freq[i];
		}
	}
	quickSort(freqToSort, 0, duplication);
	
	// 평균
	double s = sum<0 ? -0.5 : 0.5;
	int avr = sum/((double) size) + s;
	printf("%d\n", avr);
	
	//중앙
	printf("%d\n", arr[size/2]);
	
	//빈도
	int mostFreq;
	if(duplication == 0) {
		mostFreq = freqToSort[0];
	} else {
		mostFreq = freqToSort[1];
	}
	printf("%d\n", mostFreq);
	
	// 범위
	printf("%d\n", arr[size-1]-arr[0]);
}

/*
void swap(int arr[], int a, int b) {
	int temp = arr[b];
	arr[b] = arr[a];
	arr[a] = temp;
}

void quickSort2(int arr[], int start, int end) {
	if(end-start < 1) return;
	int pivot = arr[start];
	int left = start;
	int right = end+1;
	while(1) {
		while(arr[++left] < pivot);
		while(arr[--right] > pivot);
		if(left < right) {
			swap(arr, left, right);
		} else {
			swap(arr, start, right);
			quickSort(arr, start, right-1);
			quickSort(arr, right+1, end);
			return;
		}
	}
	
}
*/
