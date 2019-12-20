from sys import argv

"""
Utilities methods.
"""
def unpack(tup):
    return ','.join(str(s) for s in tup).strip()

def matched(debit_tups, ref_tups):
    for item in debit_tups:
        found = False
        for ref in ref_tups:
            if ref[0] in item[1]:
                found = True
                yield item + tuple([ref[1]])
        if not found:
            yield item + tuple(['misc'])

"""
Business methods
"""
def parse_transaction_file(trans_file):
    with open(trans_file) as f:
        contents = f.readlines()
        keyword = 'CHECK'
        to_file_name = keyword+'.CSV'
        with open(to_file_name, 'w') as to:
            to.writelines(line.strip()+'\n' for line in contents if line.split(',')[0]==keyword)

        keyword = 'CREDIT'
        to_file_name = keyword+'.CSV'
        with open(to_file_name, 'w') as to:
            to.writelines(line.strip()+'\n' for line in contents if line.split(',')[0]==keyword)

        keyword = 'DEBIT'
        to_file_name = keyword+'.CSV'
        with open(to_file_name, 'w') as to:
            to.writelines(line.strip()+'\n' for line in contents
                          if line.split(',')[0]==keyword
                          and 'CHASE CREDIT CRD AUTOPAYBUS' not in line.split(',')[2] #remove chase credit, analysis will be done seperatedly
                          and 'CITI AUTOPAY' not in line.split(',')[2]) #remove citi credit, analysis will be done seperatedly

        return parse_debit_file('DEBIT.CSV')


def parse_debit_file(file_name):
    with open(file_name) as f:
        contents = f.readlines()
        debit_tups = sorted([(date, desc, amt) for _, date, desc, amt, _, _, _, _ in (line.split(',') for line in contents)],key=lambda tup:tup[1])

    return debit_tups



def parse_chase_credit_card_file(credit_card_file):
    with open(credit_card_file) as f:
        contents = f.readlines()
        creditcard_tups = sorted([(date, desc, amt) for _,_,date,desc,_,_,amt,_ in (line.split(',') for line in contents[1:])],key=lambda tup:tup[1])

    return creditcard_tups


def write_expense_file(debitcard_tups, chase_creditcard_tups, file_name):
    with open("reference.txt") as ref:
        refs = ref.readlines()
        ref_tups = sorted([(desc, cat) for desc, cat in (line.strip().split(',') for line in refs)],key=lambda tup:tup[1])

    debitcard_items = [unpack(i) for i in matched(debitcard_tups, ref_tups) ]
    chase_creditcard_items = [unpack(i) for i in matched(chase_creditcard_tups,ref_tups)]

    all_expense_items = debitcard_items + chase_creditcard_items
    [print(i) for i in all_expense_items]

    with open(file_name,'w') as to:
        to.writelines(i+'\n' for i in all_expense_items)


def main(argu):
    if argu is None or len(argu) == 0:
        trans_file = 'chase_biz_account_all_transactions_2019.CSV'
        credit_file = 'chase_biz_credit_card_expense_2019.CSV'
    else:
        trans_file, credit_file = argu

    debitcard_items = parse_transaction_file(trans_file)
    chase_credditcard_items = parse_chase_credit_card_file(credit_file)
    write_expense_file(debitcard_items,chase_credditcard_items, 'processed.CSV')

if __name__ == '__main__':
    main(argv[1:])