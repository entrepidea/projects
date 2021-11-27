#include <iostream>
#include <math.h>
#include <set>

using namespace std;

int get_next(int n){
    int sum = 0;
    while(n!=0){
        sum += pow(n%10,2);
        n /=10;
    }
    return sum;
};

int main(int argc, char** argv){
    set<int> s;
    int n;
    cin>>n;
    while(n!=1 && !(s.find(n)!=s.end())){
        s.insert(n);
        n = get_next(n);
    }
    cout<<n<<endl;
}