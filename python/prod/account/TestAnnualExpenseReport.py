from AnnualExpenseReport import parse_transaction_file
from AnnualExpenseReport import parse_chase_credit_card_file
from AnnualExpenseReport import parse_citi_credit_card_file
from AnnualExpenseReport import merge_expense_items
from AnnualExpenseReport import parse_chase_checks_file
from AnnualExpenseReport import merge_expense_items_exclude_checks
from AnnualExpenseReport import write_expense_file
from AnnualExpenseReport import aggregate_expense_items
from datetime import datetime
from os import path
import pandas as pd

class TestAnnualExpenseReportCases:
    def test_parse_transaction_file(self):
        trans_file = "./transactions/chase_biz_account_all_transactions_2020.CSV"
        assert path.exists(trans_file)
        # parse_transaction_file(trans_file)
        l = parse_transaction_file(trans_file)
        assert l is not None and len(l) > 0
        [print(t) for t in l]

    def test_parse_chase_credit_card_file(self):
        credit_exp_file = "./transactions/chase_biz_credit_card_expense_2020.CSV"
        assert path.exists(credit_exp_file)
        chase_creditcard_items = parse_chase_credit_card_file(credit_exp_file)
        assert chase_creditcard_items is not None
        [print(t) for t in chase_creditcard_items]

    def test_parse_citi_credit_card_file(self):
        citi_credit_exp_file = "./transactions/citi_credit_card_expense_2020.CSV"
        assert path.exists(citi_credit_exp_file)
        citi_creditcard_items = parse_citi_credit_card_file(citi_credit_exp_file)
        assert citi_creditcard_items is not None and len(citi_creditcard_items) > 0
        [print(t) for t in citi_creditcard_items]

    def test_parse_chase_checks_file(self):
        chase_checks_file = './generated/CHASE_CHECK_enriched.CSV'
        chase_check_items = parse_chase_checks_file(chase_checks_file)
        assert chase_check_items is not None and len(chase_check_items) > 0
        [print(t) for t in chase_check_items]

    def test_merge_expense_items_exclude_checks(self):
        trans_file = "./transactions/chase_biz_account_all_transactions_2020.CSV"
        credit_exp_file = "./transactions/chase_biz_credit_card_expense_2020.CSV"
        citi_credit_exp_file = "./transactions/citi_credit_card_expense_2020.CSV"
        all_expense_items = merge_expense_items_exclude_checks(parse_transaction_file(trans_file),
                                                               parse_chase_credit_card_file(credit_exp_file),
                                                               parse_citi_credit_card_file(citi_credit_exp_file))

        # [print(i) for i in all_expense_items]

        output_filename = './generated/processed#1_' + datetime.now().strftime('%Y%m%d_%H%M%S') + '.CSV'
        write_expense_file(all_expense_items, output_filename)

        output_filename = './generated/processed#2_' + datetime.now().strftime('%Y%m%d_%H%M%S') + '.CSV'
        aggregate_expense_items(all_expense_items, output_filename)
