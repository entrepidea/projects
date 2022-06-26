#python cookbook 7.10

def apply_async(func, arg, *, callback):
	res = func(*arg)
	callback(res)

def add(x,y):
	return x + y

def print_result(r):
	print('result=', r)

#use bound-method to carry extra state - in this case, the sequence.
class ResultHandler:
	def __init__(self):
		self.sequence = 0
	def handle_result(self, result):
		self.sequence +=1
		print('[{}]: the result is: {}'.format(self.sequence, result))

#use closure for the same purpose
def make_handler():
	sequence = 0
	def handler(result):
		nonlocal sequence
		sequence += 1
		print('[{}]: Got {}'.format(sequence, result))
	return handler

#use coroutine
def make_handler2():
	sequence = 0
	while True:
		result = yield
		sequence += 1
		print('[{}]: Got {}'.format(sequence, result))
		
if __name__ == '__main__':
	apply_async(add,(2,3),callback=print_result)
	apply_async(add,('hello, ','world!'),callback=print_result)

	#test bound-method
	print('use bound method to carry extra state/information')
	r = ResultHandler()
	apply_async(add,(2,3),callback=r.handle_result)
	apply_async(add,('hello, ','world!'),callback=r.handle_result)

	#test closure
	print('use closure to carry extra state')
	h = make_handler()
	apply_async(add, (2,3), callback=h)
	apply_async(add, ('hello, ','world!'), callback=h)

	#test coroutine
	print('use coroutine to carry extra state')
	h = make_handler2()
	next(h)
	apply_async(add, (2,3), callback=h.send)
	apply_async(add, ('hello, ','world!'), callback=h.send)
	
