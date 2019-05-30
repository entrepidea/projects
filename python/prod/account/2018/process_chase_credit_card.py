import sys

def process(file_name):

    with open(file_name) as f:
        content = f.readlines()

    #test.
    #[print("%d: %s"%(content.index(line), line.strip())) for line in content[1:]]


    #convert a list of strings into a list of tuples
    tuples = [(date, desc, cat, type, amt) for _, _, date, desc, cat, type, amt, _ in (line.split(',')
        for line in content[1:])]

    debit_tuples = [("chase", "credit_card", "debit", t[0],t[1],t[4],t[2]) for t in tuples if str(t[3]) != "Payment" and str(t[3]) == "Sale"]
    credit_tuples = [("chase","credit_card", "credit", t[0], t[1], t[4], t[2]) for t in tuples if str(t[3]) != "Payment" and str(t[3]) == "Return"]

    tuples = debit_tuples+credit_tuples
    #test
    #[print("%d==> [card=%s, type=%s, date=%s, desc=%s, amount=%s, cat=%s]" % (
    #tuples.index(t), t[0], t[1], t[2], t[3], t[4], t[5])) for t in sorted(tuples, key=lambda tup:tup[5])]

    return tuples
