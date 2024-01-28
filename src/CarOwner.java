import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CarOwner {
	//Scanner sc;
	int coid;

	void Display() throws SQLException, ParseException
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Welcome TO CarOwner Page");
		int option1;
		String choice="y";
		while (choice.equalsIgnoreCase("y"))
		{
			System.out.println("Select 1 For CarOwner Register ");
			System.out.println("Select 2 For CarOwner Login");
			option1 =sc.nextInt();
			switch(option1)
			{
			case 1:
				Register();
				break;
			case 2:
				Login();
				break;
			case 3: System.exit(1);
			break;
			default:
				System.out.println("Entered Invalid Option " + option1);
			}
			System.out.println("Do you want to continue? Press 'y' Or press any other key to exit()");
			choice = sc.next();
		}
	}
	void Register() throws SQLException
	{
		Scanner sc=new Scanner(System.in);
		sc=new Scanner(System.in);
		String name,username,password,mobile,email;
		System.out.println("Enter Your Name");
		name=sc.next();
		System.out.println("Enter User Name");
		username=sc.next();
		System.out.println("Enter Password");
		password=sc.next();
		System.out.println("Enter mobile number include Country code(+91)");
		mobile=sc.next();
		System.out.println("Enter emailID");
		email=sc.next();
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Car", "root", "vamshi1@NK");
		String sql="insert into carowner (name,username,password,mobile,email) values(?,?,?,?,?)";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, name);
		ps.setString(2, username);
		ps.setString(3, password);
		ps.setString(4, mobile);
		ps.setString(5, email);
		int i=ps.executeUpdate();
		if(i==1)
		{
			System.out.println("Customer data enter to database");

		}
		else 
		{
			System.out.println("Application Failed To sent");
		}

	}
	void Login() throws SQLException, ParseException
	{
		Scanner sc=new Scanner(System.in);
		String username,password;
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Car", "root", "vamshi1@NK");
		System.out.println("Enter User Name");
		username=sc.next();
		System.out.println("Enter Password");
		password=sc.next();
		String sql="select * from carowner where username='"+username+"' and  password='"+password+"'";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(rs.next())
		{
			coid = rs.getInt(1);
			//System.out.println("Welcome To CustomerLogin");
			System.out.println("*************WELCOME TO ShiftGears CarOwner Login****************");
			Verify_documents();

			System.out.println("Add Your Car");
			carowner();
		}
	}
	void Verify_documents()
	{
		System.out.println("Pease verifiy Car Documents");
	}
	void addcars() throws ParseException, SQLException
	{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Car", "root", "vamshi1@NK");
		System.out.println(coid); 
		String Brand,Model,year,color,RegistrationNumber,fuel_type,transmission_type,city;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Brand Name");
		Brand=sc.next();
		String sql1="select *from car_rental_info where brand_name='"+Brand+"'";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql1);
		if(rs.next())
		{
			Double price=rs.getDouble(3);
			//System.out.println(price);
			System.out.println("Enter Model ");
			Model=sc.next();
			System.out.println("Enter year");
			year=sc.next();
			System.out.println("Enter color");
			color=sc.next();
			System.out.println("Enter Registration Number");
			RegistrationNumber=sc.next();
			System.out.println("Enter fuel_type");
			fuel_type=sc.next();
			System.out.println("Enter transmission_type");
			transmission_type=sc.next();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			// Read fromdate from the user
			System.out.print("Enter fromdate (yyyy-MM-dd): ");
			String fromdateStr = sc.next();
			java.util.Date fromdate = dateFormat.parse(fromdateStr);
			// Read todate from the user
			System.out.print("Enter todate (yyyy-MM-dd): ");
			String todateStr = sc.next();
			java.util.Date todate = dateFormat.parse(todateStr);
			System.out.println("Enter City");
			city=sc.next();
			String sql="insert into addcars(Brand,Model,year,color,RegistrationNumber,fuel_type,transmission_type,fromdateStr,todateStr,city,coid,rent_per_hour)values(?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, Brand);

			ps.setString(2, Model);
			ps.setString(3, year);
			ps.setString(4, color);
			ps.setString(5, RegistrationNumber);
			ps.setString(6, fuel_type);
			ps.setString(7, transmission_type);
			ps.setString(8, fromdateStr);
			ps.setString(9, todateStr);
			ps.setString(10, city);
			ps.setInt(11, coid);
			ps.setDouble(12, price);
			int i=ps.executeUpdate();

			if(i==1)
			{
				System.out.println("Entered data success");
			}
			else
			{
				System.out.println("Application Failed To sent database");
			}
		}
		else 
		{
			System.out.println("Brand Not Found");
		}
	}
	void view_car() throws SQLException
	{
		Scanner sc=new Scanner(System.in);
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Car", "root", "vamshi1@NK");
		String sql="select *from addcars where coid='"+coid+"'";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		System.out.println("+--------+---------+---------+------+-------+--------------------+-----------+-------------------+-------------+------------+-----------+------+--------+---------------+\r\n"
				+ "| car_id | Brand   | Model   | year | color | RegistrationNumber | fuel_type | transmission_type | fromdateStr | todateStr  | city      | coid | status | rent_per_hour |\r\n"
				+ "+--------+---------+---------+------+-------+--------------------+-----------+-------------------+-------------+------------+-----------+------+--------+---------------+");
		while(rs.next())
		{
			System.out.println("  "+rs.getInt(1)+"\t "+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7)+"\t"+rs.getString(8)+"\t"+rs.getString(9)+"\t"+rs.getString(10)+"\t"+rs.getString(11)+"\t"+rs.getInt(12)+"\t"+rs.getBoolean(13)+"\t"+rs.getDouble(14));
		}
	}
	void Booking_Car_List() throws SQLException
	{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Car", "root", "vamshi1@NK");
		String sql="select *from booking_list where coid='"+coid+"'";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		System.out.println("+------------+--------+-------------+--------------+------------+------------+--------------+----------------+\r\n"
				+ "| booking_id | car_id | customer_id | booking_date | start_date | end_date   | total_amount | payment_status |\r\n"
				+ "+------------+--------+-------------+--------------+------------+------------+--------------+----------------+");
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"\t\t"+rs.getInt(2)+"\t\t"+rs.getInt(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getDouble(7)+"\t"+rs.getBoolean(8));
		}
	}
	void carowner() throws ParseException, SQLException
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Welcome TO CarOwner Page");
		int option2;
		String choice="y";
		while (choice.equalsIgnoreCase("y"))
		{
			System.out.println("1.Add Car ");
			System.out.println("2.View Your Cars");
			System.out.println("3.Booking Car List");
			System.out.println("4.previwes Menu");
			option2 =sc.nextInt();
			switch(option2)
			{
			case 1: addcars();
			break;
			case 2:view_car();
			break;
			case 3:Booking_Car_List();
			break;
			case 4: Display();
			break;
			default:
				System.out.println("Entered Invalid Option " + option2);
			}
			System.out.println("Do you want to continue? Press 'y' Or press any other key to exit()");
			choice = sc.next();
		}
	}
}
