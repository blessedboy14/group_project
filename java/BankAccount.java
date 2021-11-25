import java.util.Scanner;
import java.sql.*;

/**
 * @author Prokopchuk Daniil, Darya Samusenko, Alexander Strukov
 * @version 1.0
 */
public class BankAccount {

    /**
     * This local variable for checking user access
     */
    public static boolean isLogin = false;

    /**
     * Main project method
     * @param args Empty field
     */
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        regOrLog(sc);

        sc.close();
    }

    /**
     * This method returns main menu after success entrance
     * @param sc Scanner for this method
     * @param operate Operate class object for operations
     * @param cardNumber current user card number to db access
     * @return this method always returns true
     */
    public static boolean mainMenu(Scanner sc, Operation operate, String cardNumber) {
        System.out.println("Enter an option");
        System.out.println("1. Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdrawal");
        System.out.println("4. Send to other person");
        System.out.println("5. Show account transaction history(5 records)");
        System.out.println("6. Exit");
        try(Connection c = DB.connection()){
        } catch (Exception e) {
            return false;
        }
        int option_user = 0;
        while (option_user < 1 || option_user > 6) {
            System.out.print("\nIncorrect input.Please retry.\nType your choice: ");
            option_user = Integer.parseInt(sc.nextLine());
        }
        int balance = 0;
        switch(option_user) {
            case 1 -> {
                System.out.println("==== Show balance ====");
                balance = operate.showBalance(cardNumber);
                System.out.println("\nYour balance is " + balance + "$");
            }
            case 2 -> {
                System.out.println("==== Make deposit ====");
                int amount = 0;
                boolean isIncorrect = true;
                do{
                    isIncorrect  = false;
                    System.out.print("Type amount: ");
                    try{
                        amount = Integer.parseInt(sc.nextLine());
                    }catch(NumberFormatException e){
                        isIncorrect = true;
                        System.out.println("Incorrect input. Please retry.");
                    }
                    if ((!isIncorrect)&&(amount < 0)){
                        isIncorrect = true;
                        System.out.println("Amount is under zero. Please retry.");
                    }
                    if ((!isIncorrect)&&(amount == 0)){
                        isIncorrect = true;
                        System.out.println("Are you serious?");
                    }
                    if ((!isIncorrect)&&(amount > 32767){
                        isIncorrect = true;
                        System.out.println("Wow, man... That is too much. Please do it by parts.");
                    }
                }while (isIncorrect);
                System.out.print("Enter a memo: ");
                String memo = sc.nextLine();
                operate.deposit(amount, cardNumber, memo);
                balance = operate.showBalance(cardNumber);
                System.out.println("\nCurrent balance is " + balance + "$");
            }
            case 3 -> {
                System.out.println("==== Withdrawal funds ====");
                int amount_yours = 0;
                boolean isIncorrect = true;
                do{
                    isIncorrect = false;
                    System.out.print("Type amount: ");
                    try{
                        amount_yours = Integer.parseInt(sc.nextLine());
                    }catch(NumberFormatException e){
                        isIncorrect = true;
                        System.out.println("Incorrect input. Please retry.");
                    }
                    if ((!isIncorrect)&&(amount_yours < 0){
                        isIncorrect = true;
                        System.out.println("Amount is under zero. Please retry.");
                    }
                    if ((!isIncorrect)&&(amount_yours == 0)){
                        isIncorrect = true;
                        System.out.println("Are you serious?");
                    }
                    if ((!isIncorrect)&&(amount_yours > 32767){
                        isIncorrect = true;
                        System.out.println("Wow, man... That is too much. Please do it by parts.");
                    }
                }while (isIncorrect);
                System.out.print("Enter a memo: ");
                String memo = sc.nextLine();
                operate.withdrawal(amount_yours, cardNumber, memo);
                int your_balance = operate.showBalance(cardNumber);
                System.out.println("\nCurrent balance is " + your_balance + "$");
            }
            case 4 -> {
                System.out.println("==== Send money to other card ====");
                System.out.print("Enter other client card number: ");
                String number_other = sc.nextLine();
                int amount_other = 0;
                boolean isIncorrect = true;
                do{
                    isIncorrect = false;
                    System.out.print("Type amount: ");
                    try{
                        amount_other = Integer.parseInt(sc.nextLine());
                    }catch(NumberFormatException e){
                        isIncorrect = true;
                        System.out.println("Incorrect input. Please retry.");
                    }
                    if ((!isIncorrect)&&(amount_other < 0){
                        isIncorrect = true;
                        System.out.println("Amount is under zero. Please retry.");
                    }
                    if ((!isIncorrect)&&(amount_other == 0)){
                        isIncorrect = true;
                        System.out.println("Are you serious?");
                    }
                    if ((!isIncorrect)&&(amount_other > 32767){
                        isIncorrect = true;
                        System.out.println("Wow, man... That is too much. Please do it by parts.");
                    }
                }while (isIncorrect);
                System.out.print("Enter a memo: ");
                String memo = sc.nextLine();
                operate.sendToOther(amount_other, number_other, cardNumber, memo);
                System.out.println("\nYou sent " + amount_other + "$ to " + number_other);
            }
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

    /**
     * This method prints sign in steps for user
     * @param sc Scanner for this method
     * @return this method always returns true
     */
    public static boolean mainMenuA(Scanner sc){
        try(Connection c = DB.connection()){
        } catch (Exception e) {
            return false;
        }
        while (!isLogin) {
            System.out.println("\n\n==== Sign In ====\n");
            System.out.print("Enter your card number: ");
            String cardNumber = sc.nextLine();
            System.out.print("Enter your pin code: ");
            String pin = sc.nextLine();
            Operation operate = new Operation(cardNumber, pin);
            try(Connection c = DB.connection();
                Statement stmt7 = c.createStatement()){
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

    /**
     * This method prints start menu with 2 choices
     * @param sc Scanner for this method
     * @return this method always returns true
     */
    public static boolean regOrLog(Scanner sc) {
        try(Connection c = DB.connection()){
        } catch (Exception e) {
            return false;
        }
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
                System.out.println("\n\n==== Create new Account ====\n");
                System.out.print("Enter first name: ");
                String firstName = sc.next().trim();
                System.out.print("Enter last name: ");
                String lastName = sc.next().trim();
                Account anAcc = new Account(firstName, lastName);
                anAcc.register();
                break;
            case 2:
                mainMenuA(sc);
                break;
        }
        regOrLog(sc);
        return true;
    }
}
