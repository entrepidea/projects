
#https://www.tutorialspoint.com/python/python_date_time.htm
import time ;
ticks = time.time();
print "Number of ticks since Jan, 1, 1970, 12:00 am: ", ticks

localtime = time.localtime(time.time())

print "current time is: ", localtime

localtime = time.asctime(time.localtime(time.time()))

print "formatted local time: ", localtime

import calendar

cal = calendar.month(2008,1)
print "calendar for 2008, jan"
print cal

weekday = calendar.weekday(2017,04,01)

print "2017/04/01/ is a : ", weekday