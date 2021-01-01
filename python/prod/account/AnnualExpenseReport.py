from sys import argv
import os
import re
from datetime import datetime
import pandas as pd

"""
Utilities methods.
"""


def num(s):
    s = re.sub('[!"]', '', s)
    pat = re.compile(r'^[0-9]*[.,]?[0-9]*$')
    if pat.match(s):
        if ',' in s:
            s = s.replace(',', '')
        try:
            return int(s)
        except ValueError:
            return float(s)
    else:
        return 0


# Convert a tuple to a string
def unpack(tup):
    return ','.join(str(s) for s in tup).strip()


# keyword 'yield' is used to create a generator, it's handy in some case as the one below.
def matched(debit_tups, ref_tups):
    for item in debit_tups:
        found = False
        for ref in ref_tups:
            if ref[0] in item[1]:
                found = True
                yield item + tuple([ref[1]])
        if not found:
            yield item + tuple(['misc'])


def def_dest_folder(d=None):
    d = 'generated' if d is None else d
    if not os.path.exists(d):
        os.makedirs(d)
    return d


"""
Business methods
"""


def parse_transaction_file(trans_file):
    with open(trans_file) as f:
        contents = f.readlines()
        keyword = 'CHECK'

        to_file_name = def_dest_folder() + '\\CHASE_' + keyword + '.CSV'
        # create a file including the transactions paid in checks. This file needs further process to extract individual items.
        with open(to_file_name, 'w') as to:
            to.writelines(line.split(',')[1] + ',' + line.split(',')[2] + ',' + line.split(',')[3] + '\n'
                          for line in contents if line.split(',')[0] == keyword and float(line.split(',')[3]) != -1402.45)  # the number is monthly salary, it's excluded.

        keyword = 'CREDIT'
        to_file_name = def_dest_folder() + '\\CHASE_' + keyword + '.CSV'
        with open(to_file_name, 'w') as to:  # create a file including all the incomes
            to.writelines(line.strip() + '\n' for line in contents if line.split(',')[0] == keyword)

        keyword = 'DEBIT'
        to_file_name = def_dest_folder() + '\\CHASE_' + keyword + '.CSV'
        with open(to_file_name,
                  'w') as to:  # create a file including all the expense items paid via ACH and debit card. This file needs further process to extract individual items.
            to.writelines(line.strip() + '\n' for line in contents
                          if line.split(',')[0] == keyword
                          and 'CHASE CREDIT CRD AUTOPAYBUS' not in line.split(',')[
                              2]  # remove chase credit, analysis will be done seperatedly
                          and 'CITI AUTOPAY' not in line.split(',')[
                              2]  # remove citi credit, analysis will be done seperatedly
                          and 'GODDARD SCHO' not in line.split(',')[2]  # remove Goddard tuition
                          and 'INSUFFICIENT FUNDS FEE' not in line.split(',')[2]  # remove insuffecient funds fee
                          and 'Transfer from' not in line.split(',')[2]  # remove fund transfer
                          and 'Transfer to' not in line.split(',')[2]  # remove fund transfer
                          and 'PARK CITY' not in line.split(',')[2]
                          # and 'IRS' not in line.split(',')[2]
                          # and 'NEW JERSEY EFT' not in line.split(',')[2]
                          # and 'NJ GIT' not in line.split(',')[2]
                          # and 'NJ WEB PMT' not in line.split(',')[2]
                          and 'VANGUARD BUY' not in line.split(',')[2]
                          and 'ROBINHOOD        Funds' not in line.split(',')[2])

        return parse_debit_file(to_file_name)


def parse_debit_file(file_name):
    with open(file_name) as f:
        contents = f.readlines()
        debit_tups = [(date, desc, amt) for _, date, desc, amt, _, _, _, _ in (line.split(',') for line in contents)]

    return debit_tups


def parse_chase_credit_card_file(credit_card_file):
    with open(credit_card_file) as f:
        contents = f.readlines()
        creditcard_tups = [(date, desc, amt) for _, _, date, desc, _, _, amt, _ in
                           (line.split(',') for line in contents[1:]
                            if 'THANK' not in line and 'ALBROOK' not in line)]  # children tuition excluded.

    with open(def_dest_folder() + '\\' + 'CHASE_CREDITCARD_THANK.CSV', 'w') as f:
        f.writelines(line.strip() + '\n' for line in contents if 'THANK' in line)

    return creditcard_tups


def parse_citi_credit_card_file(file_name):
    with open(file_name) as f:
        s = ' '.join([line.rstrip('\n') for line in f])

    line_list = [line.split(',') for line in s.split("Cleared")]
    # for line in line_list:
    ret = []
    for line in line_list:
        if len(line) < 5:
            continue

        if str(line[3]) != '':
            line[3] = str(0 - num(line[3].strip()))
        if str(line[4]).strip() != '':
            line[3] = line[4]

        t = (line[1], line[2], line[3])
        ret.append(t)

    [print(t) for t in ret]

    return ret


def parse_chase_checks_file(file_name):
    with open(file_name) as f:
        contents = f.readlines()
        tups = [(date, desc, amt) for date, _, amt, desc in
                (line.strip().split(',') for line in contents if 'priority tax' not in line)]
        # print(tups)
    return tups


def merge_expense_items(debitcard_tups, chase_creditcard_tups, citi_creditcard_tups, chase_check_tups):
    with open("resources\\reference.txt") as ref:
        refs = ref.readlines()
        ref_tups = sorted([(desc, cat) for desc, cat in (line.strip().split(',') for line in refs)],
                          key=lambda tup: tup[1])

    debitcard_items = [unpack(i) for i in matched(debitcard_tups, ref_tups)]
    chase_creditcard_items = [unpack(i) for i in matched(chase_creditcard_tups, ref_tups)]
    citi_creditcard_items = [unpack(i) for i in matched(citi_creditcard_tups, ref_tups)]
    chase_check_items = [unpack(i) for i in matched(chase_check_tups, ref_tups)]

    all_expense_items = sorted(debitcard_items + chase_creditcard_items + citi_creditcard_items + chase_check_items,
                               key=lambda line: (line.split(',')[3], line.split(',')[1]))
    [print(i) for i in all_expense_items]

    return all_expense_items


# using pandas to do some aggregation work.
def aggregate_expense_items(all_expense_items, aggregate_output_file_name):
    data = {"cat": [line.split(',')[3] for line in all_expense_items],
            "amnt": [float(line.split(',')[2]) for line in all_expense_items]}
    df = pd.DataFrame(data)
    # print(df)
    grouped_total = df.groupby('cat').sum()
    print(grouped_total)
    grouped_total.to_csv(aggregate_output_file_name, sep=',', encoding='utf-8')


def write_expense_file(all_expense_items, file_name):
    with open(file_name, 'w') as to:
        to.writelines(i + '\n' for i in all_expense_items)


def main(argu):
    if argu is None or len(argu) == 0:
        trans_file = 'resources\\chase_biz_account_all_transactions_2019.CSV'
        chase_credit_file = 'resources\\chase_biz_credit_card_expense_2019.CSV'
        citi_credit_file = 'resources\\citi_credit_card_expense_2019.CSV'
        chase_checks_file = def_dest_folder() + '\\CHASE_CHECK_enriched.CSV'
    else:
        trans_file, chase_credit_file, citi_credit_file = argu

    debitcard_items = parse_transaction_file(trans_file)
    chase_creditcard_items = parse_chase_credit_card_file(chase_credit_file)
    citi_creditcard_items = parse_citi_credit_card_file(citi_credit_file)

    chase_check_items = parse_chase_checks_file(chase_checks_file)
    all_expense_items = merge_expense_items(debitcard_items, chase_creditcard_items, citi_creditcard_items,
                                            chase_check_items)
    output_filename = def_dest_folder() + '\\processed#1_' + datetime.now().strftime('%Y%m%d_%H%M%S') + '.CSV'
    write_expense_file(all_expense_items, output_filename)

    output_filename = def_dest_folder() + '\\processed#2_' + datetime.now().strftime('%Y%m%d_%H%M%S') + '.CSV'
    aggregate_expense_items(all_expense_items, output_filename)


if __name__ == '__main__':
    main(argv[1:])
