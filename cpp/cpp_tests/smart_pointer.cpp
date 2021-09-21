
/*

smart point in c++ take care of the creation of destroy of a class, the new way as old school new/delete
among them are unique_ptr and shared_ptr (and others).

resource: https://www.youtube.com/watch?v=UOB7-B2MfwA
date: 09/21/21
 
*/

#include <memory>
#include <iostream>

class Entity{
	public:
		Entity(){
			std::cout<<"created!"<<std::endl;
		}

		~Entity(){
			std::cout<<"Destroyed!"<<std::endl;
		}

		void print(){std::cout<<"print entity"<<std::endl;}
};

int main(){
	//test unique_ptr
	{
		std::cout<<"testing unique ptr..."<<std::endl;
		std::unique_ptr<Entity> ep = std::make_unique<Entity>();
		ep->print();
		std::cin.get();
	}

	//test shared pointer
	{
		std::shared_ptr<Entity> sp2;
		{
			std::shared_ptr<Entity> sp = std::make_shared<Entity>();
			sp2 = sp; //the pointer sp is shared with sp2, reference count is 2
			sp->print();
		}
		//sp is out of scope, reference count descreased to 1
		sp2->print();
	}	

	//only at this point, sp2 is out  of scope too, the reference count becomes 0
	std::cin.get();
	
	
}
