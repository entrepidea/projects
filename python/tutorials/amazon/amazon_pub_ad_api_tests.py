"""
Python-amazon-simple-product-api
this is a wrapper api of the amazon public advertise api
it's based on another wrapper bottlenose
bottlenose only return XML response
this one simplifies response
there are documents of this api, for example:
https://media.readthedocs.org/pdf/python-amazon-simple-product-api/latest/python-amazon-simple-product-api.pdf

"""

from flask import json, jsonify
from objdict import ObjDict

from amazon.api import AmazonAPI

AMAZON_ACCESS_KEY="AKIAIFAB5GCOBKQQDXIA"
AMAZON_SECRET_KEY="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"
AMAZON_ASSOC_TAG="fontsidea-20"

amazon = AmazonAPI(AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY, AMAZON_ASSOC_TAG)


def item_search_test():
    products = amazon.search(Keywords='Deals', SearchIndex='Electronics')
    l = list()
    for i, product in enumerate(products):
        data = ObjDict()
        data.title = product.title
        data.small_image_url = product.small_image_url
        data.detail_page_url = product.detail_page_url
        data.asin = product.asin
        data.price = "{}: {}".format(product.price_and_currency[1], product.price_and_currency[0])
        #price(data, product.asin)
        l.append(data)
    dic = {}
    dic["poilist"] = l
    print(json.dumps(dic))

def lookup_test():
    product = amazon.lookup(ItemId="B00LV4WAYS")
    print(product.price_and_currency)
    print(product.get_attribute("Manufacturer"))
    print(product.get_attribute("ProductGroup"))
    print(product.get_attribute("Title"))
    print(product.list_price)



def main():
    #lookup_test()
    item_search_test()

if __name__ == "__main__":
    main()


