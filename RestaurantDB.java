package KANGHANNAYOUNG;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.Scanner;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.Reader;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.Reader;
	public class RestaurantDB {
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "epdlxjqpdltm6^";

	/*
	 * getDBConnection: connect to database
	 * DO NOT MODIFY!!
	 */
	static Connection getDBConnection() {
		Connection dbConnection = null;

		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);

			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return dbConnection;
	}
	//
	//
	//
	//query start
	//
	//
	/*
	 * getRestaurantByReservation: 여는 시간, 닫는 시간, 지역명을 각각 입력받아 해당 요구조건에 부합하는 음식점 이름들을 배열 형식으로 반환합니다.
	 *                             배열은 array[0], array[1]... 이런식으로 접근하면 안.되.고!
	 *                             array.get(0), array.get(1)... 이런식으로 하여야만 하옵니다.                            
	 */
	
	/*
	public static ArrayList<String> getRestaurantByReservation(int opening, int closing, String location) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		ArrayList<String> restaurant_name = new ArrayList<String>();
		
		//지원
		Statement stmt = null;
		
		
		String selectTableSQL = "SELECT restaurant_name "
				+ "FROM Working_hours NATURAL JOIN Service "
				+ "WHERE opening <= ? AND closing >= ? AND reservation = 'Y' AND "
				+ "restaurant_name IN ( SELECT restaurant_name "
				+ "FROM Restaurant, Location "
				+ "WHERE Restaurant.address = Location.address AND Location.location = ?)";

		try {
			dbConnection = getDBConnection();
			
			
			
			//지원
			stmt = dbConnection.createStatement();
			 //use database
	         String UseSQL = "USE RestaurantDB";
	         stmt.executeUpdate(UseSQL);
	         System.out.println("USE DATABASE done...");

			
	         
	         
			dbConnection.setAutoCommit(false);

			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setInt(1, opening);
			preparedStatementSelect.setInt(2, closing);
			preparedStatementSelect.setString(3, location);

			rs = preparedStatementSelect.executeQuery();

			while (rs.next()) {
				restaurant_name.add(rs.getString("restaurant_name"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			dbConnection.rollback();
		} finally {
			if (preparedStatementSelect != null)
				preparedStatementSelect.close();
			if (dbConnection != null)
				dbConnection.close();
		}

		return restaurant_name;
	}
	
	*/

	/*
	 * getSingleSeats: 지역명을 기준으로 해당하는 지역에 있는 음식점들의 실내, 실외 1인 테이블 개수들을 반환합니다.
	 *                 각각 getName(), getInside(), getOutside() 멤버 함수를 이용하여 구할 수 있습니다.
	 */
	/*public ArrayList<AvailableSeat> getSingleSeats(String location) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		ArrayList<AvailableSeat> availableSeat = new ArrayList<AvailableSeat>();
		String selectTableSQL = "SELECT restaurant_name, inside_seat_available.single_seat, outside_seat_available.single_seat"
				+ "FROM restaruant NATURAL JOIN inside_seat_available NATURAL JOIN outside_seat_available"
				+ "WHERE EXISTS (SELECT restaurant_name"
				+ "FROM restaurant, location"
				+ "WHERE restaurant.address = location.address AND location.location = ?)";

		try {
			dbConnection = getDBConnection();
			dbConnection.setAutoCommit(false);

			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setString(1, location);

			rs = preparedStatementSelect.executeQuery();

			while (rs.next()) {
				availableSeat.add(new AvailableSeat(rs.getString("restaurant_name"),
						rs.getInt("inside_seat_available.single_seat"),
						rs.getInt("outside_seat_available.single_seat")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			dbConnection.rollback();
		} finally {
			if (preparedStatementSelect != null)
				preparedStatementSelect.close();
			if (dbConnection != null)
				dbConnection.close();
		}

		return availableSeat;
	}
*/
	/*
	 * getDoubleSeats: name, inside, outside
	 */
/*	public ArrayList<AvailableSeat> getDoubleSeats(String location) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		ArrayList<AvailableSeat> availableSeat = new ArrayList<AvailableSeat>();
		String selectTableSQL = "SELECT restaurant_name, inside_seat_group.double_seat, outside_seat_group.double_seat"
				+ "FROM restaruant NATURAL JOIN inside_seat_group NATURAL JOIN outside_seat_group"
				+ "WHERE EXISTS (SELECT restaurant_name"
				+ "FROM restaurant, locatiion"
				+ "WHERE restaurant.address = location.address AND location.location = ?)";

		try {
			dbConnection = getDBConnection();
			dbConnection.setAutoCommit(false);

			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setString(1, location);

			rs = preparedStatementSelect.executeQuery();

			while (rs.next()) {
				availableSeat.add(new AvailableSeat(rs.getString("restaurant_name"),
						rs.getInt("inside_seat_group.double_seat"),
						rs.getInt("outside_seat_group.double_seat")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			dbConnection.rollback();
		} finally {
			if (preparedStatementSelect != null)
				preparedStatementSelect.close();
			if (dbConnection != null)
				dbConnection.close();
		}

		return availableSeat;
	}


*/
	public void getRestaurantByReservation_simple(int opening, int closing) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		String restaurant_name;

		String selectTableSQL = "SELECT restaurant_name "
				+ "FROM Working_hours NATURAL JOIN Service "
				+ "WHERE opening <= ? AND closing >= ? AND reservation = 'Y'";

		try {
			dbConnection = getDBConnection();
			dbConnection.setAutoCommit(false);

			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setInt(1, opening);
			preparedStatementSelect.setInt(2, closing);

			rs = preparedStatementSelect.executeQuery();



			while (rs.next()) {
				restaurant_name = rs.getString("restaurant_name");
				System.out.println(restaurant_name);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			dbConnection.rollback();
		} finally {
			if (preparedStatementSelect != null)
				preparedStatementSelect.close();
			if (dbConnection != null)
				dbConnection.close();
		}
	}

	//add query like this^^^^^
	//add other query function here...
	//...
	//...
	//query end

	public static void main(String[] args) throws SQLException {
		insertTest();
		
	

		RestaurantDB db = new RestaurantDB();	// Create instance. DO NOT MODIFY

		
	}

	//insert part start

	public static void insertTest() throws SQLException {

Connection dbConnection = null;

		Statement stmt = null;

		try {

			dbConnection = getDBConnection();

			stmt = dbConnection.createStatement();
			
			//create database
	         String CreateSQL = "CREATE DATABASE RestaurantDB";
	         stmt.executeUpdate(CreateSQL);
	         System.out.println("CREATE DATABASE done...");
	         
	         //use database
	         String UseSQL = "USE RestaurantDB";
	         stmt.executeUpdate(UseSQL);
	         System.out.println("USE DATABASE done...");



			//테이블만 생성. foreign key 나중에 추가

			//String createDB="CREATE DATABASE TEST";
			//stmt.execute(createDB);
			//Working_hours테이블 생성. foreign key 나중에 추가

			String sql1 = "CREATE TABLE Working_hours "+

                    "(restaurant_name VARCHAR(25) NOT NULL, " +

                    "opening int, " +

                    "closing  int, " +

                    "PRIMARY KEY (restaurant_name)) ";

			System.out.println("Working hours table created.");



			//Company테이블 생성

			String sql2 = "CREATE TABLE Company"+

                    "(company VARCHAR(20) NOT NULL, " +

                    "category VARCHAR(15), " +

                    "PRIMARY KEY (company)) ";

			System.out.println("company table created.");



			//Location테이블 생성

			String sql3 = "CREATE TABLE Location"+

                    "(address VARCHAR(30), " +

                    "location VARCHAR(10), " +

                    "PRIMARY KEY (address)) ";

			System.out.println("Location table created.");



			//seat_available 테이블 생성

			String sql4="CREATE TABLE Seat_available"+

                    "(size CHAR(1), "+

                    "inside_single_seat int, "+

                    "inside_group_seat int, "+

                    "outside_single_seat int, "+

                    "outside_group_seat int, "+

                    "PRIMARY KEY (size)) ";

			System.out.println("Seat_available table created.");



			//Restaurant테이블 생성. 이후에 foreign key 추가해야함

			String sql5 = "CREATE TABLE Restaurant"+

                    "(restaurant_name VARCHAR(25) NOT NULL, " +

                    "address VARCHAR(30), " +

                    "phonenum VARCHAR(20), " +

                    "company VARCHAR(20), "+

                    "size CHAR(1), "+

                    "PRIMARY KEY (restaurant_name)) ";

			System.out.println("Restaurant table created.");


			//Service 테이블 생성 이후에 foreign key 추가

			String sql6 = "CREATE TABLE Service"+

                    "(restaurant_name VARCHAR(25) NOT NULL, " +

                    "reservation CHAR(1), " +

                    "delivery_possible CHAR(1), " +

                    "takeout CHAR(1), "+

                    "coupon CHAR(1), "+

                    "PRIMARY KEY (restaurant_name)) ";

			System.out.println("Service table created.");



			//Menu 테이블 생성 이후에 foreign key 추가

			String sql7 = "CREATE TABLE Menu "+

                    "(company VARCHAR(20), " +

                    "menu_name VARCHAR(30), " +

                    "price int, " +

                    "PRIMARY KEY (menu_name)) ";

			System.out.println("Menu table created.");



			//Service_Evaluation 테이블 생성 이후에 foreign key 추가

			String sql8 = "CREATE TABLE Service_Evaluation "+

                    "(restaurant_name VARCHAR(25) NOT NULL, " +

                    "clean int, " +

                    "waiting_time int, " +

                    "kindness int, "+

                    "parking int, "+

                    "PRIMARY KEY (restaurant_name)) ";

			System.out.println("Service_Evaluation table created.");


			//Inside_seat_group테이블 생성 이후에 foreign key 추가

			String sql9 = "CREATE TABLE Inside_seat_group "+

                    "(size CHAR(1), " +

                    "double_seat int, " +

                    "quad_seat int, " +

                    "PRIMARY KEY (size)) ";

			System.out.println("Inside_seat_group table created.");



			//Outside_seat_group테이블 생성 이후에 foreign key 추가

			String sql10 = "CREATE TABLE Outside_seat_group "+

                    "(size CHAR(1), " +

                    "double_seat int, " +

                    "quad_seat int, " +

                    "PRIMARY KEY (size)) ";

			System.out.println("Outside_seat_group table created.");


			//Menu_evaluation테이블 생성 이후에 foreign key 추가

			String sql11 = "CREATE TABLE Menu_evaluation "+

                    "(restaurant_name VARCHAR(25) NOT NULL, " +

                    "menu_name VARCHAR(30), " +

                    "spicy int, "+

                    "sweetness int, "+

                    "salty int, "+

                    "sourness int, "+

                    "PRIMARY KEY (restaurant_name, menu_name)) ";

			System.out.println("Menu_evaluation table created.");


			//Major_client테이블 생성 이후에 foreign key 추가

			String sql12 = "CREATE TABLE Major_client "+

                    "(company VARCHAR(15) NOT NULL, " +

                    "age int, " +

                    "gender CHAR(1), " +

                    "PRIMARY KEY (company)) ";

			System.out.println("Major_client table created.");



			//Beverage테이블 생성 이후에 foreign key 추가

			String sql13 = "CREATE TABLE Beverage "+

                    "(company VARCHAR(15) NOT NULL, " +

                    "alcohol_drink VARCHAR(20), " +

                    "nonalcohol_drink VARCHAR(20), " +

                    "PRIMARY KEY (company)) ";

			System.out.println("Beverage table created.");

			//

			//

			//

			//Payment테이블 생성 이후에 foreign key 추가

			String sql14 = "CREATE TABLE Payment "+

                    "(company VARCHAR(15) NOT NULL, " +

                    "coupon CHAR(1), " +

                    "tele_discount CHAR(1), " +

                    "PRIMARY KEY (company)) ";

			System.out.println("Payment table created.");



			//Delivery_location테이블 foreign key 없애는 걸로

			String sql15= "CREATE TABLE Delivery_location "+

                    "(location VARCHAR(10), " +

                    "delivery_location VARCHAR(10), " +

                    "PRIMARY KEY (location, delivery_location)) ";

			System.out.println("Delivery_location table created.");



			//Working_hours table에 값 입력

			String insert_sql1="INSERT INTO Working_hours values('Nature_shinchon',10,22)";

			String insert_sql2="INSERT INTO Working_hours values('Mr.pizza_Ewha',10,22)";

			String insert_sql3="INSERT INTO Working_hours values('Mcdonalds_Shinchon',6,24)";

			String insert_sql4="INSERT INTO Working_hours values('Momotaro_Ewha',10,22)";

			String insert_sql5="INSERT INTO Working_hours values('KFC_Ewha',7,24)";

			String insert_sql6="INSERT INTO Working_hours values('Mcdonalds_Hongdae',6,24)";

			String insert_sql7="INSERT INTO Working_hours values('Hongkong0410_Shinchon',9,22)";

			String insert_sql8="INSERT INTO Working_hours values('Hongkong0410_Hongdae',9,23)";

			String insert_sql9="INSERT INTO Working_hours values('Easyindia_Shinchon',10,23)";

			String insert_sql10="INSERT INTO Working_hours values('Nolboo_Ewha',10,22)";

			String insert_sql11="INSERT INTO Working_hours values('KFC_Hongdae',7,24)";

			String insert_sql12="INSERT INTO Working_hours values('Nolboo_Hongdae',7,23)";

			String insert_sql13="INSERT INTO Working_hours values('Easyindia_Ewha',9,22)";

			String insert_sql14="INSERT INTO Working_hours values('Shinsun_Shinchon',9,24)";

			//Restaurant table에 값 입력

			String sql21="insert into Restaurant values('Nature_Shinchon', 'Nogosandong 57-1', '02-338-8054', 'Nature', 'L')";

			String sql22="insert into Restaurant values('Mr.pizza_Ewha', 'Ewha 8 street 10', '02-363-3355', 'Mr.pizza', 'M')";

			String sql23="insert into Restaurant values('Mcdonalds_Shinchon', 'Donggyudong 162-4', '070-7017-0636', 'Mcdonalds', 'M')";

			String sql24="insert into Restaurant values('Momotaro_Ewha', 'Changchondong 18-50', '02-393-5585', 'Momotaro', 'S')";

			String sql25="insert into Restaurant values('KFC_Ewha', 'Daehyundong 37-3', '02-312-3921', 'KFC', 'M')";

			String sql26="insert into Restaurant values('Mcdonalds_Hongdae', 'Daehyundong 37-71', '070-7209-0512', 'Mcdonalds', 'L')";

			String sql27="insert into Restaurant values('Hongkong0410_Shinchon', 'Myungmun street 19', '02-3147-0410', 'Hongkong0410', 'M')";

			String sql28="insert into Restaurant values('Hongkong0410_Hongdae', 'Wawoosan 23 street 44', '02-333-0410', 'Hongkong0410', 'L')";

			String sql29="insert into Restaurant values('Easyindia_Shinchon', 'Changchondong 9-20', '02-393-7771', 'Easyindia', 'M')";

			String sql30="insert into Restaurant values('Nolboo_Ewha', 'Daehyundong 45-33', '02-313-7892', 'Nolboo', 'M')";

			String sql31="insert into Restaurant values('KFC_Hongdae', 'Yanghwa street 156', '02-323-7554', 'KFC', 'S')";

			String sql32="insert into Restaurant values('Nolboo_Hongdae', 'Dockmack street 101', '02-332-5382', 'Nolboo', 'S')";

			String sql33="insert into Restaurant values('Easyindia_Ewha', 'Daehyundong 18-2', '02-332-6754', 'Easyindia', 'S')";

			String sql34="insert into Restaurant values('Shinsun_Shinchon', 'Changchondong 5-38', '02-393-0040', 'Shinsun', 'M')";



			//location 테이블에 값 입력

			String sql35="insert into Location values('Nogosandong 57-1', 'Shinchon')";

			String sql36="insert into Location values('Ewha 8 street 10', 'Ewha')";

			String sql37="insert into Location values('Donggyudong 162-4', 'Shinchon')";

			String sql38="insert into Location values('Changchondong 18-50', 'Ewha')";

			String sql39="insert into Location values('Daehyundong 37-3', 'Ewha')";

			String sql40="insert into Location values('Daehyundong 37-71', 'Hongdae')";

			String sql41="insert into Location values('Myungmun street 19', 'Shinchon')";

			String sql42="insert into Location values('Wawoosan 23 street 44', 'Hongdae')";

			String sql43="insert into Location values('Changchondong 9-20', 'Shinchon')";

			String sql44="insert into Location values('Daehyundong 45-33', 'Ewha')";

			String sql45="insert into Location values('Yanghwa street 156', 'Hongdae')";

			String sql46="insert into Location values('Dockmack street 101', 'Hongdae')";

			String sql47="insert into Location values('Daehyundong 18-2', 'Ewha')";

			String sql48="insert into Location values('Changchondong 5-38', 'Shinchon')";





			//Service 테이블에 값 입력

			String sql49="insert into Service values('Nature_Shinchon','Y','N','N','N')";

			String sql50="insert into Service values('Mr.pizza_Ewha','Y','Y','Y','N')";

			String sql51="insert into Service values('Mcdonalds_Shinchon','N','Y','Y','N')";

			String sql52="insert into Service values('Momotaro_Ewha','Y','N','Y','N')";

			String sql53="insert into Service values('KFC_Ewha','N','Y','Y','Y')";

			String sql54="insert into Service values('Mcdonalds_Hongdae','N','Y','Y','N')";

			String sql55="insert into Service values('Hongkong0410_Shinchon','Y','N','Y','N')";

			String sql56="insert into Service values('Hongkong0410_Hongdae','Y','N','Y','N')";

			String sql57="insert into Service values('Easyindia_Shinchon','Y','N','N','N')";

			String sql58="insert into Service values('Nolboo_Ewha','Y','N','Y','N')";

			String sql59="insert into Service values('KFC_Hongdae','N','N','Y','Y')";

			String sql60="insert into Service values('Nolboo_Hongdae','Y','N','Y','N')";

			String sql61="insert into Service values('Easyindia_Ewha','Y','N','N','N')";

			String sql62="insert into Service values('Shinsun_Shinchon','Y','N','Y','N')";


			//Company 테이블에 값 입력

			String sql63="insert into Company values('Nature', 'Korean')";

			String sql64="insert into Company values('Momotaro', 'Japanese')";

			String sql65="insert into Company values('Easyindia', 'Indian')";

			String sql66="insert into Company values('Mcdonalds', 'Fastfood')";

			String sql67="insert into Company values('Hongkong0410', 'Chinese')";

			String sql68="insert into Company values('KFC', 'Fastfood')";

			String sql69="insert into Company values('Mr.pizza', 'Western')";

			String sql70="insert into Company values('Shinsun', 'Korean')";
			
			String sql172="insert into Company values('Nolboo', 'Korean')";


			//Menu 테이블에 값 입력

			String sql71="insert into Menu values('Nature', 'Bulgogi', 12000)";

			String sql72="insert into Menu values('Nature', 'Doenjang stew', 5500)";

			String sql73="insert into Menu values('Momotaro', 'Ross cutlets', 7500)";

			String sql74="insert into Menu values('Momotaro', 'Soba', 8000)";

			String sql75="insert into Menu values('Easyindia', 'Curry', 8000)";

			String sql76="insert into Menu values('Easyindia', 'Nan', 10000)";

			String sql77="insert into Menu values('Mcdonalds', 'Big mac', 5200)";

			String sql78="insert into Menu values('Mcdonalds', 'Happy meal', 3600)";

			String sql79="insert into Menu values('Nolboo', 'Nolboo', 8000)";

			String sql80="insert into Menu values('Nolboo', 'Heungbu', 9000)";

			String sql81="insert into Menu values('Hongkong0410', 'Jjambbong', 5000)";

			String sql82="insert into Menu values('Hongkong0410', 'Jajangmyeon', 4000)";

			String sql83="insert into Menu values('KFC', 'Chicken twister', 1900)";

			String sql84="insert into Menu values('KFC', 'Gingers Choice', 12000)";

			String sql85="insert into Menu values('Mr.pizza', 'Cheesecrust', 5600)";

			String sql86="insert into Menu values('Mr.pizza', 'Potato pizza', 15000)";

			String sql87="insert into Menu values('Shinsun', 'Beef soup with rice', 8000)";

			String sql88="insert into Menu values('Shinsun', 'Meat-born', 13000)";



			//Delivery_location 테이블에 값 입력

			String sql89="insert into Delivery_location values('Ewha', 'Ewha')";

			String sql90="insert into Delivery_location values('Ewha', 'Shinchon')";

			String sql91="insert into Delivery_location values('Ewha', 'Hongdae')";

			String sql92="insert into Delivery_location values('Shinchon', 'Ewha')";

			String sql93="insert into Delivery_location values('Shinchon', 'Shinchon')";



			//Seat_available 테이블에 값 입력

			String sql94="insert into Seat_available values('S',5,5,0,0)";

			String sql95="insert into Seat_available values('M',10,15,5,10)";

			String sql96="insert into Seat_available values('L',20,30,15,20)";



			// Inside_seat_group 테이블에 값 입력

			String sql97="insert into Inside_seat_group values('S',3,2)";

			String sql98="insert into Inside_seat_group values('M',10,5)";

			String sql99="insert into Inside_seat_group values('L',15,15)";




			// Outside_seat_group 테이블에 값 입력

			String sql100="insert into Outside_seat_group values('S',0,0)";

			String sql101="insert into Outside_seat_group values('M',5,5)";

			String sql102="insert into Outside_seat_group values('L',10,10)";


			// Payment 테이블에 값 입력

			String sql103="insert into Payment values('Nature', 'Y','Y')";

			String sql104="insert into Payment values('Momotaro', 'Y','N')";

			String sql105="insert into Payment values('Easyindia', 'N','Y')";

			String sql106="insert into Payment values('Mcdonalds', 'N','N')";

			String sql107="insert into Payment values('Nolboo', 'N','N')";

			String sql108="insert into Payment values('Hongkong0410', 'N','N')";

			String sql109="insert into Payment values('KFC', 'Y','Y')";

			String sql110="insert into Payment values('Mr.pizza', 'Y','Y')";

			String sql111="insert into Payment values('Shinsun', 'N','N')";


			//Beverage 테이블에 값 입력

			String sql112="insert into Beverage values('Nature', 'Makgeolli', 'Ssanghwa_Tea')";

			String sql113="insert into Beverage values('Momotaro', 'Japanese_Rice_Wine', 'Green_Tea')";

			String sql114="insert into Beverage values('Easyindia', 'Whiskey', 'Lassi')";

			String sql115="insert into Beverage values('Mcdonalds', 'Beer', 'Coke')";

			String sql116="insert into Beverage values('Nolboo', 'Makgeolli', 'Barley_Tea')";

			String sql117="insert into Beverage values('Hongkong0410', 'Mao_tai', 'Chrysanthemum_Tea')";

			String sql118="insert into Beverage values('KFC', 'Beer', 'Coke')";

			String sql119="insert into Beverage values('Mr.pizza', 'Wine', 'Ade')";

			String sql120="insert into Beverage values('Shinsun', 'Soju', 'Barley_Tea')";


			//Major_client 테이블에 값 입력

			String sql121="insert into Major_client values('Nature', 30, 'F')";

			String sql122="insert into Major_client values('Momotaro', 20, 'F')";

			String sql123="insert into Major_client values('Easyindia', 20, 'F')";

			String sql124="insert into Major_client values('Mcdonalds', 10, 'M')";

			String sql125="insert into Major_client values('Nolboo', 40, 'M')";

			String sql126="insert into Major_client values('Hongkong0410', 30, 'M')";

			String sql127="insert into Major_client values('KFC', 20, 'M')";

			String sql128="insert into Major_client values('Mr.pizza', 20, 'F')";

			String sql129="insert into Major_client values('Shinsun', 40, 'M')";


			//Service_Evaluation 테이블에 값 입력

			String sql130="insert into Service_Evaluation values('Nature_Shinchon', 5, 2, 4, 3)";

			String sql131="insert into Service_Evaluation values('Mr.pizza_Ewha', 3, 4, 4, 2)";

			String sql132="insert into Service_Evaluation values('Mcdonalds_Shinchon', 4, 5, 5, 2)";

			String sql133="insert into Service_Evaluation values('Momotaro_Ewha', 3, 4, 3, 3)";

			String sql134="insert into Service_Evaluation values('KFC_Ewha', 5, 5, 5, 5);";

			String sql135="insert into Service_Evaluation values('Mcdonalds_Hongdae', 3, 4, 4, 2)";

			String sql136="insert into Service_Evaluation values('Hongkong0410_Shinchon', 4, 3, 4, 3)";

			String sql137="insert into Service_Evaluation values('Hongkong0410_Hongdae',4,3,4,3)";

			String sql138="insert into Service_Evaluation values('Easyindia_Shinchon',4,4,4,4)";

			String sql139="insert into Service_Evaluation values('Nolboo_Ewha',3,4,3,3)";

			String sql140="insert into Service_Evaluation values('KFC_Hongdae',5,5,5,5)";

			String sql141="insert into Service_Evaluation values('Nolboo_Hongdae',3,3,3,3)";

			String sql142="insert into Service_Evaluation values('Easyindia_Ewha',4,4,3,3)";

			String sql143="insert into Service_Evaluation values('Shinsun_Shinchon',4,5,3,5)";


			//Menu_evaluation 테이블에 값 입력 

			String sql144="insert into Menu_evaluation values('Nature_Shinchon', 'Bulgogi',2,5,4,0)";

			String sql145="insert into Menu_evaluation values('Nature_Shinchon', 'Doenjang stew',5,1,4,0)";

			String sql146="insert into Menu_evaluation values('Mr.pizza_Ewha', 'Cheesecrust',2,3,4,0)";

			String sql147="insert into Menu_evaluation values('Mr.pizza_Ewha', 'Potato pizza',3,2,4,0)";

			String sql148="insert into Menu_evaluation values('Mcdonalds_Shinchon', 'Big mac',2,2,4,1)";

			String sql149="insert into Menu_evaluation values('Mcdonalds_Shinchon', 'Happy meal',2,4,4,1)";

			String sql150="insert into Menu_evaluation values('Momotaro_Ewha', 'Ross cutlets',0,2,3,0)";

			String sql151="insert into Menu_evaluation values('Momotaro_Ewha', 'Soba',1,2,1,3)";

			String sql152="insert into Menu_evaluation values('KFC_Ewha', 'Gingers Choice',3,2,4,1)";

			String sql153="insert into Menu_evaluation values('KFC_Ewha', 'Chicken twister',4,3,4,1)";

			String sql154="insert into Menu_evaluation values('Mcdonalds_Hongdae', 'Big mac',1,2,5,1)";

			String sql155="insert into Menu_evaluation values('Mcdonalds_Hongdae', 'Happy meal',1,4,4,2)";

			String sql156="insert into Menu_evaluation values('Hongkong0410_Shinchon', 'Jjambbong',4,2,3,1)";

			String sql157="insert into Menu_evaluation values('Hongkong0410_Shinchon', 'Jajangmyeon',3,3,1,2)";

			String sql158="insert into Menu_evaluation values('Hongkong0410_Hongdae', 'Jjambbong',5,1,2,2)";

			String sql159="insert into Menu_evaluation values('Hongkong0410_Hongdae', 'Jajangmyeon',1,4,3,2)";

			String sql160="insert into Menu_evaluation values('Easyindia_Shinchon', 'Curry',4,4,2,1)";

			String sql161="insert into Menu_evaluation values('Easyindia_Shinchon', 'Nan',0,0,0,0)";

			String sql162="insert into Menu_evaluation values('Nolboo_Ewha', 'Nolboo',5,2,5,0)";

			String sql163="insert into Menu_evaluation values('Nolboo_Ewha','Heungbu',5,4,5,1)";

			String sql164="insert into Menu_evaluation values('KFC_Hongdae', 'Chicken twister',4,3,4,1)";

			String sql165="insert into Menu_evaluation values('KFC_Hongdae', 'Gingers Choice',3,2,4,1)";

			String sql166="insert into Menu_evaluation values('Nolboo_Hongdae', 'Nolboo',3,1,4,0)";

			String sql167="insert into Menu_evaluation values('Nolboo_Hongdae', 'Heungbu',4,2,5,1)";

			String sql168="insert into Menu_evaluation values('Easyindia_Ewha', 'Curry',5,4,4,2)";

			String sql169="insert into Menu_evaluation values('Easyindia_Ewha', 'Nan',2,3,3,0)";

			String sql170="insert into Menu_evaluation values('Shinsun_Shinchon', 'Beef soup with rice',0,2,2,0)";

			String sql171="insert into Menu_evaluation values('Shinsun_Shinchon', 'Meat-born',0,2,2,0)";


			//Restaurant table foreign key 추가

			String addkey_sql1="alter table Restaurant add foreign key (address) references Location(address) on delete cascade on update cascade";

			String addkey_sql2="alter table Restaurant add foreign key (company) references Company(company) on delete cascade on update cascade";

			String addkey_sql3="alter table Restaurant add foreign key (size) references seat_available(size) on delete cascade on update cascade";



			// Working_hourst table foreign key 추가

			String addkey_sql4="alter table Working_hours add foreign key (restaurant_name) references Restaurant(restaurant_name) on delete cascade on update cascade";



			//Service table foreign key 추가

			String addkey_sql5="alter table Service add foreign key (restaurant_name) references Restaurant(restaurant_name) on delete cascade on update cascade";



			//Menu table foreign key 추가

			String addkey_sql6="alter table Menu add foreign key(company) references Company(company) on delete cascade on update cascade";


			//Service_Evaluation foreign key 추가

			String addkey_sql7="alter table Service_Evaluation add foreign key (restaurant_name) references Restaurant(restaurant_name) on delete cascade on update cascade";


			//Inside_seat_group foreign key 추가

			String addkey_sql8="alter table Inside_seat_group add foreign key (size) references seat_available(size) on delete cascade on update cascade";


			//Outside_seat_group foreign key 추가

			String addkey_sql9="alter table Outside_seat_group add foreign key (size) references seat_available(size) on delete cascade on update cascade";


			//Menu_evaluation foreign key 1 추가

			String addkey_sql10="alter table Menu_evaluation add foreign key (restaurant_name) references Restaurant(restaurant_name) on delete cascade on update cascade";


			//Menu_evaluation foreign key 2추가

			String addkey_sql11="alter table Menu_evaluation add foreign key (menu_name) references Menu (menu_name) on delete cascade on update cascade";


			//Major_client foreign key 추가

			String addkey_sql12="alter table Major_client add foreign key (company) references Company(company) on delete cascade on update cascade";


			//Beverage foreign key 추가

			String addkey_sql13="alter table Beverage add foreign key (company) references Company(company) on delete cascade on update cascade";


			//Payment foreign key 추가

			String addkey_sql14="alter table Payment add foreign key (company) references Company(company) on delete cascade on update cascade";
			
			//index 추가
			String index_on_sql1="create index index_on_location on Location(location)";
			String index_on_sql2="create index index_on_address on Location(address)";
			String index_on_sql3="create index index_on_Res_address on Restaurant(address)";
			String index_on_sql4="create index index_on_category on Company(category)";
			String index_on_sql5="create index index_on_price on Menu(price)";
			//Delivery_location foreign key 추가. 오류 필요없음 
			//String addkey_sql15="alter table Delivery_location add foreign key (company) references Company(company)";


			//여기 FK 더 추가해야할 테이블:

			// Delivery_location (PK 아나어서 FK Add 안함)


			//실행할 쿼리문을 합침

			stmt.addBatch(sql1);

			stmt.addBatch(sql2);

			stmt.addBatch(sql3);

			stmt.addBatch(sql4);

			stmt.addBatch(sql5);

			stmt.addBatch(sql6);

			stmt.addBatch(sql7);

			stmt.addBatch(sql8);

			stmt.addBatch(sql9);

			stmt.addBatch(sql10);

			stmt.addBatch(sql11);

			stmt.addBatch(sql12);

			stmt.addBatch(sql13);

			stmt.addBatch(sql14);

			stmt.addBatch(sql15);

			//여기까지 테이블을 생성하는 쿼리!

			//Working hours테이블에 데이터값 넣음

			stmt.addBatch(insert_sql1);

			stmt.addBatch(insert_sql2);

			stmt.addBatch(insert_sql3);

			stmt.addBatch(insert_sql4);

			stmt.addBatch(insert_sql5);

			stmt.addBatch(insert_sql6);

			stmt.addBatch(insert_sql7);

			stmt.addBatch(insert_sql8);

			stmt.addBatch(insert_sql9);

			stmt.addBatch(insert_sql10);

			stmt.addBatch(insert_sql11);

			stmt.addBatch(insert_sql12);

			stmt.addBatch(insert_sql13);

			stmt.addBatch(insert_sql14);

			//Restaurant테이블에 데이터값 넣음

			stmt.addBatch(sql21);

			stmt.addBatch(sql22);

			stmt.addBatch(sql23);

			stmt.addBatch(sql24);

			stmt.addBatch(sql25);

			stmt.addBatch(sql26);

			stmt.addBatch(sql27);

			stmt.addBatch(sql28);

			stmt.addBatch(sql29);

			stmt.addBatch(sql30);

			stmt.addBatch(sql31);

			stmt.addBatch(sql32);

			stmt.addBatch(sql33);

			stmt.addBatch(sql34);



			//location테이블에 데이터값 넣음

			stmt.addBatch(sql35);

			stmt.addBatch(sql36);

			stmt.addBatch(sql37);

			stmt.addBatch(sql38);

			stmt.addBatch(sql39);

			stmt.addBatch(sql40);

			stmt.addBatch(sql41);

			stmt.addBatch(sql42);

			stmt.addBatch(sql43);

			stmt.addBatch(sql44);

			stmt.addBatch(sql45);

			stmt.addBatch(sql46);

			stmt.addBatch(sql47);

			stmt.addBatch(sql48);

			//Service테이블에 데이터값 넣음

			stmt.addBatch(sql49);

			stmt.addBatch(sql50);

			stmt.addBatch(sql51);

			stmt.addBatch(sql52);

			stmt.addBatch(sql53);

			stmt.addBatch(sql54);

			stmt.addBatch(sql55);

			stmt.addBatch(sql56);

			stmt.addBatch(sql57);

			stmt.addBatch(sql58);

			stmt.addBatch(sql59);

			stmt.addBatch(sql60);

			stmt.addBatch(sql61);

			stmt.addBatch(sql62);

			//Company 테이블에 값 넣음

			stmt.addBatch(sql63);

			stmt.addBatch(sql64);

			stmt.addBatch(sql65);

			stmt.addBatch(sql66);

			stmt.addBatch(sql67);

			stmt.addBatch(sql68);

			stmt.addBatch(sql69);

			stmt.addBatch(sql70);
			
			stmt.addBatch(sql172);
			

			//Menu 테이블에 값 넣음

			stmt.addBatch(sql71);

			stmt.addBatch(sql72);

			stmt.addBatch(sql73);

			stmt.addBatch(sql74);

			stmt.addBatch(sql75);

			stmt.addBatch(sql76);

			stmt.addBatch(sql77);

			stmt.addBatch(sql78);

			stmt.addBatch(sql79);

			stmt.addBatch(sql80);

			stmt.addBatch(sql81);

			stmt.addBatch(sql82);

			stmt.addBatch(sql83);

			stmt.addBatch(sql84);

			stmt.addBatch(sql85);

			stmt.addBatch(sql86);

			stmt.addBatch(sql87);

			stmt.addBatch(sql88);



			//Delivery_location 테이블에 값 넣음


			stmt.addBatch(sql89);

			stmt.addBatch(sql90);

			stmt.addBatch(sql91);

			stmt.addBatch(sql92);

			stmt.addBatch(sql93);



			//Seat_available 테이블에 값 넣음

			stmt.addBatch(sql94);

			stmt.addBatch(sql95);

			stmt.addBatch(sql96);



			//Inside_seat_group 테이블에 값 넣음

			stmt.addBatch(sql97);

			stmt.addBatch(sql98);

			stmt.addBatch(sql99);



			//Outside_seat_group 테이블에 값 넣음

			stmt.addBatch(sql100);

			stmt.addBatch(sql101);

			stmt.addBatch(sql102);


			// Payment 테이블에 값 넣음

			stmt.addBatch(sql103);

			stmt.addBatch(sql104);

			stmt.addBatch(sql105);

			stmt.addBatch(sql106);

			stmt.addBatch(sql107);

			stmt.addBatch(sql108);

			stmt.addBatch(sql109);

			stmt.addBatch(sql110);

			stmt.addBatch(sql111);


			//Beverage 테이블에 값 넣음

			stmt.addBatch(sql112);

			stmt.addBatch(sql113);

			stmt.addBatch(sql114);

			stmt.addBatch(sql115);

			stmt.addBatch(sql116);

			stmt.addBatch(sql117);

			stmt.addBatch(sql118);

			stmt.addBatch(sql119);

			stmt.addBatch(sql120);

			//Major_client 테이블에 값 넣음

			stmt.addBatch(sql121);

			stmt.addBatch(sql122);

			stmt.addBatch(sql123);

			stmt.addBatch(sql124);

			stmt.addBatch(sql125);

			stmt.addBatch(sql126);

			stmt.addBatch(sql127);

			stmt.addBatch(sql128);

			stmt.addBatch(sql129);


			//Service_Evaluation 테이블에 값 넣음

			stmt.addBatch(sql130);

			stmt.addBatch(sql131);

			stmt.addBatch(sql132);

			stmt.addBatch(sql133);

			stmt.addBatch(sql134);

			stmt.addBatch(sql135);

			stmt.addBatch(sql136);

			stmt.addBatch(sql137);

			stmt.addBatch(sql138);

			stmt.addBatch(sql139);

			stmt.addBatch(sql140);

			stmt.addBatch(sql141);

			stmt.addBatch(sql142);

			stmt.addBatch(sql143);


			//Menu_evaluation 테이블에 값 넣음

			stmt.addBatch(sql144);

			stmt.addBatch(sql145);

			stmt.addBatch(sql146);

			stmt.addBatch(sql147);

			stmt.addBatch(sql148);

			stmt.addBatch(sql149);

			stmt.addBatch(sql150);

			stmt.addBatch(sql151);

			stmt.addBatch(sql152);

			stmt.addBatch(sql153);

			stmt.addBatch(sql154);

			stmt.addBatch(sql155);

			stmt.addBatch(sql156);

			stmt.addBatch(sql157);

			stmt.addBatch(sql158);

			stmt.addBatch(sql159);

			stmt.addBatch(sql160);

			stmt.addBatch(sql161);

			stmt.addBatch(sql162);

			stmt.addBatch(sql163);

			stmt.addBatch(sql164);

			stmt.addBatch(sql165);

			stmt.addBatch(sql166);

			stmt.addBatch(sql167);

			stmt.addBatch(sql168);

			stmt.addBatch(sql169);

			stmt.addBatch(sql170);

			stmt.addBatch(sql171);


			//foreign key를 추가하는 쿼리

			stmt.addBatch(addkey_sql1);

			stmt.addBatch(addkey_sql2);

			stmt.addBatch(addkey_sql3);

			stmt.addBatch(addkey_sql4);

			stmt.addBatch(addkey_sql5);

			stmt.addBatch(addkey_sql6);

            stmt.addBatch(addkey_sql7);

            stmt.addBatch(addkey_sql8);

            stmt.addBatch(addkey_sql9);

            stmt.addBatch(addkey_sql10);

            stmt.addBatch(addkey_sql11);

            stmt.addBatch(addkey_sql12);

            stmt.addBatch(addkey_sql13);

            stmt.addBatch(addkey_sql14);

            //stmt.addBatch(addkey_sql15); 

            //index
            stmt.addBatch(index_on_sql1);
            stmt.addBatch(index_on_sql2);
            stmt.addBatch(index_on_sql3);
            stmt.addBatch(index_on_sql4);
            stmt.addBatch(index_on_sql5);

			//한번에 쿼리문을 실행

			stmt.executeBatch();

			System.out.println("Insert Batch Done.");
			
			//view 생성을 위한 작업
			CreateView viewTable = new CreateView();
			
			
			
			//지원	
			Scanner scanner = new Scanner(System.in);
			RestaurantDBinput userInput =new RestaurantDBinput();
			
			System.out.println("=====program started. WELCOME!=====");
			
			//while0 starts(customer와 manager선택)
			while(true){
				int CusOrMan = userInput.CustomerOrManager();

				//Customer을 선택한 경우
				if(CusOrMan == 1){
			//while1 starts(location과 category 선택 loop)
			while(true){
				int LocOrCat= userInput.LocaionOrCategory();

				
				//location 선택한 경우
				if(LocOrCat==1){
					//사용자가 입력한 지역을 식당의 지역으로 저장
					String restaurantLoc=userInput.getLocation();

					
					//while2 starts(지역 선택한 경우 그다음 조건 선택하는 loop)
					while(true){
						System.out.println("Choose the next condition of searching: ");
						System.out.println("1. Working hours");
						System.out.println("2. Delivery");
						System.out.println("3. Major client");
						System.out.println("4. Seat");
						int t= scanner.nextInt();

						if(t==1){
							userInput.getOpeningClosing(restaurantLoc);
							break;
						}
						else if(t==2){
							userInput.getDeliveryLocation(restaurantLoc);
							break;
						}
						else if(t==3){
							userInput.getAgeGender(restaurantLoc);
							break;
						}
						else if(t==4){
							userInput.getSizeSeat(restaurantLoc);
							break;
						}
						else
							System.out.println("Insert 1 or 2 or 3 or 4 again.\n");

					}//while2 ends


				}

				
//				//category 선택한 경우
				else if(LocOrCat==2){
//					
				//위와같이 작성
				String category=userInput.getCategory();

				
//					//while2 starts(지역 선택한 경우 그다음 조건 선택하는 loop)
					while(true){
						System.out.println("Choose the next condition of searching: ");
						System.out.println("1. The way of discount");
						System.out.println("2. Price");
						System.out.println("3. Beverage");
						//System.out.println("4. 메뉴 별점");
						int t= scanner.nextInt();
						
						//수정수정 
						if(t==1){
							userInput.getPayment(category);
							break;
						}
						else if(t==2){
							userInput.getPrice(category);
							break;
						}
						else if(t==3){
							userInput.getPayment(category);
							break;
						}
						//else if(t==4){
						//	userInput.getPayment(category);
						//	break;
						//}
						
						else
							System.out.println("Insert 1~3 again.\n");
					}//while2 ends

				}

				
				//user가 exit 선택한 경우 (while1에서 나가고 포로그램 종료)
				
				else if(LocOrCat==3){
					break;
				}
				

			}//while1 ends
			}

			//Manager Mode 선택
			else if(CusOrMan == 2){
				
				//String restaurantLoc=userInput.getLocation();

					
					//while11 starts(매니저모드를 선택한후 테이블 수정하기)
					while(true){
						System.out.println("Which section do you want to manage: ");
						System.out.println(“1. Modify menu evaluation");
						System.out.println(“2. Delete restaurant");
						System.out.println(“3. Insert delivery location");
						int c= scanner.nextInt();
						
						if(c==1){
							userInput.getEditMenuEval();
							break;
						}
						else if(c==2){
							userInput.getNameForDelete();
							break;
						}
						else if(c==3){
							userInput.getLocationForInsert();
							break;
						}
						
	
							System.out.println("Insert 1 or 2 again.\n");
							

					}//while11 ends

			}
				
			
			
			
			
		}
		}//try

		



		catch(SQLException e) {
			System.out.println(e.getMessage());
			dbConnection.rollback();
		} finally {
			if (stmt != null)
				stmt.close();
			if (dbConnection != null)
				dbConnection.close();
		}//catch
		
		
	}//insertTest()
	
	
}//main
