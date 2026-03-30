import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Asset> myPortfolio = new ArrayList<>();
        boolean keepRunning = true;

        System.out.println("Welcome to the Portfolio Manager");

        // Using a while loop to keep the program running until the user chooses to exit
        while (keepRunning){
            System.out.println("Main Menu");
            System.out.println("1. Add a new stock");
            System.out.println("2 .Add a new ETF");
            System.out.println("3. Calculate Total Value & Exit");
            System.out.println("Please enter your choice (1-3): ");

            int choice = scanner.nextInt();

            // We need this empty nextLine() to consume the leftover "Enter" key character
            // Otherwise, the next String input will be skipped
            scanner.nextLine();

            if (choice == 1){
                // Taking inputs for a Stock
                System.out.println("Enter Stock name: ");
                String name = scanner.nextLine();

                System.out.println("Enter Stock symbol: ");
                String symbol = scanner.nextLine();

                System.out.println("Enter number of shares: ");
                int shares = scanner.nextInt();

                System.out.println("Enter average cost: ");
                double cost = scanner.nextDouble();

                System.out.println("Enter current price: ");
                double price = scanner.nextDouble();

                Stock myStock = new Stock(name, symbol, shares, cost, price);
                myPortfolio.add(myStock);
                System.out.println("Stock added successfully!");
            } else if (choice == 2) {
                // Taking inputs for an ETF
                System.out.println("Enter ETF name: ");
                String name = scanner.nextLine();

                System.out.println("Enter ETF symbol: ");
                String symbol = scanner.nextLine();

                System.out.println("Enter number of shares: ");
                int shares = scanner.nextInt();

                System.out.println("Enter average cost: ");
                double cost = scanner.nextDouble();

                System.out.println("Enter current price: ");
                double price = scanner.nextDouble();

                // Convert user input from percentage to decimal format
                System.out.println("Enter management fee in %: ");
                double fee = scanner.nextDouble() / 100;

                ETF myETF = new ETF(name, symbol, shares, cost, price, fee);
                myPortfolio.add(myETF);
                System.out.println("ETF added successfully!");
            } else if (choice == 3) {
                keepRunning = false;
                System.out.println("Generating your portfolio report...");
            }else {
                System.out.println("Invalid input. Please enter 1, 2, or 3.");
            }
        }

        // Calculation and Output
        double totalPortfolioValue = 0;
        System.out.println("Your portfolio summary");

        // Treating all child objects (Stock, ETF) as the parent type 'Asset'.
        for (Asset currentAsset : myPortfolio){

            // Java calls the overridden calculateValue()
            // method of the specific child class at runtime.
            double assetValue = currentAsset.calculateValue();
            totalPortfolioValue += assetValue;

            System.out.println("Asset: " + currentAsset.getName() + " (" + currentAsset.getSymbol() + ")");
            System.out.println("Current value: $" + assetValue);

            // The 'Asset' variable doesn't know about calculateProfit().
            // We use 'instanceof' to verify if it's a Stock, then unlock its specific profit method.
            if (currentAsset instanceof Stock s){
                System.out.println("Unrealized profit: $" + s.calculateProfit());
            } else if (currentAsset instanceof ETF e) {
                System.out.println("Unrealized profit: $" + e.calculateProfit());
            }
            System.out.println();
        }
        System.out.println("Total portfolio value: $" + totalPortfolioValue);

        scanner.close();
    }
}
