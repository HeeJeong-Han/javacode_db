//1215131 ������
package KANGHANNAYOUNG;
//����ڰ� �˻��ɼ����� Seat �������� ���, �˻� ����� ����� �ֱ� ���� Ŭ����
//restaurant�̸��� restaurant���� �̿밡���� Inside_seat�� Outside_seat�� �¼����� ����Ѵ�.
public class AvailableSeat {
	private String name;
	private int inside;
	private int outside;

	public AvailableSeat(String name, int inside, int outside) {
		this.name = name;
		this.inside = inside;
		this.outside = outside;
	}
	
	public String toString(){
		   int length = name.length();
	         
	         if(length <= 7)
	        	 return name+"			"+inside+"			"+outside;
	         else if(7 <= length && length <= 15)
	        	 return name+"		"+inside+"			"+outside;
	         else
	        	 return name+"	"+inside+"			"+outside;
		   
	   }

	public String getName() { return this.name; }
	public int getInside() { return this.inside; }
	public int getOutside() { return this.outside; }
}