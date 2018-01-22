package driver;


import java.util.Scanner;

import order.OrderRequestList;
import theatre.Theater;


//Driver program 

public class MainClass {
	
	//private data members
	private static Theater theater = null;
	private static OrderRequestList orderRequestList = null;

	public static void main(String[] args)
	{
			//getting the Theater and OrderRequestList objects
			ObjectHolderClass obj = new ObjectHolderClass();
			theater = obj.getTheatre();
			orderRequestList = obj.getOrderRequestList();
			
			//creating an object of ExecuteInputs
			ExecuteInputs tm = new ExecuteInputs(theater,orderRequestList);
			
		 	String line;
	        StringBuilder layout = new StringBuilder();
	        StringBuilder ticketRequests = new StringBuilder();
	        boolean isLayoutComplete = false;
	        
	        System.out.println("Please enter Layout, order requests seperated by an empty line and then enter 'end'.");
	        System.out.println("Assumptions/Rules for input:");
    			System.out.println("1. Section Number/ theater layout should contain only numbers");
    			System.out.println("2. Seats requested should be only number");
    			System.out.println("3. All Seats/Sections should be only numbers");
    			System.out.println("4. Please enter 'end' to indicate that the input is entered\n");
	        
	        Scanner sc = new Scanner(System.in);

	        while((line = sc.nextLine()) != null && !line.equals("end")){
	            
	            if(line.length() == 0){
	                
	                isLayoutComplete = true;
	                continue;
	                
	            }
	            
	            if(!isLayoutComplete){
	                
	                layout.append(line + System.lineSeparator());
	                
	            }else{
	                
	                ticketRequests.append(line + System.lineSeparator());
	                
	            }
	            
	        }
	        
	        sc.close();
	        
	        
	        
	        //try catch block, to verify that entered input is correct and to process the orders
	        try{
	        
	            tm.executeLayout(layout.toString());
	            
	            tm.executeRequests(ticketRequests.toString());
	            
	            tm.execute();
	           
	            
	        }catch(NumberFormatException nfe){
	            
	            System.out.println(nfe.getMessage());
	            
	        }
	        catch(IllegalArgumentException nfe){
	            
	        		System.out.println(nfe.getMessage());
	        }
	        catch(Exception e){
	            
	            e.printStackTrace();
	            
	        }

	        
	}
	
}
