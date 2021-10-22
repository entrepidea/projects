/*
 *
 * 
 if you want to have a deep copy (as opposed to copying only the reference, a.k.a shallow copy
 best way is to implement a copy constructor

 source: https://www.youtube.com/watch?v=BvR1Pgzzr38&list=WL&index=3&t=630s

	date: 10/07/21

 * */
#include <string>
#include <cstring>
#include <iostream>

class String{
	private:
		int m_size;
		char* m_buffer;
	public:
		String(const char* str){
			m_size = strlen(str);
			m_buffer = new char[m_size];
			memcpy(m_buffer,str,m_size);
		}		
			
		String(const String& other)
		:m_size(other.m_size)
		{
			std::cout<<"copy constructor. "<<std::endl;						
			m_buffer = new char[m_size + 1];						
			memcpy(m_buffer, other.m_buffer, m_size+1);						
		}

	friend std::ostream& operator<<(std::ostream& stream, const String& str);

	~String(){
		delete[] m_buffer;
	}

	char& operator[](unsigned int index){
		return m_buffer[index];
	}

};

std::ostream& operator<<(std::ostream& stream, const String& str){
	stream<<str.m_buffer;
	return stream;
};

void printString(const String& str){
				std::cout<<str<<std::endl;
};

int main(){
	String str = "john";	
	String  sec = str;
	std::cout<<str<<std::endl;
	std::cout<<sec<<std::endl;

	printString(str);
	printString(sec);

	std::cin.get();

}
