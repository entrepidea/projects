import random

"""
    
    Bubble Sort Implementation
    Aaron
    07/23/2025

"""

def create_random_list(size, lower_bound, upper_bound):
    return [random.randint(lower_bound, upper_bound) for _ in range(size)]

def sort(numbers):
    #iterated=numbers
    numbersIterated=len(numbers)-1
    while numbersIterated>0:
        for i in range(0,numbersIterated):
            if numbers[i]>numbers[i+1]:
                numbers[i], numbers[i+1] = numbers[i+1], numbers[i]            
        numbersIterated-=1

if __name__ == "__main__":  
    #numbers = create_random_list(9, 1, 30)
    numbers = [30, 14, 21, 20, 23, 12, 29, 28, 7]
    print(f"Original:\n{numbers}")
    sort(numbers)
    print(f"Sorted:\n{numbers}")
