package Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {

    Connection con = null;

    public Connection GetConnectDB() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=HMSP";
            String user = "sa"; // Đảm bảo rằng bạn đang sử dụng đúng tên đăng nhập
            String password = "1"; // Đảm bảo rằng bạn đang sử dụng đúng mật khẩu
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connect Successful");
        } catch (Exception e) {
            System.out.println("Can't connect: " + e.getMessage());
        }
        return con;
    }
    
    public static void main(String[] args) {
        ConnectDB db = new ConnectDB();
        db.GetConnectDB();
    }
}
