from prod.account.AnnualExpenseReport import parse_transaction_file
from os import path


class TestAnnualExpenseReportCases:
    def test_parse_transaction_file(self):
        trans_file = "./transactions/chase_biz_account_all_transactions_2020.CSV"
        assert path.exists(trans_file)
        # parse_transaction_file(trans_file)
        l = parse_transaction_file(trans_file)
        assert l is not None and len(l) > 0

    def test_parse_chase_credit_card_file(self):
        credit_exp_file = "./transactions/chase_biz_credit_card_expense_2020.CSV"
        assert path.exists(credit_exp_file)
