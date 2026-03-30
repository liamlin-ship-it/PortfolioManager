public class Asset {

    private final String name;
    private final String symbol;

    public Asset(String name, String symbol){
        this.name = name;
        this.symbol =symbol;
    }

    public String getName(){
        return name;
    }

    public String getSymbol(){
        return symbol;
    }

    // Default method to calculate total value.
    // Designed to be overridden by child classes
    public double calculateValue(){
        return 0;
    }
}
