"""
bottlenose is a wrapper of amazon public advertise api, but the return is only XML which requires further parsing.
https://github.com/lionheart/bottlenose
"""
import bottlenose

AMAZON_ACCESS_KEY="AKIAJJNNR4PHY3WHM2WQ"
AMAZON_SECRET_KEY="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"
AMAZON_ASSOC_TAG="fontsidea-20"

def bn_test1():
    amazon = bottlenose.Amazon(AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY, AMAZON_ASSOC_TAG)
    response = amazon.ItemSearch(Keywords="Kindle 3G", SearchIndex="All")
    print(response)
    response = amazon.ItemLookup(ItemId="B00LV4WAYS")
    print(response)

def main():
    bn_test1()

if __name__ == "__main__":
    main()