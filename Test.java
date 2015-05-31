//1215129 한나영
package KANGHANNAYOUNG;
//사용자로부터 Configuration 파일과 SQL script의 정보를 읽어오는 클래스
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
	
	//Configuration 정보를 읽어올 변수 선언
	private static String DRIVER_NAME;
	private static String URL;
	private static String USER ;
	private static String PASSWORD;
	
	private static String INSTRUCTIONS = new String();
	
	
	//txt file로부터 configuration 정보를 읽어오는 메소드
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

		//configuration 파일로부터 차례로 Driver 이름, driver경로, username, userpassword정보를 읽어온다.
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
	
	
	//SQLscript를 읽고 db를 생성하는 메소드
	public static void MakeDB() throws SQLException
	{
		String s = new String();
		StringBuffer sb = new StringBuffer();
		Reader reader;
		try
		{	
			//SQLscript의 경로를 받아올 변수 선언 
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
			
			//SQLscript를 한 줄 씩 받아올 String 배열 선언
			String[] inst = sb.toString().split(";");
			Connection c = Test.getDBConnection();
			Statement st = c.createStatement();
			
			System.out.println("Now loading... please wait");
			
			//String 배열에 받아온 script를 한줄 씩 실행
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