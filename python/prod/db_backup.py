#this script back up mysql's databases on a periodical basis.
#consulted below posts: 
#https://stackoverflow.com/questions/17889465/python-subprocess-and-mysqldump
#create a service: https://stackoverflow.com/questions/16420092/how-to-make-python-script-run-as-service
#create a scheduler: https://stackoverflow.com/questions/373335/how-do-i-get-a-cron-like-scheduler-in-python
#code check in bitbucket
from subprocess import Popen, PIPE
import os
import time
import schedule
from datetime import date, timedelta

def job():

  BACKUP_DIR=os.getenv('HOME')+'/apps/backups/db'
  FILENAME=BACKUP_DIR+'/mysql_backup_'

  #first remove any backup files older than 7 days ago
  old_date = date.today() - timedelta(7)
  old_file = FILENAME+old_date.strftime('%Y%m%d')+".sql.gz"

  print('removing ... '+old_file)

  os.remove(old_file)

  args = ['mysqldump', '-u', 'root', '-pz0diac_c00L', '--all-databases']

  timestr = time.strftime("%Y%m%d")
  FILENAME = FILENAME + timestr + ".sql.gz"

  with open(FILENAME, 'wb', 0) as f:
    p1 = Popen(args, stdout=PIPE)
    p2 = Popen('gzip', stdin=p1.stdout, stdout=f)
    p1.stdout.close() # force write error (/SIGPIPE) if p2 dies
    p2.wait()
    p1.wait()

schedule.every().day.at("23:42").do(job)
while True:
  schedule.run_pending()
  time.sleep(1)
