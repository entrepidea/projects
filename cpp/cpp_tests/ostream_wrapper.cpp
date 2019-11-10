/**
 * @Desc: 
 * learn how to create a simple ostream wrapper for vector and set.
 * 'g++ ostream_wrapper.cpp -o ostream_wrapper'
 * 
 * @Source: 算法竞赛入门经典（习题与解答）, 1.1.4
 * @Date: 10/17/19
 * 
*/
#include <iostream>
#include <vector>
#include <set>
#include <string>

using namespace std;

template<typename T>
ostream& operator<<(ostream& os, const vector<T>& v){
    for(int i=0;i<v.size();i++){
        os<<v[i]<<" ";
    }
    return os;
}

template<typename T>
ostream& operator<<(ostream& os, set<T> s){
    for(typename set<T>::iterator iter = s.begin();iter!=s.end();iter++){
        os<<*iter<<" ";
    }
    return os;
}

int main(int argc, char** argv){
    vector<int> v;
    v.push_back(1);
    v.push_back(2);
    v.push_back(3);
    cout<<v<<endl;


    set<int> s;
    s.insert(1);
    s.insert(2);
    s.insert(3);
    cout<<s<<endl; 

    set<string> s2;
    s2.insert("1");
    s2.insert("2");
    s2.insert("3");
    cout<<s2<<endl;
}