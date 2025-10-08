/**
7453번: 합이 0인 네 정수
*/
#include <stdio.h>
#include <stdlib.h>
#define SCALE   16000000

typedef long long ll;

int n;
ll groupA[SCALE], groupB[SCALE];
int groupFreqA[SCALE], groupFreqB[SCALE];
int arr[4][4000];

int cmp(const void *a, const void *b) {
    return (*(ll*)a - *(ll*)b);
}

int makeGroup(int *arrA, int *arrB, ll *destValue, int *destFreq) {
    static int buf[SCALE];
    for(int i = 0; i<n; i++) {
        for(int j = 0; j<n; j++) {
            buf[i*n+j] = arrA[i] + arrB[j];
        }   
    }
    int nCouple = 0;
    qsort(buf, n*n, sizeof(int), cmp);
    int v = buf[0], freq = 0;
    for(int i = 0; i<n*n; i++) {
        if(v == buf[i]) {
            freq++;
            continue;
        }
        destValue[nCouple] = v;
        destFreq[nCouple] = freq;
        v = buf[i];
        freq = 1;
        nCouple++;
    }
    destValue[nCouple] = v;
    destFreq[nCouple++] = freq;
    return nCouple;
}


int main() {
    scanf("%d", &n);
    for(int i = 0; i<4*n; i++) scanf("%d", arr[i%4] + i/4);
    int as = makeGroup(arr[0], arr[1], groupA, groupFreqA);
    int bs = makeGroup(arr[2], arr[3], groupB, groupFreqB);
    int ai = 0;
    int bi = bs - 1;
    ll ans = 0;
    while(ai < as && bi > -1) {
        ll sum = groupA[ai] + groupB[bi];
        if(sum == 0) {
            ans += ((ll)groupFreqA[ai]) * groupFreqB[bi];
            ai++;
            bi--;
        } else if (sum < 0) ai++;
          else bi--;
    }
    printf("%lld", ans);
    return 0;
}
