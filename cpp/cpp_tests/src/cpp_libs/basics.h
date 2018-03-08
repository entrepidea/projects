//
// this header file is intended to include C++'s most basic concepts, knowledge points
//


#ifndef CPP_TESTS_BASICS_H
#define CPP_TESTS_BASICS_H


/*
 * The below three classes demo inheritance and polymophism.
 * Be noted that "area" is defined as "virtual". Only by that when Derived classes are referenced by a Base class, the specific implementation would be called.
 *
 * see also: http://www.cplusplus.com/doc/tutorial/polymorphism/
 * */
class Polygon{
protected:
    int width, height;
public:
    void set_value(int w, int h){width = w; height = h;};
    virtual int area(){return 0;};
};

class Rectangle:public Polygon{
public:
    virtual int area(){return width*height;};
};

class Triangle:public Polygon{
public:
    virtual int area(){
        return width*height/2;
    }
};
#endif //CPP_TESTS_BASICS_H
