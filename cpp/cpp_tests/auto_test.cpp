#include <vector>
#include <iostream>

/**
 * @Desc: test C++ new feature: auto
 * @Date: 10/22/2019
*/
using namespace std;

int main(int argc, char** argv){
    vector<int> v;
    v.push_back(1);
    v.push_back(2);
    v.push_back(3);
    cout<<v.back()<<endl;

    for(auto iter = v.begin();iter!=v.end();iter++){
        cout<<*iter<<", ";
    }
    cout<<endl;
    //or...
    for(const auto& p:v){
        cout<<p<<", ";
    }
    cout<<endl;
    
    return 0;
}