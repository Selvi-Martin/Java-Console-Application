package railway;

public class Passenger {
	static int id=1;
	String name;
	int age;
	String berthpreference;
	int passengerid;
	String alloted;
	int number;
	public Passenger(String name,int age,String berthpreference) {
		this.name=name;
		this.age=age;
		this.berthpreference=berthpreference;
		passengerid=id++;
		alloted=""; //initially none
		number=-1;  //initially none
	}
}
