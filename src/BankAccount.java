import java.text.NumberFormat;

public class BankAccount {
        
    private int pin;
    private long accountNo;
    private double balance;
    private User accountHolder;
    
    public BankAccount(int pin, long accountNo, User accountHolder) {
    	this.pin = pin;
    	this.accountNo = accountNo;
    	this.balance = 0.0;
    	this.accountHolder;
    }
    
    public int getPin() {
        return pin;
    }
    
    public long getAccountNo() {
        return accountNo;
    }
    
    public double getBalance() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
    	return currency.format(balance);
    }
    
    public User getAccountHolder() {
        return accountHolder;
    }
    
    public void deposit(double amount) {
    	if (amount <= 0) {
    		return "zero deposit";
    	} else if ((amount + (getBalance())) > 999999999999.99) {
    		return "overwhelm deposit";
    	} else {
    		balance = balance + amount;
    		return "succcessful deposit";
    	}
    }
    
    public void withdraw(double amount) {
    	if (amount <= 0) {
    		return "zero withdrawal";
    	} else if (((getBalance()) - amount) < 0.00) {
    		return "overdraw";
    	} else {
    		balance = balance - amount;
    		return "succcessful withdrawal";
    	}
    }
    
    public void transfer(BankAccount transferAccount, double transferAmount) {
    	if (amount <= 0) {
    		return "zero transfer";
    	} else if (transferAccount == null) {
    		return "not found";
    	} else if (amount > (getBalance())) {
    		return "overdraw";
    	} else if ((amount + (transferAccount.getBalance())) > 999999999999.99) {
    		return "overwhelm transfer";
    	} else {
    		return "successful transfer";
    	}
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //                                                                        //
    // Refer to the Simple ATM tutorial to fill in the details of this class. //
    //                                                                        //
    ////////////////////////////////////////////////////////////////////////////
    
    /*
     * Formats the account balance in preparation to be written to the data file.
     * 
     * @return a fixed-width string in line with the data file specifications.
     */
    
    private String formatBalance() {
        return String.format("%1$15s", balance);
    }
    
    /*
     * Converts this BankAccount object to a string of text in preparation to
     * be written to the data file.
     * 
     * @return a string of text formatted for the data file
     */
    
    @Override
    public String toString() {
        return String.valueOf(accountNo) +
            String.valueOf(pin) +
            accountHolder.serialize() +
            formatBalance();
    }
}
