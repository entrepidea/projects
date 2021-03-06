"""

https://github.com/Jamonek/Robinhood

"""



from Robinhood import Robinhood

#Setup
my_trader = Robinhood()
#login
my_trader.login(username="jonathan.eahy.yee@gmail.com", password="aggress10n")

#Get stock information
    #Note: Sometimes more than one instrument may be returned for a given stock symbol
stock_instrument = my_trader.instruments("GEVO")[0]

#Get a stock's quote
#my_trader.print_quote("AAPL")

#Prompt for a symbol
#my_trader.print_quote()

#Print multiple symbols
#my_trader.print_quotes(stocks=["BBRY", "FB", "MSFT"])

#View all data for a given stock ie. Ask price and size, bid price and size, previous close, adjusted previous close, etc.
quote_info = my_trader.quote_data("BABA")
print(quote_info)

#Place a buy order (uses market bid price)
#buy_order = my_trader.place_buy_order(stock_instrument, 1)

#Place a sell order
#sell_order = my_trader.place_sell_order(stock_instrument, 1)