#this is a script for beowulf project at Barclays.
#requirement:
#create a text file in which each line is a date in format mm/dd/yy
#the date start from 06/02/2018 and ends 12/31/2019
#for each month, start from the 2nd week day of that month and only the first 10 weekdays count.
#holiday not excluded. weekends excluded.
#Note: this code doesn't really work but I decided not to waste more time on it.
import datetime;
from dateutil.relativedelta import relativedelta
from math import ceil

def week_of_month(dt):
    """ Returns the week of the month for the specified date.
    """

    first_day = date.replace(day=1)

    day_of_month = date.day

    if (first_day.weekday() == 6):
        adjusted_dom = (1 + first_day.weekday()) / 7
    else:
        adjusted_dom = day_of_month + first_day.weekday()

    return int(ceil(adjusted_dom / 7.0))


count = 0;
start_of_month = datetime.datetime(2018,6,2);
date = start_of_month;
with open("cal.txt", 'w') as file:
    while date.date() < datetime.datetime(2020,1,2).date():
        if date.isoweekday() in range(1,6):
            if week_of_month(date) == 1:
                if date.isoweekday() == 1: # if date falls into first week
                    date += datetime.timedelta(days=1);
                    continue;
            s = date.strftime("%m/%d/%Y");
            print(s);
            file.write(s+'\n');
            count += 1;
            if count == 10:
                count = 0;
                #date increment by month.
                start_of_month += relativedelta(months=1)
                date = start_of_month;
                continue;

        date += datetime.timedelta(days=1);