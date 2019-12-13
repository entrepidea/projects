package com.entrepidea.java.io;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by jonat on 5/29/2017.
 *
 *
    Interview question from BofA

 There is a folder containing stock files, each line of which includes info as below, and delimiter is "\t".
 - date in the format "YYYY-MM-DD"
 - counter party
 - indicator (BUY/SELL), indicating a BofA's counterparty BUY from or SELL to BofA
 - symbol
 - quantity
 - price

 There are 100 such files and each file contains 10,000 lines.

 In the same folder there is also a short file, <strong>marks.txt,/strong> in which there are two columns in each line showing the symbol and market price, for example:
 <strong>AAPL 350.00</strong>

 Task: reading the files and figure out the long positions that BofA holds on the stocks, and list the top 20 in the descendant order with regard to value (quantity*price) and their respective market values. For instance, BofA might buy/sell AAPL, if the net is long, you have to figure it out and count it as required.


 */

public class StockCaculator {

    static class Stock{
        private Date cob;
        private String counterParty;
        private boolean indicator;
        private String symbol;
        private int quantity;
        private double price;
        private double notional;

        public double getNotional(){
            return notional;
        }

        @Override
        public boolean equals(Object theOther){
            return symbol.equals(((Stock)theOther).symbol);
        }

        public void readLine(String line){

            String[] components = line.split("\t");
            //...
            symbol = components[3];
            quantity = new Integer(components[4]);
            price = new Double(components[5]);
            notional = quantity*price;
            boolean b = components[2].equals("SELL");
            if(b){
                notional = -notional;
            }
        };


    }

    static void readFile(String fileName, Map<Stock, Double> stockMap) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = null;
        while((line = br.readLine())!=null){
            Stock stock = new Stock();
            stock.readLine(line);
            if(stockMap.containsKey(stock)){
                double notional = stock.getNotional();
                double total = stockMap.get(stock);
                total += notional;
                stockMap.put(stock, total);
            }
            else{
                stockMap.put(stock, stock.getNotional());
            }
        }
    }

    public static void main(String ... args){

    }
}
