import threading, zipfile
class AsycZip(threading.Thread):
    def __init__(self,infile,outfile):
        threading.Thread.__init__(self)
        self.infile = infile
        self.outfile = outfile

    def run(self):
        f = zipfile.ZipFile(self.outfile, 'w', zipfile.ZIP_DEFLATED)
        f.write(self.infile)
        f.close()
        print('Finish background zip file: ', self.infile)
    
background = AsycZip('test.txt','text.zip')
background.start()

print('Main thread started...')
background.join()
print('Main thread ended')
