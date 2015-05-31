

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
		Test mn=new Test();
		Test.readConfig();
		Test.MakeDB();
		insertTest();

	}

	//insert part start

	public static void insertTest() throws SQLException {


		//view 생성을 위한 작업
		CreateView viewTable = new CreateView();



		//지원	
		Scanner scanner = new Scanner(System.in);
		RestaurantDBinput userInput =new RestaurantDBinput();

		System.out.println("\n=====program started. WELCOME!=====");

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
							System.out.println("Choose the condition for searching(insert 1~4)");
							System.out.println("1. opening and closing time");
							System.out.println("2. delivery available location");
							System.out.println("3. major client");
							System.out.println("4. seat");
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
								System.out.println("<Warning> insert 1 or 2 or 3 or 4 again.\n");

						}//while2 ends


					}//location 선택한 경우 ends


					//category 선택한 경우
					else if(LocOrCat==2){
											
						
						String category=userInput.getCategory();

						//while2 starts(지역 선택한 경우 그다음 조건 선택하는 loop)
						while(true){
							System.out.println("Choose the condition for searching(insert 1~4)");
							System.out.println("1. discount");
							System.out.println("2. price");
							System.out.println("3. beverage");
							//System.out.println("4. menu evaluation");
							int t= scanner.nextInt();

							 
							if(t==1){
								userInput.getPayment(category);
								break;
							}
							else if(t==2){
								userInput.getPrice(category);
								break;
							}
							else if(t==3){
								userInput.getDrink(category);
								break;
							}
							
							
							else
								System.out.println("<Warning> insert 1 or 2 or 3 again.\n");
						}//while2 ends

					}//category ends


					//user가 exit 선택한 경우 (while1에서 나가고 customer mode 종료)
					else if(LocOrCat==3){
						break;
					}


				}//while1 ends
			}//customer mode ends

			//Manager Mode 선택
			else if(CusOrMan == 2){

				//String restaurantLoc=userInput.getLocation();


				//while11 starts(매니저모드를 선택한후 테이블 수정하기)
				while(true){
					System.out.println("choose the operation you want to do:");
					System.out.println("1. edit menu evaluation");
					System.out.println("2. delete restaurnt");
					System.out.println("3. insert new delivery location");
					
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


					System.out.println("<Warning> insert 1 or 2 again.\n");


				}//while11 ends

			}//manager mode ends
			
			
			else if(CusOrMan == 3){
				System.exit(0);
			}





		}
	}//insertTest()







}//main


