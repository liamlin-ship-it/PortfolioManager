# PortfolioManager

A Java-based application for managing and tracking personal investment portfolios, including stocks and ETFs.

## About The Project
This project is an object-oriented financial tracking tool. It is designed to help users monitor their asset allocation, track individual holdings, and easily manage different types of financial instruments within a single application.

## Features
* **Asset Management**: A core system to handle various types of financial assets.
* **Stock Tracking**: Monitor individual stock holdings and calculate their current value.
* **ETF Tracking**: Manage Exchange-Traded Funds and oversee broader market investments.

## Built With
* Java

## Project Structure
This project applies Object-Oriented Programming (OOP) concepts:
* `Asset`: The base class representing a generic financial asset, defining common attributes (like symbol, shares) and methods.
* `Stock`: Inherits from `Asset`, handling specific logic for individual company shares.
* `ETF`: Inherits from `Asset`, handling specific logic for diversified funds.

## Author
* **liamlin-ship-it**
---
## Development Log & Case Study
# Phase 1: Defining the Base Class "Asset"

**Purpose:** This class holds common properties that all financial instruments share (like `name` and `symbol`) to avoid code duplication in the future.
```java
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
```

# Phase 2: Implementing the Child Class "Stock"
**Purpose:** Built the `Stock` class, which inherits from `Asset`. This class is designed to track individual company shares.
```java
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
```

# Phase 3: Expanding the Portfolio with "ETF" Class
**Purpose:** Created the `ETF` class to handle ETF. It also inherits from `Asset` but introduces logic specific to funds.
```java
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
```

# Phase 4: Implementation & Bug Fixing
**Purpose:** Create a command-line interface in `Main.java` to test adding assets and generating a portfolio report dynamically.
```java
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
```

### Challenges Encountered & Debugging
#### 1. The Scanner "Enter" Key Bug
While building the menu, I encountered a classic Java input bug. When I pressed "Enter" after inputting my menu choice, the program completely skipped the next input prompt for the stock name.

I found out that `scanner.nextInt()` reads the number but ignores the "Enter" key (`\n`). As a result, my next `scanner.nextLine()` caught that invisible "Enter" instead of my actual text.



**How I fixed it**
info
I simply added a blank `scanner.nextLine();` to clear that leftover "Enter" out of the way. It was a small bug, but it taught me a lot about how Java reads user inputs!


```java
int choice = scanner.nextInt();

            // We need this empty nextLine() to consume the leftover "Enter" key character
            // Otherwise, the next String input will be skipped
            scanner.nextLine();
```

#### 2. The Percentage Math Trap
When I added an ETF and typed `0.5` for the management fee, my total asset value suddenly dropped by half!

I realized there's a gap between human thinking and computer math. I was thinking "0.5%", but Java calculates `0.5` as 50%. The correct decimal for 0.5% is actually `0.005`.

**How I fixed it**
info
Instead of forcing the user to do the math and type `0.005`, I updated the prompt to say `"Enter management fee in %: "`. Then, in my code, I simply divided the input by 100 (`scanner.nextDouble() / 100`). It's a small change, but it makes the program much more intuitive and user-friendly!


```java
// Convert user input from percentage to decimal format
                System.out.println("Enter management fee in %: ");
                double fee = scanner.nextDouble() / 100;
```
#### 3. Unlocking Child Methods with `instanceof`
While looping through my portfolio, I couldn't figure out how to print the "unrealized profit". My `Asset` list only knows about general asset features, so it blocked me from using the `calculateProfit()` method hidden inside the `Stock` and `ETF` child classes.

I asked AI for guidance, and it introduced me to the `instanceof` keyword.


**How I fixed it**
info
The `instanceof` checks if the current asset is specifically a `Stock` or an `ETF`. Once verified, Java automatically "unlocks" that specific class, allowing me to call its unique profit method safely.


```java
// The 'Asset' variable doesn't know about calculateProfit().
// We use 'instanceof' to verify if it's a Stock, then unlock its specific profit method.
            if (currentAsset instanceof Stock s){
                System.out.println("Unrealized profit: $" + s.calculateProfit());
            } else if (currentAsset instanceof ETF e) {
                System.out.println("Unrealized profit: $" + e.calculateProfit());
            }
```

# Phase 5: Final System Test
After fixing the bugs and refining the logic, I ran a full test by adding one Stock (TSMC) and one ETF (QQQ). The program successfully calculated the exact unrealized profit, including the 0.5% management fee deduction for the ETF.

**Terminal Output**
```
Welcome to the Portfolio Manager
Main Menu
1. Add a new stock
2. Add a new ETF
3. Calculate Total Value & Exit
Please enter your choice (1-3): 1

Enter Stock name: TSMC
Enter Stock symbol: TSM
Enter number of shares: 10
Enter average cost: 200
Enter current price: 320
Stock added successfully!

... (Menu skipped for brevity) ...
Please enter your choice (1-3): 2

Enter ETF name: Invesco QQQ Trust
Enter ETF symbol: QQQ
Enter number of shares: 10
Enter average cost: 450
Enter current price: 550
Enter management fee in %: 0.5
ETF added successfully!

... (Menu skipped for brevity) ...
Please enter your choice (1-3): 3

Generating your portfolio report...

Your portfolio summary
Asset: TSMC (TSM)
Current value: $3200.0
Unrealized profit: $1200.0

Asset: Invesco QQQ Trust (QQQ)
Current value: $5472.5
Unrealized profit: $972.5

Total portfolio value: $8672.5
```

# Learning Reflection
**The Process:** In building this final report generation block, I utilized an AI assistant as an interactive tutor to help bridge the gap between my business logic and actual Java syntax.

**My Current Understanding:** Through this process, I now understand the practical application of treating mixed objects generically as an `Asset` to avoid loop errors, and using `instanceof` to unlock child-specific methods like `calculateProfit()`.

**Work in Progress:** While I know how to apply these patterns to make the code work, the deep mechanics of Java's dynamic method dispatch (how it automatically knows to use the ETF's formula instead of the Stock's) is still a bit of a "black box" to me.

**Next Steps:** I am currently treating these AI-guided syntax patterns as standard templates. To truly make this knowledge my own, I plan to write more code, experiment with different object structures, and keep practicing until these OOP concepts become second nature.
