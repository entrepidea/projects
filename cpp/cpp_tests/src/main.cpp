#include <iostream>

#include "cpp_libs/containers.h"

using namespace std;

int main() {

    string word("apple");
    pluralize(3,word);

    cout<<" the plural of apple is "<<word<<endl;


    list<int> l;
    init_list(4, l);

    for(auto iter = l.begin();iter != l.end();iter++){
        cout<<*iter<<endl;
    }
    return 0;


}