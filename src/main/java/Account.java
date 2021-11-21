import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Account {

    private String firstName;

    private String lastName;

    public Account(String firstName,String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Boolean register() {

        try{
            Connection c = DB.connection();
            Statement stmt;
            try{
                stmt = c.createStatement();
            } catch(Exception e) {
                e.printStackTrace();
            }
            String sql = "INSERT INTO users VALUES('" + this.firstName + "', '" + this.lastName + "', null)";
            stmt.executeUpdate(sql);
            Statement stmt2;
            try{
                stmt2 = c.createStatement();
            } catch(Exception e) {
                e.printStackTrace();
            }
            String sql2 = "SELECT LAST_INSERT_ID()";
            ResultSet rs = stmt2.executeQuery(sql2);
            int lastCountId = 0;
            while (rs.next()) {
                lastCountId = rs.getInt(1);
            }
            String cardNumber = this.generateCardNumber();
            String pin = this.inputPIN();
            Statement stmt3;
            try{
                stmt3 = c.createStatement();
            } catch(Exception e) {
                e.printStackTrace();
            }
            String sql3 = "INSERT INTO card VALUES('" + lastCountId + "'" +
                    ", '" + cardNumber + "', '" +  pin + "')";
            stmt3.executeUpdate(sql3);
            Statement stmt4;
            try{
                stmt4 = c.createStatement();
            } catch(Exception e) {
                e.printStackTrace();
            }
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

    public String inputPIN() {
        String pin;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your pin-code: ");
        pin = sc.nextLine();
        return pin;
    }

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
