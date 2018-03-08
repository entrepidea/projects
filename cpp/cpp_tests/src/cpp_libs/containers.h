//
// Created by jonat on 3/25/2017.
//

#ifndef CPP_TESTS_CONTAINERS_H
#define CPP_TESTS_CONTAINERS_H

#include <vector>
#include <cstddef>
#include <string>
#include <list>

void pluralize(size_t cnt, std::string &word)
{
if(cnt >  0){
word.push_back('s');
}
};

void init_list(size_t s, std::list<int> &l){
    for(int i=0;i<s;i++){
        l.push_back(i);
    }
};

#endif //CPP_TESTS_CONTAINERS_H
