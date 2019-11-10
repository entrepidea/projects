#include <iostream>
using namespace std;

int foo(int n){
    int fac = 1;
    for(int i=n;i>1;i--){
        fac *= i;
    }
    cout<<fac<<endl;
    int count =0;
    while(fac%10==0){
        count++;
        fac /= 10;
    }
    return count;
}
int main(int argc, char** argv){
    int n;
    cin>>n;
    cout<<foo(n)<<endl;
    return 0;
}