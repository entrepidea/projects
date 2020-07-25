
"""
Desc: I came across xml.dom.minidom package in a BNP Paribas project. Here are some tests to familirize myself to this lib
Reference: https://docs.python.org/3.6/library/xml.dom.minidom.html
Note:
    I can't have xml.dom.minidom installed and I posted a question on SO: https://stackoverflow.com/questions/61719391/pycharm-2020-1-doesnt-find-minidom-in-python-3-6-in-linux-mint
    someone point to this similar post: https://stackoverflow.com/questions/61208926/i-cant-import-xml-dom-minidom-in-pycharm-what-could-i-try
    which helped fix the issue.
Date: 05/10/20
"""
#TODO
# from xml.dom.minidom import parse, paring
# noinspection PyUnresolvedReferences
from xml.dom.minidom import Document
# noinspection PyUnresolvedReferences
from xml.dom.minidom import parse, parseString


def test_tag_name():
    dom3 = parseString('<myxml>Some data<empty/> some more data</myxml>')
    assert dom3.documentElement.tagName == "myxml"

def test_text_node():
    document = """\
    <slideshow>
    <title>Demo slideshow</title>
    <slide><title>Slide title</title>
    <point>This is a demo</point>
    <point>Of a program for processing slides</point>
    </slide>

    <slide><title>Another demo slide</title>
    <point>It is important</point>
    <point>To have more than</point>
    <point>one slide</point>
    </slide>
    </slideshow>
    """
    dom = parseString(document)
    title = dom.getElementsByTagName("title")[0]
    children = title.childNodes
    rc = []
    for child in children:
        if child.nodeType == child.TEXT_NODE:
            rc.append(child.data)

    assert len(rc) == 1 and rc[0] == "Demo slideshow"

    #handle point
    slides = dom.getElementsByTagName("slide")
    for slide in slides:
        rc = []
        points = slide.getElementsByTagName("point")
        for pt in points:
            for c in pt.childNodes:
                if c.nodeType == c.TEXT_NODE:
                    rc.append(c.data)
    assert len(rc) == 3 and rc[0] == "It is important" and rc[2] == "one slide"