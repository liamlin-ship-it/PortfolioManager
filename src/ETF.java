public class ETF extends Asset {

    private final int shares;
    private final double averageCost;
    private final double currentPrice;
    // Specific attribute for ETF: Management fee rate
    private final double managementFeeRate;

    // Constructor
    public ETF(String name, String symbol, int shares, double averageCost, double currentPrice, double managementFeeRate){
        super(name, symbol);
        this.shares = shares;
        this.averageCost = averageCost;
        this.currentPrice = currentPrice;
        this.managementFeeRate = managementFeeRate;
    }

    @Override
    // Polymorphism: ETF has a slightly different calculation logic due to fees
    public double calculateValue() {
        double rawValue = shares * currentPrice;
        return rawValue - (rawValue * managementFeeRate);
    }

    public double calculateProfit(){
        double totalCost = shares * averageCost;
        return calculateValue() - totalCost;
    }
}
