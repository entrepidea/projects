//
// Created by jonat on 3/25/2017.
//

#include "containers.h"

void pluralize(size_t cnt, std::string &word){
    if(cnt >  0){
        word.push_back('s');
    }
}

void init_list(size_t s, std::list<int> &l){
    for(int i=0;i<s;i++){
        l.push_back(i);
    }
}