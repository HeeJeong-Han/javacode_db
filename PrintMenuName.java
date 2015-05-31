//1215129 한나영
package KANGHANNAYOUNG;
//사용자가 검색옵션으로 menuname을 선택했을 경우, 결과를 출력하기 위한 클래스  
//restaurant이름과 restaurant에서 판매하는 menuname을 출력한다.
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
