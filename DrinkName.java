//1215001 ����
package KANGHANNAYOUNG;
//����ڰ� �˻��ɼ�����  Beverage�� �������� ���, ����� ����ϱ� ���� Ŭ����
//restaurant�̸��� restaurant���� �Ǹ��ϴ� alcohol����� nonalcohol���Ḧ ����Ѵ�.
public class DrinkName {
	
	private String name;
	private String alcohol;
	private String non_alcohol;

	public DrinkName(String name, String alcohol, String non_alcohol) {
		this.name = name;
		this.alcohol = alcohol;
		this.non_alcohol = non_alcohol;
	}

	public String toString(){

		int length = name.length();
		int length2 = alcohol.length();




		if(length <= 7){
			if(length2 <= 7){
				return name+"			"+alcohol+"			"+non_alcohol;
			}
			else if(7 <= length2 && length2 <= 15){
				return name+"			"+alcohol+"		"+non_alcohol;
			}
			else{
				return name+"			"+alcohol+"	"+non_alcohol;
			}
		}
		else if(7 <= length && length <= 15){
			if(length2 <= 7){
				return name+"		"+alcohol+"			"+non_alcohol;
			}
			else if(7 <= length2 && length2 <= 15){
				return name+"		"+alcohol+"		"+non_alcohol;
			}
			else{
				return name+"		"+alcohol+"	"+non_alcohol;
			}
		}
		else{
			if(length2 <= 7){
				return name+"	"+alcohol+"			"+non_alcohol;
			}
			else if(7 <= length2 && length2 <= 15){
				return name+"	"+alcohol+"		"+non_alcohol;
			}
			else{
				return name+"	"+alcohol+"	"+non_alcohol;
			}
		}
	}

	public String getName() { return this.name; }
	public String getAlcohol() { return this.alcohol; }
	public String getNonAlcohol() { return this.non_alcohol; }

}