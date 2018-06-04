
#this program shows the usage of module getopt

import sys, getopt
def main(argv):
    global label
    global filter
    global date
    global url
    global Log_level
    global email_to
    global email_from

    try:
        opts, args = getopt.getopt(argv, "ahl:f:t:d:e:w:p:",["log=","url=","emailto=", "emailfrom="])
        for opt, arg in opts:
            if opt == '-h':
                print ("usage of a program")
                sys.exit(2)
            elif opt == '-l':
                label = arg
            elif opt == '-f':
                filter = arg
            elif opt == '-d':
                date = arg
            elif opt == '--log':
                Log_level = arg
            elif opt == '--url':
                url = arg
            elif opt == '--emailto':
                email_to = arg
            elif opt == '--emailfrom':
                email_from = arg

    except getopt.GetoptError:
        print('Unexpected error: ', sys.exe_info()[0])

    print (label, filter, date, url, email_to, email_from)


if __name__ == "__main__":
    main(sys.argv[1:])



