/**
13925번: 수열과 쿼리 13
- lazy segment tree로 해결.
- y = ax + b
*/
#include <stdio.h>
#define SCALE   (100000)
#define NODE_SCALE  (400000)
int MOD = 1000000007;

typedef struct {
    int idx, hasRight, isLeaf, s, e;
    int childSum;

    int x, b; // a = ax + b

} node;

node tree[NODE_SCALE];

int calc_sum(int sum, int x, int b, int len) {
    int v = (((long long) sum) * x) % MOD;
    long long bs = ((long long) b)  * len;
    v += bs % MOD;
    return v % MOD;
}

// (ax + b)x2 + b2
// = axx2 + x2b + b2
int pull(node *cur, int x, int b) {
    if(cur->isLeaf) {
        cur->childSum = calc_sum(cur->childSum, x, b, 1);
        return cur->childSum % MOD;
    }
    int len = cur->e - cur->s + 1;
    cur->childSum = calc_sum(cur->childSum, x, b, len);
    cur->x = (((long long) x) * cur->x) % MOD;
    cur->b = (((long long) x) * cur->b + b) % MOD;
    return cur->childSum % MOD;
}

int push(node *cur) {
    int ret = pull(&tree[cur->idx*2], cur->x, cur->b);
    if(cur->hasRight) {
        ret += pull(&tree[cur->idx*2 + 1], cur->x, cur->b);
    }
    cur->x = 1;
    cur->b = 0;
    cur->childSum = ret % MOD;
    return ret % MOD;
}

void init_node(int i, int s, int e) {
    node *c = &tree[i];
    c->x=1;
    c->idx=i;
    c->s = s; c->e = e;
    if(s==e) {
        c->isLeaf = 1;
        return;
    }
    int mid = (s+e)/2;
    init_node(i*2, s, mid);
    if(mid+1 <= e) {
        c->hasRight = 1;
        init_node(i*2+1, mid+1, e);
    }
}

void operate(node *cur, int s, int e, int x, int b) {
    int isOutside = e < cur->s || s > cur->e;
    if(isOutside) return;
    int isFullyAssociated = s <= cur->s && cur->e <= e;
    if(isFullyAssociated) {
        pull(cur, x, b);
        return;
    }
    push(cur);
    operate(&tree[cur->idx * 2], s, e, x, b);
    cur->childSum = tree[cur->idx*2].childSum;
    if(cur->hasRight) {
        operate(&tree[cur->idx*2+1], s,e,x,b);
        cur->childSum += tree[cur->idx*2+1].childSum;
        cur->childSum %= MOD;
    }
}
int query(node *cur, int s, int e) {
    int isOutside = e < cur->s || s > cur->e;
    if(isOutside) return 0;
    int isFullyAssociated = s <= cur->s && cur->e <= e;
    if(isFullyAssociated) {
        return cur->childSum;
    }
    cur->childSum = push(cur) % MOD;
    int ret = query(tree + cur->idx*2, s, e);
    if(cur->hasRight) {
        ret += query(tree + cur->idx*2 + 1, s, e);
        ret %= MOD;
    }
    return ret;
}

int main() {
    int n;
    int a[SCALE];
    scanf("%d", &n);
    for(int i = 0; i<n; i++) scanf("%d", a + i);
    init_node(1, 0, n-1);
    for(int i = 0; i<n; i++) {
        operate(tree+1, i, i, 1, a[i]);
    }
    int q;
    scanf("%d", &q);
    for(int i = 0; i<q; i++) {
        int t, s, e;
        scanf("%d%d%d", &t, &s, &e);
        s--; e--;
        if(t == 4) {
            printf("%d\n", query(tree+1,s,e));
            continue;
        }
        int v;
        scanf("%d", &v);
        int x = 1, b = 0;
        if(t == 1) {
            b = v;
        } else if(t == 2) {
            x = v;
        } else {
            x = 0; b = v;
        }
        operate(tree+1, s, e, x, b);
    }
    
    return 0;
}
