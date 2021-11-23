import java.sql.*;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Prokopchuk Daniil, Darya Samusenko, Alexandr Strukov
 * @version 1.0
 */
public class Account {
    /**
     * This field is for first name of user
     */
    private String firstName;

    /**
     * This field is for last name of user
     */
    private String lastName;

    /**
     * It is a constructor for account class
     * @param firstName first name
     * @param lastName last name
     */
    public Account(String firstName,String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * This method do all registration processes
     * @return true if success, else false
     */
    public Boolean register() {

        try(Connection c = DB.connection();Statement stmt = c.createStatement();
            Statement stmt2 = c.createStatement();Statement stmt3 = c.createStatement();
            Statement stmt4 = c.createStatement()){
            String sql = "INSERT INTO users VALUES('" + this.firstName + "', '" + this.lastName + "', null)";
            stmt.executeUpdate(sql);
            String sql2 = "SELECT LAST_INSERT_ID()";
            ResultSet rs = stmt2.executeQuery(sql2);
            int last_count_id = 0;
            while (rs.next()) {
                last_count_id = rs.getInt(1);
            }
            String cardNumber = this.generateCardNumber();
            String pin = this.inputPIN();
            String sql3 = "INSERT INTO card VALUES('" + last_count_id + "'" +
                    ", '" + cardNumber + "', '" +  pin + "')";
            stmt3.executeUpdate(sql3);
            String sql4 = "INSERT INTO balance VALUES('" + cardNumber + "', '0')";
            stmt4.executeUpdate(sql4);
            c.close();
            System.out.println("\n\nNew Account was successfully created!\n");
            System.out.println("Card number :" + cardNumber);
            System.out.println("Pin : " + pin);
            return true;
        }catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * This method gets Pin from user
     * @return pin for new user
     */
    public String inputPIN() {
        String pin;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your pin-code: ");
        pin = sc.nextLine();
        return pin;
    }

    /**
     * This method generates new unique card number
     * @return unique card number with 5 symbols
     */
    public String generateCardNumber() {
        String uuid;
        Random rng = new Random();
        int len = 5;
        uuid = "";
        for (int c = 0; c < len; c++) {
            uuid += ((Integer)rng.nextInt(10)).toString();
        }
        return uuid;
    }
}

