public class Stock extends Asset{

    // Encapsulation
    private final int shares;
    private final double averageCost;
    private final double currentPrice;

    // Constructor
    public Stock(String name, String symbol, int shares, double averageCost, double currentPrice){
        // 'super' calls the constructor of the parent class (Asset)
        super(name, symbol);
        this.shares = shares;
        this.averageCost = averageCost;
        this.currentPrice =currentPrice;
    }

    @Override
    // Polymorphism: Overriding the parent's method to provide specific logic
    public double calculateValue() {
        return shares * currentPrice;
    }

    public double calculateProfit(){
        return (currentPrice - averageCost) * shares;
    }
}
