package railway;
import java.util.*;
public class Main {	
	public static void bookTicket(Passenger P) {
		TicketBooker booker=new TicketBooker();
		
		//If no waiting tickets are available Then no tickets are available
		if(TicketBooker.availablewaitinglist==0) {
			System.out.println("Sorry! No tickets available.");
			return;
		}
		
		//Check if preferred berth is available
		if((P.berthpreference.equals("L") && TicketBooker.availablelowerberth>0)||(P.berthpreference.equals("M") && TicketBooker.availablemiddleberth>0)||(P.berthpreference.equals("U") && TicketBooker.availableupperberth>0)) {
			System.out.println("Preferred Berth Available");
			if(P.berthpreference.equals("L")) {
				System.out.println("Lower berth given");
				booker.bookTicket(P,(TicketBooker.lowerBerthPositions.get(0)),"L");
				TicketBooker.lowerBerthPositions.remove(0);
				TicketBooker.availablelowerberth--;
			}
			else if(P.berthpreference.equals("M")) {
				System.out.println("Middle berth given");
				booker.bookTicket(P,(TicketBooker.middleBerthPositions.get(0)),"M");
				TicketBooker.middleBerthPositions.remove(0);
				TicketBooker.availablemiddleberth--;
			}
			else {
				System.out.println("Upper berth given");
				booker.bookTicket(P,(TicketBooker.upperBerthpositions.get(0)),"U");
				TicketBooker.upperBerthpositions.remove(0);
				TicketBooker.availableupperberth--;
	
			}
		}
		
		//If preferred berth not available
		
		else if(TicketBooker.availablelowerberth>0) {
			System.out.println("Lower berth given");
			booker.bookTicket(P,(TicketBooker.lowerBerthPositions.get(0)), "L");
			TicketBooker.lowerBerthPositions.remove(0);
			TicketBooker.availablelowerberth--;
		}
		else if(TicketBooker.availablemiddleberth>0) {
			System.out.println("Middle berth given");
			booker.bookTicket(P,(TicketBooker.middleBerthPositions.get(0)),"M");
			TicketBooker.middleBerthPositions.remove(0);
			TicketBooker.availablemiddleberth--;
		}
		else if(TicketBooker.availableupperberth>0){
			System.out.println("Upper berth available");
			booker.bookTicket(P, (TicketBooker.upperBerthpositions.get(0)), "U");
			TicketBooker.upperBerthpositions.remove(0);
			TicketBooker.availableupperberth--;
		}
		
		//If no berth available check for RAC
		
		else if(TicketBooker.availableractickets>0) {
			System.out.println("Reserve against Cancellation available");
			booker.addtoRac(P,(TicketBooker.racpositions.get(0)),"RAC");
		}
		
		//If no RAC also available check for waiting list tickets
		
		else if(TicketBooker.availablewaitinglist>0) {
			System.out.println("Waiting list tickets available");
			booker.addtowaitinglist(P,(TicketBooker.waitingpositions.get(0)),"W");
		}
	}
	public static void cancelTickets(int id) {
		TicketBooker booker=new TicketBooker();
		if(!TicketBooker.passengers.containsKey(id)) {
			System.out.println("Invalid Passenger Id");
		}
		else {
			booker.cancelTicket(id);
		}
	}
	public static void main(String args[]) {
		Scanner scan =new Scanner(System.in);
		boolean loop=true;
		while(true) {
			System.out.println("1.Book Ticket \n2.Cancel Ticket \n3.Available Tickets \n4.Booked Tickets \n5.Exit");
			int choice=scan.nextInt();
			switch(choice) {
				case 1:
				{
					System.out.println("Enter passenger name, age, berthprefernce(L,U or M)");
					String name=scan.next();
					int age=scan.nextInt();
					String berthpreference=scan.next();
					Passenger P= new Passenger(name,age,berthpreference);
					bookTicket(P);
					break;
				}
				case 2:
				{
					System.out.println("Enter passenger Id to cancel:");
					int id=scan.nextInt();
					cancelTickets(id);
					break;
				}
				case 3:
				{
					TicketBooker booker=new TicketBooker();
					booker.printAvailable();
					break;
				}
				case 4:
				{
					TicketBooker booker=new TicketBooker();
					booker.printPassengers();
					break;
				}
				case 5:
				{
					loop=false;
					break;
				}
				default:{
					break;
				}
			}
		}
	}
}
