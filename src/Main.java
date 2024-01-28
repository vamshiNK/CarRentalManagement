import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws SQLException, ParseException {
		boolean connection=DBConnect.getconnection();
		if(connection==true)
		{
			System.out.println("*************WELCOME TO ShiftGears****************");
			Scanner sc=new Scanner(System.in);
			int option;
			String choice="y";
			while (choice.equalsIgnoreCase("y"))
			{
				System.out.println("Select 1 For Customer  ");
				System.out.println("Select 2 For CarOwner ");
				option =sc.nextInt();
				switch(option)
				{
				case 1:
					Customer c=new Customer();
					c.display();
					break;
				case 2:
					CarOwner co=new CarOwner();
					co.Display();
					break;
				case 3: System.exit(1);
				break;
				default:
					System.out.println("Entered Invalid Option " + option);
				}
				System.out.println("Do you want to continue? Press 'y' Or press any other key to exit()");
				choice = sc.next();
			}

		}
	}
}
