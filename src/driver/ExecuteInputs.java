package driver;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import order.OrderRequest;
import order.OrderRequestList;
import theatre.Section;
import theatre.Theater;


class ExecuteInputs{
	private static Theater theater = null;
	private static OrderRequestList obj = null;
	private boolean layoutSyntax = false;
	private boolean requestSyntax = false;


public ExecuteInputs(Theater t, OrderRequestList ot)
{
		theater = t;
		obj = ot;
}

//method to check theater layout syntax
public void executeLayout(String layout)
{
	
    int totalCapacity = 0, value;
    String[] rows = layout.split(System.lineSeparator());
    String[] sections;
    
    for(int i=0 ; i<rows.length ; i++){
        
        sections = rows[i].split(" ");
        
        for(int j=0 ; j<sections.length ; j++){
        
            try{
            		
            		value = Integer.parseInt(sections[j]);
                if(value <= 0)
                {
                	throw new NumberFormatException();
                }
                
            }catch(NumberFormatException nfe){
                
                throw new NumberFormatException("'" + sections[j] + "'" + " is invalid section capacity. Please correct it.");
                
            }
            
            totalCapacity = totalCapacity + value;  
            theater.createSection(i + 1, value, j + 1);
            
        }

    }
    theater.setTotalSeats(totalCapacity);
    
    //setting the syntax valid to true
    layoutSyntax = true;
  
    return;

}

//method to check for orders input syntax
public void executeRequests(String requestsBuffer)
{
	
    String[] requests = requestsBuffer.split(System.lineSeparator());
    
    for(String r : requests){
        
        String[] rData = r.split(" ");
        
       
        try{
        
        		int value = Integer.parseInt(rData[1]);
        		//try catch block
        		try {
	            if(value <=0)
	            {
	            		throw new NumberFormatException("value cannot be negative");
	            }
        		}catch(NumberFormatException nfe){
                    
                    throw new NumberFormatException();
                }
            
            //try catch block
        		try {
	            if(rData.length>2 || rData.length<2)
	            {
	            		throw new IllegalArgumentException("Wrong number of Arguments for " + rData[0]);
	            }
            }catch(IllegalArgumentException nfe){
                
                 throw new IllegalArgumentException();
            }
        		
               
        }catch(NumberFormatException nfe){
            
            throw new NumberFormatException("'" + rData[1] + "'" + " is invalid order request. Please correct it.");
        }
        catch(IllegalArgumentException nfe){
            
            throw new IllegalArgumentException("Wrong number of Arguments for " + rData[0]);
        }
        
        obj.createOrder(rData[0], Integer.valueOf(rData[1]));
       
    }
    //setting the syntax valid to true
    requestSyntax = true;
    return;
}

public void execute()
{
	//processing the orders now and print the statuses of the orders
	if(layoutSyntax && requestSyntax)
	{
		 processOrders();
		 getOrdersStatus();
	}
	 
}

//method to process the orders, it is private so that It cannot be called from outside
private void processOrders()
{
	List<OrderRequest> orders = obj.getOrderList();
	//iterating through the requests and processing them
	for(int i=0; i<orders.size(); i++)
	{
		OrderRequest order = orders.get(i);
		
		if(!order.getStatus().isEmpty())
		{
			//do nothing and go to next order
		}
        
		else if(order.getSeatsNeeded() > theater.getTotalSeats())
		{
			//it is not possible to handle the order
			order.setStatus(order.getName() + " Sorry, we can't handle your party.");
			
		}
		else
		{
			//get sections
			List<Section> sections = theater.getSectionList();
			//iterating through all sections 
			for(int j=0; j<sections.size(); j++)
			{
				Section section = sections.get(j);
				//this is fairly straightforward, if the order needs exact remaining seats of a section, then we just fill that section of that row
				if(order.getSeatsNeeded() == section.getRemainingSeats()){          
				       order.setRowAllotted(section.getRowNum());
				       order.setSectionAllotted(section.getSectionNum());
				       section.setRemainingSeats(section.getRemainingSeats() - order.getSeatsNeeded());
				       theater.setRemainingSeats(theater.getRemainingSeats()- order.getSeatsNeeded());
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
	                        theater.setRemainingSeats(theater.getRemainingSeats()- order.getSeatsNeeded());
	                        order.setStatus(order.getName() + " Row " + order.getRowAllotted() + " Section " + order.getSectionAllotted());
	                        
	                        //setting the status of the complement order, since we filled this with remaining seats
	                        OrderRequest order_complement = orders.get(orderNo);
	                        order_complement.setRowAllotted(section.getRowNum());
	                        order_complement.setSectionAllotted(section.getSectionNum());
	                        section.setRemainingSeats(section.getRemainingSeats() - order_complement.getSeatsNeeded());
	                        theater.setRemainingSeats(theater.getRemainingSeats() - order_complement.getSeatsNeeded());
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
		                            theater.setRemainingSeats(theater.getRemainingSeats() - order.getSeatsNeeded());
		                            order.setStatus(order.getName() + " Row " + order.getRowAllotted() + " Section " + order.getSectionAllotted());
		                            break;
		                            
		                        }else{
		                            
		                            order.setRowAllotted(section.getRowNum());
		                            order.setSectionAllotted(section.getSectionNum());
		                            section.setRemainingSeats(section.getRemainingSeats() - order.getSeatsNeeded());
		                            theater.setRemainingSeats(theater.getRemainingSeats() - order.getSeatsNeeded());
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
    
	List<OrderRequest> orders = obj.getOrderList();
    int orderNo = -1;

    for(int i=currentOrderIndex+1 ; i<orders.size() ; i++){
        
        OrderRequest order = orders.get(i);
        
        if(order.getStatus()!=null && order.getSeatsNeeded() == remaining_seats){
            
            orderNo = i;
            break;
            
        }
        
    }
    
    return orderNo;
}

//method to find the section, with the given number of seats required for the orderRequest
private int findSectionByAvailableSeats(List<Section> sections ,int availableSeats){
    
	
    int i = 0;
    Section section = new Section();
    section.setRemainingSeats(availableSeats);
    
    //sorting the sections before calling binary search on the sections
    Collections.sort(sections);
    
    Comparator<Section> byAvailableSeats = new Comparator<Section>() {
        
        @Override
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
	List<OrderRequest> orders = obj.getOrderList();
	
	for(int i=0; i<orders.size(); i++)
	{
		System.out.println(orders.get(i).getStatus());
	}
}



}
