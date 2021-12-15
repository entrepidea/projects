/*
 
	lvalue, allocation value; rvalue is a temporary storage. also covered: lvalue reference, rvalue reference.

	Resource: https://www.youtube.com/watch?v=fbYknr-HPYE&t=640s

	09/12/21

 * */
#include <iostream>
#include <string>


//"type&" accpet only lValue
void setValue(int& i){
}
void test1(){
	int i = 10;
	setValue(i);
	//setValue(10); //this line, if uncommented, will report error, because 10 is rvalue; How to fix this, see below test2.
}

//"const type&" accept both lvalue and rvalue
void printName(const std::string& name){
	std::cout<<name<<std::endl;
}

void test2(){
	std::string firstName = "Jonathan ";
	std::string lastName = "Yee";

	std::string fullName = firstName + lastName;

	printName(fullName);
	printName(firstName + lastName);
}

//to accept only rvalue, it would look like: "type&&"
void printName3(std::string&& name){ //if another override method exists with this signature "constant std::string&", which also support rvalue, but this one takes precedence when the input parameter is detected as rvalue.
	std::cout<<name<<std::endl;
}
void test3(){
	
	std::string firstName = "Jonathan ";
	std::string lastName = "Yee";

	std::string fullName = firstName + lastName;

	//printName3(fullName); //this will report error if not commented, because this is a lvalue, and printName3 only accepts rvalue given its && signature.
	printName3(firstName + lastName);

}
int main(){
	//test1();
	//test2();
	test3();
}

