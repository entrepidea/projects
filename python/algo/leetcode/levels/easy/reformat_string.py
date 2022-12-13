"""

"""
def reformat(s : str)->str:
	alph_num = list(filter(lambda x : x.isalpha(), s))
	digi_num = list(filter(lambda x : x.isdigit(),s))
	print(f'alph={alph_num}, digi_num={digi_num}')
	l = len(s)
	if l%2 == 0: # the length is an even number
		if len(alph_num) != l//2 or len(digi_num) != l//2:
			return ''
		if s[0].isalpha():
			print(f's[0]={s[0]}, {s[0].isalpha()}')
			j=0
			k=0
			rlt=[]
			for i in range(0, l//2+2,2):	
				print(f'i={i}')
				rlt.append(digi_num[j])
				j += 1
				rlt.append(alph_num[k])
				k += 1
			return ''.join(rlt)
		else:
			j=0
			k=0
			rlt=[]
			for i in range(0,l//2+2,2):	
				rlt.append(alph_num[j])
				j += 1
				rlt.append(digi_num[k])
				k += 1
			return ''.join(rlt)
	return ''


if __name__ == '__main__':
	s = 'a0b1c2'
	print(f's={s}, after reformating: ={reformat(s)}')
