package KANGHANNAYOUNG;
//MAIN CLASS
//����ڰ� ������ ����� �����ϴ� Ŭ����
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//����
import java.util.Scanner;

public class RestaurantDB {



	public static void main(String[] args) throws FileNotFoundException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		//Test:����ڷκ��� Configuration ���ϰ� SQL script�� ������ �о���� Ŭ����
		//configuration������ ������ �о���� DB�� �����Ѵ�
		Test mn=new Test();
		Test.readConfig();
		Test.MakeDB();
		//���α׷��� ����
		insertTest();

	}

	//insert part start

	public static void insertTest() throws SQLException {


		//view ������ ���� �۾�
		CreateView viewTable = new CreateView();
		
		Scanner scanner = new Scanner(System.in);
		
		//RestaurantDBinput class:����ڷκ��� �Է��� �޾ƿ��� Ŭ����. 
		RestaurantDBinput userInput =new RestaurantDBinput();

		System.out.println("\n=====Program started. WELCOME!=====");

		//Customer mode�� Manager mode�� �����Ѵ�
		//while0 starts
		while(true){
			
			int CusOrMan = userInput.CustomerOrManager();

			//Customer mode�� ������ ���
			if(CusOrMan == 1){
				
				//�˻��ɼ� ����(Location/Category����)
				//while1 starts
				while(true){
					
					int LocOrCat= userInput.LocaionOrCategory();


					//location ������ ���
					if(LocOrCat==1){
						//����ڰ� �Է��� ������ �Ĵ��� �������� ����
						String restaurantLoc=userInput.getLocation();


						//while2 starts(���� ������ ��� �״��� ������ �����ϴ� loop)
						while(true){
							System.out.println("Choose the condition for searching(Insert 1~4)");
							System.out.println("1. Opening and Closing time");
							System.out.println("2. Delivery available location");
							System.out.println("3. Major client");
							System.out.println("4. Seat");
							int t= scanner.nextInt();
							
							//Opening and Closing time�� �����ϴ� ���
							if(t==1){
								userInput.getOpeningClosing(restaurantLoc);
								break;
							}
							
							//Delivery available location�� �����ϴ� ���
							else if(t==2){
								userInput.getDeliveryLocation(restaurantLoc);
								break;
							}
							
							//Major client�� �����ϴ� ���
							else if(t==3){
								userInput.getAgeGender(restaurantLoc);
								break;
							}
							
							//Seat�� �����ϴ� ��� 
							else if(t==4){
								userInput.getSizeSeat(restaurantLoc);
								break;
							}
							else
								System.out.println("<Warning> insert 1 or 2 or 3 or 4 again.\n");

						}//while2 ends


					}//location ������ ��� ends


					//category ������ ���
					else if(LocOrCat==2){
											
						
						String category=userInput.getCategory();

						//while2 starts(���� ������ ��� �״��� ���� �����ϴ� loop)
						while(true){
							System.out.println("Choose the condition for searching(Insert 1~4)");
							System.out.println("1. Discount");
							System.out.println("2. Price");
							System.out.println("3. Beverage");
							
							int t= scanner.nextInt();

							 //Discount�� ������ ��� 
							if(t==1){
								userInput.getPayment(category);
								break;
							}
							
							//Price�� ������ ��� 
							else if(t==2){
								userInput.getPrice(category);
								break;
							}
							
							//Beverage�� ������ ��� 
							else if(t==3){
								userInput.getDrink(category);
								break;
							}
							
							
							else
								System.out.println("<Warning> insert 1 or 2 or 3 again.\n");
						
						}//while2 ends

					}//category ends


					//����ڰ� program exit ������ ��� (while1���� ������ customer mode ����)
					else if(LocOrCat==3){
						break;
					}


				}//while1 ends
			}//customer mode ends

			//Manager Mode ����
			else if(CusOrMan == 2){


				//while11 starts(Manager Mode�� �������� ���̺� �����ϱ�)
				while(true){
					System.out.println("Choose the operation you want to do:");
					System.out.println("1. Edit Menu evaluation");
					System.out.println("2. Delete restaurant");
					System.out.println("3. Insert new delivery location");
					
					int c= scanner.nextInt();

					//Edit Menu evaluation�� ������ ��� 
					if(c==1){
						userInput.getEditMenuEval();
						break;
					}
					
					//Delete restaurant�� ������ ���
					else if(c==2){
						userInput.getNameForDelete();
						break;
					}
					
					//Insert new delivery location�� ������ ��� 
					else if(c==3){
						userInput.getLocationForInsert();
						break;
					}


					System.out.println("<Warning> insert 1 or 2 again.\n");


				}//while11 ends

			}//manager mode ends
			
			//program ���Ḧ ������ ��� 
			else if(CusOrMan == 3){
				System.exit(0);
			}





		}
	}//insertTest()







}//main


