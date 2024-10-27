#include <stdio.h>
#include <stdlib.h>

/**
 * 14572번: 스터디 그룹
 *
 * E = (그룹 내의 학생들이 아는 모든 알고리즘의 수 - 그룹 내의 모든 학생들이 아는 알고리즘의 수) * 그룹원의 수
 * 이를 E = (OR - AND) * COUNT 로 치환
 *
 * 어떤 그룹에 학생을 추가할 때:
 * - OR의 값이 줄어드는 경우는 없다.
 * - AND의 값이 늘어나는 경우는 없다.
 *
 * 학생을 뺄 때(단 COUNT >= 2):
 * - OR의 값이 줄어드는 경우 발생 가능(뺀 학생 혼자 그 알고리즘을 알고 있었을 때)
 * - AND의 값이 늘어나는 경우 발생 가능(뺀 학생 혼자 그 알고리즘을 몰랐을 때)
 *
 * 결국 학생을 많이 추가할 수록 E가 높아질 수 밖에 없다.
 *
 */

#define MAX_ST  (100000)
int n,k,d;
int pk[31];
int gkind = 0, gall = 0, gs = 0;

void rc() {
    gkind = 0; gall = 0;
    for(int i = 1; i<=k; i++) {
        if(pk[i]) gkind++;
        if(pk[i] == gs) gall++;
    }
}

void mpk(int set, int delta) {
    for(int i = 1; i<=k; i++) {
        set = set >> 1;
        if((1 & set) == 0) continue;
        pk[i] += delta;
    }
}

void add(int *std) {
    mpk(std[1], 1);
    gs++;
    rc();
}

void remst(int *std) {
    mpk(std[1], -1);
    gs--;
    rc();
}

int cmpStudent(const void *a, const void *b) {
    int *p = (int*) a;
    int *q = (int*) b;
    return p[0] - q[0];
}

int main() {
    for(int i = 0; i<31; i++) pk[i] = 0;
    scanf("%d%d%d", &n, &k, &d);
    int students[MAX_ST][2]; // level, algorithm
    for(int i = 0; i<n; i++) {
        int kind = 0;
        scanf("%d %d", &kind, students[i]);
        int set = 0;
        int input;
        for(int j = 0; j<kind; j++) {
            scanf("%d", &input);
            set = set | (1 << input);
        }
        students[i][1] = set;
    }
    qsort(students, n, sizeof(int)*2, cmpStudent);
    int left = 0, right = 1, ans = 0;
    add(students[0]);
    while(left <= right && right < n) {
        int *ls = students[left];
        do {
            int *rs = students[right];
            int ldif = rs[0] - ls[0];
            if(ldif > d) break;
            add(rs);
            right++;
        } while(right < n);
        int e = (gkind - gall) * gs;
        if(e > ans) ans = e;
        remst(ls);
        left++;
    }
    printf("%d", ans);
    return 0;
}
