"""

The task is to search through all java files and replace the word "class Solution" with "class <the file name>"
(w/o extension, of course). Some javas are screwed up. This is to correct them.

"""
import os

def new_file(file, key_str):
    path = os.path.dirname(file)
    filename_w_ext = os.path.basename(file)
    filename, _ = os.path.splitext(filename_w_ext)

    with open(file, encoding="utf8") as f:
        with open(path+'/'+filename+'.bak', 'w') as out:
            for line in f:
                if key_str in line:
                    line = line.replace(key_str, filename)
                    out.write(line)
                else:
                    out.write(line)

    os.remove(file)




#find a list of files with the right extensions
def find_and_replace(path, ext, key_str):
    matches = []
    for root, dirnames, filenames in os.walk(path):
        for filename in filenames:
            if filename.endswith(ext):
                matches.append(os.path.join(root,filename))

    for file in matches:
        new_file(file, key_str)


def main():
    find_and_replace('/media/sf_projects/github/java/java_tests/src/test/java/com/entrepidea/algo/tests', '.java', 'Solution')


main()

