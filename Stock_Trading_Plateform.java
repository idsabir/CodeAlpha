import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Stock_Trading_Plateform {
    private String symbol;
    private double price;
    public Stock_Trading_Plateform(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return symbol + " - $" + price;
    }
}
 class Portfolio {
    private Map<String, Integer> holdings;

    public Portfolio() {
        holdings = new HashMap<>();
    }

    public void addStock(String symbol, int quantity) {
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
    }

    public void removeStock(String symbol, int quantity) {
        if (holdings.containsKey(symbol)) {
            int currentQuantity = holdings.get(symbol);
            if (currentQuantity > quantity) {
                holdings.put(symbol, currentQuantity - quantity);
            } else {
                holdings.remove(symbol);
            }
        }
    }

    public int getQuantity(String symbol) {
        return holdings.getOrDefault(symbol, 0);
    }

    public Map<String, Integer> getHoldings() {
        return holdings;
    }
}


class Market {
    private Map<String, Stock_Trading_Plateform> stocks;

    public Market() {
        stocks = new HashMap<>();
    }

    public void addStock(Stock_Trading_Plateform stock) {
        stocks.put(stock.getSymbol(), stock);
    }

    public Stock_Trading_Plateform getStock(String symbol) {
        return stocks.get(symbol);
    }

    public void updateStockPrice(String symbol, double newPrice) {
        Stock_Trading_Plateform stock = stocks.get(symbol);
        if (stock != null) {
            stock.setPrice(newPrice);
        }
    }
}
 class Trading {
    private Market market;
    private Portfolio portfolio;

    public Trading(Market market, Portfolio portfolio) {
        this.market = market;
        this.portfolio = portfolio;
    }

    public void buyStock(String symbol, int quantity) {
        Stock_Trading_Plateform stock = market.getStock(symbol);
        if (stock != null) {
            portfolio.addStock(symbol, quantity);
            System.out.println("Bought " + quantity + " of " + symbol + " at $" + stock.getPrice());
        } else {
            System.out.println("Stock not found!");
        }
    }

    public void sellStock(String symbol, int quantity) {
        Stock_Trading_Plateform stock = market.getStock(symbol);
        if (stock != null) {
            int currentQuantity = portfolio.getQuantity(symbol);
            if (currentQuantity >= quantity) {
                portfolio.removeStock(symbol, quantity);
                System.out.println("Sold " + quantity + " of " + symbol + " at $" + stock.getPrice());
            } else {
                System.out.println("Not enough stock to sell!");
            }
        } else {
            System.out.println("Stock not found!");
        }
    }
}


class Main {
    public static void main(String[] args) {
        Market market = new Market();
        Portfolio portfolio = new Portfolio();
        Trading trading = new Trading(market, portfolio);
        Scanner scanner = new Scanner(System.in);


        market.addStock(new Stock_Trading_Plateform("Apple", 150.00));
        market.addStock(new Stock_Trading_Plateform("Safari Car", 58000.00));
        market.addStock(new Stock_Trading_Plateform("Laptop", 3400.00));

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Buy Stock");
            System.out.println("2. Sell Stock");
            System.out.println("3. View Portfolio");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int buyQuantity = scanner.nextInt();
                    scanner.nextLine();
                    trading.buyStock(buySymbol, buyQuantity);
                    break;
                case 2:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int sellQuantity = scanner.nextInt();
                    scanner.nextLine();
                    trading.sellStock(sellSymbol, sellQuantity);
                    break;
                case 3:
                    System.out.println("Portfolio:");
                    for (Map.Entry<String, Integer> entry : portfolio.getHoldings().entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
