import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


class ExecuteProcess{
	
	private static Theatre theatre = null;
	private static OrderRequest obj = null;

public void execute() throws ArrayIndexOutOfBoundsException
{
    Scanner sc = new Scanner(System.in);
    int row = 0;
    int total_seats = 0;
    
    //getting the Theatre and OrderRequest instances
    theatre = Theatre.getInstance();
    obj =  OrderRequest.getInstance();

    System.out.println("Please Enter Theatre Layout followed by the customer orders with 'end' on last line to indicate end of input");
    //reading the theatre section inputs and orders
    	 while(sc.hasNextLine())
    	    {
    	        String line = sc.nextLine();
    	        if(line.equals("end"))
    	        {
    	        		//setting the total_no_of_seats on encountering "end" and breaking
    	        		theatre.setTotalSeats(total_seats);
    	        		break;
    	        }
    	        else if(!line.isEmpty() && line.split(" ")[0].matches("[-+]?\\d*\\.?\\d+") && line.split(" ")[1].matches("[-+]?\\d*\\.?\\d+"))
    	        {
    	          //reading section inputs
    	          row++;
    	          String[] in = line.split(" ");
    	          for(int i=0; i<in.length; i++)
    	          {
    	        	  	total_seats = total_seats + Integer.parseInt(in[i].toString());
    	            theatre.createSection(row,Integer.parseInt(in[i].toString()),i+1);
    	          }

    	        }
    	        else if(!line.isEmpty() && line.split(" ")[1].matches("[-+]?\\d*\\.?\\d+"))
    	        {
    	        			//reading orders
    	        	        String[] in = line.split(" ");
    	        	        Order order = obj.createOrder(in[0].toString(),Integer.parseInt(in[1].toString()));
    	        }
    	        else if(line.isEmpty())
    	        {
    	        		//skip
    	        }
    	        else
    	        {
    	        		//catch exceptions 
    	        		System.out.println("Input format is not correct, Please try again");
    	        		System.exit(0);
    	 
    	        }
    	        
    	    }//end of while
    	 
    	 sc.close();
    	 
    	
    	    
    	//processing the orders now and print the statuses of the orders
    	 processOrders();
    	 getOrdersStatus();
}

//method to process the orders, it is private so that It cannot be called from outside
private void processOrders()
{
	List<Order> orders = obj.getOrderList();
	//iterating through the requests and processing them
	for(int i=0; i<orders.size(); i++)
	{
		Order order = orders.get(i);
		
		if(!order.getStatus().isEmpty())
		{
			//do nothing and go to next order
		}
        
		else if(order.getSeatsNeeded() > theatre.getTotalSeats())
		{
			//it is not possible to handle the order
			order.setStatus(order.getName() + " Sorry, we can't handle your party.");
			
		}
		else
		{
			//get sections
			List<Section> sections = theatre.getSectionList();
			//iterating through all sections 
			for(int j=0; j<sections.size(); j++)
			{
				Section section = sections.get(j);
				//this is fairly straightforward, if the order needs exact remaining seats of a section, then we just fill that section of that row
				if(order.getSeatsNeeded() == section.getRemainingSeats()){          
				       order.setRowAllotted(section.getRowNum());
				       order.setSectionAllotted(section.getSectionNum());
				       section.setRemainingSeats(section.getRemainingSeats() - order.getSeatsNeeded());
				       theatre.setRemainingSeats(theatre.getRemainingSeats()- order.getSeatsNeeded());
				       order.setStatus(order.getName() + " Row " + order.getRowAllotted() + " Section " + order.getSectionAllotted());
				       break;             
				 }
				else if(order.getSeatsNeeded() < section.getRemainingSeats())
				{
					int orderNo = findOtherOrder(section.getRemainingSeats() - order.getSeatsNeeded(), i);
						if(orderNo != -1){
                        
							//setting the status for the current order
	                        order.setRowAllotted(section.getRowNum());
	                        order.setSectionAllotted(section.getSectionNum());
	                        section.setRemainingSeats(section.getRemainingSeats() - order.getSeatsNeeded());
	                        theatre.setRemainingSeats(theatre.getRemainingSeats()- order.getSeatsNeeded());
	                        order.setStatus(order.getName() + " Row " + order.getRowAllotted() + " Section " + order.getSectionAllotted());
	                        
	                        //setting the status of the complement order, since we filled this with remaining seats
	                        Order order_complement = orders.get(orderNo);
	                        order_complement.setRowAllotted(section.getRowNum());
	                        order_complement.setSectionAllotted(section.getSectionNum());
	                        section.setRemainingSeats(section.getRemainingSeats() - order_complement.getSeatsNeeded());
	                        theatre.setRemainingSeats(theatre.getRemainingSeats() - order_complement.getSeatsNeeded());
	                        order_complement.setStatus(order_complement.getName() + " Row " + order_complement.getRowAllotted() + " Section " + order_complement.getSectionAllotted());
	                        
	                        break;
						}
						else
						{
							 int sectionNo = findSectionByAvailableSeats(sections,order.getSeatsNeeded());
		                        
		                        if(sectionNo >= 0){
		                            
		                            Section perferctSection = sections.get(sectionNo);
		                            
		                            order.setRowAllotted(perferctSection.getRowNum());
		                            order.setSectionAllotted(perferctSection.getSectionNum());
		                            perferctSection.setRemainingSeats(perferctSection.getRemainingSeats() - order.getSeatsNeeded());
		                            theatre.setRemainingSeats(theatre.getRemainingSeats() - order.getSeatsNeeded());
		                            order.setStatus(order.getName() + " Row " + order.getRowAllotted() + " Section " + order.getSectionAllotted());
		                            break;
		                            
		                        }else{
		                            
		                            order.setRowAllotted(section.getRowNum());
		                            order.setSectionAllotted(section.getSectionNum());
		                            section.setRemainingSeats(section.getRemainingSeats() - order.getSeatsNeeded());
		                            theatre.setRemainingSeats(theatre.getRemainingSeats() - order.getSeatsNeeded());
		                            order.setStatus(order.getName() + " Row " + order.getRowAllotted() + " Section " + order.getSectionAllotted());
		                            break;
		                            
		                        }
						}
				}
				else {
						//skip the order if it doesn't fit the section, go to next one
				}
				
				
			} // end of inner for loop
			
			 /*
             * if the order is not accepted because they couldn't find one section that could fit the party
             */
            if(order.getStatus().isEmpty()){
                
                order.setStatus(order.getName() + " Call to split party");
                
            }	
			
		}// end of else
		
	} //end of outer for loop
	
}


//method to search for other request that needs remaining seats to complement the deficit in the current orderRequest
//method is private so that It cannot be called from outside

private int findOtherOrder(int remaining_seats, int currentOrderIndex){
    
	List<Order> orders = obj.getOrderList();
    int orderNo = -1;

    for(int i=currentOrderIndex+1 ; i<orders.size() ; i++){
        
        Order order = orders.get(i);
        
        if(order.getStatus()!=null && order.getSeatsNeeded() == remaining_seats){
            
            orderNo = i;
            break;
            
        }
        
    }
    
    return orderNo;
}

private int findSectionByAvailableSeats(List<Section> sections ,int availableSeats){
    
	
    int i = 0;
    Section section = new Section();
    section.setRemainingSeats(availableSeats);
    
    //sorting the sections before calling binary search on the sections
    Collections.sort(sections);
    
    Comparator<Section> byAvailableSeats = new Comparator<Section>() {
        
        public int compare(Section o1, Section o2) {
            
            return o1.getRemainingSeats() - o2.getRemainingSeats();
            
        }
    };
    
    int sectionNo = Collections.binarySearch(sections, section, byAvailableSeats);
    
    /*
     * sectionNo < 0 means could not find section
     * sectionNo = 0 means found section and it's very first one
     * sectionNo > 0 means found section but have to check for duplicate sections
     * 
     */
    
    if(sectionNo > 0){
        
        for(i=sectionNo-1 ; i>=0 ; i--){
            
            Section s = sections.get(i);
            
            if(s.getRemainingSeats()!= availableSeats) break;
            
        }
        
        sectionNo = i + 1;
        
    }
    
    return sectionNo;
}

//method to print all the statuses
private void getOrdersStatus()
{
	List<Order> orders = obj.getOrderList();
	
	for(int i=0; i<orders.size(); i++)
	{
		System.out.println(orders.get(i).getStatus());
	}
}



}
