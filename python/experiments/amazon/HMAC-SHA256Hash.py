"""

This code is to help create a signature which facilitates the construction of a amazon REST request for commodities info

See here for details.
https://docs.aws.amazon.com/AWSECommerceService/latest/DG/rest-signature.html

note: the code below is NOT an effective way of retrieving Amazon data, a better way is to use a python wrapper which is already there.

"""
import hmac
import hashlib
import base64
def createSignature(amazon_search_str, version, date_time):

    prefix="""GET
webservices.amazon.com
/onca/xml
"""

    final_str = prefix + amazon_search_str + date_time + version
    secret = b'/Cf9/BQL/NLkCibehi6qjg9wTXf2QwMWhp9KVd7C' #my amazon secret key to public api

    dig = hmac.new(secret, msg=final_str.encode(), digestmod=hashlib.sha256).digest()

    sig = base64.b64encode(dig).decode()  # py3k-mode
    # TODO replace +, = with %2B, %3D
    sig = sig.replace("+", "%2B")
    sig = sig.replace("=", "%3D")
    return sig


from datetime import datetime
import pytz
from urllib.parse import quote
def constructDateString():
    # Timestamp=2018-10-25T03%3A03%3A00Z
    fmt = '%Y-%m-%dT%H:%M:%SZ'
    loc_dt = datetime.now(pytz.utc)
    return quote(loc_dt.strftime(fmt))


def constructRequest(amazon_search_str):
    prefix = "http://webservices.amazon.com/onca/xml?"
    version = "&Version=2013-08-01"
    date_time = "&Timestamp="+constructDateString()
    sig = "&Signature="+createSignature(amazon_search_str, version, date_time)
    request = prefix + amazon_search_str+version+date_time+ sig
    print(request)




def itemSearch():
    amazon_search_str     = "AWSAccessKeyId=AKIAJJNNR4PHY3WHM2WQ&"\
                "AssociateTag=fontsidea-20&"\
                "Keywords=games&"\
                "SearchIndex=Books&"\
                "Operation=ItemSearch&" \
                "ResponseGroup=Images%2CItemAttributes%2COffers%2CReviews&" \
                "Service=AWSECommerceService"
    return constructRequest(amazon_search_str)


def itemLookup():
    amazon_search_str   = "AWSAccessKeyId=AKIAJJNNR4PHY3WHM2WQ&"\
            "AssociateTag=fontsidea-20&"\
            "ItemId=0679722769&"\
            "Operation=ItemLookup&"\
            "ResponseGroup=Images%2CItemAttributes%2COffers%2CReviews&"\
            "Service=AWSECommerceService"
    return constructRequest(amazon_search_str)

def main():
    print(itemLookup())
    print(itemSearch())

if __name__ == '__main__':
    main()


