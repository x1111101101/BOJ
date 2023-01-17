#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/**
5639번: 이진 검색 트리
자식이 부모의 키값 범위를 물려받을 수 있고, 부모의 키값에 따라 후손의 키값 범위가 달라지는 것에 주목해서 풀었다.
*/

#define MAX(X,Y)	(((X)>(Y))?(X):(Y))
#define MIN(X,Y)	(((X)<(Y))?(X):(Y))

typedef struct node {
	struct node* left;
	struct node* right;
	struct node* parent;
	int min, max, key;
} Node;

const int INF = 1000000000;
Node root;

int tryLink(Node* parent, Node* child) {
	int key = child->key;
	if(key > parent->max || key < parent->min) return 0;
	if(key < parent->key) {
		// 왼쪽에 연결
		if(parent->left != NULL) return 0;
		parent->left = child;
		child->min = parent->min;
		child->max = MIN(parent->key, parent->max);
	} else {
		if(parent->right != NULL) return 0;
		// 오른쪽에 연결
		parent->right = child;
		child->max = parent->max;
		child->min = MAX(parent->key, parent->min);
	}
	child->parent = parent;
	return 1;
}

int append(Node* child, int key) {
	static Node* parent = &root;
	int r;
	do {
		child->key = key;
		r = tryLink(parent, child);
		parent = parent->parent;
	} while(!r);
	parent = child;
}

void postorder(Node* n) {
	if(n == NULL) return;
	postorder(n->left);
	postorder(n->right);
	printf("%d\n", n->key);
}


int main(void) {
	int key;
	scanf("%d", &key);
	root.key = key;
	root.max = INF;
	root.min = 0;
	Node* cptr = malloc(sizeof(Node)*10000);
	while(scanf("%d", &key) == 1) {
		append(cptr, key);
		cptr++;
	}
	postorder(&root);
}
