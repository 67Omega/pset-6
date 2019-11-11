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
    	System.out.println("Account No.: ");
    	long accountNo = in.nextLong();
    	System.out.println("PIN        : ");
    	int pin = in.nextInt();
    	if ((accountNo == activeAccount.getAccountNo()) && (pin == activeAccount.getPin())) {
    		System.out.println("\nHello, again, " + activeAccount.getAccountHolder().getFirstName() + "!\n");
    		System.out.println("[1] View balance");
            System.out.println("[2] Deposit money");
            System.out.println("[3] Withdraw money");
    	} else {
    		System.out.println("\nInvalid account number and/or PIN.\n");
    	}
    }
    
    public static void main(String[] args) {
        ATM atm = new ATM();
    }
}
