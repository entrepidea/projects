
#this read a directory and search for files of certain pattern recursively.
#refer to:
#https://stackoverflow.com/questions/18394147/recursive-sub-folder-search-and-return-files-in-a-list-python
#note: this script failed to run. No solution yet

from sys import argv
import glob

def words_search(words, file):
    with open(file, encoding="utf8") as f:
        found=0
        for line in f:
            for word in words:
                if word in line:
                    found = found+1
                    if found == len(words):
                        print(f.name)
                        return f.name
                    continue



def main():
    if len(argv) == 2:
        script, PATH = argv
    else:
        PATH = 'c:\\users\\jonat\\opt'

    words = ["Solution"]
    files = [file for file in glob.glob(PATH + '/**/*.java', recursive=True)]
    found_files = []
    for file in files:
        words_search(words, file)

    for f in found_files:
        print(f)

main()
