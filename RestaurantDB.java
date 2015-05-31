package KANGHANNAYOUNG;
//MAIN CLASS
//사용자가 실행할 기능을 선택하는 클래스
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//지원
import java.util.Scanner;

public class RestaurantDB {



	public static void main(String[] args) throws FileNotFoundException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		//Test:사용자로부터 Configuration 파일과 SQL script의 정보를 읽어오는 클래스
		//configuration파일의 정보를 읽어오고 DB를 생성한다
		Test mn=new Test();
		Test.readConfig();
		Test.MakeDB();
		//프로그램을 실행
		insertTest();

	}

	//insert part start

	public static void insertTest() throws SQLException {


		//view 생성을 위한 작업
		CreateView viewTable = new CreateView();
		
		Scanner scanner = new Scanner(System.in);
		
		//RestaurantDBinput class:사용자로부터 입력을 받아오는 클래스. 
		RestaurantDBinput userInput =new RestaurantDBinput();

		System.out.println("\n=====Program started. WELCOME!=====");

		//Customer mode와 Manager mode를 선택한다
		//while0 starts
		while(true){
			
			int CusOrMan = userInput.CustomerOrManager();

			//Customer mode를 선택한 경우
			if(CusOrMan == 1){
				
				//검색옵션 선택(Location/Category선택)
				//while1 starts
				while(true){
					
					int LocOrCat= userInput.LocaionOrCategory();


					//location 선택한 경우
					if(LocOrCat==1){
						//사용자가 입력한 지역을 식당의 지역으로 저장
						String restaurantLoc=userInput.getLocation();


						//while2 starts(지역 선택한 경우 그다음 조건을 선택하는 loop)
						while(true){
							System.out.println("Choose the condition for searching(Insert 1~4)");
							System.out.println("1. Opening and Closing time");
							System.out.println("2. Delivery available location");
							System.out.println("3. Major client");
							System.out.println("4. Seat");
							int t= scanner.nextInt();
							
							//Opening and Closing time을 선택하는 경우
							if(t==1){
								userInput.getOpeningClosing(restaurantLoc);
								break;
							}
							
							//Delivery available location을 선택하는 경우
							else if(t==2){
								userInput.getDeliveryLocation(restaurantLoc);
								break;
							}
							
							//Major client를 선택하는 경우
							else if(t==3){
								userInput.getAgeGender(restaurantLoc);
								break;
							}
							
							//Seat을 선택하는 경우 
							else if(t==4){
								userInput.getSizeSeat(restaurantLoc);
								break;
							}
							else
								System.out.println("<Warning> insert 1 or 2 or 3 or 4 again.\n");

						}//while2 ends


					}//location 선택한 경우 ends


					//category 선택한 경우
					else if(LocOrCat==2){
											
						
						String category=userInput.getCategory();

						//while2 starts(지역 선택한 경우 그다음 조건 선택하는 loop)
						while(true){
							System.out.println("Choose the condition for searching(Insert 1~4)");
							System.out.println("1. Discount");
							System.out.println("2. Price");
							System.out.println("3. Beverage");
							
							int t= scanner.nextInt();

							 //Discount를 선택한 경우 
							if(t==1){
								userInput.getPayment(category);
								break;
							}
							
							//Price를 선택한 경우 
							else if(t==2){
								userInput.getPrice(category);
								break;
							}
							
							//Beverage를 선택한 경우 
							else if(t==3){
								userInput.getDrink(category);
								break;
							}
							
							
							else
								System.out.println("<Warning> insert 1 or 2 or 3 again.\n");
						
						}//while2 ends

					}//category ends


					//사용자가 program exit 선택한 경우 (while1에서 나가고 customer mode 종료)
					else if(LocOrCat==3){
						break;
					}


				}//while1 ends
			}//customer mode ends

			//Manager Mode 선택
			else if(CusOrMan == 2){


				//while11 starts(Manager Mode를 선택한후 테이블 수정하기)
				while(true){
					System.out.println("Choose the operation you want to do:");
					System.out.println("1. Edit Menu evaluation");
					System.out.println("2. Delete restaurant");
					System.out.println("3. Insert new delivery location");
					
					int c= scanner.nextInt();

					//Edit Menu evaluation을 선택한 경우 
					if(c==1){
						userInput.getEditMenuEval();
						break;
					}
					
					//Delete restaurant를 선택한 경우
					else if(c==2){
						userInput.getNameForDelete();
						break;
					}
					
					//Insert new delivery location을 선택한 경우 
					else if(c==3){
						userInput.getLocationForInsert();
						break;
					}


					System.out.println("<Warning> insert 1 or 2 again.\n");


				}//while11 ends

			}//manager mode ends
			
			//program 종료를 선택한 경우 
			else if(CusOrMan == 3){
				System.exit(0);
			}





		}
	}//insertTest()







}//main


