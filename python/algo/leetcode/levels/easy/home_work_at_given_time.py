"""
Given two integer arrays startTime and endTime and given an integer queryTime.

The ith student started doing their homework at the time startTime[i] and finished it at time endTime[i].

Return the number of students doing their homework at time queryTime. More formally, return the number of students where queryTime lays in the interval [startTime[i], endTime[i]] inclusive.
https://leetcode.com/problems/number-of-students-doing-homework-at-a-given-time/

12/06/22

"""
def home_work(st,et,qt):
	l = list(zip(st,et))
	#print(l)
	return len(list(filter(lambda x : x[0]<=qt<=x[1],l)))
		
if __name__ == '__main__':
	start_time = [1,2,3]
	end_time = [3,2,7]
	query_time = 4
	print(home_work(start_time, end_time, query_time))
