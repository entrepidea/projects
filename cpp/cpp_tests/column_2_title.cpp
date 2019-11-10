#include <string>
#include <iostream>

using namespace std;

/**
 * @Source: https://leetcode.com/problems/excel-sheet-column-title/
 * @Description: convert the number to the excel column title
 * @Date: 10/27/19
 */

string convert2title(int x){
    string str;
    while(x){
        str += (--x%26+'A');
        x /=26;
    }
    return {str.rbegin(),str.rend()};
}

int main(int argc, char** argv){
    int x;
    cin>>x;
    cout<<convert2title(x)<<endl;
    return 0;
}