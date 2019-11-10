#include <iostream>
#include <istream>

/**
 * @Desc: 
 * @Source: 算法竞赛入门经典（习题与解答）, 1.1.5
 * @Date: 10/20/19
 * 
*/
using namespace std;

struct Point {
    int x,y;
    Point(int x=0, int y=0):x(x),y(y){}
    Point& operator=(Point& p)
    {
        x=p.x;
        y=p.y;
        return *this;
    }
};

typedef Point Vector;

Vector operator+(const Vector& a, const Vector& b){
    return Vector(a.x+b.x, a.y+b.y);
}
Vector operator-(const Vector& a, const Vector& b){
    return Vector(a.x-b.x, a.y-b.y);
}

Vector operator*(const Vector& a, const Vector& b){
    return Vector(a.x*b.x, a.y*b.y);
}
bool operator==(const Vector& a, Vector& b){
    return a.x==b.x && a.y==b.y;
}

istream& operator>>(istream& is, Vector& v){
    return is>>v.x>>v.y;
}
ostream& operator<<(ostream& os, const Vector& v){
    os<<v.x<<","<<v.y;
    return os;
}

int main(int argc, char** argv){
    Vector v1(1,2), v2(3,4);
    cout<<(v1+v2)<<endl;
    return 0;
}