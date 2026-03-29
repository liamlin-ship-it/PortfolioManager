public class Asset {

    private String name;
    private String symbol;

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

    public double calculateValue(){
        return 0.0;
    }
}
