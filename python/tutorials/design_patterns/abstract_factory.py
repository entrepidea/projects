'''
AbstractFactory design pattern.
source: https://refactoring.guru/design-patterns/abstract-factory/python/example
date: 10/01/21
note: this example looks quite silly. The pseudocode (https://refactoring.guru/design-patterns/abstract-factory) 
feels like more connnected. 
'''
#note: the future lib is supported only since Python 3.7 or later
from __future__ import annotations
from abc import ABC, abstractmethod

class AbstractFactory(ABC):
    @abstractmethod
    def create_product_a(self) -> AbstractProductA:
        pass

    @abstractmethod
    def create_product_b(self) -> AbstractProductB:
        pass

class ConcreteFactory1(AbstractFactory):
    def create_product_a(self) -> AbstractProductA:
        return ConcreteProductA1()
    def create_product_b(self) -> AbstractProductB:
        return ConcreteProductB1()

class AbstractProductA(ABC):
    @abstractmethod
    def useful_function_a(self) -> str:
        pass

class ConcreteProductA1(AbstractProductA):
    def useful_function_a(self) -> str:
        return "The result of function A1."

class ConcreteProductA2(AbstractProductA):
    def useful_function_a(self) -> str:
        return "The result of function A2."

class AbstractProductB(ABC):
    @abstractmethod
    def useful_function_b(self) -> str:
        pass

class ConcreteProductB1(AbstractProductB):
    def useful_function_b(self) -> str:
        return "The result of function B1."

def client_code(factory: AbstractFactory) -> None:
    product_a = factory.create_product_a()
    product_b = factory.create_product_b()
    print(f"{product_a.useful_function_a()}")
    print(f"{product_b.useful_function_b()}")

if __name__ == '__main__':
    """
    The client code can work with any concrete factory class.
    """
    print("Client: Testing client code with the first factory type:")
    client_code(ConcreteFactory1())

    print("\n")
