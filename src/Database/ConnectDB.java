
package Database;


import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectDB {
       Connection con=null;
       
 public Connection GetConnectDB(){
        try{
       
            String url="jdbc:sqlserver://localhost:1433;databaseName=Servicestest";
            String user="sa";
            String password="1";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con= DriverManager.getConnection(url,user,password);
            System.out.println("Connect Sucessful");
    }catch(Exception e){
            System.out.println("Cant connect: " + e.getMessage());
    }
        return con;
}
    
    public static void main(String[] args) {
        ConnectDB db= new  ConnectDB();
        db.GetConnectDB();
    }
     
}

