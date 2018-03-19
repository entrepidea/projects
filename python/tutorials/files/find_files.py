import os
from shutil import copy

# list certain files under a directory recursively.
# http://stackoverflow.com/questions/22714013/recursively-searching-for-files-with-specific-extensions-in-a-directory

def find_ext(folder):
    matches = []
    for root, dirnames, filenames in os.walk(folder):
        for filename in filenames:
            if filename.endswith('.jar'):
                matches.append(os.path.join(root,filename))
    return matches

matches = find_ext('C:\\Users\\jonat\\.m2\\repository')
print('Total jar files number is ' + len(matches).__str__())

#copy files to another directory
#if i use shutil it will complain permission denied.
#look at Jason's comment on this link:
#http://stackoverflow.com/questions/10575750/python-ioerror-errno-13-permission-denied
dst = 'C:\\Users\\jonat\\projects\\temp\\jars'
for file in matches:
    print(file)
    copy(file, dst)

