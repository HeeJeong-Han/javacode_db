//1215001 강린
package KANGHANNAYOUNG;
//사용자가 검색옵션으로 Major_client의 AGE,GENDER 선택했을 경우, 결과를 출력해주기 위한 클래스
//사용자가 Major_client로 설정한 age 혹은 gender의 조건을 만족하는 restaurant이름과 restaurant를 이용하는 Major_client의 age와 gender를 출력한다.
public class AgeGenderName {
	private String name;
	private int age;
	private String gender;
	
	public AgeGenderName(String name, int age, String gender) {
		   this.name = name;
		   this.age = age;
		   this.gender = gender;
		}
	   public String toString(){
		   int length = name.length();
	         
	         if(length <= 7)
	        	 return name+"			"+age+"	"+gender;
	         else if(7 <= length && length <= 15)
	        	 return name+"		"+age+"	"+gender;
	         else
	        	 return name+"	"+age+"	"+gender;
		   
	   }	
	public String getName() { return this.name; }
	public int getAge() { return this.age; }
	public String getGender() { return this.gender; }
}