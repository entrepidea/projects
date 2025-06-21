#Python cookbook 7.8
#comments: partial function is often used to tweak callback's signature

def output_result(result, log=None):
	if log is not None:
		log.debug('result is: {}'.format(result))

def add(a,b):
	return a + b

if __name__ == '__main__':
	from multiprocessing import Pool
	from functools import partial
	import logging

	logging.basicConfig(level=logging.DEBUG)
	log = logging.getLogger('test')
	
	p = Pool()
	p.apply_async(add,(3,4),callback=partial(output_result,log=log))
	p.close()
	p.join()

