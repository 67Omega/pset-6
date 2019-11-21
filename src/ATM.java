import java.io.IOException;
import java.util.Scanner;

public class ATM {
    
    public static final int FIRST_NAME_WIDTH = 20;
	public static final int LAST_NAME_WIDTH = 30;
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
    public void greet() {
    	System.out.println("Welcome to the AIT ATM!\n");
    	startup();
    }
    
    public void startup() {
        while (true) {
            System.out.print("Account No.: ");
            String accountNum = in.next();
            if (accountNum.equals("+")) {
            	createNewAccount();
            }
            long accountNo = Long.valueOf(accountNum);
            System.out.print("PIN        : ");
            int pin = in.nextInt();
            if (accountNo == -1 && pin == -1) {
                shutdown();
            } else if (isValidLogin(accountNo, pin)) {
                System.out.println("\nHello, again, " + activeAccount.getAccountHolder().getFirstName() + "!\n");
                boolean validLogin = true;
                while (validLogin) {
                    switch (getSelection()) {
                        case 1: 
                        	showBalance(); 
                        	break;
                        case 2: 
                        	deposit(); 
                        	break;
                        case 3: 
                        	withdraw(); 
                        	break;
                        case 4: 
                        	transfer(); 
                        	break;
                        case 5: 
                        	validLogin = false;
                        	bank.save();
                        	break;
                        default: System.out.println("\nInvalid selection.\n"); break;
                    }
                }
            } else {
               System.out.println("\nInvalid account number and/or PIN.\n");
            }
        }
    }
    
    public boolean isValidLogin(long accountNo, int pin) {
    	activeAccount = bank.login(accountNo, pin);
        return (activeAccount != null);
    }
    
    public int getSelection() {
        System.out.println("[1] View balance");
        System.out.println("[2] Deposit money");
        System.out.println("[3] Withdraw money");
        System.out.println("[4] Transfer money");
        System.out.println("[5] Logout\n");
        return in.nextInt();
    }
 
    public void createNewAccount() {
    	String fName = "";
    	String lName = "";
    	int newPIN = 0;
    	while (((fName.length() < 1) || (fName.length()) > 20)|| (lName == null)) {
    			System.out.print("\nFirst name: ");
    			fName = in.next();
    	}
    	while (((lName.length() < 1) || (lName.length()) > 30) || (lName == null)) {
			System.out.print("Last name: ");
			lName = in.next();
		}
    	User username = new User(fName, lName);
    	while ((newPIN < 1000) || (newPIN > 9999)) {
    		System.out.print("PIN: ");
    		newPIN = in.nextInt();
    	}
    	BankAccount userAccount = bank.createAccount(newPIN, username);
    	bank.save();
    	System.out.println("\nThank you. Your account number is " + userAccount.getAccountNo() + ".");
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
        switch (depositStatus) {
        case "zero deposit":
        	System.out.println("Deposit rejected. Amount must be greater than $0.00.\n");
        	break;
        case "overwhelm deposit":
        	System.out.println("Deposit rejected. Amount would cause balance to exceed $999,999,999,999.99.\n");
        	break;
        case "successful deposit":
        	System.out.println("Deposit accepted.\n");
        	break;
        default:
        	System.out.println("Invalid input.\n");
        	break;
        }
        bank.update(activeAccount);
    }
    
    public void withdraw() {
    	System.out.print("\nEnter amount: ");
        double amount = in.nextDouble();
        String withdrawalStatus = activeAccount.withdraw(amount);
        System.out.println();
        switch (withdrawalStatus){
        case "zero withdrawal":
        	System.out.println("Withdrawal rejected. Amount must be greater than $0.00.\n");
        	break;
        case "overdraw":
        	System.out.println("Withdrawal rejected. Insufficient funds.\n");
        	break;
        case "successful withdrawal":
        	System.out.println("Withdrawal accepted.\n");
        	break;
        default:
        	System.out.println("Invalid input.\n");
        	break;
        }
        bank.update(activeAccount);
    }
    public void transfer() {
    	System.out.print("Enter account: ");
    	long destinationAccount = in.nextLong();
    	BankAccount transferAccount = bank.getAccount(destinationAccount);
    	System.out.print("Enter amount: ");
    	double transferAmount = in.nextDouble();
    	String transferStatus = activeAccount.transfer(transferAccount, transferAmount);
    	System.out.println();
    	switch (transferStatus) {
        case "zero transfer":
        	System.out.println("Transfer rejected. Amount must be greater than $0.00.\n");
        	break;
        case "not found":
        	System.out.println("Transfer rejected. Destination account not found.\n");
        	break;
        case "overdraw":
        	System.out.println("Transfer rejected. Insufficient funds.\n");
        	break;
        case "overwhelm transfer":
        	System.out.println("Transfer rejected. Amount would cause destination balance to exceed $999,999,999,999.99.\n");
        	break;
        case "successful transfer":
        	System.out.println("Transfer accepted.\n");
        	break;
        default:
        	System.out.println("Invalid input.\n");
        	break;
        }
    	bank.update(activeAccount);
    	if (transferAccount != null) {
    		bank.update(transferAccount);
    	}
    }
    public void shutdown() {
        if (in != null) {
            in.close();
        }
        System.out.println("\nGoodbye!");
        bank.save();
        System.exit(0);
    }
    
    public static void main(String[] args) {
        ATM atm = new ATM();
        
        atm.greet();
    }
}
