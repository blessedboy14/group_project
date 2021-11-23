import java.sql.*;

/**
 * @author Prokopchuk Daniil, Darya Samusenko, Alexander Strukov
 * @version 1.0
 */
public class DB {

    /**
     * This is url for database
     */
    public static final String DB_URL = "jdbc:mysql://localhost:3306/user_schema";

    /**
     * This is driver for database
     */
    public static final String DB_Driver = "com.mysql.cj.jdbc.Driver";

    /**
     * This is username for database
     */
    public static final String User = "root";

    /**
     * This is pin for database access
     */
    public static final String Pin = "root";

    /**
     * This method make connection with dataBase mysql
     * @return Connection object, which makes connection to database
     * @throws Exception if database is not found
     */
    public static java.sql.Connection connection() throws Exception {
        Class.forName(DB_Driver); //Проверяем наличие JDBC драйвера для работы с БД
        return DriverManager.getConnection(DB_URL, User, Pin);//соединениесБД
    }
}
