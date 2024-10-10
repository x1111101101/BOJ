#include <stdio.h>

/**
1043번: 거짓말
disjoint set 문제. 노드의 개수가 50으로 제한되어있어 비트마스킹을 이용해 Union-Find 사용 없이 간단히 해결.
*/
typedef long long   LL;

int main() {
    int n, m, t;
    scanf("%d %d %d", &n, &m, &t);
    LL tp = 0;
    LL party[50] = {0, };
    for(int i = 0; i<t; i++) {
        int id;
        scanf("%d", &id);
        tp = tp | (1L << id);
    }
    for(int i = 0; i<m; i++) {
        int sz;
        scanf("%d", &sz);
        for(int k = 0; k<sz; k++) {
            int id;
            scanf("%d", &id);
            party[i] = party[i] | (1L << id);
        }
    }
    for(int i = 0; i<m; i++) {
        LL ct = tp & party[i];
        if(ct > 0) {
            tp = tp | party[i];
        }
        for(int k = 0; k<m; k++) {
            LL ct2 = tp & party[k];
            if(ct2 > 0) {
                tp = tp | party[k];
            }
            
        }
    }
    int ans = 0;
    for(int i = 0; i<m; i++) {
        LL ct = tp & party[i];
        ans += ct == 0;
    }
    printf("%d", ans);
    return 0;
}
