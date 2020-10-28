import sys
from getopt import getopt
from getopt import GetoptError

from os import walk
from os import path
from os import makedirs
from os import rename

from PIL import Image
from PIL import UnidentifiedImageError
from PIL.ExifTags import TAGS

"""

This small utility is to extract the meta info from a given picture (use --input=<file)
or
read in all images from a given directory and reorganize them basing on the years of creation (use --dir=<dir>)

usage: python ImageOrganizer -i <file>
       python ImageOrganizer -d <dir>
       
date: 10/28/2020

Note: The initial goal was to help wife to organize her photo collections in iMac. 
The package Pillow is employed for image analysis. However it seemed problematic in some late tests. We might want to use
other package for similar purpose to test more.

"""


# https://www.thepythoncode.com/article/extracting-image-metadata-in-python
def parse_img(img_name):
    # read the image data using PIL
    image = Image.open(img_name)
    # extract EXIF data
    exifdata = image.getexif()
    # iterating over all EXIF data fields
    for tag_id in exifdata:
        # get the tag name, instead of human unreadable tag id
        tag = TAGS.get(tag_id, tag_id)
        data = exifdata.get(tag_id)
        # decode bytes
        if isinstance(data, bytes):
            data = data.decode()
            print(f"{tag:25}: {data}")


def extract_img_year(img_path):
    date_str = Image.open(img_path).getexif()[36867]
    if not date_str:
        return None
    else:
        print(date_str)
        return date_str[0:4]


def check_create_folder(folder):
    if not path.exists(folder):
        makedirs(folder)


def count_files(valid_path):
    x = 0
    for root, dirs, files in walk(valid_path):
        for f in files:
            x = x + 1
    print('There are,', x, 'files in this directory.')
    return x


def move_files(valid_path):
    x = 0
    for root, dirs, files in walk(valid_path):
        for f in files:
            file_path = path.join(root, f)
            try:
                create_year = extract_img_year(file_path)
                if create_year is None:
                    print("Year can't be determined for the file: " + file_path)
                    continue

                check_create_folder(create_year)
                dest_path = path.join(create_year, f)
                rename(file_path, dest_path)
                x = x + 1

            except UnidentifiedImageError:
                print(str(UnidentifiedImageError) + ": " + file_path)
                continue

    print('There are,', x, 'files in this directory.')
    return x


def usage():
    print('usage: imageOrganizer -i <file>\n')
    print('usage: imageOrganizer -d <dir>\n')


def main(argv):
    try:
        opts, remainder = getopt(argv, "hi:d:", ["help", "input=", "dir="])
    except GetoptError as err:
        print(err)
        usage()
        sys.exit(2)

    run_single_file = False
    img_path = None
    for opt, arg in opts:
        if opt in ("-i", "--input"):
            run_single_file = True
            img_path = arg
        elif opt in ("-d", "--dir"):
            run_single_file = False
            img_path = arg

    # test parsing image
    # img_path = 'pictures/1980/IMG_1914.jpg'
    if run_single_file:
        parse_img(img_path)
    else:
        move_files(img_path)

    # if extract_img_year(img_path) is None:
    #    print("Year can't be determined for the file: " + img_path)

    # count_files(argv[0])


if __name__ == '__main__':
    main(sys.argv[1:])
