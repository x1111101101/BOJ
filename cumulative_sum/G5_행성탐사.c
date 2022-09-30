#include <stdio.h>

/**
5549번: 행성 탐사
문제를 이해하자 마자 누적합이 바로 떠오름.
*/
int w,h;
int i[1000][1000];
int j[1000][1000];
int o[1000][1000];

int fixLoc(int* x, int*y) {
	if(*x<0) {
		*x += w;
		*y -= 1;
	} else if(*x>=w) {
		*x -= w;
		*y += 1;
	}
	if(*y<0) return -1;
	if(*y >= h) return -1;
	return 0;
}

void add(int x, int y, int* pj, int* pi, int* po, int mul) {
	if(fixLoc(&x, &y) == -1) return;
	*pj += j[x][y] * mul;
	*po += o[x][y] * mul;
	*pi += i[x][y] * mul;
}

int main(void) {
	char in[1001];
	int k;
	scanf("%d %d", &h, &w); scanf("%d", &k);
	int cj = 0, co = 0, ci = 0;
	for(int y = 0; y<h; y++) {
		scanf("%s", in);
		for(int x = 0; x<w; x++) {
			switch(in[x]) {
				case 'O':
					co++; break;
				case 'J':
					cj++; break;
				case 'I':
					ci++; break;
			}
			i[x][y] = ci;
			o[x][y] = co;
			j[x][y] = cj;
		}
	}

	int nx, ny, xx, xy;
	while(k-- > 0) {
		scanf("%d %d %d %d", &ny, &nx,  &xy, &xx);
		nx--;ny--;xx--;xy--;
		int sumO = 0, sumI = 0, sumJ = 0;
		for(int fy=ny; fy<=xy; fy++) {
			add(xx, fy, &sumJ, &sumI, &sumO, 1);
			add(nx-1, fy, &sumJ, &sumI, &sumO, -1);
		}
		printf("%d %d %d\n", sumJ, sumO, sumI);
	}
}

