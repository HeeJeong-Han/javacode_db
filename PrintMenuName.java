//1215129 �ѳ���
package KANGHANNAYOUNG;
//����ڰ� �˻��ɼ����� menuname�� �������� ���, ����� ����ϱ� ���� Ŭ����  
//restaurant�̸��� restaurant���� �Ǹ��ϴ� menuname�� ����Ѵ�.
public class PrintMenuName {
	private String restaurant_name;
	private String menuname;

	public PrintMenuName(String restaurant_name, String menuname){
		this.restaurant_name = restaurant_name;
		this.menuname = menuname;

	}

	public String toString(){
		int length = restaurant_name.length();
		
		if(length <= 7)
       	 return restaurant_name+"			"+menuname;
        else if(7 <= length && length <= 15)
       	 return restaurant_name+"		"+menuname;
        else
       	 return restaurant_name+"	"+menuname;

	}	
	
	public String getName() { return this.restaurant_name; }
	public String getmenuname() { return this.menuname; }

}
