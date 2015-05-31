//1315002 ������
package KANGHANNAYOUNG;
//View�� �����ϴ� �޼ҵ�
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateView {
	Test dbdb = new Test();
	Connection dbConnection=null;
	Statement stmt = null;


	//CreateView class�� constructor
	public CreateView(){
		createEwhaView();
		createShinchonView();
		createHongdaeView();
	}

	
	//Ewha�� ��ġ�� �Ĵ� �̸� �����ϴ� view table �����ϴ� �޼ҵ�
	public void createEwhaView(){

		String createStmt="CREATE VIEW DBCOURSE_location_Ewha AS (SELECT restaurant_name FROM DBCOURSE_restaurant NATURAL JOIN DBCOURSE_location WHERE location='ewha')";
		try{

			dbConnection=Test.getDBConnection();
			stmt = dbConnection.createStatement();

			//use database
			String UseSQL = "USE RestaurantDB";
			stmt.executeUpdate(UseSQL);



			Statement stmt02=dbConnection.createStatement();
			stmt02.executeUpdate(createStmt);
			
		}

		catch(SQLException excp){
			System.out.println("Error in create Ewha view");
		}

	}//createEwhaView ends



	
	//Shinchon�� ��ġ�� �Ĵ� �̸� �����ϴ� view table �����ϴ� �޼ҵ�
	public void createShinchonView(){

		String createStmt="CREATE VIEW DBCOURSE_location_Shinchon AS (SELECT restaurant_name FROM DBCOURSE_restaurant NATURAL JOIN DBCOURSE_location WHERE location='Shinchon')";
		try{

			dbConnection=dbdb.getDBConnection();
			stmt = dbConnection.createStatement();

			//use database
			String UseSQL = "USE RestaurantDB";
			stmt.executeUpdate(UseSQL);



			Statement stmt02=dbConnection.createStatement();
			stmt02.executeUpdate(createStmt);

		}
		catch(SQLException excp){
			System.out.println("Error in create Shinchon view");
		}

	}//createShinchonView ends





	
	//Hongdae�� ��ġ�� �Ĵ� �̸� �����ϴ� view table �����ϴ� �޼ҵ�
	public void createHongdaeView(){

		String createStmt="CREATE VIEW DBCOURSE_location_Hongdae AS (SELECT restaurant_name FROM DBCOURSE_restaurant NATURAL JOIN DBCOURSE_location WHERE location='Hongdae')";
		try{

			dbConnection=dbdb.getDBConnection();
			stmt = dbConnection.createStatement();

			//use database
			String UseSQL = "USE RestaurantDB";
			stmt.executeUpdate(UseSQL);



			Statement stmt02=dbConnection.createStatement();
			stmt02.executeUpdate(createStmt);

		}

		catch(SQLException excp){
			System.out.println("Error in create Hongdae view");
		}

	}//createHongdaeView ends

}
