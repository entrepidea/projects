"""
This task is to look for certain file types under a folder recursively and rename the extensions to another type

"""

import os
from pathlib import Path

def rename(file, ext):
    base = os.path.splitext(file)[0]
    os.rename(file, base+ext)


def find_file(path,ext):
    matches = []
    for root, dirnames, filenames in os.walk(path):
        for filename in filenames:
            if filename.endswith(ext):
                matches.append(os.path.join(root,filename))

    return matches

def main():
    #matches = find_file('/media/sf_projects/temp', 'bak')
    matches = find_file('/media/sf_projects/github/java/java_tests/src/test/java/com/entrepidea/algo/tests', 'bak')
    for file in matches:
        rename(file, '.java')


main()