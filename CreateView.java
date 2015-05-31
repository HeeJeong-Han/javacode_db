//1315002 강지원
package KANGHANNAYOUNG;
//View를 생성하는 메소드
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateView {
	Test dbdb = new Test();
	Connection dbConnection=null;
	Statement stmt = null;


	//CreateView class의 constructor
	public CreateView(){
		createEwhaView();
		createShinchonView();
		createHongdaeView();
	}

	
	//Ewha에 위치한 식당 이름 저장하는 view table 생성하는 메소드
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



	
	//Shinchon에 위치한 식당 이름 저장하는 view table 생성하는 메소드
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





	
	//Hongdae에 위치한 식당 이름 저장하는 view table 생성하는 메소드
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
