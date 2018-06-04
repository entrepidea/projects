package console;

/*
*
* This is a Bloomberg interview question, courtesy of Victor Tan.
* See the details here: https://bintanvictor.wordpress.com/2017/12/16/bbg-eq-filters-in-a-screen/
* TODO: not finished
* */
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

class Stock{

    public String getTick() {
        return tick;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    private String tick;
    private String type;
    private int value;

    public Stock(String tick, String type, int val){
        this.tick = tick;
        this.type = type;
        this.value = val;
    }

    //TODO more...
}

class Filter {
    private String type;
    private int min;
    private int max;
}

public class StockScreener {



    private static List<Predicate<Stock>> createPredicates(List<Filter> filters){
        //TODO
        return null;
    }

    private static List<Stock> applyFilters(List<Stock> stocks, Predicate<Stock> predicate){
        List<Stock> ret = new ArrayList<>();
        for(Stock s : stocks){
            if(predicate.test(s)){
                ret.add(s);
            }
        }
        return ret;
    }


    public static void main(String[] args){
        //TODO create a sample list of stocks;
        List<Stock> stocks = new ArrayList<>();
        Stock s = new Stock("GOOG", "price", 300);
        stocks.add(s);
        s = new Stock("IBM", "price", 99);
        stocks.add(s);
        s = new Stock("MSFT", "volumn", 300000);
        stocks.add(s);

        s = new Stock("FB", "price", 500);
        stocks.add(s);


        //a testing predicate
        Predicate<Stock> pricePred = sp -> sp.getType().equals("price") && sp.getValue() > 100;
        List<Stock> filtered = applyFilters(stocks, pricePred);
        for(Stock stock : filtered){
            System.out.println(stock.getTick());
        }
    }
}
