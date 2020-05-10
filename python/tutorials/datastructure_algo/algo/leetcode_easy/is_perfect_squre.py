def is_perfect(num):
	if num==0 or num ==1:
		return True
	i=1
	j=num
	count = 0
	while i<j:
		mid = i + (int)((j-i)/2)
		#print('mid={},num/mid={}'.format(mid,(int)(num/mid)))
		if mid == (int)(num/mid):
			if num%mid ==0:
				count=1
			break
		else:
			if mid > num/mid:
				j = mid-1
			else:
				i = mid + 1
		
	return count == 1

print(is_perfect(16))
print(is_perfect(19))
print(is_perfect(169))
print(is_perfect(9801))
