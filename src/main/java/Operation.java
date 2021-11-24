import java.sql.*;
import java.util.Date;

/**
 * @author Prokopchuk Daniil, Darya Samusenko, Alexander Strukov
 * @version 1.0
 */
public class Operation {

    /**
     * This is card number for operations
     */
    private String cardNumber;

    /**
     * This is pin code number for operations
     */
    private String pinCode;

    /**
     * This is balance field for operations
     */
    private int balance; 

    /**
     * This is datetime field for operations
     */
    private Date timestamp;

    /**
     * This is memo field for operations
     */
    private String memo;

    /**
     * This is a constructor for Operation class
     * @param cardNumber card number of user
     * @param pinCode pin code of user
     */
    public Operation(String cardNumber, String pinCode) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.timestamp = new Date();
        this.memo = "";
    }

    /**
     * This method takes balance from DB
     * @param cardNumber card number of user
     * @return current user balance
     */
    public int showBalance (String cardNumber) {
        try(Connection c = DB.connection();
            Statement stmt5 = c.createStatement()){
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

    /**
     * This method makes deposit to chosen card
     * @param amount amount to deposit
     * @param cardNumber card number of current user
     * @param memo message with this transaction
     * @return true if success and false if error
     */
    public boolean deposit(int amount, String cardNumber, String memo){
        try(Connection c = DB.connection();
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

    /**
     * This method makes transfer to other card owner
     * @param amount_other amount to send to other guy
     * @param number_other card number of other user
     * @param cardNumber current user card number
     * @param memo message with this transaction
     * @return  true if success and false if error
     */
    public boolean sendToOther(int amountOther, String numberOther, String cardNumber, String memo) {
        try(Connection c = DB.connection();
            Statement stmt7 = c.createStatement();
            Statement stmt8 = c.createStatement();
            Statement stmt = c.createStatement()){
            String sql7 = "UPDATE balance SET balance = balance + '" + amountOther + "' WHERE cardNumber='" +
                    numberOther + "'";
            stmt7.executeUpdate(sql7);
            String sql8 = "UPDATE balance SET balance = balance - '" + amountOther + "' WHERE cardNumber='" +
                    cardNumber + "'";
            stmt8.executeUpdate(sql8);
            amountOther = -amountOther;
            String sql = "INSERT INTO transactions VALUES(null,  NOW() , '" + memo + "', '"
                    + amountOther + "', '" + cardNumber + "')";
            stmt.executeUpdate(sql);
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method helps user to withdrawal his money from card
     * @param amount amount to withdrawal from your card
     * @param cardNumber card number of current user
     * @param memo message with this transaction
     * @return true if success and false if error
     */
    public boolean withdrawal(int amount, String cardNumber, String memo) {
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

    /**
     * This method returns user transactions history(only 5 last transactions)
     * @param cardNumber card number of current user
     * @return true if success and false if error
     */
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
