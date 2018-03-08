#include <iostream>

#include "cpp_libs/containers.h"
#include "cpp_libs/basics.h"

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


    //
    //Code below tests inheritance, polymophism and virtual functions.
    //
    /*
    Polygon* pPoly1 = new Rectangle();
    Polygon* pPoly2 = new Triangle();
    pPoly1->set_value(10,20);
    cout<<"rectangle area is: "<<pPoly1->area()<<endl;

    pPoly2->set_value(10,20);
    cout<<"triangle area is: "<<pPoly2->area()<<endl;
     */

    return 0;


}