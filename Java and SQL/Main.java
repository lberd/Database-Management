import java.sql.*;

public class Main
{
  public static void main(String args[])
  {
    String dbRes;
    ResultSet rs;
    Statement stmt;
    
    try
    {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      
      Connection con = DriverManager.getConnection(
          "jdbc:mysql://db4free.net/customersandprod",
          "western195",
          "195password1!");
      if(!con.isClosed())
        System.out.println("***Connect***\n");
      
      stmt = con.createStatement();
      
      System.out.println("*** #1 ***");
      rs=stmt.executeQuery("SELECT fname, lname FROM customerContacts");
      
      while(rs.next())
      {
        dbRes = rs.getString(1) + " " + rs.getString(2);
        System.out.println(dbRes);
      }
      
      System.out.println("\n*** #2 ***");
      rs=stmt.executeQuery("SELECT lower(date_format(now(), '%l:%i%p'))");
      
      while(rs.next())
      {
        dbRes = rs.getString(1);
        System.out.println(dbRes);
      }
      
      System.out.println("\n*** #3 ***");
      rs=stmt.executeQuery("SELECT orders.orderId, quantity, orderLines.productId, "
                          + "substring(products.productName,1,locate(' ',products.productName,1)-1) as 'Product Name', "
                          + "price, round(quantity * price, 2) as 'Extended Price' FROM orders, orderLines, products WHERE "
                          + "orders.orderId = orderLines.orderId and orderLines.productId = products.productId "
                          + "ORDER BY price, orders.orderId");
      
      while(rs.next())
      {
        dbRes = "Order Id: " + rs.getString(1) + " Quantity: " + rs.getString(2) + " ProductId: " + rs.getString(3)
                + " Product Name: " + rs.getString(4) + " Price: " + rs.getString(5) + " Extended Price: " + rs.getString(6);
        System.out.println(dbRes);
      }
      
      System.out.println("\n*** #4 ***");
      rs=stmt.executeQuery("SELECT * from customerContacts");
      ResultSetMetaData md = rs.getMetaData();
      for(int i = 1; i <= md.getColumnCount(); i++)
      System.out.print(md.getColumnName(i) + " | ");
      
      con.close();
      if(con.isClosed())
        System.out.println("\n*** Disconnect ***\n");
    }
    
    catch(SQLException e)
    { e.printStackTrace(); }
    catch(Exception ex)
    { System.out.println("Error: " + ex.getMessage()); }
  }
}