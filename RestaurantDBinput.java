package KANGHANNAYOUNG;
//����ڷκ��� �Է��� �޾ƿ��� Ŭ����
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class RestaurantDBinput{
	
	//Test:����ڷκ��� Configuration ���ϰ� SQL script�� ������ �о���� Ŭ����
	static Test dbdb = new Test();
	static Scanner scanner = new Scanner(System.in);
	
	//1215001 ����
	/* Custom�� Manager �����ϴ� Method*/
	public int CustomerOrManager(){
		//����ڰ� ���� ��带 �޾ƿ� ���� ����
		int customerormanager;

		while(true){
			System.out.println(" <<Select mode: 1. Customer mode  2. Manager mode 3. Exit the program>>");
			customerormanager = scanner.nextInt();

			//Customer mode�� �����Ѱ�� 
			if(customerormanager == 1){
				System.out.println("You choose Customer mode.");
				break;
			}
			
			//Manager mode�� �����Ѱ�� 
			else if(customerormanager == 2){
				System.out.println("You choose Manager mode.");
				break;		
			}
			
			//Program ���Ḧ ������ ��� 
			else if(customerormanager == 3){
				System.out.println("=====program ended. BYE!=====");
				break;
			}
			
			else
				System.out.println("<Warning> Insert among 1, 2, 3 again.");
			
		}
		
		return customerormanager;
	}

	/*1215001 ����
	 * ������ ī�װ� �� ��� ������ �˻��� �������� �����ϴ� method
	 * 1�� 2�� �Է¹��� �ϳ��� return
	 * �ٸ� �� �Է¹����� 1�� 2�� �ϳ� ������ ������ loop
	 * 1-Location, 2-Category
	 */
	public int LocaionOrCategory(){
		//������� �˻� �ɼ��� �޾ƿ� ���� ����
		int locationorcategory;

		while(true){
			System.out.println("\n\n\nStart searching with 1. restaurant's location 2. food's category (Insert 1 or 2)");
			System.out.println("(Insert 3 to exit Customer mode)");
			locationorcategory =scanner.nextInt();

			//������ �����Ѱ�� 
			if(locationorcategory==1){
				System.out.println("You choose restaurant's location.\n");
				break;
			}

			//ī�װ��� �����Ѱ�� 
			else if(locationorcategory==2){
				System.out.println("You choose food's category.");
				break;
			}

			//Customer mode ���Ḧ ������ ��� 
			else if(locationorcategory==3){
				System.out.println("=====Customer mode ended=====");
				break;
			}


			else
				System.out.println("<Warning> Insert again among 1, 2, 3");
		}


		return locationorcategory;

	}//ends LocationOrCategory method


	/*1215001 ����
	 * �������� �˻��� ������ ��� ���� ����
	 * �ش� ���� return
	 */
	
	public String getLocation(){
		//������� �˻� �ɼ��� �޾ƿ� ���� ����
		int locationNum;
		String location="";

		while(true){
			System.out.println("Which location you want to search?");
			System.out.println("1. Ewha 2. Shinchon 3. Hongdae (Insert among 1, 2, 3)");
			locationNum =scanner.nextInt();

			//Ewha�� ������ ��� 
			if(locationNum==1){
				System.out.println("You selected Ewha.\n");
				location="Ewha";
				break;
			}

			//Shinchon�� ������ ��� 
			else if(locationNum==2){
				System.out.println("You selected Shinchon.\n");
				location="Shinchon";
				break;
			}

			//Hongdae�� ������ ��� 
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

	/* 1215131 ������
	 * �Ĵ� �� ����ڰ� ���ϴ� �����ð��� �Է��ϴ� �޼ҵ�
	 * �Ĵ��� ������ �Ķ���ͷ� ����
	 * getRestaurantByWorkinghour�� �Է°� ��ȯ
	 */
	
	public void getOpeningClosing(String restaurantLoc) throws SQLException {
		
		//����ڰ� ������ ������ ���� ���� ����
		String location = restaurantLoc;
		
		//����ڷκ��� opening hour, closing hour���� ����
		System.out.println("\nInput maximum opening_hours: ");
		int opening = scanner.nextInt();
		System.out.println("Input minimum closing_hours: ");
		int closing = scanner.nextInt();

		//getRestaurantByWorkinghour(opening, closing, location)�κ��� ���� �Ĵ��̸��� ArrayList�� ����

		ArrayList<String> names = getRestaurantByWorkinghour(opening, closing, location);


		System.out.println("Restaurants which open between "+opening+" and "+closing);

		//�˻������� �����ϴ� restaurant�� �̸��� ���
		System.out.println("[Restaurant name]");
		for (int i = 0; i < names.size(); i++) {
			if(names.size()==0)
				System.out.println("NONE");
			else
				System.out.println(names.get(i));
		}//for ends


	}//getOpeningClosing ends



	/* 1215131 ������
	 * getOpeningClosing���� ���� opening,closing,location�� �޾Ƽ�
	 * �Է¹��� ���� �� ���� �ð��� �ش��ϴ� restaurant�� �̸� ��ȯ�ϴ� �޼ҵ�
	 */
	public static ArrayList<String> getRestaurantByWorkinghour(int opening, int closing, String location) throws SQLException {
		/*
		 * �����ͺ��̽����� ������ ���� Connection
		 * SQL���� �����ϱ� ���� PreparedStatement
		 * select ������ ������� �ޱ� ���� ResultSet
		 * 
		 * */
		
		
		
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		
		//restaurant�� �̸��� ���� array list ����
		ArrayList<String> restaurant_name = new ArrayList<String>();

		
		Statement stmt = null;

		//������� �Է� ���ǿ� ���� Select Query�� �ۼ�
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

			//����ڷκ��� �Է¹��� ������ select �������� ����
			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setInt(1, opening);
			preparedStatementSelect.setInt(2, closing);
			
			
			//select �������� ������ ����� rs�� ����
			rs = preparedStatementSelect.executeQuery();

			//select �������� �����Ͽ� ���� restaurant_name(�����)�� restaurant_name arraylist�� ����
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
	}//����, ����, ���� select ends


	/*1215131 ������
	 * �̹� ������ �������� ��� �����Ѱ� �˾ƺ��� ���� �Է¹޴� �޼ҵ�
	 * �Ĵ��� ������ �Ķ���ͷ� ����
	 * getRestaurantByDelivery�� �Է°��� �Ĵ��� ���� ��ȯ
	 */
	public void getDeliveryLocation(String restaurantLoc) throws SQLException{
		//����ڰ� ������ ��������� ��ȣ�� ���� ���� ����
		int locationNum;
		//����ڰ� ������ ������ ���� ���� ����
		String restlocation;

		while(true){
			System.out.println("\nChoose the delivery location you want to check.");
			System.out.println("1. Ewha 2. Shinchon 3. Hongdae (Insert among 1, 2, 3)");
			locationNum =scanner.nextInt();
			
			//Ewha�� ������ ���
			if(locationNum==1){
				System.out.println("You choose Ewha.\n");
				restlocation="Ewha";
				break;
			}

			//Shinchon�� ������ ���
			else if(locationNum==2){
				System.out.println("You choose Shinchon.\n");
				restlocation="Shinchon";
				break;
			}

			//Hongdae�� ������ ��� 
			else if(locationNum==3){
				System.out.println("You choose Hongdae.\n");
				restlocation="Hongdae";
				break;
			}

			else
				System.out.println("<Warning> Insert among 1, 2, 3)");
		}

		//getRestaurantByDelivery(restaurantLoc,restlocation)�κ��� ���� �Ĵ��̸��� ArrayList�� ����
		ArrayList<String> names =getRestaurantByDelivery( restaurantLoc, restlocation);
		
		System.out.println("Next restaurants can deliver to "+restlocation);
		System.out.println("[Restaurant location]");
		
		//�˻������� �����ϴ� restaurant�� �̸��� ���
		for(int i=0;i<names.size();i++){
			if(names.size()==0)
				System.out.println("NONE");
			else
				System.out.println(names.get(i));

		}
	}//ends getDeliveryLocation







	/*1215131 ������
	 * �Է¹��� ��ȣ�� �ش��ϴ� ������ ��� ������ �Ĵ� �̸� ��ȯ
	 * �Ķ���ͷ� �Ĵ��� ������ ��ް������� �˾ƺ��� ������ ����
	 */
	public static ArrayList<String> getRestaurantByDelivery(String restaurantLoc, String restlocation) throws SQLException{

		/*
			 * �����ͺ��̽����� ������ ���� Connection
			 * SQL���� �����ϱ� ���� PreparedStatement
			 * select ������ ������� �ޱ� ���� ResultSet
			 * 
			 * */
		
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		
		//restaurant�� �̸��� ���� array list ����
		ArrayList<String> restaurant_name = new ArrayList<String>();


		Statement stmt = null;


		//������� �Է� ���ǿ� ���� Select Query�� �ۼ�
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
			
			//����ڷκ��� �Է¹��� ������ select �������� ����
			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setString(1, restaurantLoc);
			preparedStatementSelect.setString(2, restaurantLoc);
			preparedStatementSelect.setString(3, restlocation);
			

			//������� �Է� ���ǿ� ���� Select Query�� ����
			rs = preparedStatementSelect.executeQuery();

			//select �������� �����Ͽ� ���� restaurant_name(�����)�� restaurant_name arraylist�� ����
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

	/*1215131 ������
	 * ����ڰ� ������ ������ �������� �Է¹��� �¼� ������ �ش��ϴ� �Ĵ��� �̸��� �¼����� ��ȯ
	 * �Ķ���ͷ� �Ĵ��� ������ ����
	 */
	public void getSizeSeat(String restaurantLoc) throws SQLException {
		
		//�¼� ������ �ޱ� ���� ���� ����
		int seatNUM;
		//�¼� ������ �ޱ� ���� ���� ����
		String seat;

		while(true){

			System.out.println("\nWhich seat do you want to sit?");

			System.out.println("1. Single 2. Double 3. Quad (Insert 1 or 2 or 3)");

			seatNUM =scanner.nextInt();


			//Single�� ������ ��� 
			if(seatNUM==1){

				System.out.println("You selected Single.\n");

				seat="Single";

				break;

			}


			//Double�� ������ ���
			else if(seatNUM==2){

				System.out.println("You selected Double.\n");

				seat="Double";

				break;

			}


			//Quad�� ������ ��� 
			else if(seatNUM==3){

				System.out.println("You selected Quad.\n");

				seat="Quad";

				break;

			}



			else

				System.out.println("<Warning> Insert 1 or 2 or 3 again.");

		}




		//getRestaurantBySeat(restaurantLoc, seat)�κ��� ���� ��°���� ArrayList�� ����
		ArrayList<AvailableSeat> result = getRestaurantBySeat(restaurantLoc, seat);

		//�������
		System.out.println("[Restaurant name]	[inside "+seat+" seat]	[outside "+seat+" seat]");
		

		//�˻������� �����ϴ� restaurant�� �̸��� �¼������� ���� �¼� ���� ��� 
		for (int i = 0; i < result.size(); i++) {
			if(result.size()==0)
				System.out.println("NONE");
			else

				System.out.println(result.get(i).toString());

		}//for ends



	}//getOpeningClosing ends


/*1215131 ������
	 * �Է¹��� ������ �¼� ������ �ش��ϴ� �Ĵ��� �̸��� �¼� ������ ���� �¼� �� ��ȯ
	 * �Ķ���ͷ� �Ĵ��� ������ �¼������� ����
	 */
	
	public static ArrayList<AvailableSeat> getRestaurantBySeat(String location, String seat) throws SQLException {


		/*
			 * �����ͺ��̽����� ������ ���� Connection
			 * SQL���� �����ϱ� ���� PreparedStatement
			 * select ������ ������� �ޱ� ���� ResultSet
			 * 
			 * */
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		
		//������� ���� array list ����
		ArrayList<AvailableSeat> availableSeat= new ArrayList<AvailableSeat>();


		Statement stmt = null;
		//Select ���� ���� ���� String ����
		String selectTableSQL = "";
		
		//������� �Է� ���ǿ� ���� Select Query�� �ۼ�
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

			//����ڷκ��� �Է¹��� ������ select �������� ����

			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setString(1, location);

			//������� �Է� ���ǿ� ���� Select Query�� ����
			rs = preparedStatementSelect.executeQuery();


			//select �������� �����Ͽ� ���� �������  arraylist�� ����
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



	//������ �������� ����� �޼ҵ� ��


	
	//ī�װ��� �������� ����� �޼ҵ����
	


	/*1215001 ����
	 * ����ڷκ��� �Ĵ��� ī�װ��� �Է¹޴� �޼ҵ�
	 */
	public String getCategory(){
		//����ں��� ī�װ��� ���� ���� ����
		int categoryNum;
		String category="";

		while(true){
			System.out.println("Choose the category of the restaurant");
			System.out.println("1.Korean  2.Chinese 3.Japanese 4.Indian 5.Fastfood 6.Western (Insert 1~6)");
			categoryNum =scanner.nextInt();
			
			//Korean�� ������ ���
			if(categoryNum==1){
				System.out.println("You selected Korean.\n");
				category="Korean";
				break;
			}

			//Chinese�� ������ ���
			else if(categoryNum==2){
				System.out.println("You selected Chinese.\n");
				category="Chinese";
				break;
			}
			
			//Japanese�� ������ ���
			else if(categoryNum==3){
				System.out.println("You selected Japanese.\n");
				category="Japanese";
				break;
			}
			
			//Indian�� ������ ���
			else if(categoryNum==4){
				System.out.println("You selected Indian.\n");
				category="Indian";
				break;
			}
			
			//Fastfood�� ������ ��� 
			else if(categoryNum==5){
				System.out.println("You selected Fastfood.\n");
				category="Fastfood";
				break;
			}
			
			//Western�� ������ ��� 
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
	 * �Ĵ� �� ����ڰ� ���ϴ� ���� ����� �Է��ϴ� �޼ҵ�
	 * �Ĵ��� ī�װ��� �Ķ���ͷ� ����
	 * getRestaurantByPayment�� �Է°� ��ȯ
	 */
	public void getPayment(String category) throws SQLException{

		//����ڷκ��� ���� ���񽺸� �ޱ� ���� ���� ����
		int paymentNum;
		String payment="";

		while(true){
			System.out.println("Choose the payment service ");
			System.out.println("1.Coupon only  2. Tele_discount only 3.Coupon&Tele_discount 4.None (Insert 1~4)");
			paymentNum =scanner.nextInt();

			//Coupon only�� ������ ���
			if(paymentNum==1){
				System.out.println("You selected Coupon only.\n");
				payment="coupon";
				break;
			}
			
			//Tele_discount only�� ������ ���
			else if(paymentNum==2){
				System.out.println("You selected Tele_discount only.\n");
				payment="tele_discount";
				break;
			}
			
			//Coupon&Tele_discount�� ������ ���
			else if(paymentNum==3){
				System.out.println("You selected Coupon&Tele_discount.\n");
				payment="coupon&tele_discount";
				break;
			}
			
			//None�� ������ ��� 
			else if(paymentNum==4){
				System.out.println("You selected None.\n");
				payment="None";
				break;
			}
			else
				System.out.println("<Warning> Insert 1~4 again.\n");
		}//while�� ��
		
		//������� ���� array list ����
		ArrayList<String>names=getRestaurantByPayment(category,payment);

		System.out.println("Restaurants which offer "+payment+" discount:");
		//�������
		System.out.println("[Restaurant name]");
		
		//�˻������� �����ϴ� ����� ���
		for (int i = 0; i < names.size(); i++) {
			if(names.size()==0)
				System.out.println("NONE");
			else
				System.out.println(names.get(i));
		}//for ends
	}//getPayment method ends!

	/*1215129 �ѳ��� 
	 * �Է¹��� ī�װ����� �ش� �ϴ� ���μ��񽺰� ������ �Ĵ� �̸� ��ȯ
	 * �Ķ���ͷ� �Ĵ��� ī�װ�, ���� ������ ����
	 */
	public static ArrayList<String> getRestaurantByPayment(String category,String payment) throws SQLException {

		/*
			 * �����ͺ��̽����� ������ ���� Connection
			 * SQL���� �����ϱ� ���� PreparedStatement
			 * select ������ ������� �ޱ� ���� ResultSet
			 * 
			 * */
		
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		ArrayList<String> restaurant_name = new ArrayList<String>();

	
		Statement stmt = null;



		//Select ���� ���� ���� String ����
		String selectTableSQL="";
		
		//������� �Է� ���ǿ� ���� Select Query�� �ۼ�

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
			
			//����ڷκ��� �Է¹��� ������ select �������� ����
			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setString(1, category);

			//������� �Է� ���ǿ� ���� Select Query�� ����
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
	


	/* 1215129 �ѳ���	
	 * �Ĵ� �� ����ڰ� ���ϴ� ���ݴ븦 �Է��ϴ� �޼ҵ�
	 * �Ĵ��� ī�װ��� �Ķ���ͷ� ����
	 * getRestaurantByPrice�� �Է°� ��ȯ
	 */
	public void getPrice(String category) throws SQLException {
		
		//����ڰ� ���ϴ� ���ݴ븦 �Է¹ޱ� ���� ���� ����
		int priceNum;
		String price;

		while(true){

			System.out.println("\nChoose the price range");

			System.out.println("1. Under 10000won 2. Over 10000won (Insert 1 or 2)");

			priceNum =scanner.nextInt();


			//Under 10000won�� ������ ���
			if(priceNum==1){

				System.out.println("You selected under 10000won.\n");

				price="up";

				break;

			}


			//Over 10000won�� ������ ��� 
			else if(priceNum==2){

				System.out.println("You selected over 10000won.\n");

				price="down";

				break;

			}



			else

				System.out.println("<Warning> Insert 1 or 2 again.");

		}




		//getRestaurantByPrice(category, price)�κ��� ���� �Ĵ��̸��� ArrayList�� ����				
		ArrayList<PrintMenuName> names = getCategoryByPrice(category, price);
		//�������
		System.out.println("[Restaurant name]	[Menu name]");
		
		//�˻������� �����ϴ� ����� ���
		for (int i = 0; i < names.size(); i++) {
			if(names.size()==0)
				System.out.println("NONE");
			else

				System.out.println(names.get(i).toString());

		}//for ends



	}//getPrice ends

	/*1215129 �ѳ���
	 * �Է¹��� ī�װ��� ���ݴ뿡 �ش��ϴ� �Ĵ��� �̸� ��ȯ
	 * �Ķ���ͷ� �Ĵ��� ī�װ��� ���ݴ븦 ����
	 */
	public static ArrayList<PrintMenuName> getCategoryByPrice(String category, String price) throws SQLException {

		/*
			 * �����ͺ��̽����� ������ ���� Connection
			 * SQL���� �����ϱ� ���� PreparedStatement
			 * select ������ ������� �ޱ� ���� ResultSet
			 * 
			 * */
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		
		//������� ���� array list ����
		ArrayList<PrintMenuName> restaurant_name = new ArrayList<PrintMenuName>();

		Statement stmt = null;




		//Select ���� ���� ���� String ����
		String selectTableSQL = "";
		
		//������� �Է� ���ǿ� ���� Select Query�� �ۼ�
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

			//����ڷκ��� �Է¹��� ������ select �������� ����
			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);

			preparedStatementSelect.setString(1, category);

			//������� �Է� ���ǿ� ���� Select Query�� ����
			rs = preparedStatementSelect.executeQuery();


			//select �������� �����Ͽ� ���� ������� arraylist�� ����

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

	/* 1215001 ����
	 * ����ڰ� ���ϴ� ���ɴ�� ���� �Է¹޴� �޼ҵ�
	 * �Ĵ��� ������ �Ķ���ͷ� ����
	 *  getRestaurantByAgeGender �� �Է°��� �Ĵ� ���� ��ȯ
	 */
	public void getAgeGender(String restaurantLoc) throws SQLException{
		
		//����ڰ� ���ϴ� age�� gender���� �ޱ� ���� ���� ����
		int age=0;
		String gender = "";
		
		//����ڰ� ������ ������ �޴� ���� 
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
		//������� ���� array list ����
		ArrayList<AgeGenderName> result =  getRestaurantByAgeGender(age, gender, location);

		//������ �ϼ��� �ܾ�� �����ϴ� string
		String genderLetter;
		if(gender.equals("M")){
			genderLetter="Male";               
		}
		else
			genderLetter="Female";

		//�������
		System.out.println(age+" OR "+genderLetter+" is the major_client of these restaurant: ");
		System.out.println("[Restaurant name]	[Age]	[Gender]");


		//�˻������� �����ϴ� ����� ���
		for (int i = 0; i < result.size(); i++) {
			if(result.size()==0)
				System.out.println("NONE");
			else
			System.out.println(result.get(i));
		}//for ends


	}

	/*1215001 ����
	 * �Է¹��� ��ȣ�� �ش��ϴ� ������ Major client�� ����ڰ� �Է��� age Ȥ�� gender�� �Ĵ� �̸� ��ȯ
	 * �Ķ���ͷ� �Ĵ��� ������ age, gender���� ����
	 */		

	public static ArrayList<AgeGenderName> getRestaurantByAgeGender(int age, String gender, String restaurantLoc) throws SQLException{
		/*
		 * �����ͺ��̽����� ������ ���� Connection
		 * SQL���� �����ϱ� ���� PreparedStatement
		 * select ������ ������� �ޱ� ���� ResultSet
		 * 
		 * */
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		ArrayList<AgeGenderName> agegendername = new ArrayList<AgeGenderName>();

		Statement stmt = null;
		
		//Select ���� ���� ���� String ����
		String selectTableSQL="";
		
		//������� �Է� ���ǿ� ���� Select Query�� �ۼ�
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
			

			//����ڷκ��� �Է¹��� ������ select �������� ����
			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setInt(1, age);
			preparedStatementSelect.setString(2, restaurantLoc);
			preparedStatementSelect.setString(3, gender);
			preparedStatementSelect.setString(4, restaurantLoc);

			//������� �Է� ���ǿ� ���� Select Query�� ����
			rs = preparedStatementSelect.executeQuery();

			//select �������� �����Ͽ� ���� ������� arraylist�� ����

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
	
	/*1215001 ����
	 * ����ڰ� ���ϴ� (��)���� ���Ḧ ��ȣ�� �Է¹޴� �޼ҵ�
	 * �Ķ���ͷ� ī�װ��� �޴´�
	 * ī�װ��� ���� ��ȣ�� getDrinkName�� �Ѱ��ش�
	 */
	public static void getDrink(String category) throws SQLException{
		//����ڰ� ���ϴ� (��)���� ���Ḧ �ޱ� ���� ���� ����
		int alcoholNum = 0;
		int non_alcoholNum= 0;
		String alcohol="";
		String non_alcohol="";


		//while 
		while(true){
			//�������� ����
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
			//���������� ����
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



		//switch ����ڰ� ���ϴ� ������������ String���� ����
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



		//switch ����ڰ� ���ϴ� ������������ String���� ����
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

		//�������
		System.out.println("Alcohol drink "+alcohol+" or Nonalcohol drink "+non_alcohol+" is offered in these restaurant");
		System.out.println("[Restaurant name]	[alcohol]		[non_alcohol]");

		//getDrinkName(category, alcohol, non_alcohol)�κ��� ���� ������� ArrayList�� ����
		ArrayList<DrinkName> result =  getDrinkName(category, alcohol, non_alcohol);

		//�˻������� �����ϴ� ����� ���
		for (int i = 0; i < result.size(); i++) {
			if(result.size()==0)
				System.out.println("NONE");
			else
			System.out.println(result.get(i));
		}//for ends
	}//getDrink method ends



	/*1215001 ����
	 * ī�װ��� ��������, ������ ���Ḧ �Ķ���ͷ� �޾� �ش� ���ᰡ �ִ� �Ĵ��� �̸��� ���� ���� ��ȯ�ϴ� �޼ҵ�
	 */

	public static ArrayList<DrinkName> getDrinkName(String category_name, String alcohol, String non_alcohol) throws SQLException{

		/*
			 * �����ͺ��̽����� ������ ���� Connection
			 * SQL���� �����ϱ� ���� PreparedStatement
			 * select ������ ������� �ޱ� ���� ResultSet
			 * 
			 * */
		Connection dbConnection = null;
		PreparedStatement preparedStatementSelect = null;
		ResultSet rs = null;
		
		//������� ���� array list ����
		ArrayList<DrinkName> drinkname = new ArrayList<DrinkName>();

		
		Statement stmt = null;


		//Select ���� ���� ���� String ����
		String selectTableSQL="";
		//������� �Է� ���ǿ� ���� Select Query�� �ۼ�
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
			
			//����ڷκ��� �Է¹��� ������ select �������� ����
			preparedStatementSelect = dbConnection.prepareStatement(selectTableSQL);
			preparedStatementSelect.setString(1, alcohol);
			preparedStatementSelect.setString(2, category_name);
			preparedStatementSelect.setString(3, non_alcohol);
			preparedStatementSelect.setString(4, category_name);

			//������� �Է� ���ǿ� ���� Select Query�� ����
			rs = preparedStatementSelect.executeQuery();

			//select �������� �����Ͽ� ���� ������� arraylist�� ����
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
	

	/*1215131 ������
	 * �Է¹��� �޴��� �ش��ϴ� �޴��� �޴��򰡸� �����ϴ� �޼ҵ� 
	 */
	public void getEditMenuEval()throws SQLException {

		//����ڷκ��� �޴� �̸��� �Է¹ޱ� ���� ��������
		String menuName;
		
		//����ڷκ��� �޴� �� ������ �Է¹ޱ� ���� ���� ����
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
		
		//getEditMenuEvalByMenu:�޴� �򰡸� �����ϴ� �޼ҵ� 
		//����ڰ� �Է��� ������ �ش� �޴��� �޴� �򰡸� ����
		getEditMenuEvalByMenu(menuName,spicy,sweetness,salty,sourness);

	}//getEditMenuEval() ends

	/*1215131 ������
	 * getEditMenuEval()�� ���� �޴��򰡸� ������ �޴��� ������ ������ �޾� 
	 * �ش� �޴��� �޴� �򰡸� �����ϴ� �޼ҵ�
	 * */
	public static void getEditMenuEvalByMenu(String menuName, int spicy, int sweetness, int salty, int sourness) throws SQLException {
		
		/*
		 * �����ͺ��̽����� ������ ���� Connection
		 * SQL���� �����ϱ� ���� PreparedStatement,Statement
		 
		 * */
			Connection dbConnection = null;
			PreparedStatement preparedStatementUpdate = null;
			Statement stmt = null;
			
			//UPDATE�������� �ۼ�
			String updateTableSQL = "UPDATE DBCOURSE_Menu_evaluation set spicy = ?, sweetness = ?, salty = ?, sourness = ? where menu_name = ?";

			try {
				dbConnection = dbdb.getDBConnection();
				stmt = dbConnection.createStatement();
				//use database
				String UseSQL = "USE RestaurantDB";
				stmt.executeUpdate(UseSQL);
				
				//����ڷκ��� �Է¹��� ������ UPDATE�������� ����
				preparedStatementUpdate = dbConnection.prepareStatement(updateTableSQL);

				preparedStatementUpdate.setInt(1, spicy);				
				preparedStatementUpdate.setInt(2, sweetness);				
				preparedStatementUpdate.setInt(3, salty);				
				preparedStatementUpdate.setInt(4, sourness);
				preparedStatementUpdate.setString(5, menuName);
				
				//UPDATE������ ����
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
	
	
	/*1315002 ������
	    * ������ �Ĵ��� �̸��� �޴� �޼ҵ�
	    * �Է°��� deleteRestaurant�� �Ѱ��ش�
	    */
	   public void getNameForDelete() throws SQLException{
	      System.out.println("enter restaurant name to delete");
	      String nameToDelete=scanner.next();

	      deleteRestaurant(nameToDelete);


	   }//getNameForDelete method ends



	   /*1315002 ������
	    * �Ķ���ͷ� ���� �Ĵ��̸��� �Ĵ� ���̺��� �����ϴ� �޼ҵ�
	    */
	   public void deleteRestaurant(String name) throws SQLException{
		  //Test:����ڷκ��� Configuration ���ϰ� SQL script�� ������ �о���� Ŭ����
		   Test dbdb = new Test();
		   /*
			 * �����ͺ��̽����� ������ ���� Connection
			 * SQL���� �����ϱ� ���� PreparedStatement,Statement
			 * 
			 * */
	      PreparedStatement preparedStatementDelete = null;
	      Connection dbConnection=null;
	      Statement stmt = null;
	      
	    //DELETE ���� ���� ���� String 
	      String createStmt="DELETE from DBCOURSE_Restaurant WHERE restaurant_name= ? ";
	      try{

	         dbConnection=dbdb.getDBConnection();
	         stmt = dbConnection.createStatement();

	         //use database
	         String UseSQL = "USE RestaurantDB";
	         stmt.executeUpdate(UseSQL);
	         

	       //����ڷκ��� �Է¹��� ������ DELETE �������� ����
	         preparedStatementDelete = dbConnection.prepareStatement(createStmt);
	         preparedStatementDelete.setString(1, name);
	         
	       //DELETE ������ ����
	         preparedStatementDelete.executeUpdate(); 
	         System.out.println("restaurant "+name+" is deleted.");

	      }

	      catch(SQLException excp){
	         System.out.println("Error in deletion");
	      }



	   }//deleteRestaurant method ends

	   /*1315002 ������
	    * �߰��� ������ �̸��� �޴� �޼ҵ�
	    * �Է°��� insertintoDeliveryLocation���� �Ѱ��ش�
	    */
	   public void getLocationForInsert() throws SQLException{
	      System.out.println("Enter Location to insert");
	      String nameTolocation=scanner.next();
	      System.out.println("Enter Delivery location to insert");
	      String nameToDeliverylocation=scanner.next();
	     
	      //����ڷκ��� �Է¹��� nameTolocation,nameToDeliverylocation ���� �̿��� intoDeliveryLocation ȣ��
	      insertintoDeliveryLocation(nameTolocation, nameToDeliverylocation);


	   }//getNameForDelete method ends



	   /*1315002 ������
	    * delivery location �߰��ϴ� �޼ҵ�
	    */
	   public void insertintoDeliveryLocation(String location, String delivery_location) throws SQLException{
	      //Test:����ڷκ��� Configuration ���ϰ� SQL script�� ������ �о���� Ŭ����
		   Test dbdb = new Test();
		   
		   /*
			 * �����ͺ��̽����� ������ ���� Connection
			 * SQL���� �����ϱ� ���� PreparedStatement,Statement
			 * 
			 * */
	      PreparedStatement preparedStatementInsert = null;
	      Connection dbConnection=null;
	      Statement stmt = null;
	      
	      //INSERT INTO ������ �ۼ�
	      String insertStmt="INSERT INTO DBCOURSE_Delivery_location (location, delivery_location) values (?,?) ";
	      try{

	         dbConnection=dbdb.getDBConnection();
	         stmt = dbConnection.createStatement();

	         //use database
	         String UseSQL = "USE RestaurantDB";
	         stmt.executeUpdate(UseSQL);
	       

	       //����ڷκ��� �Է¹��� ������ INSERT INTO �������� ����
	         preparedStatementInsert = dbConnection.prepareStatement(insertStmt);
	         preparedStatementInsert.setString(1, location);
	         preparedStatementInsert.setString(2, delivery_location);
	         
	         //INSERT INTO ������ ����
	         preparedStatementInsert.executeUpdate(); 
	         System.out.println("Location "+location+" is inserted and Delivery location "+delivery_location+" is inserted");

	      }

	      catch(SQLException excp){
	         System.out.println("Error in deletion");
	      }



	   }//insert delivery location ends
}//RestaurantDBinput class endS!



