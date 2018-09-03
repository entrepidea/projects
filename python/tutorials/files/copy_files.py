"""
copy files from one given directory to another

"""

import sys
import shutil
from os import walk

def main(argv):
    if(len(argv)!=2):
        print('usage: copy_files <src_dir> <dest_dir> ')
        sys.exit(1)

    src, dst = argv
    for root, dirnames, filenames in walk(src):
        #print(dirnames)
        for file_name in filenames:
            f = root + '/' + file_name
            shutil.copy2(f, dst)

if __name__ == '__main__':
    main(sys.argv[1:])