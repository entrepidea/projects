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

	friend std::ostream& operator<<(std::ostream& stream, const String& str);

	~String(){
		delete[] m_buffer;
	}
};

std::ostream& operator<<(std::ostream& stream, const String& str){
	stream<<str.m_buffer;
	return stream;
};

int main(){
	String str = "john";	
	String  sec = str;
	std::cout<<str<<std::endl;
	std::cout<<sec<<std::endl;
	std::cin.get();
}
