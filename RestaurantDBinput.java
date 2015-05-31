package KANGHANNAYOUNG;
//사용자로부터 입력을 받아오는 클래스
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class RestaurantDBinput{
	
	//Test:사용자로부터 Configuration 파일과 SQL script의 정보를 읽어오는 클래스
	static Test dbdb = new Test();
	static Scanner scanner = new Scanner(System.in);
	
	//1215001 강린
	/* Custom과 Manager 선택하는 Method*/
	public int CustomerOrManager(){
		//사용자가 실행 모드를 받아올 변수 선언
		int customerormanager;

		while(true){
			System.out.println(" <<Select mode: 1. Customer mode  2. Manager mode 3. Exit the program>>");
			customerormanager = scanner.nextInt();

			//Customer mode를 선택한경우 
			if(customerormanager == 1){
				System.out.println("You choose Customer mode.");
				break;
			}
			
			//Manager mode를 선택한경우 
			else if(customerormanager == 2){
				System.out.println("You choose Manager mode.");
				break;		
			}
			
			//Program 종료를 선택한 경우 
			else if(customerormanager == 3){
				System.out.println("=====program ended. BYE!=====");
				break;
			}
			
			else
				System.out.println("<Warning> Insert among 1, 2, 3 again.");
			
		}
		
		return customerormanager;
	}

	/*1215001 강린
	 * 지역과 카테고리 중 어느 것으로 검색을 시작할지 결정하는 method
	 * 1과 2중 입력받은 하나를 return
	 * 다른 것 입력받으면 1과 2중 하나 선택할 때까지 loop
	 * 1-Location, 2-Category
	 */
	public int LocaionOrCategory(){
		//사용자의 검색 옵션을 받아올 변수 선언
		int locationorcategory;

		while(true){
			System.out.println("\n\n\nStart searching with 1. restaurant's location 2. food's category (Insert 1 or 2)");
			System.out.println("(Insert 3 to exit Customer mode)");
			locationorcategory =scanner.nextInt();

			//지역을 선택한경우 
			if(locationorcategory==1){
				System.out.println("You choose restaurant's location.\n");
				break;
			}

			//카테고리를 선택한경우 
			else if(locationorcategory==2){
				System.out.println("You choose food's category.");
				break;
			}

			//Customer mode 종료를 선택한 경우 
			else if(locationorcategory==3){
				System.out.println("=====Customer mode ended=====");
				break;
			}


			else
				System.out.println("<Warning> Insert again among 1, 2, 3");
		}


		return locationorcategory;

	}//ends LocationOrCategory method


	/*1215001 강린
	 * 지역으로 검색을 시작할 경우 지역 설정
	 * 해당 지역 return
	 */
	
	public String getLocation(){
		//사용자의 검색 옵션을 받아올 변수 선언
		int locationNum;
		String location="";

		while(true){
			System.out.println("Which location you want to search?");
			System.out.println("1. Ewha 2. Shinchon 3. Hongdae (Insert among 1, 2, 3)");
			locationNum =scanner.nextInt();

			//Ewha를 선택한 경우 
			if(locationNum==1){
				System.out.println("You selected Ewha.\n");
				location="Ewha";
				break;
			}

			//Shinchon을 선택한 경우 
			else if(locationNum==2){
				System.out.println("You selected Shinchon.\n");
				location="Shinchon";
				break;
			}

			//Hongdae를 선택한 경우 
			else if(locationNum==3){
				System.out.println("You selected Hongdae.\n");
				location="Hongdae";
				break;
			}

			else
				System.out.println("<Warning> Insert again among 1, 2, 3.\n");
		}

		return location;

	}//getLocation method ends

	/* 1215131 한희정
	 * 식당 중 사용자가 원하는 영업시간을 입력하는 메소드
	 * 식당의 지역을 파라미터로 받음
	 * getRestaurantByWorkinghour에 입력값 반환
	 */
	
	public void getOpeningClosing(String restaurantLoc) throws SQLException {
		
		//사용자가 선택한 지역을 받을 변수 선언
		String location = restaurantLoc;
		
		//사용자로부터 opening hour, closing hour값을 받음
		System.out.println("\nInput maximum opening_hours: ");
		int opening = scanner.nextInt();
		System.out.println("Input minimum closing_hours: ");
		int closing = scanner.nextInt();

		//getRestaurantByWorkinghour(opening, closing, location)로부터 받은 식당이름을 ArrayList에 넣음

		ArrayList<String> names = getRestaurantByWorkinghour(opening, closing, location);


		System.out.println("Restaurants which open between "+opening+" and "+closing);

		//검색조건을 만족하는 restaurant의 이름을 출력
		System.out.println("[Restaurant name]");
		for (int i = 0; i < names.size(); i++) {
			if(names.size()==0)
				System.out.println("NONE");
			else
				System.out.println(names.get(i));
		}//for ends


	}//getOpeningClosing ends



	/* 1215131 한희정
	 * getOpeningClosing으로 부터 opening,closing,location을 받아서
	 * 입력받은 개장 및 마감 시간에 해당하는 restaurant의 이름 반환하는 메소드
	 */
	public static ArrayList<String> getRestaurantByWorkinghour(int opening, int closing, String location) throws SQLException {
		/*
		 * 데이터베이스와의 연결을 위한 Connection
		 * SQL문을 수행하기 위한 PreparedStatement
		 * select 쿼리의 결과값을 받기 위한 ResultSet
		 * 
		 * */
		
		
		
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		
		//restaurant의 이름을 받을 array list 선언
		ArrayList<String> restaurant_name = new ArrayList<String>();

		
		Statement stmt = null;

		//사용자의 입력 조건에 따른 Select Query문 작성
		String selectTableSQL="";
		if(location=="Ewha"){
			selectTableSQL = "SELECT restaurant_name "
					+ "FROM DBCOURSE_Working_hours NATURAL JOIN DBCOURSE_Service "
					+ "WHERE opening <= ? AND closing >= ? AND "
					+ "restaurant_name IN ( SELECT restaurant_name "
					+ "FROM DBCOURSE_location_ewha)";

		}


		else if(location=="Shinchon"){
			selectTableSQL = "SELECT restaurant_name "
					+ "FROM DBCOURSE_Working_hours NATURAL JOIN DBCOURSE_Service "
					+ "WHERE opening <= ? AND closing >= ? AND "
					+ "restaurant_name IN ( SELECT restaurant_name "
					+ "FROM DBCOURSE_location_shinchon)";

		}

		else if(location=="Hongdae"){
			selectTableSQL = "SELECT restaurant_name "
					+ "FROM DBCOURSE_Working_hours NATURAL JOIN DBCOURSE_Service "
					+ "WHERE opening <= ? AND closing >= ? AND "
					+ "restaurant_name IN ( SELECT restaurant_name "
					+ "FROM DBCOURSE_location_hongdae)";
		}





		try {
			
			
			dbConnection = dbdb.getDBConnection();



			stmt = dbConnection.createStatement();
			//use database
			String UseSQL = "USE RestaurantDB";
			stmt.executeUpdate(UseSQL);
			

			dbConnection.setAutoCommit(false);

			//사용자로부터 입력받은 조건을 select 쿼리문에 대입
			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setInt(1, opening);
			preparedStatementSelect.setInt(2, closing);
			
			
			//select 쿼리문을 수행한 결과를 rs에 넣음
			rs = preparedStatementSelect.executeQuery();

			//select 쿼리문을 수행하여 나온 restaurant_name(결과값)을 restaurant_name arraylist에 넣음
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
	}//지역, 오픈, 마감 select ends


	/*1215131 한희정
	 * 이미 선택한 지역에서 배달 가능한가 알아보는 지역 입력받는 메소드
	 * 식당의 지역을 파라미터로 받음
	 * getRestaurantByDelivery에 입력값과 식당의 지역 반환
	 */
	public void getDeliveryLocation(String restaurantLoc) throws SQLException{
		//사용자가 선택한 배달지역의 번호를 받을 변수 선언
		int locationNum;
		//사용자가 선택한 지역을 받을 변수 선언
		String restlocation;

		while(true){
			System.out.println("\nChoose the delivery location you want to check.");
			System.out.println("1. Ewha 2. Shinchon 3. Hongdae (Insert among 1, 2, 3)");
			locationNum =scanner.nextInt();
			
			//Ewha를 선택한 경우
			if(locationNum==1){
				System.out.println("You choose Ewha.\n");
				restlocation="Ewha";
				break;
			}

			//Shinchon을 선택한 경우
			else if(locationNum==2){
				System.out.println("You choose Shinchon.\n");
				restlocation="Shinchon";
				break;
			}

			//Hongdae를 선택한 경우 
			else if(locationNum==3){
				System.out.println("You choose Hongdae.\n");
				restlocation="Hongdae";
				break;
			}

			else
				System.out.println("<Warning> Insert among 1, 2, 3)");
		}

		//getRestaurantByDelivery(restaurantLoc,restlocation)로부터 받은 식당이름을 ArrayList에 넣음
		ArrayList<String> names =getRestaurantByDelivery( restaurantLoc, restlocation);
		
		System.out.println("Next restaurants can deliver to "+restlocation);
		System.out.println("[Restaurant location]");
		
		//검색조건을 만족하는 restaurant의 이름을 출력
		for(int i=0;i<names.size();i++){
			if(names.size()==0)
				System.out.println("NONE");
			else
				System.out.println(names.get(i));

		}
	}//ends getDeliveryLocation







	/*1215131 한희정
	 * 입력받은 번호에 해당하는 지역에 배달 가능한 식당 이름 반환
	 * 파라미터로 식당의 지역과 배달가능한지 알아보는 지역을 받음
	 */
	public static ArrayList<String> getRestaurantByDelivery(String restaurantLoc, String restlocation) throws SQLException{

		/*
			 * 데이터베이스와의 연결을 위한 Connection
			 * SQL문을 수행하기 위한 PreparedStatement
			 * select 쿼리의 결과값을 받기 위한 ResultSet
			 * 
			 * */
		
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		
		//restaurant의 이름을 받을 array list 선언
		ArrayList<String> restaurant_name = new ArrayList<String>();


		Statement stmt = null;


		//사용자의 입력 조건에 따른 Select Query문 작성
		String selectTableSQL = "SELECT restaurant_name "
				+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Location "
				+ "WHERE location= ? AND "
				+"exists"
				+"(SELECT location "
				+ "FROM DBCOURSE_Delivery_location "
				+ "WHERE location = ? AND delivery_location = ?)";


		try {
			
			dbConnection = dbdb.getDBConnection();

			stmt = dbConnection.createStatement();
			//use database
			String UseSQL = "USE RestaurantDB";
			stmt.executeUpdate(UseSQL);
			

			dbConnection.setAutoCommit(false);
			
			//사용자로부터 입력받은 조건을 select 쿼리문에 대입
			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setString(1, restaurantLoc);
			preparedStatementSelect.setString(2, restaurantLoc);
			preparedStatementSelect.setString(3, restlocation);
			

			//사용자의 입력 조건에 따른 Select Query문 수행
			rs = preparedStatementSelect.executeQuery();

			//select 쿼리문을 수행하여 나온 restaurant_name(결과값)을 restaurant_name arraylist에 넣음
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
	}//getDeliveryAvailableLocation ends

	/*1215131 한희정
	 * 사용자가 사전에 선택한 지역에서 입력받은 좌석 종류에 해당하는 식당의 이름과 좌석수를 반환
	 * 파라미터로 식당의 지역을 받음
	 */
	public void getSizeSeat(String restaurantLoc) throws SQLException {
		
		//좌석 종류를 받기 위한 변수 선언
		int seatNUM;
		//좌석 종류를 받기 위한 변수 선언
		String seat;

		while(true){

			System.out.println("\nWhich seat do you want to sit?");

			System.out.println("1. Single 2. Double 3. Quad (Insert 1 or 2 or 3)");

			seatNUM =scanner.nextInt();


			//Single을 선택한 경우 
			if(seatNUM==1){

				System.out.println("You selected Single.\n");

				seat="Single";

				break;

			}


			//Double을 선택한 경우
			else if(seatNUM==2){

				System.out.println("You selected Double.\n");

				seat="Double";

				break;

			}


			//Quad를 선택한 경우 
			else if(seatNUM==3){

				System.out.println("You selected Quad.\n");

				seat="Quad";

				break;

			}



			else

				System.out.println("<Warning> Insert 1 or 2 or 3 again.");

		}




		//getRestaurantBySeat(restaurantLoc, seat)로부터 받은 출력결과를 ArrayList에 넣음
		ArrayList<AvailableSeat> result = getRestaurantBySeat(restaurantLoc, seat);

		//출력형식
		System.out.println("[Restaurant name]	[inside "+seat+" seat]	[outside "+seat+" seat]");
		

		//검색조건을 만족하는 restaurant의 이름과 좌석종류에 따른 좌석 수를 출력 
		for (int i = 0; i < result.size(); i++) {
			if(result.size()==0)
				System.out.println("NONE");
			else

				System.out.println(result.get(i).toString());

		}//for ends



	}//getOpeningClosing ends


/*1215131 한희정
	 * 입력받은 지역과 좌석 종류에 해당하는 식당의 이름과 좌석 종류에 따른 좌석 수 반환
	 * 파라미터로 식당의 지역과 좌석종류를 받음
	 */
	
	public static ArrayList<AvailableSeat> getRestaurantBySeat(String location, String seat) throws SQLException {


		/*
			 * 데이터베이스와의 연결을 위한 Connection
			 * SQL문을 수행하기 위한 PreparedStatement
			 * select 쿼리의 결과값을 받기 위한 ResultSet
			 * 
			 * */
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		
		//결과값을 받을 array list 선언
		ArrayList<AvailableSeat> availableSeat= new ArrayList<AvailableSeat>();


		Statement stmt = null;
		//Select 쿼리 문을 받을 String 선언
		String selectTableSQL = "";
		
		//사용자의 입력 조건에 따른 Select Query문 작성
		if(seat=="Single"){

			selectTableSQL = "SELECT restaurant_name, inside_single_seat, outside_single_seat "

					+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_seat_available "

					+ "WHERE restaurant_name IN (SELECT restaurant_name "

					+ "FROM DBCOURSE_Restaurant, DBCOURSE_Location "

					+ "WHERE DBCOURSE_Restaurant.address = DBCOURSE_Location.address AND DBCOURSE_Location.location = ?)";
		}

		else if(seat == "Double"){
			selectTableSQL = "SELECT R.restaurant_name, I.double_seat as Inside_double_seat, O.double_seat as Outside_double_seat "

					+ "FROM DBCOURSE_Restaurant as R, DBCOURSE_Inside_seat_group as I, DBCOURSE_Outside_seat_group as O "

					+ "WHERE I.size=O.size and I.size=R.size and "

					+ "R.restaurant_name IN ( SELECT restaurant_name "
					+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Location "

					+ "WHERE location = ?)";
		}

		else if(seat == "Quad"){
			selectTableSQL = "SELECT R.restaurant_name, I.quad_seat as Inside_quad_seat, O.quad_seat as Outside_quad_seat "

					+ "FROM DBCOURSE_Restaurant as R, DBCOURSE_Inside_seat_group as I, DBCOURSE_Outside_seat_group as O "

					+ "WHERE I.size=O.size and I.size=R.size and "

					+ "R.restaurant_name IN ( SELECT restaurant_name "
					+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Location "

					+ "WHERE location = ?)";
		}




		try {

			dbConnection = dbdb.getDBConnection();

			stmt = dbConnection.createStatement();

			//use database

			String UseSQL = "USE RestaurantDB";

			stmt.executeUpdate(UseSQL);

			
			dbConnection.setAutoCommit(false);

			//사용자로부터 입력받은 조건을 select 쿼리문에 대입

			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setString(1, location);

			//사용자의 입력 조건에 따른 Select Query문 수행
			rs = preparedStatementSelect.executeQuery();


			//select 쿼리문을 수행하여 나온 결과값을  arraylist에 넣음
			while (rs.next()) {
				if(seat=="Single"){

					availableSeat.add(new AvailableSeat(rs.getString("restaurant_name"),
							rs.getInt("inside_single_seat"),
							rs.getInt("outside_single_seat")));
				}
				else if(seat=="Double"){
					availableSeat.add(new AvailableSeat(rs.getString("restaurant_name"),
							rs.getInt("Inside_double_seat"),
							rs.getInt("Outside_double_seat")));
				}
				else if(seat=="Quad"){
					availableSeat.add(new AvailableSeat(rs.getString("restaurant_name"),
							rs.getInt("Inside_quad_seat"),
							rs.getInt("Outside_quad_seat")));
				}
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



	//지역을 선택했을 경우의 메소드 끝


	
	//카테고리를 선택했을 경우의 메소드시작
	


	/*1215001 강린
	 * 사용자로부터 식당의 카테고리를 입력받는 메소드
	 */
	public String getCategory(){
		//사용자부터 카테고리를 받을 변수 선언
		int categoryNum;
		String category="";

		while(true){
			System.out.println("Choose the category of the restaurant");
			System.out.println("1.Korean  2.Chinese 3.Japanese 4.Indian 5.Fastfood 6.Western (Insert 1~6)");
			categoryNum =scanner.nextInt();
			
			//Korean을 선택한 경우
			if(categoryNum==1){
				System.out.println("You selected Korean.\n");
				category="Korean";
				break;
			}

			//Chinese를 선택한 경우
			else if(categoryNum==2){
				System.out.println("You selected Chinese.\n");
				category="Chinese";
				break;
			}
			
			//Japanese를 선택한 경우
			else if(categoryNum==3){
				System.out.println("You selected Japanese.\n");
				category="Japanese";
				break;
			}
			
			//Indian을 선택한 경우
			else if(categoryNum==4){
				System.out.println("You selected Indian.\n");
				category="Indian";
				break;
			}
			
			//Fastfood를 선택한 경우 
			else if(categoryNum==5){
				System.out.println("You selected Fastfood.\n");
				category="Fastfood";
				break;
			}
			
			//Western을 선택한 경우 
			else if(categoryNum==6){
				System.out.println("You selected Western.\n");
				category="Western";
				break;
			}


			else
				System.out.println("<Warning> Insert 1~6 again.\n");
		}

		return category;

	}//getCategory method ends


	/* 1215129
	 * 식당 중 사용자가 원하는 할인 방식을 입력하는 메소드
	 * 식당의 카테고리를 파라미터로 받음
	 * getRestaurantByPayment에 입력값 반환
	 */
	public void getPayment(String category) throws SQLException{

		//사용자로부터 할인 서비스를 받기 위한 변수 선언
		int paymentNum;
		String payment="";

		while(true){
			System.out.println("Choose the payment service ");
			System.out.println("1.Coupon only  2. Tele_discount only 3.Coupon&Tele_discount 4.None (Insert 1~4)");
			paymentNum =scanner.nextInt();

			//Coupon only를 선택한 경우
			if(paymentNum==1){
				System.out.println("You selected Coupon only.\n");
				payment="coupon";
				break;
			}
			
			//Tele_discount only를 선택한 경우
			else if(paymentNum==2){
				System.out.println("You selected Tele_discount only.\n");
				payment="tele_discount";
				break;
			}
			
			//Coupon&Tele_discount를 선택한 경우
			else if(paymentNum==3){
				System.out.println("You selected Coupon&Tele_discount.\n");
				payment="coupon&tele_discount";
				break;
			}
			
			//None을 선택한 경우 
			else if(paymentNum==4){
				System.out.println("You selected None.\n");
				payment="None";
				break;
			}
			else
				System.out.println("<Warning> Insert 1~4 again.\n");
		}//while문 끝
		
		//결과값을 받을 array list 선언
		ArrayList<String>names=getRestaurantByPayment(category,payment);

		System.out.println("Restaurants which offer "+payment+" discount:");
		//출력형식
		System.out.println("[Restaurant name]");
		
		//검색조건을 만족하는 결과를 출력
		for (int i = 0; i < names.size(); i++) {
			if(names.size()==0)
				System.out.println("NONE");
			else
				System.out.println(names.get(i));
		}//for ends
	}//getPayment method ends!

	/*1215129 한나영 
	 * 입력받은 카테고리에서 해당 하는 할인서비스가 가능한 식당 이름 반환
	 * 파라미터로 식당의 카테고리, 할인 종류를 받음
	 */
	public static ArrayList<String> getRestaurantByPayment(String category,String payment) throws SQLException {

		/*
			 * 데이터베이스와의 연결을 위한 Connection
			 * SQL문을 수행하기 위한 PreparedStatement
			 * select 쿼리의 결과값을 받기 위한 ResultSet
			 * 
			 * */
		
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		ArrayList<String> restaurant_name = new ArrayList<String>();

	
		Statement stmt = null;



		//Select 쿼리 문을 받을 String 선언
		String selectTableSQL="";
		
		//사용자의 입력 조건에 따른 Select Query문 작성

		if(payment=="coupon"){
			selectTableSQL = "SELECT restaurant_name "
					+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Payment "
					+ "WHERE coupon='Y' AND tele_discount='N' AND "
					+ "restaurant_name IN ( SELECT restaurant_name "
					+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Company "
					+ "WHERE category = ?)";

		}


		else if(payment=="tele_discount"){
			selectTableSQL = "SELECT restaurant_name "
					+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Payment "
					+ "WHERE coupon='N' AND tele_discount='Y' AND "
					+ "restaurant_name IN ( SELECT restaurant_name "
					+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Company "
					+ "WHERE category = ?)";


		}

		else if(payment=="coupon&tele_discount"){
			selectTableSQL = "SELECT restaurant_name "
					+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Payment "
					+ "WHERE coupon='Y' AND tele_discount='Y' AND "
					+ "restaurant_name IN ( SELECT restaurant_name "
					+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Company "
					+ "WHERE category = ?)";


		}

		else if(payment=="None"){
			selectTableSQL = "SELECT restaurant_name "
					+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Payment "
					+ "WHERE coupon='N' AND tele_discount='N' AND "
					+ "restaurant_name IN ( SELECT restaurant_name "
					+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Company "
					+ "WHERE category = ?)";


		}


		try {
			dbConnection = dbdb.getDBConnection();



			
			stmt = dbConnection.createStatement();
			//use database
			String UseSQL = "USE RestaurantDB";
			stmt.executeUpdate(UseSQL);
	


			dbConnection.setAutoCommit(false);
			
			//사용자로부터 입력받은 조건을 select 쿼리문에 대입
			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setString(1, category);

			//사용자의 입력 조건에 따른 Select Query문 수행
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
	


	/* 1215129 한나영	
	 * 식당 중 사용자가 원하는 가격대를 입력하는 메소드
	 * 식당의 카테고리를 파라미터로 받음
	 * getRestaurantByPrice에 입력값 반환
	 */
	public void getPrice(String category) throws SQLException {
		
		//사용자가 원하는 가격대를 입력받기 위한 변수 선언
		int priceNum;
		String price;

		while(true){

			System.out.println("\nChoose the price range");

			System.out.println("1. Under 10000won 2. Over 10000won (Insert 1 or 2)");

			priceNum =scanner.nextInt();


			//Under 10000won을 선택한 경우
			if(priceNum==1){

				System.out.println("You selected under 10000won.\n");

				price="up";

				break;

			}


			//Over 10000won을 선택한 경우 
			else if(priceNum==2){

				System.out.println("You selected over 10000won.\n");

				price="down";

				break;

			}



			else

				System.out.println("<Warning> Insert 1 or 2 again.");

		}




		//getRestaurantByPrice(category, price)로부터 받은 식당이름을 ArrayList에 넣음				
		ArrayList<PrintMenuName> names = getCategoryByPrice(category, price);
		//출력형식
		System.out.println("[Restaurant name]	[Menu name]");
		
		//검색조건을 만족하는 결과를 출력
		for (int i = 0; i < names.size(); i++) {
			if(names.size()==0)
				System.out.println("NONE");
			else

				System.out.println(names.get(i).toString());

		}//for ends



	}//getPrice ends

	/*1215129 한나영
	 * 입력받은 카테고리와 가격대에 해당하는 식당의 이름 반환
	 * 파라미터로 식당의 카테고리와 가격대를 받음
	 */
	public static ArrayList<PrintMenuName> getCategoryByPrice(String category, String price) throws SQLException {

		/*
			 * 데이터베이스와의 연결을 위한 Connection
			 * SQL문을 수행하기 위한 PreparedStatement
			 * select 쿼리의 결과값을 받기 위한 ResultSet
			 * 
			 * */
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		
		//결과값을 받을 array list 선언
		ArrayList<PrintMenuName> restaurant_name = new ArrayList<PrintMenuName>();

		Statement stmt = null;




		//Select 쿼리 문을 받을 String 선언
		String selectTableSQL = "";
		
		//사용자의 입력 조건에 따른 Select Query문 작성
		if(price=="up"){
			selectTableSQL = "SELECT restaurant_name, menu_name "

			+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Menu "

			+ "WHERE price <= 10000 AND "

			+ "restaurant_name IN ( SELECT restaurant_name "

			+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Company "
			+ "WHERE category = ?)";
		}

		else if(price== "down"){
			selectTableSQL = "SELECT restaurant_name, menu_name "

			+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Menu "

			+ "WHERE price > 10000 AND "

			+ "restaurant_name IN ( SELECT restaurant_name "

			+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Company "
			+ "WHERE category = ?)";
		}




		try {

			dbConnection = dbdb.getDBConnection();

			stmt = dbConnection.createStatement();

			//use database

			String UseSQL = "USE RestaurantDB";

			stmt.executeUpdate(UseSQL);


			dbConnection.setAutoCommit(false);

			//사용자로부터 입력받은 조건을 select 쿼리문에 대입
			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);

			preparedStatementSelect.setString(1, category);

			//사용자의 입력 조건에 따른 Select Query문 수행
			rs = preparedStatementSelect.executeQuery();


			//select 쿼리문을 수행하여 나온 결과값을 arraylist에 넣음

			while (rs.next()) {

				restaurant_name.add(new PrintMenuName(rs.getString("restaurant_name"),rs.getString("menu_name")));

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

	/* 1215001 강린
	 * 사용자가 원하는 연령대와 성별 입력받는 메소드
	 * 식당의 지역을 파라미터로 받음
	 *  getRestaurantByAgeGender 에 입력값과 식당 지역 반환
	 */
	public void getAgeGender(String restaurantLoc) throws SQLException{
		
		//사용자가 원하는 age와 gender값을 받기 위한 변수 선언
		int age=0;
		String gender = "";
		
		//사용자가 선택한 지역을 받는 변수 
		String location = restaurantLoc;

		while(true){
			System.out.println("\nChoose age(among 10, 20, 30, 40): ");
			age = scanner.nextInt();
			
			System.out.println("Choose gender(among F or M): ");
			gender = scanner.next();

			if(age==10||age==20||age==30||age==40){
				if(gender.equals("M")||gender.equals("F"))
					break;               
			}

			else
				System.out.println("<Warning> Insert age among 10, 20, 30, 40 and gender among M, F");

		}
		//결과값을 받을 array list 선언
		ArrayList<AgeGenderName> result =  getRestaurantByAgeGender(age, gender, location);

		//성별을 완성된 단어로 저장하는 string
		String genderLetter;
		if(gender.equals("M")){
			genderLetter="Male";               
		}
		else
			genderLetter="Female";

		//출력형식
		System.out.println(age+" OR "+genderLetter+" is the major_client of these restaurant: ");
		System.out.println("[Restaurant name]	[Age]	[Gender]");


		//검색조건을 만족하는 결과를 출력
		for (int i = 0; i < result.size(); i++) {
			if(result.size()==0)
				System.out.println("NONE");
			else
			System.out.println(result.get(i));
		}//for ends


	}

	/*1215001 강린
	 * 입력받은 번호에 해당하는 지역의 Major client가 사용자가 입력한 age 혹은 gender인 식당 이름 반환
	 * 파라미터로 식당의 지역과 age, gender값을 받음
	 */		

	public static ArrayList<AgeGenderName> getRestaurantByAgeGender(int age, String gender, String restaurantLoc) throws SQLException{
		/*
		 * 데이터베이스와의 연결을 위한 Connection
		 * SQL문을 수행하기 위한 PreparedStatement
		 * select 쿼리의 결과값을 받기 위한 ResultSet
		 * 
		 * */
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		ArrayList<AgeGenderName> agegendername = new ArrayList<AgeGenderName>();

		Statement stmt = null;
		
		//Select 쿼리 문을 받을 String 선언
		String selectTableSQL="";
		
		//사용자의 입력 조건에 따른 Select Query문 작성
		selectTableSQL = "(SELECT restaurant_name, age, gender "
				+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Major_client "
				+ "WHERE age = ? AND "
				+ "restaurant_name IN ( SELECT restaurant_name "
				+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Location "
				+ "WHERE location=? )) "
				+ "UNION "
				+ "(SELECT restaurant_name, age, gender "
				+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Major_client "
				+ "WHERE gender = ? AND "
				+ "restaurant_name IN ( SELECT restaurant_name "
				+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Location "
				+ "WHERE location = ? ))";




		try {
			dbConnection = dbdb.getDBConnection();



			
			stmt = dbConnection.createStatement();
			//use database
			String UseSQL = "USE RestaurantDB";
			stmt.executeUpdate(UseSQL);
			
			dbConnection.setAutoCommit(false);
			

			//사용자로부터 입력받은 조건을 select 쿼리문에 대입
			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setInt(1, age);
			preparedStatementSelect.setString(2, restaurantLoc);
			preparedStatementSelect.setString(3, gender);
			preparedStatementSelect.setString(4, restaurantLoc);

			//사용자의 입력 조건에 따른 Select Query문 수행
			rs = preparedStatementSelect.executeQuery();

			//select 쿼리문을 수행하여 나온 결과값을 arraylist에 넣음

			while (rs.next()) {

				agegendername.add(new AgeGenderName(rs.getString("restaurant_name"),
						rs.getInt("age"),
						rs.getString("gender")));
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
		return agegendername;
	}
	
	/*1215001 강린
	 * 사용자가 원하는 (무)알콜 음료를 번호로 입력받는 메소드
	 * 파라미터로 카테고리를 받는다
	 * 카테고리와 음료 번호를 getDrinkName에 넘겨준다
	 */
	public static void getDrink(String category) throws SQLException{
		//사용자가 원하는 (무)알콜 음료를 받기 위한 변수 선언
		int alcoholNum = 0;
		int non_alcoholNum= 0;
		String alcohol="";
		String non_alcohol="";


		//while 
		while(true){
			//알콜음료 선택
			System.out.println("\nChoose alcohol drink you want (Insert the number)");
			System.out.println("1. Makgeolli");
			System.out.println("2. Japanese_Rice_Wine");
			System.out.println("3. Whiskey");
			System.out.println("4. Beer");
			System.out.println("5. Mao_tai");
			System.out.println("6. Wine");
			System.out.println("7. Soju");
			alcoholNum=scanner.nextInt();

			if(alcoholNum>=1&&alcoholNum<=7)
				break;
			else
				System.out.println("<Warning> Insert from 1 ~ 7");

		}


		//while2 
		while(true){
			//무알콜음료 선택
			System.out.println("\nChoose nonalcohol drink you want (Insert the number)");
			System.out.println("1. Ssanghwa_tea");
			System.out.println("2. Green_tea");
			System.out.println("3. Lassi");
			System.out.println("4. Coke");
			System.out.println("5. Barley_tea");
			System.out.println("6. Chrysanthemum_tea");
			System.out.println("7. Ade");
			non_alcoholNum=scanner.nextInt();

			if(non_alcoholNum>=1&&non_alcoholNum<=7)
				break;
			else
				System.out.println("<Warning>Insert 1~7 again");

		} //while ends



		//switch 사용자가 원하는 음료종류값을 String으로 저장
		switch(alcoholNum){
		case 1: 
			alcohol="Makgeolli";
			break;
		case 2: 
			alcohol="Japanese_Rice_Wine";
			break;
		case 3: 
			alcohol="Whiskey";
			break;
		case 4:
			alcohol="Beer";
			break;
		case 5: 
			alcohol="Mao_tai";
			break;
		case 6: 
			alcohol="Wine";
			break;               
		case 7: 
			alcohol="Soju";
			break;
		default: 
			System.out.println("default");
		}//switch ends



		//switch 사용자가 원하는 음료종류값을 String으로 저장
		switch(non_alcoholNum){
		case 1: 
			non_alcohol="Ssanghwa_tea";
			break;
		case 2:
			non_alcohol="Green_tea";
			break;
		case 3: 
			non_alcohol="Lassi";
			break;
		case 4: 
			non_alcohol="Coke";
			break;
		case 5: 
			non_alcohol="Barley_tea";
			break;
		case 6: 
			non_alcohol="Chrysanthemum_tea";
			break;
		case 7: 
			non_alcohol="Ade";
			break;
		default:
			System.out.println("default");
		}//switch ends

		//출력형식
		System.out.println("Alcohol drink "+alcohol+" or Nonalcohol drink "+non_alcohol+" is offered in these restaurant");
		System.out.println("[Restaurant name]	[alcohol]		[non_alcohol]");

		//getDrinkName(category, alcohol, non_alcohol)로부터 받은 결과값을 ArrayList에 넣음
		ArrayList<DrinkName> result =  getDrinkName(category, alcohol, non_alcohol);

		//검색조건을 만족하는 결과를 출력
		for (int i = 0; i < result.size(); i++) {
			if(result.size()==0)
				System.out.println("NONE");
			else
			System.out.println(result.get(i));
		}//for ends
	}//getDrink method ends



	/*1215001 강린
	 * 카테고리와 알콜음료, 무알콜 음료를 파라미터로 받아 해당 음료가 있는 식당의 이름과 음료 종류 반환하는 메소드
	 */

	public static ArrayList<DrinkName> getDrinkName(String category_name, String alcohol, String non_alcohol) throws SQLException{

		/*
			 * 데이터베이스와의 연결을 위한 Connection
			 * SQL문을 수행하기 위한 PreparedStatement
			 * select 쿼리의 결과값을 받기 위한 ResultSet
			 * 
			 * */
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		
		//결과값을 받을 array list 선언
		ArrayList<DrinkName> drinkname = new ArrayList<DrinkName>();

		
		Statement stmt = null;


		//Select 쿼리 문을 받을 String 선언
		String selectTableSQL="";
		//사용자의 입력 조건에 따른 Select Query문 작성
		selectTableSQL = "(SELECT restaurant_name, alcohol_drink, nonalcohol_drink "
				+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Beverage "
				+ "WHERE alcohol_drink = ? AND "
				+ "restaurant_name IN ( SELECT restaurant_name "
				+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Company "
				+ "WHERE category = ? )) "
				+ "UNION "
				+ "(SELECT restaurant_name, alcohol_drink, nonalcohol_drink "
				+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Beverage "
				+ "WHERE nonalcohol_drink = ? AND "
				+ "restaurant_name  IN ( SELECT restaurant_name "
				+ "FROM DBCOURSE_Restaurant NATURAL JOIN DBCOURSE_Company "
				+ "WHERE category = ? )) ";
		



		try {
			dbConnection = dbdb.getDBConnection();


			stmt = dbConnection.createStatement();
			//use database
			String UseSQL = "USE RestaurantDB";
			stmt.executeUpdate(UseSQL);



			dbConnection.setAutoCommit(false);
			
			//사용자로부터 입력받은 조건을 select 쿼리문에 대입
			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setString(1, alcohol);
			preparedStatementSelect.setString(2, category_name);
			preparedStatementSelect.setString(3, non_alcohol);
			preparedStatementSelect.setString(4, category_name);

			//사용자의 입력 조건에 따른 Select Query문 수행
			rs = preparedStatementSelect.executeQuery();

			//select 쿼리문을 수행하여 나온 결과값을 arraylist에 넣음
			while (rs.next()) {

				drinkname.add(new DrinkName(rs.getString("restaurant_name"),
						rs.getString("alcohol_drink"),
						rs.getString("nonalcohol_drink")));


			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			dbConnection.rollback();
		} finally {
			if (preparedStatementSelect != null)
				preparedStatementSelect.close();
			if (dbConnection != null)
				dbConnection.close();
		}

		return drinkname;
	}//getDrinkName method ends
	

	/*1215131 한희정
	 * 입력받은 메뉴에 해당하는 메뉴의 메뉴평가를 수정하는 메소드 
	 */
	public void getEditMenuEval()throws SQLException {

		//사용자로부터 메뉴 이름을 입력받기 위한 변수선언
		String menuName;
		
		//사용자로부터 메뉴 평가 점수를 입력받기 위한 변수 선언
		int spicy=0;
		int sweetness=0;
		int salty=0;
		int sourness=0;

		System.out.println("\nChoose the menu you want to edit");
		menuName = scanner.next();

		System.out.println("\n you choose "+ menuName);

		System.out.println("Insert the score (0~5)");
		
		System.out.print("Spicy: ");
		spicy = scanner.nextInt();
		
		System.out.print("Sweetness: ");
		sweetness = scanner.nextInt();

		System.out.print("salty: ");
		salty = scanner.nextInt();

		System.out.print("Sourness: ");
		sourness = scanner.nextInt();
		
		//getEditMenuEvalByMenu:메뉴 평가를 변경하는 메소드 
		//사용자가 입력한 값으로 해당 메뉴의 메뉴 평가를 변경
		getEditMenuEvalByMenu(menuName,spicy,sweetness,salty,sourness);

	}//getEditMenuEval() ends

	/*1215131 한희정
	 * getEditMenuEval()로 부터 메뉴평가를 변경할 메뉴와 변경할 내용을 받아 
	 * 해당 메뉴의 메뉴 평가를 변경하는 메소드
	 * */
	public static void getEditMenuEvalByMenu(String menuName, int spicy, int sweetness, int salty, int sourness) throws SQLException {
		
		/*
		 * 데이터베이스와의 연결을 위한 Connection
		 * SQL문을 수행하기 위한 PreparedStatement,Statement
		 
		 * */
			Connection dbConnection = null;
			PreparedStatement preparedStatementUpdate = null;
			Statement stmt = null;
			
			//UPDATE쿼리문을 작성
			String updateTableSQL = "UPDATE DBCOURSE_Menu_evaluation set spicy = ?, sweetness = ?, salty = ?, sourness = ? where menu_name = ?";

			try {
				dbConnection = dbdb.getDBConnection();
				stmt = dbConnection.createStatement();
				//use database
				String UseSQL = "USE RestaurantDB";
				stmt.executeUpdate(UseSQL);
				
				//사용자로부터 입력받은 조건을 UPDATE쿼리문에 대입
				preparedStatementUpdate = dbConnection.prepareStatement(updateTableSQL);

				preparedStatementUpdate.setInt(1, spicy);				
				preparedStatementUpdate.setInt(2, sweetness);				
				preparedStatementUpdate.setInt(3, salty);				
				preparedStatementUpdate.setInt(4, sourness);
				preparedStatementUpdate.setString(5, menuName);
				
				//UPDATE쿼리문 수행
				preparedStatementUpdate.executeUpdate();

				System.out.println("Update Table!");
			} catch (SQLException e) {

				System.out.println(e.getMessage());

				dbConnection.rollback();

			} finally {

				if (preparedStatementUpdate != null)

					preparedStatementUpdate.close();

				if (dbConnection != null)

					dbConnection.close();
			}
			
			}//getEditMenuEvalByMenu ends
	
	
	/*1315002 강지원
	    * 삭제할 식당의 이름을 받는 메소드
	    * 입력값을 deleteRestaurant로 넘겨준다
	    */
	   public void getNameForDelete() throws SQLException{
	      System.out.println("enter restaurant name to delete");
	      String nameToDelete=scanner.next();

	      deleteRestaurant(nameToDelete);


	   }//getNameForDelete method ends



	   /*1315002 강지원
	    * 파라미터로 받은 식당이름을 식당 테이블에서 삭제하는 메소드
	    */
	   public void deleteRestaurant(String name) throws SQLException{
		  //Test:사용자로부터 Configuration 파일과 SQL script의 정보를 읽어오는 클래스
		   Test dbdb = new Test();
		   /*
			 * 데이터베이스와의 연결을 위한 Connection
			 * SQL문을 수행하기 위한 PreparedStatement,Statement
			 * 
			 * */
	      PreparedStatement preparedStatementDelete = null;
	      Connection dbConnection=null;
	      Statement stmt = null;
	      
	    //DELETE 쿼리 문을 받을 String 
	      String createStmt="DELETE from DBCOURSE_Restaurant WHERE restaurant_name= ? ";
	      try{

	         dbConnection=dbdb.getDBConnection();
	         stmt = dbConnection.createStatement();

	         //use database
	         String UseSQL = "USE RestaurantDB";
	         stmt.executeUpdate(UseSQL);
	         

	       //사용자로부터 입력받은 조건을 DELETE 쿼리문에 대입
	         preparedStatementDelete = dbConnection.prepareStatement(createStmt);
	         preparedStatementDelete.setString(1, name);
	         
	       //DELETE 쿼리문 수행
	         preparedStatementDelete.executeUpdate(); 
	         System.out.println("restaurant "+name+" is deleted.");

	      }

	      catch(SQLException excp){
	         System.out.println("Error in deletion");
	      }



	   }//deleteRestaurant method ends

	   /*1315002 강지원
	    * 추가할 지역의 이름을 받는 메소드
	    * 입력값을 insertintoDeliveryLocation으로 넘겨준다
	    */
	   public void getLocationForInsert() throws SQLException{
	      System.out.println("Enter Location to insert");
	      String nameTolocation=scanner.next();
	      System.out.println("Enter Delivery location to insert");
	      String nameToDeliverylocation=scanner.next();
	     
	      //사용자로부터 입력받은 nameTolocation,nameToDeliverylocation 값을 이용해 intoDeliveryLocation 호출
	      insertintoDeliveryLocation(nameTolocation, nameToDeliverylocation);


	   }//getNameForDelete method ends



	   /*1315002 강지원
	    * delivery location 추가하는 메소드
	    */
	   public void insertintoDeliveryLocation(String location, String delivery_location) throws SQLException{
	      //Test:사용자로부터 Configuration 파일과 SQL script의 정보를 읽어오는 클래스
		   Test dbdb = new Test();
		   
		   /*
			 * 데이터베이스와의 연결을 위한 Connection
			 * SQL문을 수행하기 위한 PreparedStatement,Statement
			 * 
			 * */
	      PreparedStatement preparedStatementInsert = null;
	      Connection dbConnection=null;
	      Statement stmt = null;
	      
	      //INSERT INTO 쿼리문 작성
	      String insertStmt="INSERT INTO DBCOURSE_Delivery_location (location, delivery_location) values (?,?) ";
	      try{

	         dbConnection=dbdb.getDBConnection();
	         stmt = dbConnection.createStatement();

	         //use database
	         String UseSQL = "USE RestaurantDB";
	         stmt.executeUpdate(UseSQL);
	       

	       //사용자로부터 입력받은 조건을 INSERT INTO 쿼리문에 대입
	         preparedStatementInsert = dbConnection.prepareStatement(insertStmt);
	         preparedStatementInsert.setString(1, location);
	         preparedStatementInsert.setString(2, delivery_location);
	         
	         //INSERT INTO 쿼리문 수행
	         preparedStatementInsert.executeUpdate(); 
	         System.out.println("Location "+location+" is inserted and Delivery location "+delivery_location+" is inserted");

	      }

	      catch(SQLException excp){
	         System.out.println("Error in deletion");
	      }



	   }//insert delivery location ends
}//RestaurantDBinput class endS!



