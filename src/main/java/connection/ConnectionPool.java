package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
   static Connection conn = null;

   private ConnectionPool() {
   }

   public static Connection getConnection() throws SQLException, ClassNotFoundException {
      //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      //String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=E:\\HospitalCareDB\\HospitalCare.accdb";
      String url = "jdbc:ucanaccess://C:\\Code\\InvoiceGenNew\\InvoiceGenConfig\\HospitalCare.accdb";
      if (conn == null) {
         conn = DriverManager.getConnection(url, "", "");
         conn.setAutoCommit(false);
      }

      return conn;
   }
}
