import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnect {
	static boolean getconnection() throws SQLException
	{
		try 
		{
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Car", "root", "vamshi1@NK");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return true;
	}
}