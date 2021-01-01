from sys import argv
import process_citi_credit_card
import process_chase_credit_card
import process_chase_debit_card
import process_chase_debit_ACH
import process_chase_debit_checks
import process_chase_credit_ACH

#this is only for the chase debit account 1833, this account will not be used going forward.
def temp_sln(file_name):
    with open(file_name) as f:
        contents = f.readlines()

    return [(t[0],t[1],t[2],t[3],'"'+t[4]+'"',float(t[5]),t[6]) for t in (line.split(',') for line in (l.strip() for l in contents[1:]))]


def update_item_catogories(orig_item_list):

    with open('data/reference.txt', 'r') as f:
        references = f.readlines()

    #Create a dictionary
    dic = {key:val for key, val in (line.split(',') for line in references)}
    updated_item_list = []
    for t in orig_item_list:
        desc = t[4]
        found = False
        for key in dic.keys():
            if key in desc.upper():
                #tuple is immutable. To update value in a tuple, you can use trick like below
                #https://stackoverflow.com/questions/11458239/python-changing-value-in-a-tuple
                lst = list(t)
                lst[6] = dic[key].strip()
                tup = tuple(lst)
                updated_item_list.append(tup)
                found = True
                continue

        if not found:
            lst = list(t)
            lst[6] = 'MISC'
            tup = tuple(lst)
            updated_item_list.append(tup)

    return updated_item_list

def write_expenses_file(item_list, file_name):
    sorted_list = sorted(item_list,key=lambda tup: tup[6])
    with open(file_name, 'w') as f:
        total = 0
        amt = 0
        temp = sorted_list[0]
        cat = temp[6]
        for t in sorted_list:
            if temp[6] == t[6]:
                amt += float(t[5])
            else:
                f.write("%s,%s,%s,%s,%s,%s,%s\n" % ('', '', '', '', 'sub total', str(amt), cat))
                total+=amt
                temp = t
                amt =  float(t[5])
                cat = t[6]

            f.write("%s,%s,%s,%s,%s,%s,%s\n" % (t[0], t[1], t[2], t[3], t[4], t[5], t[6]))

        f.write("%s,%s,%s,%s,%s,%s,%s\n" % ('', '', '', '', 'sub total', str(amt), cat))
        total+=amt
        f.write("%s,%s,%s,%s,%s,%s,%s\n" % ('', '', '', '', 'Total', str(total), ''))


def show_misc_items(item_list):
    misc_list = [t for t in item_list if t[6] == "MISC"]
    #The below line shows how to sort a tuple list basing on its float type element.
    #Also see: https://www.geeksforgeeks.org/python-sort-tuple-float-element/
    sorted_misc_list = sorted(misc_list, key=lambda x:float(x [5]))
    [print("%d==> [bank=%s, source=%s, type=%s, date=%s, desc=%s, amount=%s, cat=%s]" % (
        sorted_misc_list.index(t), t[0], t[1], t[2], t[3], t[4], t[5], t[6])) for t in sorted_misc_list]


def show_all_items(item_list):
    sorted_list = sorted(item_list,key=lambda tup: tup[6])
    [print("%d==> [bank=%s, source=%s, type=%s, date=%s, desc=%s, amount=%s, cat=%s]" % (
        sorted_list.index(t), t[0], t[1], t[2], t[3], t[4], t[5], t[6])) for t in sorted_list]

def main(argv):
    citi_credit_card_file = argv[0]
    chase_credit_card_file = argv[1]
    chase_debit_card_file = argv[2]
    chase_debit_ACH_file = argv[3]
    chase_debit_checks_file = argv[4]


    citi_credit_card_list = process_citi_credit_card.process(citi_credit_card_file)
    chase__credit_card_list = process_chase_credit_card.process(chase_credit_card_file)
    chase_debit_card_list = process_chase_debit_card.process(chase_debit_card_file)
    chase_debit_ACH_list = process_chase_debit_ACH.process(chase_debit_ACH_file)
    chase_debit_checks_list = process_chase_debit_checks.process(chase_debit_checks_file)
    chase_debit_acnt_1833_list = temp_sln('data/chase_1833_acnt_2018_activity.csv')

    expenses = citi_credit_card_list + chase__credit_card_list + chase_debit_card_list + chase_debit_ACH_list + chase_debit_checks_list + chase_debit_acnt_1833_list
    mod_expenses = update_item_catogories(expenses)

    write_expenses_file(mod_expenses, 'processed.csv')

    show_misc_items(mod_expenses)
    #show_all_items(mod_expenses)



    income = process_chase_credit_ACH.process('data/chase_acnt_1833_ACH_credit_2018.csv')
    income += process_chase_credit_ACH.process('data/chase_acnt_5090_ACH_credit_2018.csv')

    print("income:===> $%.2f"%income)

if __name__ == '__main__':
    main(argv[1:])