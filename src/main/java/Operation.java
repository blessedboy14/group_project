import java.sql.*;
import java.util.Date;

public class Operation {

    private String cardNumber;

    private String pinCode;

    private int balance;

    private Date timestamp;

    private String memo;

    public Operation(String cardNumber, String pinCode) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.timestamp = new Date();
        this.memo = "";
    }

    public int showBalance (String cardNumber) {
        try(Connection c = DB.connection();
            Statement stmt5 = c.createStatement()) {
            String sql5 = "SELECT * FROM balance WHERE cardNumber='"+ cardNumber + "'";
            ResultSet rs = stmt5.executeQuery(sql5);
            while (rs.next()) {
                this.balance = rs.getInt(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.balance;
    }

    public boolean deposit(int amount, String cardNumber, String memo){
        try (Connection c = DB.connection();
            Statement stmt6 = c.createStatement();
            Statement stmt = c.createStatement()){
            String sql6 = "UPDATE balance SET balance= balance + '" + amount + "' WHERE cardNumber='" +
                    cardNumber + "'";
            stmt6.executeUpdate(sql6);
            String sql = "INSERT INTO transactions VALUES(null,  NOW() , '" + memo + "', '"
            + amount + "', '" + cardNumber + "')";
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendToOther(int amount_other, String number_other, String cardNumber, String memo) {
        try(Connection c = DB.connection();
            Statement stmt7 = c.createStatement();
            Statement stmt8 = c.createStatement();
            Statement stmt = c.createStatement()) {
            String sql7 = "UPDATE balance SET balance = balance + '" + amount_other + "' WHERE cardNumber='" +
                    number_other + "'";
            stmt7.executeUpdate(sql7);
            String sql8 = "UPDATE balance SET balance = balance - '" + amount_other + "' WHERE cardNumber='" +
                    cardNumber + "'";
            stmt8.executeUpdate(sql8);
            amount_other = -amount_other;
            String sql = "INSERT INTO transactions VALUES(null,  NOW() , '" + memo + "', '"
                    + amount_other + "', '" + cardNumber + "')";
            stmt.executeUpdate(sql);
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean refill(int amount, String cardNumber, String memo) {
        try(Connection c = DB.connection();
            Statement stmt = c.createStatement();
            Statement stmt2 = c.createStatement()){
            String sql = "UPDATE balance SET balance = balance - '" + amount + "' WHERE cardNumber='" +
                    cardNumber + "'";
            stmt.executeUpdate(sql);
            amount = -amount;
            String sql2 = "INSERT INTO transactions VALUES(null,  NOW() , '" + memo + "', '"
                    + amount + "', '" + cardNumber + "')";
            stmt2.executeUpdate(sql2);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showTransHistory(String cardNumber) {
        try(Connection c = DB.connection();
            Statement stmt = c.createStatement()){
            String sql = "SELECT * FROM transactions WHERE cardNumber='" + cardNumber + "' ORDER BY datetime DESC";
            ResultSet rs = stmt.executeQuery(sql);
            String result = "\n";
            int count = 0;
            while(rs.next() && count < 5){
                result += rs.getString(2) + "  " + rs.getString(3) + "  " + rs.getString(4)
                        + "$\n";
                count++;
            }
            System.out.print(result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
