def process(file_name):

    with open(file_name, 'r') as f:
        content = f.readlines()


    credit_tuples = [("chase","debit_card","credit",date, desc, amt,"MISC") for _, date, desc, amt, _, _, _, _ in
                     (line.split(',') for line in content[1:]) if float(amt)>0]

    debit_tuples = [("chase", "debit_card", "debit", date, desc, amt,"MISC") for _, date, desc, amt, _, _, _, _ in
                     (line.split(',') for line in content[1:]) if float(amt) < 0]

    tuples = credit_tuples+debit_tuples

    #[print("%d===> %s, %s, %s, %s, %.2f, %s"%(tuples.index(t), t[0],t[1],t[2],t[3],float(t[4]), t[5])) for t in tuples]

    return tuples

