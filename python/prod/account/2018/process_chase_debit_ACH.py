def process(file_name):

    with open(file_name,'r') as f:
        content = f.readlines()

    #['DEBIT', '12/28/2018', '"THE GODDARD SCHO PPD                        PPD ID: 9049034669"', '-2520.90', 'ACH_DEBIT', ' ', '', '']
    credit_tuples = [("chase", "debit_ACH", "credit", date, desc, amt, "MISC") for _, date, desc, amt, _, _, _, _
        in (line.split(',') for line in content[1:]) if(float(amt.replace("'","").replace(',',''))>0)]

    debit_tuples = [("chase", "debit_ACH", "debit", date, desc, amt, "MISC") for _, date, desc, amt, _, _, _, _
        in (line.split(',') for line in content[1:]) if float(amt.replace("'","").replace(',',''))<0
                    and "GODDARD" not in desc
                    and "CHASE CREDIT CRD AUTOPAYBUS" not in desc
                    and "CITI AUTOPAY" not in desc
                    and "PARK CITY" not in desc]

    tuples = credit_tuples + debit_tuples

    #[print("%d===>[bank=%s, source=%s, type=%s, date=%s, desc=%s, amount=%s, catogory=%s]"%(tuples.index(t),t[0],t[1],t[2],t[3],t[4],t[5],t[6])) for t in tuples]
    return tuples
