import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Admin {
	void Login() throws SQLException
	{
		Scanner sc=new Scanner(System.in);
		String uname,password;
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Car", "root", "vamshi1@NK");
		System.out.println("*************WELCOME TO ShiftGears Admin Login****************");
		System.out.println("Enter User Name");
		uname=sc.next();
		System.out.println("Enter Password");
		password=sc.next();
		String sql="select * from admin where auname='"+uname+"' and  password='"+password+"'";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(rs.next())
		{
			System.out.println("Login ");
		}
		else
		{
		System.out.println("Failed to Login");
		}
		
	}
	static void view_cars() throws SQLException
	{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Car", "root", "vamshi1@NK");
		String sql="select *from addcars";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		System.out.println("+--------+---------+------------+------+-------+--------------------+-----------+-------------------+-------------+------------+-----------+------+--------+---------------+\r\n"
				+ "| car_id | Brand   | Model   | year      | color | RegistrationNumber | fuel_type | transmission_type | fromdateStr | todateStr  | city      | coid | status | rent_per_hour |\r\n"
				+ "+--------+---------+---------+-----------+-------+--------------------+-----------+-------------------+-------------+------------+-----------+------+--------+---------------+");
		while(rs.next())
		{
			System.out.println("  "+rs.getInt(1)+"\t  "+rs.getString(2)+"  \t"+rs.getString(3)+"   \t"+rs.getString(4)+"    \t"+rs.getString(5)+"  \t"+rs.getString(6)+"  \t"+rs.getString(7)+"  \t"+rs.getString(8)+"  \t"+rs.getString(9)+"   \t"+rs.getString(10)+"    \t"+rs.getString(11)+"\t"+rs.getInt(12)+"\t"+rs.getBoolean(13)+"\t"+rs.getDouble(14));
		}
	}
	
static void  Booking_list() throws SQLException
{
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Car", "root", "vamshi1@NK");
	String sql="select *from booking_list";
	Statement st = con.createStatement();
	ResultSet rs = st.executeQuery(sql);
	System.out.println("+------------+--------+-------------+-----------------+---------------+------------+--------------+----------------+\r\n"
			+ "| booking_id | car_id | customer_id | booking_date | start_date    | end_date      | total_amount | payment_status |\r\n"
			+ "+------------+--------+-------------+--------------+---------------+---------------+--------------+----------------+");

	while(rs.next())
	{
		System.out.println(rs.getInt(1)+"\t\t"+rs.getInt(2)+"\t"+rs.getInt(3)+"\t\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getDouble(7)+"\t\t"+rs.getBoolean(8));
	}
	System.out.println("+------------+--------+--------------+--------------+---------------+--------------+---------------+----------------+");
}
	public static void main(String[] args) throws SQLException {
		Admin a=new Admin();
		a.Login();
		int option ;
		Scanner sc=new Scanner(System.in);
		System.out.println("1.For view_cars");
		System.out.println("2.Booking_list");
		option=sc.nextInt();
		switch(option)
		{
		
		case 1:view_cars();
			
			break;
		case 2:Booking_list();
			break;
		
		}
	}

}
