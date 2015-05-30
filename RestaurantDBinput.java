package KANGHANNAYOUNG;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class RestaurantDBinput{
	static RestaurantDB dbdb = new RestaurantDB();
	static Scanner scanner = new Scanner(System.in);

	/* Custom과 Manager 선택하는 Method*/
	public int CustomerOrManager(){
		int customerormanager;

		while(true){
			System.out.println("Choose 1. Customer mode or 2. Manager mode");
			customerormanager = scanner.nextInt();

			if(customerormanager == 1){
				System.out.println("You choose Customer mode.");
				break;
			}
			else if(customerormanager == 2){
				System.out.println("You choose Manger mode.");
				break;		
			}
		}
		return customerormanager;
	}

	/*
	 * 지역과 카테고리 중 어느 것으로 검색을 시작할지 결정하는 method
	 * 1과 2중 입력받은 하나를 return
	 * 다른 것 입력받으면 1과 2중 하나 선택할 때까지 loop
	 * 1-location, 2-category
	 */
	public int LocaionOrCategory(){
		int locationorcategory;

		while(true){
			System.out.println("\n\n\nStart searching with 1. restaurant's location 2. food's category (enter 1 or 2)");
			System.out.println("(or enter 3 if you want to exit the program)");
			locationorcategory =scanner.nextInt();

			if(locationorcategory==1){
				System.out.println("You select restaurant's location.\n");
				break;
			}

			else if(locationorcategory==2){
				System.out.println("You select  food's category.");
				//System.out.println("Which food’s category do you want to find?(enter Korean or ...)");
				//이거 필요해? 중복일꺼같은데??
				break;
			}

			else if(locationorcategory==3){
				System.out.println("=====Program ended. BYE!=====");
				break;
			}


			else
				System.out.println("<Warning> insert 1 or 2 or 3 again.");
		}


		return locationorcategory;

	}//ends LocationOrCategory method


	/*
	 * 지역으로 검색을 시작할 경우 지역 설정
	 * 해당 지역 return
	 */
	public String getLocation(){
		int locationNum;
		String location="";

		while(true){
			System.out.println("Which restaurant’s location do you prefer?");
			System.out.println("1. Ewha 2. Shinchon 3. Hongdae (enter 1 or 2 or 3)");
			locationNum =scanner.nextInt();

			if(locationNum==1){
				System.out.println("You select Ewha.\n");
				location="Ewha";
				break;
			}

			else if(locationNum==2){
				System.out.println("You select Shinchon.\n");
				location="Shinchon";
				break;
			}

			else if(locationNum==3){
				System.out.println("You select Hongdae.\n");
				location="Hongdae";
				break;
			}

			else
				System.out.println("<Warning> insert 1 or 2 or 3 again.\n");
		}

		return location;

	}//getLocation method ends

	/*
	 * 예약 가능한 식당 중 사용자가 원하는 영업시간을 입력하는 메소드
	 * 식당의 지역을 파라미터로 받음
	 * getRestaurantByReservation에 입력값 반환
	 */
	public void getOpeningClosing(String restaurantLoc) throws SQLException {

		System.out.println("\nInput maximum opening_hours: ");
		int opening = scanner.nextInt();
		System.out.println("Input minimum closing_hours: ");
		int closing = scanner.nextInt();

		String location = restaurantLoc;


		ArrayList<String> names = getRestaurantByReservation(opening, closing, location);


		System.out.println("Restaurants which open at "+opening+" and close at "+closing+": ");


		for (int i = 0; i < names.size(); i++) {
			System.out.println(names.get(i));
		}//for ends


	}//getOpeningClosing ends



	/*
	 * 입력받은 개장 및 마감 시간에 해당하는 식당의 이름 반환하는 메소드
	 */
	public static ArrayList<String> getRestaurantByReservation(int opening, int closing, String location) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		ArrayList<String> restaurant_name = new ArrayList<String>();

		//지원
		Statement stmt = null;



		String selectTableSQL="";
		if(location=="Ewha"){
			selectTableSQL = "SELECT restaurant_name "
					+ "FROM Working_hours NATURAL JOIN Service "
					+ "WHERE opening <= ? AND closing >= ? AND "
					+ "restaurant_name IN ( SELECT restaurant_name "
					+ "FROM location_ewha)";

		}


		else if(location=="Shinchon"){
			selectTableSQL = "SELECT restaurant_name "
					+ "FROM Working_hours NATURAL JOIN Service "
					+ "WHERE opening <= ? AND closing >= ? AND reservation = 'Y' AND "
					+ "restaurant_name IN ( SELECT restaurant_name "
					+ "FROM location_shinchon)";

		}

		else if(location=="Hongdae"){
			selectTableSQL = "SELECT restaurant_name "
					+ "FROM Working_hours NATURAL JOIN Service "
					+ "WHERE opening <= ? AND closing >= ? AND reservation = 'Y' AND "
					+ "restaurant_name IN ( SELECT restaurant_name "
					+ "FROM location_hongdae)";
		}





		try {
			dbConnection = dbdb.getDBConnection();



			//지원
			stmt = dbConnection.createStatement();
			//use database
			String UseSQL = "USE RestaurantDB";
			stmt.executeUpdate(UseSQL);
			//System.out.println("USE DATABASE done...");


			dbConnection.setAutoCommit(false);

			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setInt(1, opening);
			preparedStatementSelect.setInt(2, closing);

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
	}//지역, 오픈, 마감 select ends


	/*
	 * 이미 선택한 지역에서 배달 가능한가 알아보는 지역 입력받는 메소드
	 * 식당의 지역을 파라미터로 받음
	 * getRestaurantByDelivery에 입력값과 식당의 지역 반환
	 */
	public void getDeliveryLocation(String restaurantLoc) throws SQLException{
		int locationNum;
		String restlocation;

		while(true){
			System.out.println("\nWhich location do you want to get delivery?");
			System.out.println("1. Ewha 2. Shinchon 3. Hongdae (enter 1 or 2 or 3)");
			locationNum =scanner.nextInt();

			if(locationNum==1){
				System.out.println("You select Ewha.\n");
				restlocation="Ewha";
				break;
			}

			else if(locationNum==2){
				System.out.println("You select Shinchon.\n");
				restlocation="Shinchon";
				break;
			}

			else if(locationNum==3){
				System.out.println("You select Hongdae.\n");
				restlocation="Hongdae";
				break;
			}

			else
				System.out.println("<Warning> insert 1 or 2 or 3 again.");
		}


		ArrayList<String> names =getRestaurantByDelivery( restaurantLoc, restlocation);

		for(int i=0;i<names.size();i++){
			System.out.println(names.get(i));

		}
	}//ends getDeliveryLocation







	/*
	 * 입력받은 번호에 해당하는 지역에 배달 가능한 식당 이름 반환
	 * 파라미터로 식당의 지역과 배달가능한지 알아보는 지역을 받음
	 */
	public static ArrayList<String> getRestaurantByDelivery(String restaurantLoc, String restlocation) throws SQLException{

		//select query 입력
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		ArrayList<String> restaurant_name = new ArrayList<String>();

		//지원
		//예약가능하지 않은 식당도 보여주는 것으로 변경 
		Statement stmt = null;


		String selectTableSQL = "SELECT restaurant_name "
				+ "FROM Restaurant NATURAL JOIN Location "
				+ "WHERE location= ? AND "
				+"exists"
				+"(SELECT location "
				+ "FROM Delivery_location "
				+ "WHERE location = ? AND delivery_location = ?)";


		try {
			dbConnection = dbdb.getDBConnection();



			//지원
			stmt = dbConnection.createStatement();
			//use database
			String UseSQL = "USE RestaurantDB";
			stmt.executeUpdate(UseSQL);
			System.out.println("USE DATABASE done...");




			dbConnection.setAutoCommit(false);

			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setString(1, restaurantLoc);
			preparedStatementSelect.setString(2, restaurantLoc);
			preparedStatementSelect.setString(3, restlocation);

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
	}//getDeliveryAvailableLocation ends

	public void getSizeSeat(String restaurantLoc) throws SQLException {

		int seatNUM;
		String seat;

		while(true){

			System.out.println("\nWhich seats do you prefer");

			System.out.println("1. Single 2. Double 3. Quad (enter 1 or 2 or 3)\n");

			seatNUM =scanner.nextInt();



			if(seatNUM==1){

				System.out.println("You select Single.\n");

				seat="Single";

				break;

			}



			else if(seatNUM==2){

				System.out.println("You select Double.\n");

				seat="Double";

				break;

			}



			else if(seatNUM==3){

				System.out.println("You select Quad.\n");

				seat="Quad";

				break;

			}



			else

				System.out.println("<Warning> insert 1 or 2 or 3 again.");

		}





		ArrayList<AvailableSeat> names = getRestaurantBySeat(restaurantLoc, seat);



		for (int i = 0; i < names.size(); i++) {

			System.out.println(names.get(i).toString());

		}//for ends



	}//getOpeningClosing ends

	public static ArrayList<AvailableSeat> getRestaurantBySeat(String location, String seat) throws SQLException {

		Connection dbConnection = null;

		PreparedStatement preparedStatementSelect = null;

		ResultSet rs = null;

		ArrayList<AvailableSeat> availableSeat= new ArrayList<AvailableSeat>();


		Statement stmt = null;





		String selectTableSQL = "";
		if(seat=="Single"){

			selectTableSQL = "SELECT restaurant_name, Seat_available.inside_single_seat, Seat_available.outside_single_seat "

					+ "FROM Restaurant NATURAL JOIN Seat_available "

					+ "WHERE restaurant_name IN (SELECT restaurant_name "

					+ "FROM Restaurant, Location "

					+ "WHERE Restaurant.address = Location.address AND Location.location = ?)";
		}

		else if(seat == "Double"){
			selectTableSQL = "SELECT R.restaurant_name, I.double_seat as Inside_double_seat, O.double_seat as Outside_double_seat "

					+ "FROM Restaurant as R, Inside_seat_group as I, Outside_seat_group as O "

					+ "WHERE I.size=O.size and I.size=R.size and "

					+ "R.restaurant_name IN ( SELECT restaurant_name "
					+ "FROM Restaurant NATURAL JOIN Location "

					+ "WHERE location = ?)";
		}

		else if(seat == "Quad"){
			selectTableSQL = "SELECT R.restaurant_name, I.quad_seat as Inside_quad_seat, O.quad_seat as Outside_quad_seat "

					+ "FROM Restaurant as R, Inside_seat_group as I, Outside_seat_group as O "

					+ "WHERE I.size=O.size and I.size=R.size and "

					+ "R.restaurant_name IN ( SELECT restaurant_name "
					+ "FROM Restaurant NATURAL JOIN Location "

					+ "WHERE location = ?)";
		}




		try {

			dbConnection = dbdb.getDBConnection();

			stmt = dbConnection.createStatement();

			//use database

			String UseSQL = "USE RestaurantDB";

			stmt.executeUpdate(UseSQL);

			System.out.println("USE DATABASE done…");

			dbConnection.setAutoCommit(false);

			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);

			preparedStatementSelect.setString(1, location);


			rs = preparedStatementSelect.executeQuery();



			while (rs.next()) {
				if(seat=="Single"){

					availableSeat.add(new AvailableSeat(rs.getString("restaurant_name"),
							rs.getInt("Seat_available.inside_single_seat"),
							rs.getInt("Seat_available.outside_single_seat")));
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
	public String getCategory(){
		int categoryNum;
		String category="";

		while(true){
			//System.out.println("Which food’s category do you want to find?");
			System.out.println("1.Korean  2.Chinese 3.Japanese 4.Indian 5.Fastfood 6.Western (enter 1~6)");
			categoryNum =scanner.nextInt();

			if(categoryNum==1){
				System.out.println("You select Korean.\n");
				category="Korean";
				break;
			}

			else if(categoryNum==2){
				System.out.println("You select Chinese.\n");
				category="Chinese";
				break;
			}

			else if(categoryNum==3){
				System.out.println("You select Japanese.\n");
				category="Japanese";
				break;
			}
			else if(categoryNum==4){
				System.out.println("You select Indian.\n");
				category="Indian";
				break;
			}

			else if(categoryNum==5){
				System.out.println("You select Fastfood.\n");
				category="Fastfood";
				break;
			}
			else if(categoryNum==6){
				System.out.println("You select Western.\n");
				category="Western";
				break;
			}


			else
				System.out.println("<Warning> insert 1~6 again.\n");
		}

		return category;

	}//getCategory method ends

	public void getPayment(String category) throws SQLException{

		int paymentNum;
		String payment="";

		while(true){
			System.out.println("Choose a way of discount: ");
			System.out.println(“1. Coupon only  2. Tele_discount only 3. Coupon&tele_discount 4. None (enter 1~4)");
			paymentNum =scanner.nextInt();

			if(paymentNum==1){
				System.out.println("You select coupon only.\n");
				payment="coupon";
				break;
			}

			else if(paymentNum==2){
				System.out.println("You select tele_discount only.\n");
				payment="tele_discount";
				break;
			}
			else if(paymentNum==3){
				System.out.println("You select coupon&tele_discount.\n");
				payment="coupon&tele_discount";
				break;
			}
			else if(paymentNum==4){
				System.out.println("You select None.\n");
				payment="None";
				break;
			}
			else
				System.out.println("<Warning> insert 1~4 again.\n");
		}//while문 끝

		ArrayList<String>names=getRestaurantByPayment(category,payment);

		//System.out.println("Restaurants which offer "+payment+"discount: ");

		for (int i = 0; i < names.size(); i++) {
			System.out.println(names.get(i));
		}//for ends
	}//getPayment method ends!

	public static ArrayList<String> getRestaurantByPayment(String category,String payment) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		ArrayList<String> restaurant_name = new ArrayList<String>();

		//지원
		Statement stmt = null;



		String selectTableSQL="";
		if(payment=="coupon"){
			selectTableSQL = "SELECT restaurant_name "
					+ "FROM Restaurant NATURAL JOIN Payment "
					+ "WHERE coupon='Y' AND tele_discount='N' AND "
					+ "restaurant_name IN ( SELECT restaurant_name "
					+ "FROM Restaurant NATURAL JOIN Company "
					+ "WHERE category = ?)";

		}


		else if(payment=="tele_discount"){
			selectTableSQL = "SELECT restaurant_name "
					+ "FROM Restaurant NATURAL JOIN Payment "
					+ "WHERE coupon='N' AND tele_discount='Y' AND "
					+ "restaurant_name IN ( SELECT restaurant_name "
					+ "FROM Restaurant NATURAL JOIN Company "
					+ "WHERE category = ?)";


		}

		else if(payment=="coupon&tele_discount"){
			selectTableSQL = "SELECT restaurant_name "
					+ "FROM Restaurant NATURAL JOIN Payment "
					+ "WHERE coupon='Y' AND tele_discount='Y' AND "
					+ "restaurant_name IN ( SELECT restaurant_name "
					+ "FROM Restaurant NATURAL JOIN Company "
					+ "WHERE category = ?)";


		}

		else if(payment=="None"){
			selectTableSQL = "SELECT restaurant_name "
					+ "FROM Restaurant NATURAL JOIN Payment "
					+ "WHERE coupon='N' AND tele_discount='N' AND "
					+ "restaurant_name IN ( SELECT restaurant_name "
					+ "FROM Restaurant NATURAL JOIN Company "
					+ "WHERE category = ?)";


		}


		try {
			dbConnection = dbdb.getDBConnection();



			//지원
			stmt = dbConnection.createStatement();
			//use database
			String UseSQL = "USE RestaurantDB";
			stmt.executeUpdate(UseSQL);
			//System.out.println("USE DATABASE done...");


			dbConnection.setAutoCommit(false);

			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setString(1, category);

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

	public void getPrice(String category) throws SQLException {

		int priceNum;
		String price;

		while(true){

			System.out.println("\nChoose the price: ");

			System.out.println("1. Less than 10000 2. Greater than 10000 (enter 1 or 2)");

			priceNum =scanner.nextInt();



			if(priceNum==1){

				System.out.println("You select less than 10000.\n");

				price="up";

				break;

			}



			else if(priceNum==2){

				System.out.println("You select greater than 10000.\n");

				price="down";

				break;

			}



			else

				System.out.println("<Warning> insert 1 or 2 again.");

		}





		ArrayList<PrintMenuName> names = getCategoryByPrice(category, price);



		for (int i = 0; i < names.size(); i++) {

			System.out.println(names.get(i).toString());

		}//for ends



	}//getPrice ends

	public static ArrayList<PrintMenuName> getCategoryByPrice(String category, String price) throws SQLException {

		Connection dbConnection = null;

		PreparedStatement preparedStatementSelect = null;

		ResultSet rs = null;

		ArrayList<PrintMenuName> restaurant_name = new ArrayList<PrintMenuName>();

		Statement stmt = null;





		String selectTableSQL = "";
		if(price=="up"){
			selectTableSQL = "SELECT restaurant_name, menu_name "

			+ "FROM Restaurant NATURAL JOIN Menu "

			+ "WHERE price <= 10000 AND "

			+ "restaurant_name IN ( SELECT restaurant_name "

			+ "FROM Restaurant NATURAL JOIN Company "
			+ "WHERE category = ?)";
		}

		else if(price== "down"){
			selectTableSQL = "SELECT restaurant_name, menu_name "

			+ "FROM Restaurant NATURAL JOIN Menu "

			+ "WHERE price >= 10000 AND "

			+ "restaurant_name IN ( SELECT restaurant_name "

			+ "FROM Restaurant NATURAL JOIN Company "
			+ "WHERE category = ?)";
		}




		try {

			dbConnection = dbdb.getDBConnection();









			stmt = dbConnection.createStatement();

			//use database

			String UseSQL = "USE RestaurantDB";

			stmt.executeUpdate(UseSQL);

			System.out.println("USE DATABASE done...");









			dbConnection.setAutoCommit(false);



			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);

			preparedStatementSelect.setString(1, category);

			rs = preparedStatementSelect.executeQuery();



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

	/*
	 * 사용자가 원하는 연령대와 성별 입력받는 메소드
	 * 식당의 지역을 파라미터로 받음
	 *  getRestaurantByAgeGender 에 입력값과 식당 지역 반환
	 */
	public void getAgeGender(String restaurantLoc) throws SQLException{
		int age=0;
		String gender = "";
		String location = restaurantLoc;

		//while start(get input until correct input is entered)
		while(true){
			System.out.println("\nInput age (among 10, 20, 30, 40): ");

			age = scanner.nextInt();
			System.out.println("Input gender (among F or M): ");
			gender = scanner.next();

			//제대로된 input일 경우 loop 나감
			if(age==10||age==20||age==30||age==40){
				if(gender.equals("M")||gender.equals("F"))
					break;               
			}

			else
				System.out.println("<Warning> enter age among 10, 20, 30, 40 and gender among M, F");

		}




		ArrayList<AgeGenderName> names =  getRestaurantByAgeGender(age, gender, location);

		//성별을 완성된 단어로 저장하는 string
		String genderLetter;
		if(gender.equals("M")){
			genderLetter="Man";               
		}
		else
			genderLetter="Female";

		System.out.println(“Restaurants which people who is near "+age+" or "+genderLetter+" like : ");
		System.out.println("[Restaurant name]	[Age]	[Gender]");



		for (int i = 0; i < names.size(); i++) {
			System.out.println(names.get(i));
		}//for ends


	}


	public static ArrayList<AgeGenderName> getRestaurantByAgeGender(int age, String gender, String restaurantLoc) throws SQLException{
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		ArrayList<AgeGenderName> agegendername = new ArrayList<AgeGenderName>();

		//지원
		Statement stmt = null;



		String selectTableSQL="";

		selectTableSQL = "(SELECT restaurant_name, age, gender "
				+ "FROM Restaurant NATURAL JOIN Major_client "
				+ "WHERE age = ? AND "
				+ "restaurant_name IN ( SELECT restaurant_name "
				+ "FROM Restaurant NATURAL JOIN Location "
				+ "WHERE location=? )) "
				+ "UNION "
				+ "(SELECT restaurant_name, age, gender "
				+ "FROM Restaurant NATURAL JOIN Major_client "
				+ "WHERE gender = ? AND "
				+ "restaurant_name IN ( SELECT restaurant_name "
				+ "FROM Restaurant NATURAL JOIN Location "
				+ "WHERE location = ? ))";




		try {
			dbConnection = dbdb.getDBConnection();



			//지원
			stmt = dbConnection.createStatement();
			//use database
			String UseSQL = "USE RestaurantDB";
			stmt.executeUpdate(UseSQL);
			//System.out.println("USE DATABASE done...");


			dbConnection.setAutoCommit(false);

			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setInt(1, age);
			preparedStatementSelect.setString(2, restaurantLoc);
			preparedStatementSelect.setString(3, gender);
			preparedStatementSelect.setString(4, restaurantLoc);


			rs = preparedStatementSelect.executeQuery();

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
	/*
	 * 사용자가 원하는 연령대와 성별 입력받는 메소드
	 * 식당의 지역을 파라미터로 받음
	 *  getRestaurantByAgeGender 에 입력값과 식당 지역 반환
	 */

	/*
	 * 사용자가 원하는 (무)알콜 음료를 번호로 입력받는 메소드
	 * 파라미터로 카테고리를 받는다
	 * 카테고리와 음료 번호를 getDrinkName에 넘겨준다
	 */
	public static void getDrink(String category) throws SQLException{
		int alcoholNum = 0;
		int non_alcoholNum= 0;
		String alcohol="";
		String non_alcohol="";


		//while start(get input until correct input is entered)
		while(true){
			//알콜음료 선택
			System.out.println("\nChoose alcohol beverages do you want: (enter 1~7)”);
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
				System.out.println("<Warning> enter integer from 1 to 7");

		}


		//while2 starts
		while(true){
			//무알콜음료 선택
			System.out.println("\nChoose non-alcohol beverage do you want: (enter 1~7)”);
			System.out.println("1. Ssanghwa_tea");
			System.out.println("2. Green_tea");
			System.out.println("3. Lassi");
			System.out.println("4. Coke");
			System.out.println("5. Barley_tea");
			System.out.println("6. Chrysanthemum_tea");
			System.out.println("7. Ade");
			non_alcoholNum=scanner.nextInt();

			//알맞은 입력을 받은 경우 loop 나감
			if(non_alcoholNum>=1&&non_alcoholNum<=7)
				break;
			else
				System.out.println("<Warning> enter integer from 1 to 7");

		} //while ends



		//switch startswhile
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



		//switch starts
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





		System.out.println("Restaurant which has alcohol beverage "+alcohol+" or non-alcohol beverage  "+non_alcohol+" : ");
		System.out.println("[Restaurant name]	[alcohol]		[non_alcohol]");

		ArrayList<DrinkName> names =  getDrinkName(category, alcohol, non_alcohol);

		//성별을 완성된 단어로 저장하는 string






		for (int i = 0; i < names.size(); i++) {
			System.out.println(names.get(i));
		}//for ends
	}//getDrink method ends



	/*
	 * 카테고리와 알콜음료, 무알콜 음료를 파라미터로 받아 해당 음료가 있는 식당의 이름과 음료 종류 반환하는 메소드
	 */

	public static ArrayList<DrinkName> getDrinkName(String category_name, String alcohol, String non_alcohol) throws SQLException{
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		ArrayList<DrinkName> drinkname = new ArrayList<DrinkName>();

		//지원
		Statement stmt = null;



		String selectTableSQL="";

		selectTableSQL = "(SELECT restaurant_name, alcohol_drink, nonalcohol_drink "
				+ "FROM Restaurant NATURAL JOIN Beverage "
				+ "WHERE alcohol_drink = ? AND "
				+ "restaurant_name IN ( SELECT restaurant_name "
				+ "FROM Restaurant NATURAL JOIN Company "
				+ "WHERE category = ? )) "
				+ "UNION "
				+ "(SELECT restaurant_name, alcohol_drink, nonalcohol_drink "
				+ "FROM Restaurant NATURAL JOIN Beverage "
				+ "WHERE nonalcohol_drink = ? AND "
				+ "restaurant_name  IN ( SELECT restaurant_name "
				+ "FROM Restaurant NATURAL JOIN Company "
				+ "WHERE category = ? )) ";
		//쿼리입력



		try {
			dbConnection = dbdb.getDBConnection();



			//지원
			stmt = dbConnection.createStatement();
			//use database
			String UseSQL = "USE RestaurantDB";
			stmt.executeUpdate(UseSQL);
			//System.out.println("USE DATABASE done...");


			dbConnection.setAutoCommit(false);

			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setString(1, alcohol);
			preparedStatementSelect.setString(2, category_name);
			preparedStatementSelect.setString(3, non_alcohol);
			preparedStatementSelect.setString(4, category_name);


			rs = preparedStatementSelect.executeQuery();

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

	public void getEditMenuEval()throws SQLException {

		String menuName;
		int spicy=0;
		int sweetness=0;
		int salty=0;
		int sourness=0;

		System.out.println("\nPick menu that you want to modify evaluation: ");
		menuName = scanner.next();

		System.out.println("\n You choose "+ menuName);

		System.out.println("Input the grade (0~5)");
		System.out.print("Spicy: ");
		spicy = scanner.nextInt();
		System.out.print(" Sweetness: ");
		sweetness = scanner.nextInt();

		System.out.print(" salty: ");
		salty = scanner.nextInt();

		System.out.print(" Sourness: ");
		sourness = scanner.nextInt();


		getEditMenuEvalByMenu(menuName,spicy,sweetness,salty,sourness);

	}//getEditMenuEval() ends

	public static void getEditMenuEvalByMenu(String menuName, int spicy, int sweetness, int salty, int sourness) throws SQLException {
		

			Connection dbConnection = null;

			PreparedStatement preparedStatementUpdate = null;

			Statement stmt = null;
			String updateTableSQL = "UPDATE Menu_evaluation set spicy = ?, sweetness = ?, salty = ?, sourness = ? where menu_name = ?";

			try {
				dbConnection = dbdb.getDBConnection();
				stmt = dbConnection.createStatement();
				//use database
				String UseSQL = "USE RestaurantDB";
				stmt.executeUpdate(UseSQL);
				//System.out.println("USE DATABASE done...");

				preparedStatementUpdate = dbConnection.prepareStatement(updateTableSQL);

				
				preparedStatementUpdate.setInt(1, spicy);				
				preparedStatementUpdate.setInt(2, sweetness);				
				preparedStatementUpdate.setInt(3, salty);				
				preparedStatementUpdate.setInt(4, sourness);
				preparedStatementUpdate.setString(5, menuName);
				
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
	
	/*
	    * 삭제할 식당의 이름을 받는 메소드
	    * 입력값을 deleteRestaurant로 넘겨준다
	    */
	   public void getNameForDelete() throws SQLException{
	      System.out.println("Pick restaurant which do you want to delete: ");
	      String nameToDelete=scanner.next();

	      deleteRestaurant(nameToDelete);


	   }//getNameForDelete method ends



	   /*
	    * 파라미터로 받은 식당이름을 식당 테이블에서 삭제하는 메소드
	    */
	   public void deleteRestaurant(String name) throws SQLException{
	      RestaurantDB dbdb = new RestaurantDB();
	      PreparedStatement preparedStatementDelete = null;
	      Connection dbConnection=null;
	      Statement stmt = null;

	      String createStmt="DELETE from restaurant WHERE restaurant_name= ? ";
	      try{

	         dbConnection=dbdb.getDBConnection();
	         stmt = dbConnection.createStatement();

	         //use database
	         String UseSQL = "USE RestaurantDB";
	         stmt.executeUpdate(UseSQL);
	         // System.out.println("USE DATABASE done...");



	         preparedStatementDelete = dbConnection.prepareStatement(createStmt);
	         preparedStatementDelete.setString(1, name);

	         preparedStatementDelete.executeUpdate(); 
	         System.out.println("Restaurant "+name+" is deleted.");

	      }

	      catch(SQLException excp){
	         System.out.println("Error in deletion");
	      }



	   }//deleteRestaurant method ends

	   /*
	    * 삭제할 식당의 이름을 받는 메소드
	    * 입력값을 insertSize로 넘겨준다
	    */
	   public void getLocationForInsert() throws SQLException{
	      System.out.println("Enter Location to insert");
	      String nameTolocation=scanner.next();
	      System.out.println("Enter Delivery location to insert");
	      String nameToDeliverylocation=scanner.next();
	      
	      insertintoDeliveryLocation(nameTolocation, nameToDeliverylocation);


	   }//getNameForDelete method ends



	   /*
	    * delivery location 추가하는 메소드
	    */
	   public void insertintoDeliveryLocation(String location, String delivery_location) throws SQLException{
	      RestaurantDB dbdb = new RestaurantDB();
	      PreparedStatement preparedStatementInsert = null;
	      Connection dbConnection=null;
	      Statement stmt = null;

	      String insertStmt="INSERT INTO Delivery_location (location, delivery_location) values (?,?) ";
	      try{

	         dbConnection=dbdb.getDBConnection();
	         stmt = dbConnection.createStatement();

	         //use database
	         String UseSQL = "USE RestaurantDB";
	         stmt.executeUpdate(UseSQL);
	         // System.out.println("USE DATABASE done…");



	         preparedStatementInsert = dbConnection.prepareStatement(insertStmt);
	         preparedStatementInsert.setString(1, location);
	         preparedStatementInsert.setString(2, delivery_location);
	         
	         preparedStatementInsert.executeUpdate(); 
	         System.out.println("Location "+location+" is inserted and Delivery location "+delivery_location+" is inserted");

	      }

	      catch(SQLException excp){
	         System.out.println("error in deletion");
	      }



	   }//insert delivery location ends
}//RestaurantDBinput class endS!


