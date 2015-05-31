//1215129 �ѳ���
package KANGHANNAYOUNG;
//����ڷκ��� Configuration ���ϰ� SQL script�� ������ �о���� Ŭ����
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
public class Test {
	
	//Configuration ������ �о�� ���� ����
	private static String DRIVER_NAME;
	private static String URL;
	private static String USER ;
	private static String PASSWORD;
	
	private static String INSTRUCTIONS = new String();
	
	
	//txt file�κ��� configuration ������ �о���� �޼ҵ�
	public static void readConfig() throws FileNotFoundException{
		String FILEPATH;

		System.out.println("Enter the path of configuration file.");
		Scanner scanner=new Scanner(System.in);
		FILEPATH=scanner.next();


		//initialize scanner to get string from txt file 
		Scanner s = new Scanner(new File(FILEPATH));

		//initialize arraylist to store string from txt file
		ArrayList<String> list = new ArrayList<String>();

		//read line by line and add to arraylist
		while (s.hasNextLine()){
			list.add(s.next());
		}

		//configuration ���Ϸκ��� ���ʷ� Driver �̸�, driver���, username, userpassword������ �о�´�.
		DRIVER_NAME=list.get(0);
		URL=list.get(1);
		USER=list.get(2);
		PASSWORD=list.get(3);

		//close scanner
		s.close();
		
	}//readConfig ends
	
	
	
	public static Connection getDBConnection() throws SQLException
	{
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
	
	
	//SQLscript�� �а� db�� �����ϴ� �޼ҵ�
	public static void MakeDB() throws SQLException
	{
		String s = new String();
		StringBuffer sb = new StringBuffer();
		Reader reader;
		try
		{	
			//SQLscript�� ��θ� �޾ƿ� ���� ���� 
			String FILEPATH;
			System.out.println("Enter the path of sql script file.");
			Scanner scanner=new Scanner(System.in);
			FILEPATH=scanner.next();
			
			reader= new FileReader(new File(FILEPATH));
			
			
			BufferedReader br = new BufferedReader(reader);		
			while((s = br.readLine()) != null)
			{

				sb.append(s);
			}
			br.close();
			
			//SQLscript�� �� �� �� �޾ƿ� String �迭 ����
			String[] inst = sb.toString().split(";");
			Connection c = Test.getDBConnection();
			Statement st = c.createStatement();
			
			System.out.println("Now loading... please wait");
			
			//String �迭�� �޾ƿ� script�� ���� �� ����
			for(int i = 0; i<inst.length; i++)
			{
				
				if(!inst[i].trim().equals(""))
				{
					st.executeUpdate(inst[i]);
					
				}
		}
			
		}
		catch(Exception e)
		{
			System.out.println("*** Error : "+e.toString());
			System.out.println("*** ");
			System.out.println("*** Error : ");
			e.printStackTrace();
			System.out.println("################################################");
			System.out.println(sb.toString());
		}
	}

}


//class ends