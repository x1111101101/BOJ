/**
1034번: 램프
*/

#include <stdio.h>
#include <stdint.h>

typedef int64_t llong;
int N, M, K;
int answer; // 정답값
llong rows[50]; // 각 행의 값(bitmap) 저장
int zeros[50]; // 각 행별로 포함된 0의 개수 저장
llong equal[50]; // 각 행별로 <해당 행과 동일한 값을 지닌 행>의 개수 저장

// 한개의 행을 입력받고, 관련 정보를 계산해서 저장하는 함수
void read_one_row(int row_index) {
    llong value = 0;
    char str[51];
    int count_one = 0; // 1의 개수 저장
    scanf("%s", str);
    for (int i = 0; i < M; i++) {
        value <<= 1;
        int digit = str[i] - '0'; // '0' 경우: += 0, '1'일 경우: += 1
        count_one += digit;
        value += digit;
    }
    rows[row_index] = value;
    zeros[row_index] = M - count_one; // 0의 개수 저장
}

int main() {
    scanf("%d %d", &N, &M);
    // 각 행 입력받고 정보 추출
    for (int i = 0; i < N; i++) read_one_row(i);
    scanf("%d", &K);
    for (int i = 0; i < N; i++) 
        for (int k = i; k < N; k++) 
            equal[i] += (rows[i] == rows[k]);
    // 정답 값 찾기
    for (int i = 0; i < N; i++) {
        if (zeros[i] > K) continue; // 0 개수가 K개를 초과하면 스킵
        if ((K - zeros[i]) % 2 != 0) continue;
        if (equal[i] > answer) answer = equal[i];
    }
    printf("%d", answer);
    return 0;
}
