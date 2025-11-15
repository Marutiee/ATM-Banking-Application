// Save as ATMSystem.java
import java.util.*;

/**
 * ATMSystem.java
 * Single-file console-based ATM simulation demonstrating:
 * - OOP: Account, ATM classes
 * - Exception handling: InvalidPinException, InsufficientFundsException
 * - Collections: HashMap for accounts, ArrayList for transaction history
 * - Menu-driven console UI
 *
 * Compile: javac ATMSystem.java
 * Run:     java ATMSystem
 */
public class ATMSystem {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.seedDemoAccounts(); // create some demo accounts
        atm.start(); // start interactive console
    }
}

/* =========================
   Domain: Account
   ========================= */
class Account {
    private final int accountNumber;
    private final String ownerName;
    private String pin; // stored as plain here for demo; in production hash it!
    private double balance;
    private final List<String> transactionHistory; // Collections: ArrayList

    public Account(int accountNumber, String ownerName, String pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        this.transactionHistory.add(String.format("Account created with balance: %.2f", initialBalance));
    }

    // Getters
    public int getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public double getBalance() { return balance; }

    // PIN verification
    public boolean verifyPin(String attempt) {
        return this.pin.equals(attempt);
    }

    // Change PIN (example usage of exception handling in ATM class)
    public void changePin(String oldPin, String newPin) throws InvalidPinException {
        if (!verifyPin(oldPin)) throw new InvalidPinException("Old PIN is incorrect.");
        if (newPin == null || newPin.length() < 4) throw new IllegalArgumentException("New PIN must be at least 4 characters.");
        this.pin = newPin;
        transactionHistory.add("PIN changed.");
    }

    // Deposit
    public synchronized void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be positive.");
        this.balance += amount;
        transactionHistory.add(String.format("Deposited: +%.2f | New balance: %.2f", amount, this.balance));
    }

    // Withdraw
    public synchronized void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive.");
        if (amount > this.balance) throw new InsufficientFundsException("Insufficient balance for withdrawal.");
        this.balance -= amount;
        transactionHistory.add(String.format("Withdrawn: -%.2f | New balance: %.2f", amount, this.balance));
    }

    public List<String> getTransactionHistory() {
        // return a copy to avoid external modification
        return new ArrayList<>(transactionHistory);
    }

    @Override
    public String toString() {
        return String.format("Account %d - %s - Balance: %.2f", accountNumber, ownerName, balance);
    }
}

/* =========================
   Exceptions
   ========================= */
class InvalidPinException extends Exception {
    public InvalidPinException(String message) { super(message); }
}

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) { super(message); }
}

/* =========================
   ATM Controller
   ========================= */
class ATM {
    // Collections: HashMap mapping accountNumber to Account for quick lookup
    private final Map<Integer, Account> accounts = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    // Configurable: how many wrong PIN attempts allowed
    private final int MAX_PIN_ATTEMPTS = 3;

    public ATM() {}

    // Seed some demo accounts for testing
    public void seedDemoAccounts() {
        accounts.put(1001, new Account(1001, "Alice Kumar", "1234", 5000.00));
        accounts.put(1002, new Account(1002, "Rahul Sharma", "0000", 2500.50));
        accounts.put(1003, new Account(1003, "Meera Patel", "4321", 10000.00));
    }

    // Start interactive console
    public void start() {
        System.out.println("=== Welcome to Console ATM Simulation ===");
        boolean running = true;
        while (running) {
            showMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": authenticateAndServe(); break;
                case "2": createAccountFlow(); break;
                case "3": listAccountsBrief(); break; // useful for demo/testing
                case "0": running = false; System.out.println("Thank you. Goodbye!"); break;
                default: System.out.println("Invalid option. Try again.\n");
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1) Insert card (login)");
        System.out.println("2) Open new account");
        System.out.println("3) (Demo) List accounts");
        System.out.println("0) Exit");
        System.out.print("Choose an option: ");
    }

    // Create new account (demo flow)
    private void createAccountFlow() {
        try {
            System.out.print("Enter new account number (digits): ");
            int accNum = Integer.parseInt(scanner.nextLine().trim());
            if (accounts.containsKey(accNum)) {
                System.out.println("Account number already exists. Aborting.");
                return;
            }
            System.out.print("Owner name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Set 4-digit PIN: ");
            String pin = scanner.nextLine().trim();
            if (pin.length() < 4) {
                System.out.println("PIN should be at least 4 digits. Aborted.");
                return;
            }
            System.out.print("Initial deposit (numeric): ");
            double init = Double.parseDouble(scanner.nextLine().trim());
            if (init < 0) {
                System.out.println("Initial deposit cannot be negative. Aborted.");
                return;
            }
            Account acc = new Account(accNum, name, pin, init);
            accounts.put(accNum, acc);
            System.out.println("Account created successfully: " + acc);
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid numeric input. Aborted.");
        }
    }

    // Very small helper for demo/testing
    private void listAccountsBrief() {
        System.out.println("\nStored accounts (demo only):");
        for (Account a : accounts.values()) {
            System.out.println(a);
        }
    }

    // Authenticate user flow and show account menu
    private void authenticateAndServe() {
        try {
            System.out.print("Enter account number: ");
            int accNum = Integer.parseInt(scanner.nextLine().trim());
            Account acc = accounts.get(accNum);
            if (acc == null) {
                System.out.println("Account not found.");
                return;
            }
            boolean authenticated = false;
            int attempts = 0;
            while (attempts < MAX_PIN_ATTEMPTS) {
                System.out.print("Enter 4-digit PIN: ");
                String pinAttempt = scanner.nextLine().trim();
                if (acc.verifyPin(pinAttempt)) {
                    authenticated = true;
                    break;
                } else {
                    attempts++;
                    System.out.println("Incorrect PIN. Attempts left: " + (MAX_PIN_ATTEMPTS - attempts));
                }
            }
            if (!authenticated) {
                System.out.println("Maximum PIN attempts exceeded. Card retained (simulated).");
                return;
            }
            // Authenticated -> show account operations
            accountSession(acc);
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid account number format.");
        }
    }

    // Account operation menu after authentication
    private void accountSession(Account acc) {
        boolean session = true;
        System.out.println("\nWelcome, " + acc.getOwnerName() + "!");
        while (session) {
            showAccountMenu();
            String option = scanner.nextLine().trim();
            try {
                switch (option) {
                    case "1": handleBalanceInquiry(acc); break;
                    case "2": handleDeposit(acc); break;
                    case "3": handleWithdrawal(acc); break;
                    case "4": handleTransactionHistory(acc); break;
                    case "5": handleChangePin(acc); break;
                    case "0": session = false; System.out.println("Logging out..."); break;
                    default: System.out.println("Invalid option. Try again.");
                }
            } catch (Exception e) {
                // Top-level handling: show friendly message but continue session
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }

    private void showAccountMenu() {
        System.out.println("\nAccount Menu:");
        System.out.println("1) Balance inquiry");
        System.out.println("2) Deposit");
        System.out.println("3) Withdraw");
        System.out.println("4) Transaction history");
        System.out.println("5) Change PIN");
        System.out.println("0) Eject card / Logout");
        System.out.print("Choose an option: ");
    }

    private void handleBalanceInquiry(Account acc) {
        System.out.printf("Current balance: %.2f%n", acc.getBalance());
    }

    private void handleDeposit(Account acc) {
        System.out.print("Amount to deposit: ");
        String s = scanner.nextLine().trim();
        try {
            double amount = Double.parseDouble(s);
            acc.deposit(amount);
            System.out.printf("Deposit successful. New balance: %.2f%n", acc.getBalance());
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid amount input.");
        } catch (IllegalArgumentException iae) {
            System.out.println("Deposit failed: " + iae.getMessage());
        }
    }

    private void handleWithdrawal(Account acc) {
        System.out.print("Amount to withdraw: ");
        String s = scanner.nextLine().trim();
        try {
            double amount = Double.parseDouble(s);
            acc.withdraw(amount);
            System.out.printf("Withdrawal successful. New balance: %.2f%n", acc.getBalance());
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid amount input.");
        } catch (InsufficientFundsException ife) {
            System.out.println("Withdrawal failed: " + ife.getMessage());
        } catch (IllegalArgumentException iae) {
            System.out.println("Withdrawal failed: " + iae.getMessage());
        }
    }

    private void handleTransactionHistory(Account acc) {
        List<String> history = acc.getTransactionHistory();
        System.out.println("Transaction history (most recent last):");
        for (String h : history) {
            System.out.println(" - " + h);
        }
    }

    private void handleChangePin(Account acc) {
        try {
            System.out.print("Enter old PIN: ");
            String oldPin = scanner.nextLine().trim();
            System.out.print("Enter new PIN (min 4 chars): ");
            String newPin = scanner.nextLine().trim();
            acc.changePin(oldPin, newPin);
            System.out.println("PIN changed successfully.");
        } catch (InvalidPinException ipe) {
            System.out.println("PIN change failed: " + ipe.getMessage());
        } catch (IllegalArgumentException iae) {
            System.out.println("PIN change failed: " + iae.getMessage());
        }
    }
}
