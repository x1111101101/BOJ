/**
11003번: 최솟값 찾기
덱을 이용한 구간 최솟/최댓값 트릭
*/
#include <stdio.h>
#define SCALE   (5000000)

typedef struct {
    int idx, value;
} ee;

ee q[SCALE];
int offset;
int size;
int a[SCALE];
int n, l;

int main() {
    scanf("%d %d", &n, &l);
    for(int i = 0; i<n; i++) scanf("%d", a+i);
    for(int i = 0; i<n; i++) {
        while(size && q[offset].idx < i - l + 1) {
            offset = (offset+1) % SCALE;
            size--;
        }
        while(size && q[(offset + size - 1) % SCALE].value >= a[i]) {
            size--;
        }
        int k = (offset + size++) % SCALE;
        q[k].idx = i;
        q[k].value = a[i];
        printf("%d ", q[offset].value);
    }
    return 0;
}
