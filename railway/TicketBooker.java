package railway;
import java.util.*;
public class TicketBooker {
	//Total berths 63-> 21-Upperberth, 21-middleberth, 21-lowerberth
	//18-rac(reserve against cancellation, 10 waiting list
	static int availablelowerberth=21;
	static int availablemiddleberth=21;
	static int availableupperberth=21;
	static int availableractickets=18;
	static int availablewaitinglist=10;
	
	
	//Queue for waiting list and rac list... because first come first serve
	static Queue<Integer>waitingList =new LinkedList<>();
	static Queue<Integer>racList =new LinkedList<>();
	static List<Integer> bookedTicket =new ArrayList<>();
	
	
	//Initializing berth==1,2,......21, racpositions==1,2,...18, waitinglist==1,2,....10 
	static List<Integer>lowerBerthPositions = new ArrayList<>();
	static List<Integer>middleBerthPositions = new ArrayList<>();
	static List<Integer>upperBerthpositions = new ArrayList<>();
	static List<Integer>racpositions=new ArrayList<>();
	static List<Integer>waitingpositions=new ArrayList<>();
	
	//Mapping of passenger id to passengers
	static Map<Integer, Passenger> passengers= new HashMap<>(); //The Passenger is a object from the class Passenger
	
	public TicketBooker() {
		for(int i=0;i<21;i++) {
			lowerBerthPositions.add(i);
			middleBerthPositions.add(i);
			upperBerthpositions.add(i);
		}
		for(int i=0;i<18;i++) {
			racpositions.add(i);
		}
		for(int i=0;i<10;i++) {
			waitingpositions.add(i);
		}
	}
	
	public void bookTicket(Passenger p, int berthinfo,String allotedberth) {
		p.number=berthinfo;
		p.alloted=allotedberth;
		passengers.put(p.passengerid, p);   //Added passenger id and passenger details to the hashmap
		bookedTicket.add(p.passengerid); //Added passenger list to the bookedList
		System.out.println("Booked Successfully");
	}
	public void addtoRac(Passenger p,int berthinfo, String allotedberth) {
		p.number=berthinfo;
		p.alloted=allotedberth;
		passengers.put(p.passengerid, p);
		racList.add(p.passengerid);
		racpositions.remove(0);
		availableractickets--;
		System.out.println("Added to RAC successfully");
	}
	public void addtowaitinglist(Passenger p,int berthinfo, String allotedberth) {
		p.number=berthinfo;
		p.alloted=allotedberth;
		passengers.put(p.passengerid, p);
		waitingList.add(p.passengerid);
		waitingpositions.remove(0);
		availablewaitinglist--;
		System.out.println("Added to waiting list successfully");
	}
	public void cancelTicket(int passengerid) {
		//First removing the passenger id from the map
		Passenger p=passengers.get(passengerid);
		passengers.remove(passengerid);
		bookedTicket.remove(passengerid);
		int positionbooked=p.number;	//store the position of the cancelled ticket in a variable
		System.out.println("Cancelled successfully");
		
		//Now show this cancelled ticket as free positions
		
		if(p.alloted.equals("L")) {
			availablelowerberth++;
			lowerBerthPositions.add(positionbooked);
		}
		else if(p.alloted.equals("M")) {
			availablemiddleberth++;
			middleBerthPositions.add(positionbooked);
		}
		else {
			availableupperberth++;
			upperBerthpositions.add(positionbooked);
		}
		
		//After removing from berth move the passenger from Rac to berth and waiting list to RAC
		if(racList.size()>0) {
			Passenger passengerfromrac =passengers.get(racList.poll());
			int positionrac=passengerfromrac.number;
			racpositions.add(positionrac);
			racList.remove(passengerfromrac.passengerid);
			availableractickets++;
			
			
			//Check if there are waiting list and send the 1st passenger from waiting list to RAC
			//get the 1st passenger from waiting queue and store it in passengerfromwaitingqueue
			//Now store the position of that passenger to the variable called positionwaiting
			//add this position to a waiting position so it will show as free
			//Now remove this passengers id from waiting list
			if(waitingList.size()>0) {
				Passenger passengerfromwaitingList=passengers.get(waitingList.poll());
				int positionwaiting=passengerfromwaitingList.number;
				waitingpositions.add(positionwaiting);
				waitingList.remove(passengerfromwaitingList.passengerid);
				
				//Now moving the waiting list to RAC
				passengerfromwaitingList.number=racpositions.get(0);
				passengerfromwaitingList.alloted="RAC";
				racpositions.remove(0);	//Coz that position is no more available
				racList.add(passengerfromrac.passengerid);
				
				availableractickets--;
				availablewaitinglist++;
			}
			
			//Since we've moved the waiting list 1st person to RAC...We can also move the 1st person from RAC to berth tickets..For that just call bookticket function in the main with the details of 1st passenger in RAC which is retrieved by poll()
			Main.bookTicket(passengerfromrac);
		}
	}
	public void printAvailable() {
		System.out.println("***********************************************************************************");
		System.out.println("Available Lowerberth: "+availablelowerberth);
		System.out.println("Available Middeleberth: "+availablemiddleberth);
		System.out.println("Available Upperberth: "+availableupperberth);
		System.out.println("Available Reservation Against Cancellation(RAC): "+availableractickets);
		System.out.println("Available WaitingList tickets: "+availablewaitinglist);
		System.out.println("***********************************************************************************");
	}
	public void printPassengers() {
		if(passengers.size()==0) {
			System.out.println("No passengers to display");
			return;
		}
		for(Passenger p: passengers.values()) {
			System.out.println("***********************************************************************************");
			System.out.println("Passenger id: "+p.passengerid);
			System.out.println("Passenger name: "+p.name);
			System.out.println("Passenger age: "+p.age);
			System.out.println("Status: "+p.number+" "+p.alloted);
			System.out.println("***********************************************************************************");
		}
	}
}
