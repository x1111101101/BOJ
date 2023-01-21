#include <stdio.h>
#include <stdlib.h>
/**
2812번: 크게 만들기
N자리 수에서 K개 숫자를 빼서 최댓값을 만들어야 하는 문제.
(N-K)개를 선택한다는 관점으로 접근했는데, 그냥 K개를 뺀다고 생각하고 접근하는게 더 코드도 간결해지고 빨랐을 것 같다.
연결리스트에서 중복 비교 연산을 최소화하는 방식으로 구현했다.
*/

#define SCALE	(500001)

typedef struct Node {
	char value;
	struct Node* next;
	struct Node* prev;
} Node;

int n, k;
Node* first;

Node* append(Node* left) {
	Node* new = malloc(sizeof(Node));
	left->next = new;
	new->prev = left;
	new->next = NULL;
	return new;
}

int main(void) {
	scanf("%d %d", &n, &k);
	int maxSize = n-k;
	first = malloc(sizeof(Node));
	Node* last = first;
	char in;
	for(int i = 0; i<maxSize-1; i++) {
		scanf("%c", &in);
		last->value = in;
		last = append(last);
	}
	scanf("%c", &in);
	last->value = in;
	last->next = NULL;
	n -= maxSize;
	Node* suspect = first;
	while(n-- >= 0) {
		scanf("%c", &in);
		while(suspect->next) {
			if(suspect->value < suspect->next->value) {
				if(suspect != first) {
					Node* new = suspect->next;
					suspect->prev->next = new;
					new->prev = suspect->prev;
					free(suspect);
					suspect = new->prev;
				} else {
					first = suspect->next;
					free(suspect);
					suspect = first;
					// first->prev = NULL;
				}
				last = append(last);
				last->value = in;
				break;
			}
			suspect = suspect->next;
		}
		if(suspect == last && last->value < in) {
			last->value = in;
			if(last != first) {
				suspect = last->prev;
			}
		}
	}
	Node* l = first;
	for(int i = 0; i<maxSize; i++) {
		fputc(l->value, stdout);
		l = l->next;
	}
	
}
