class BankAccount{
    private String accountNumber;
    private double balance;

    //setter for AN
    public void setAccountNumber(String an){
        accountNumber=an;
    }
    //getter for AN
    public String getAccountNumber(){
        return accountNumber;
    }

    //setter for Bal
    public void setBalance(double b){
        if(b>0){
            balance=b;
        }
        else{
            System.out.println("Invalid Balance");
        }
    }
    //getter for Bal
    public double getBalance(){
        return balance;
    }

    public void Deposit(int amount){
        if(amount>0){
            balance+=amount;
            System.out.println(amount+" has been Credited");
            System.out.println("Balance left is "+balance);
        }else{
            System.out.println("Amount is Negative or zero");
        }
    }

    public void Withdraw(int amount){
        if(amount>0){
            balance-=amount;
            System.out.println(amount+" Successfully Withdrawn");
            System.out.println("Balance left is "+balance);
        }else{
            System.out.println("Enter valid Amount");
        }
    }
}
public class Chapter9 {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount();

        acc.setAccountNumber("CAN123456");
        System.out.println("Account number is "+acc.getAccountNumber());

        acc.setBalance(0);
        System.out.println("Balance is "+acc.getBalance());

        acc.setBalance(1000);
        System.out.println("Balance is "+acc.getBalance());

        acc.Deposit(500);
        acc.Withdraw(300);
    }
}


