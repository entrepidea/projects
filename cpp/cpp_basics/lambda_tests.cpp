#include <vector>
#include <algorithm>
#include <iostream>

using namespace std;
/**
 * @Desc: lambda expression the C++ way
 * note: the [] part is called capture clause (or lambda-introducer)
 * @Source: 算法竞赛入门经典（习题与解答）, 1.2.4
*/
int main(int argc, char** argv){
    vector<int> list{1,2,3};
    int total =0;
    for_each(list.begin(), list.end(), [&total](int x){total +=x;});
    cout<<total<<endl;
    return 0;
}