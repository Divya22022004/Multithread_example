import java.util.Scanner;
class BankAccount {
    private int accountNumber;
    private double balance;
    public BankAccount(int accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    // Synchronized method to ensure thread safety for deposit
    public synchronized void deposit(double amount, String timeStamp) {
        balance += amount;
        System.out.println(timeStamp + " | Deposit | $" + amount + " | Account " + accountNumber);
    }
    // Synchronized method to ensure thread safety for withdrawal
    public synchronized void withdraw(double amount, String timeStamp) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println(timeStamp + " | Withdraw | $" + amount + " | Account " + accountNumber);
        } else {
            System.out.println(timeStamp + " | Withdraw | $" + amount + " | Account " + accountNumber + " | Insufficient funds");
        }
    }
    public double getBalance() {
        return balance;
    }
}
class Transaction implements Runnable {
    private BankAccount account;
    private String transactionType;
    private double amount;
    private String timeStamp;
    public Transaction(BankAccount account, String transactionType, double amount, String timeStamp) {
        this.account = account;
        this.transactionType = transactionType;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }
    public void run() {
        if ("deposit".equalsIgnoreCase(transactionType)) {
            account.deposit(amount, timeStamp);
        } else if ("withdraw".equalsIgnoreCase(transactionType)) {
            account.withdraw(amount, timeStamp);
        }
    }
}
public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Getting initial balances from the user
        System.out.print("Enter initial balance for Account 1: ");
        double balance1 = scanner.nextDouble();
        System.out.print("Enter initial balance for Account 2: ");
        double balance2 = scanner.nextDouble();
        // Create two bank accounts
        BankAccount account1 = new BankAccount(1, balance1);
        BankAccount account2 = new BankAccount(2, balance2);
        // Print header for the transaction log
        System.out.println("\n| Timestamp | Type     | Amount | Account |");
        System.out.println("-------------------------------------------");
        // Simulate the transaction log using multithreading
        Thread t1 = new Thread(new Transaction(account1, "deposit", 500, "09:00:01"));   // 09:00:01
        Thread t2 = new Thread(new Transaction(account2, "withdraw", 200, "09:00:02"));  // 09:00:02
        Thread t3 = new Thread(new Transaction(account1, "deposit", 100, "09:00:03"));   // 09:00:03
        Thread t4 = new Thread(new Transaction(account1, "withdraw", 300, "09:00:04"));  // 09:00:04
        Thread t5 = new Thread(new Transaction(account2, "deposit", 5700, "09:00:05"));  // 09:00:05
        Thread t6 = new Thread(new Transaction(account2, "withdraw", 1000, "09:00:06")); // 09:00:06
        // Start transactions concurrently
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        // Ensure all threads complete
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Display final balances only
        System.out.println("\nFinal Balance for Account 1: $" + account1.getBalance());
        System.out.println("Final Balance for Account 2: $" + account2.getBalance());
        scanner.close();
    }
}
