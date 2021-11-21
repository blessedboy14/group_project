import java.sql.*;

public class DB {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/user_schema";
    public static final String DB_Driver = "com.mysql.cj.jdbc.Driver";

    public static final String User = "root";
    public static final String Pin = "root";

    public static java.sql.Connection connection() throws Exception {
        Class.forName(DB_Driver); //Проверяем наличие JDBC драйвера для работы с БД
        return DriverManager.getConnection(DB_URL, User, Pin);//соединениесБД
    }
}
