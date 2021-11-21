import java.util.Scanner;
import java.sql.*;

public class BankAccount {

    public static boolean isLogin = false;

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        regOrLog(sc);

        sc.close();
    }

    public static boolean depositFunds(String cardNumber, Operation operate, Scanner sc) {
        System.out.println("==== Make deposit ====");
        int amount = 0;
        while (amount <= 0 ) {
            System.out.print("Type amount: ");
            amount = Integer.parseInt(sc.nextLine());
        }
        System.out.print("Enter a memo: ");
        String memo = sc.nextLine();
        operate.deposit(amount, cardNumber, memo);
        int balance = operate.showBalance(cardNumber);
        System.out.println("\nCurrent balance is " + balance + "$");
        return true;
    }

    public static boolean refillFunds(String numberCard, Operation operate, Scanner sc) {
        System.out.println("==== Withdrawal funds ====");
        int amount_yours = 0;
        while (amount_yours <= 0) {
            System.out.print("Type amount: ");
            amount_yours = Integer.parseInt(sc.nextLine());
        }
        System.out.print("Enter a memo: ");
        String memo = sc.nextLine();
        operate.refill(amount_yours, numberCard, memo);
        int your_balance = operate.showBalance(numberCard);
        System.out.println("\nCurrent balance is " + your_balance + "$");
        return true;
    }

    public static boolean transferFunds(String cardNumber, Operation operate, Scanner sc) {
        System.out.println("==== Send money to other card ====");
        System.out.print("Enter other client card number: ");
        String number_other = sc.nextLine();
        int amount_other = 0;
        while (amount_other <= 0 ) {
            System.out.print("Type amount: ");
            amount_other = Integer.parseInt(sc.nextLine());
        }
        System.out.print("Enter a memo: ");
        String memo = sc.nextLine();
        operate.sendToOther(amount_other, number_other, cardNumber, memo);
        System.out.println("\nYou sent " + amount_other + "$ to " + number_other);
        return true;
    }

    public static boolean createAnAcc(Scanner sc) {
        System.out.println("\n\n==== Create new Account ====\n");
        System.out.print("Enter first name: ");
        String firstName = sc.next().trim();
        System.out.print("Enter last name: ");
        String lastName = sc.next().trim();
        Account anAcc = new Account(firstName, lastName);
        anAcc.register();
        return true;
    }

    public static boolean mainMenu(Scanner sc, Operation operate, String cardNumber) {
        System.out.println("Enter an option");
        System.out.println("1. Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdrawal");
        System.out.println("4. Send to other person");
        System.out.println("5. Show account transaction history(5 records)");
        System.out.println("6. Exit");
        int option_user = 0;
        while (option_user < 1 || option_user > 6) {
            System.out.print("\nType your choice: ");
            option_user = Integer.parseInt(sc.nextLine());
        }
        int balance = 0;
        switch(option_user) {
            case 1 -> {
                System.out.println("==== Show balance ====");
                balance = operate.showBalance(cardNumber);
                System.out.println("\nYour balance is " + balance + "$");
            }
            case 2 -> depositFunds(cardNumber, operate, sc);
            case 3 -> refillFunds(cardNumber, operate, sc);
            case 4 -> transferFunds(cardNumber, operate, sc);
            case 5 -> operate.showTransHistory(cardNumber);
            case 6 -> {
                System.out.println("\nSee you next time space cowboy...\n");
                System.exit(0);
            }
        }
        if (option_user != 6) {
            System.out.println("\n");
            BankAccount.mainMenu(sc, operate, cardNumber);
        }
        return true;
    }

    public static boolean mainMenuA(Scanner sc){
        while (!isLogin) {
            System.out.println("\n\n==== Sign In ====\n");
            System.out.print("Enter your card number: ");
            String cardNumber = sc.nextLine();
            System.out.print("Enter your pin code: ");
            String pin = sc.nextLine();
            Operation operate = new Operation(cardNumber, pin);
            try {
                Connection c = DB.connection();
                Statement stmt7 = c.createStatement();
                String sql7 = "SELECT * FROM card WHERE cardNumber ='" + cardNumber + "' AND pin='" + pin + "'";
                ResultSet rs = stmt7.executeQuery(sql7);
                if (rs.next()) {
                    isLogin = true;
                    System.out.println("\n\n==== Login success ====\n");
                    Statement stmt2 = c.createStatement();
                    String sql2 = "SELECT * FROM card WHERE cardNumber ='" + cardNumber + "' AND pin='" + pin + "'";
                    ResultSet rs2 = stmt2.executeQuery(sql2);
                    int last_count_id = 0;
                    if (rs2.next()) {
                        last_count_id = rs2.getInt(1);
                    }
                    String res = "";
                    Statement stmt3 = c.createStatement();
                    String sql3 = "SELECT * FROM users WHERE id='" + last_count_id + "'";
                    ResultSet rs3 = stmt3.executeQuery(sql3);
                    while(rs3.next()){
                        res += rs3.getString(1) + "  " + rs3.getString(2)
                                + " nice to meet you!\n";
                    }
                    System.out.println(res);
                    mainMenu(sc, operate, cardNumber);
                } else {
                    System.out.println("\nLogin fail");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        mainMenuA(sc);
        return true;
    }

    public static boolean regOrLog(Scanner sc) {
        sc.nextLine();
        int option = 0;
        while (option == 0) {

            System.out.println("==== Select option: ====");
            System.out.println("1. Create account");
            System.out.println("2. Sign In\n");
            System.out.print("Enter option: ");
            option = Integer.parseInt(sc.nextLine());
            while (option < 1 || option > 2){
                System.out.print("Incorrect choice!Repeat plz: ");
                option = Integer.parseInt(sc.nextLine());
            }
        }
        switch(option) {
            case 1:
                createAnAcc(sc);
                break;
            case 2:
                mainMenuA(sc);
                break;
        }
        regOrLog(sc);
        return true;
    }

}
