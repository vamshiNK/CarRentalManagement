
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
public class Customer
{
	int cid;
	void customerRegister() throws SQLException
	{
		Scanner sc=new Scanner(System.in);
		String fname,lname,uname,password,mobile,email,Dl;
		System.out.println("Enter Your Frist Name");
		fname=sc.next();
		System.out.println("Enter Your Last Name");
		lname=sc.next();
		System.out.println("Enter User Name");
		uname=sc.next();
		System.out.println("Enter Password");
		password=sc.next();
		System.out.println("Enter mobile number include Country code(+91)");
		mobile=sc.next();
		System.out.println("Enter emailID");
		email=sc.next();
		System.out.println("Enter Driving Licince Number");
		Dl=sc.next();
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Car", "root", "vamshi1@NK");
		String insertSql = "INSERT INTO customer (fname, lname, uname, password, mobile, email, Dl) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(insertSql);
		ps.setString(1, fname);
		ps.setString(2, lname);
		ps.setString(3, uname);
		ps.setString(4, password);
		ps.setString(5, mobile);
		ps.setString(6, email);
		ps.setString(7, Dl);
		int i = ps.executeUpdate();
		if(i==1)
		{
			System.out.println("Application  To sent Success");
		}
		else
		{
			System.out.println("Application Failed To sent");
		}
		//System.out.println("Welcome To CustomerRegister");
	}
	void customerLogin() throws SQLException, ParseException 
	{
		Scanner sc=new Scanner(System.in);
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Car", "root", "vamshi1@NK");
		String cusername,cpassword;
		System.out.println("Enter User Name");
		cusername=sc.next();
		System.out.println("Enter Password");
		cpassword=sc.next();
		String sql="select * from customer where  uname='"+cusername+"' and  password='"+cpassword+"'";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(rs.next())
		{
			cid = rs.getInt(1);
			//System.out.println("Welcome To CustomerLogin");
			System.out.println("*************WELCOME TO ShiftGears Customer Login****************");
			System.out.println("Verifiy Documents DL, GovtID");
			//System.out.println("Book Car");
			customer();
		}
		else
		{
			System.out.println("Failed to Login");
		}
	}
	void bookcar() throws SQLException, ParseException
	{ 
		int coid = 0;
		Scanner sc=new Scanner(System.in);
		Boolean flag = false;
		int carid;
		String city;
		double price=0,hr=24,days_difference=0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Read fromdate from the user
		System.out.println("Enter City Name To Check Available Car");
		city=sc.next();
		System.out.print("Enter fromdate (yyyy-MM-dd): ");
		String fromdateStr = sc.next();
		java.util.Date fromdate = dateFormat.parse(fromdateStr);
		// Read todate from the user
		System.out.print("Enter todate (yyyy-MM-dd): ");
		String todateStr = sc.next();
		java.util.Date todate = dateFormat.parse(todateStr);
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Car", "root", "vamshi1@NK");
		String sql="select *from addcars where status = '"+flag+"'and city='"+city+"'and (fromdateStr <= '"+fromdateStr+"' AND todateStr >= '"+todateStr+"');";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		System.out.println("+--------+---------+------------+------+-------+--------------------+-----------+-------------------+-------------+------------+-----------+------+--------+---------------+\r\n"
				+ "| car_id | Brand   | Model   | year      | color | RegistrationNumber | fuel_type | transmission_type | fromdateStr | todateStr  | city      | coid | status | rent_per_hour |\r\n"
				+ "+--------+---------+---------+-----------+-------+--------------------+-----------+-------------------+-------------+------------+-----------+------+--------+---------------+");
		while(rs.next())
		{
			System.out.println("  "+rs.getInt(1)+"\t  "+rs.getString(2)+"  \t"+rs.getString(3)+"   \t"+rs.getString(4)+"    \t"+rs.getString(5)+"  \t"+rs.getString(6)+"  \t"+rs.getString(7)+"  \t"+rs.getString(8)+"  \t"+rs.getString(9)+"   \t"+rs.getString(10)+"    \t"+rs.getString(11)+"\t"+rs.getInt(12)+"\t"+rs.getBoolean(13)+"\t"+rs.getDouble(14));
		}
		//ResultSet rs0=ps.executeQuery(sql);
		//		Statement st = con.createStatement();
		//		ResultSet rs0 = st.executeQuery(sql);
		//		if(rs0.next())

		System.out.println("Enter Car_id to Book The Car");
		carid=sc.nextInt();//8
		String sql5="select *from booking_list where customer_id='"+cid+"'";
		Statement st5= con.createStatement();
		ResultSet rs7 = st5.executeQuery(sql5);
		if(rs7.next())
		{
			System.out.println("You're not applicable to book a car");
		}
		else
		{
			System.out.println("You're Eligible to Book a car");
			System.out.println("Car Booked Successfully");
			String sql6="select *from addcars where car_id='"+carid+"'";
			Statement st1 = con.createStatement();
			ResultSet rs0 = st1.executeQuery(sql6);
			if(rs0.next())
			{

				//flag=true;
				coid=rs0.getInt(12);
				price=rs0.getDouble(14);
				System.out.println(price);
				String sql3="SELECT car_id,year,DATEDIFF('"+todateStr+"','"+fromdateStr+"') AS days_difference FROM addcars where car_id='"+carid+"'";
				Statement st12 = con.createStatement();
				ResultSet rs1 = st12.executeQuery(sql3);
				if(rs1.next())
				{
					days_difference=rs1.getDouble(3);
					System.out.println(days_difference);
					//System.out.println(price);
					price=price*hr*days_difference;
					System.out.println(price);
				}
				String sql1="update addcars set status='"+1+"' where car_id='"+carid+"' ";
				PreparedStatement ps1 = con.prepareStatement(sql1);
				int i=ps1.executeUpdate();
			}
			String sql4="INSERT INTO booking_list (car_id,customer_id,start_date,end_date,total_amount,coid) values(?,?,?,?,?,?)";
			PreparedStatement ps2 = con.prepareStatement(sql4);
			ps2.setInt(1, carid);
			ps2.setInt(2,cid);
			ps2.setString(3, fromdateStr);
			ps2.setString(4, todateStr);
			ps2.setDouble(5, price);
			ps2.setInt(6,coid);
			int j=ps2.executeUpdate();
			if(j==1)
			{

				System.out.println("Data entered to booking list");
			}
			else
			{
				System.out.println("Data not entered to booking list");	
			}
		}
        }

	void View_booking() throws SQLException, ParseException
	{
		Scanner sc=new Scanner(System.in);
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Car", "root", "vamshi1@NK");
		String sql="select *from booking_list where customer_id='"+cid+"'";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		if(rs.next())
		{
			System.out.println("+------------+--------+-------------+--------------+------------+------------+--------------+----------------+\r\n"
				+ "| booking_id | car_id | customer_id | booking_date | start_date | end_date   | total_amount | payment_status |\r\n"
				+ "+------------+--------+-------------+--------------+------------+------------+--------------+----------------+");

			
			System.out.println(rs.getInt(1)+"\t\t"+rs.getInt(2)+"\t\t"+rs.getInt(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getDouble(7)+"\t"+rs.getBoolean(8));
				System.out.println("+------------+--------+-------------+--------------+------------+------------+--------------+----------------+");

		}
		else 
		{
			System.out.println("You have not booked any car yet");
			//Scanner sc1=new Scanner(System.in);
			int option;
			System.out.println("1.Book a car");
			System.out.println("2. previews menu");
			option=sc.nextInt();
			
		switch(option)
			{
			case 1:bookcar();
			break;
			case 2:display();
			break;
			}
			
		}
	}
	void display() throws SQLException, ParseException
	{
		System.out.println("*************WELCOME TO ShiftGears Customer ****************");
		Scanner sc=new Scanner(System.in);
		int option1;
		String choice1="y";
		while (choice1.equalsIgnoreCase("y"))
		{
			System.out.println("Select 1 For CustomerRegister ");
			System.out.println("Select 2 For Customer Login");
			//System.out.println("Select 3 To View Your booking");
			option1 =sc.nextInt();
			boolean exit = false;
			switch(option1)
			{
			case 1:
				customerRegister();
				break;
			case 2:
				customerLogin();
				exit = true;
				break;
				//			case 3:
				//				View_booking();
				//				exit = true;
				//				break;
			default:
				System.out.println("Entered Invalid Option " + option1);
			}
			System.out.println("Do you want to continue? Press 'y' Or press any other key to exit()");
			choice1 = sc.next();
		}
	}
	void customer() throws SQLException, ParseException
	{
		Scanner sc=new Scanner(System.in);
		int option2;
		String choice2="y";
		while (choice2.equalsIgnoreCase("y"))
		{
			System.out.println("1.Book A Car ");
			System.out.println("2.To View Your booking");
			System.out.println("3.Return Car");
			System.out.println("4.Cancel Booked Car");
			System.out.println("5. previews menu");
			option2 =sc.nextInt();
			boolean exit = false;
			switch(option2)
			{
			case 1:
				bookcar();
				break;
			case 2:
				View_booking();
				break;
			case 3: 
				break;
			case 4:
			break;
			case 5:display();
			exit = true;
			break;
			}
			System.out.println("Do you want to continue? Press 'y' Or press any other key to exit()");
			choice2 = sc.next();
		}
	}
	void payment()
	{

	}
}


