//1215131 한희정
package KANGHANNAYOUNG;
//사용자가 검색옵션으로 Seat 선택했을 경우, 검색 결과를 출력해 주기 위한 클래스
//restaurant이름과 restaurant에서 이용가능한 Inside_seat과 Outside_seat의 좌석수를 출력한다.
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