import java.util.*;

interface Tradeable {
}

class InsufficientSharesException extends Exception {
    public InsufficientSharesException(String message) {
        super(message);
    }
}

class StockNotFoundException extends Exception {
    public StockNotFoundException(String message) {
        super(message);
    }
}

class InsufficientMarketSharesException extends Exception {
    public InsufficientMarketSharesException(String message) {
        super(message);
    }
}

class Stock {
    private final String stockId;
    private final String stockName;
    private final double pricePerShare;
    private int availableShares;

    public Stock(String stockId, String stockName, double pricePerShare, int availableShares) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.pricePerShare = pricePerShare;
        this.availableShares = availableShares;
    }

    public synchronized void addShares(int quantity) {
        this.availableShares += quantity;
    }

    public synchronized void removeShares(int quantity) throws InsufficientMarketSharesException {
        if (availableShares < quantity) {
            throw new InsufficientMarketSharesException("Not enough shares in market for " + stockId);
        }
        this.availableShares -= quantity;
    }

    public String getStockId() {
        return stockId;
    }

    public String getStockName() {
        return stockName;
    }

}

class Market {
    private final Map<String, Stock> stockList = new HashMap<>();

    public synchronized void addStock(Stock stock) {
        stockList.put(stock.getStockId(), stock);
    }

    public synchronized Stock getStock(String stockId) throws StockNotFoundException {
        Stock stock = stockList.get(stockId);
        if (stock == null) {
            throw new StockNotFoundException("Stock ID " + stockId + " not found");
        }
        return stock;
    }

}

class Trader implements Tradeable, Runnable {
    private final String name;
    private final Market market;
    private final Map<String, Integer> portfolio = new HashMap<>();

    public Trader(int traderId, String name, Market market) {
        this.name = name;
        this.market = market;
    }

    private void buyStock(String stockId, int quantity) {
        try {
            Stock stock = market.getStock(stockId);
            synchronized (stock) {
                stock.removeShares(quantity);
            }
            portfolio.put(stockId, portfolio.getOrDefault(stockId, 0) + quantity);
            System.out.println(name + " bought " + quantity + " shares of " + stock.getStockName());
        } catch (Exception e) {
            System.err.println("Buy Error for " + name + ": " + e.getMessage());
        }
    }

    private void sellStock(String stockId) {
        try {
            if (!portfolio.containsKey(stockId) || portfolio.get(stockId) < 5) {
                throw new InsufficientSharesException("Not enough shares to sell for " + stockId);
            }
            Stock stock = market.getStock(stockId);
            synchronized (stock) {
                stock.addShares(5);
            }
            portfolio.put(stockId, portfolio.get(stockId) - 5);
            System.out.println(name + " sold " + 5 + " shares of " + stock.getStockName());
        } catch (Exception e) {
            System.err.println("Sell Error for " + name + ": " + e.getMessage());
        }
    }
