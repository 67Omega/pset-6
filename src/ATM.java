import java.io.IOException;
import java.util.Scanner;

public class ATM {
    
    private Scanner in;
    private BankAccount activeAccount;
    private Bank bank;
    
    ////////////////////////////////////////////////////////////////////////////
    //                                                                        //
    // Refer to the Simple ATM tutorial to fill in the details of this class. //
    // You'll need to implement the new features yourself.                    //
    //                                                                        //
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * Constructs a new instance of the ATM class.
     */
    
    public ATM() {
        this.in = new Scanner(System.in);
        
        try {
			this.bank = new Bank();
		} catch (IOException e) {
			// cleanup any resources (i.e., the Scanner) and exit
		}
    }
    
    /*
     * Application execution begins here.
     */
    
    public void startup() {
        System.out.println("Welcome to the AIT ATM!\n");
        while (true) {
            System.out.print("Account No.: ");
            String accountNo = in.next();
            if (accountNo == "+") {
            	createNewAccount();
            } else {
            	Long.parseLong(accountNo);
            }
            System.out.print("PIN        : ");
            int pin = in.nextInt();
            if (isValidLogin(accountNo, pin)) {
                System.out.println("\nHello, again, " + activeAccount.getAccountHolder().getFirstName() + "!\n");
                
                boolean validLogin = true;
                while (validLogin) {
                    switch (getSelection()) {
                        case VIEW: showBalance(); break;
                        case DEPOSIT: deposit(); break;
                        case WITHDRAW: withdraw(); break;
                        case LOGOUT: validLogin = false; break;
                        default: System.out.println("\nInvalid selection.\n"); break;
                    }
                }
            } else {
                if (accountNo == -1 && pin == -1) {
                    shutdown();
                } else {
                    System.out.println("\nInvalid account number and/or PIN.\n");
                }
            }
        }
    }
    
    public boolean isValidLogin(long accountNo, int pin) {
        return accountNo == activeAccount.getAccountNo() && pin == activeAccount.getPin();
    }
    
    public int getSelection() {
        System.out.println("[1] View balance");
        System.out.println("[2] Deposit money");
        System.out.println("[3] Withdraw money");
        System.out.println("[4] Logout");
        
        return in.nextInt();
    }
 
    public void createNewAccount() {
    	System.out.print("\nFirst name: ");
    	String fName = in.next();
    	System.out.print("Last name: ");
    	String lName = in.next();
    	String username = User(fName, lName);
    	System.out.print("PIN: ");
    	int newPIN = in.nextInt();
    	long userAccountNum = createAccount(newPIN, username);
    	System.out.println("\nThank you. Your account number is " + userAccountNum + ".");
    	System.out.print("Please login to access your newly created account.\n");
    	startup();
    }
    
    public void showBalance() {
        System.out.println("\nCurrent balance: " + activeAccount.getBalance());
    }
    
    public void deposit() {
        System.out.print("\nEnter amount: ");
        double amount = in.nextDouble();
        String depositStatus = activeAccount.deposit(amount);
        System.out.println();
        switch depositStatus {
        case "zero deposit":
        	System.out.println("Deposit rejected. Amount must be greater than $0.00.");
        	break;
        case "overwhelm deposit":
        	System.out.println("Deposit rejected. Amount would cause balance to exceed $999,999,999,999.99.");
        	break;
        case "successful deposit":
        	System.out.println("Deposit accepted.");
        	break;
        default:
        	System.out.println("Invalid input.");
        	break;
        }
    }
    
    public void withdraw() {
    	System.out.print("\nEnter amount: ");
        double amount = in.nextDouble();
        String withdrawalStatus = activeAccount.withdraw(amount);
        System.out.println();
        switch withdrawalStatus {
        case "zero withdrawal":
        	System.out.println("Withdrawal rejected. Amount must be greater than $0.00.");
        	break;
        case "overdraw":
        	System.out.println("Withdrawal rejected. Insufficient funds.");
        	break;
        case "successful withdrawal":
        	System.out.println("Withdrawal accepted.");
        	break;
        default:
        	System.out.println("Invalid input.");
        	break;
        }
    }
    
    public void shutdown() {
        if (in != null) {
            in.close();
        }
        System.out.println("\nGoodbye!");
        System.exit(0);
    }
    
    public static void main(String[] args) {
        ATM atm = new ATM();
        
        atm.startup();
    }
}
