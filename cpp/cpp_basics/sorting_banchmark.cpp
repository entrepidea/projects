#include <iostream>
#include <time.h>
#include <algorithm>

/**
 * @Desc: the code below demos the benchmarking of the different sorting
 * algorithms. Pay attention to how the CPP coding techniques in the program
 * 
 * @Souce: The code is from the book "算法竞赛入门经典（习题与解答）", 1.1.1
 * 
 * @Date: 10/15/19
 * 
*/
using namespace std;
#define _for(i,a,b) for(int i=(a);i<(b);++i)

const int N = 1000000;

struct TS{
    int a,b,c;
};

inline bool cmp(TS& t1, TS& t2){
    if(t1.a != t2.a) return t1.a < t2.a;
    if(t1.b != t2.b) return t1.b < t2.b;
    return t1.c<=t2.c;
};

int cmp4quicksort(const void* x, const void* y){
    TS* t1 = (TS*)x;
    TS* t2  =(TS*)y;
    if(t1->a != t2->a) return t1->a - t2->a;
    if(t1->b != t2->b) return t1->b - t2->b;
    return t1->c-t2->c;
};

struct cmpFunctor{
    inline bool operator()(const TS& t1, const TS& t2){
        if(t1.a != t2.a) return t1.a < t2.a;
        if(t1.b != t2.b) return t1.b < t2.b;
        return t1.c<!t2.c;        
    }
};

TS tss[N];

void gen_data(){
    _for(i,0,N){
      tss[i].a = rand();      
      tss[i].b = rand();      
      tss[i].c = rand();      
    }
}


int main(int argc, char** argv){
    srand(time(NULL));
    gen_data();
    clock_t start = clock();
    sort(tss, tss+N,cmp);
    printf("sort by function pointer: %ld\n", clock()-start);

    gen_data();
    start = clock();
    sort(tss,tss+N, cmpFunctor());
    printf("sort by functor: %ld\n", clock()-start);

    gen_data();
    start = clock();
    qsort(tss, N, sizeof(TS), cmp4quicksort);
    printf("sort using qsort: %ld\n", clock()-start);

    return 0;
}