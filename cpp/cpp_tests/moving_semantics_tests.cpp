/*
	move semantics is strongly associated with lvalue/rvalue thus a new feature in C++ 11. Its main purpose is to introduce a new constructor that eliminate the necessicity of creating another copy of the input object when a function is called.

	resource: https://www.youtube.com/watch?v=ehMg6zvXuMY

	create: 09/13/21

 * */

#include <iostream>
#include <cstring>

class String{
public:
	String() = default;				

	String(const char* str){
		printf("created!\n");						
		m_size = strlen(str);
		m_data = new char[m_size];
		memcpy(m_data,str,m_size);		
	}					

	String(const String& other){
		printf("copied constructor!\n");					
		m_size = strlen(other.m_data);
		m_data = new char[other.m_size];
		memcpy(m_data,other.m_data,m_size);		
		
	}

	//move constructor
	String(String&& other) noexcept
	{
		printf("Moved constructor.\n");
		m_size = other.m_size;
		m_data = other.m_data;
		other.m_size = 0;
		other.m_data = nullptr;		
	}

	//move assignment operator
	String& operator=(String&& other) noexcept
	{
		if(this!=&other){
			printf("Move assignment operator.\n");
			delete[] m_data;
			m_size = other.m_size;
			m_data = other.m_data;
			other.m_size = 0;
			other.m_data = nullptr;		
		}
		return *this;		
	}

	~String(){
		printf("Destroyed!\n");					
		delete m_data;
	}


	void print(){
		for(uint32_t i = 0 ;i<m_size;i++){
			printf("%c",m_data[i]);
		}						
		printf("\n");
	}

private:
	uint32_t m_size;
	char* m_data;
		
};

//consumer class consuming String class.
class Entity{
public:
	Entity(const String& name): m_name(name){}

	//Entity(String&& name):m_name((String&&)name){}
	Entity(String&& name):m_name(std::move(name)){}

	void printName(){
		m_name.print();					
	}

private:
	String m_name;	
};

int main(){
	Entity entity(String("cherno"));				
	entity.printName();

	//testing std::move assignment operator
	String apple = "apple";
	String dest;
	std::cout<<"apple: ";
	apple.print();
	std::cout<<"dest: ";
	dest.print();

	dest = std::move(apple);//the move assinment operator is called.
	std::cout<<"apple: ";
	apple.print();
	std::cout<<"dest: ";
	dest.print();

	std::cin.get();
}
