//1215001 ����
package KANGHANNAYOUNG;
//����ڰ� �˻��ɼ����� Major_client�� AGE,GENDER �������� ���, ����� ������ֱ� ���� Ŭ����
//����ڰ� Major_client�� ������ age Ȥ�� gender�� ������ �����ϴ� restaurant�̸��� restaurant�� �̿��ϴ� Major_client�� age�� gender�� ����Ѵ�.
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