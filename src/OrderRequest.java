//class that demonstrates the order

class OrderRequest{

// data members
private String name;
private int seats_needed;
private static int count = 0;
private int order_id;
private String status = "";
private int section_num_allotted;
private int row_allotted;

//default constructor
public OrderRequest()
{
   
}

//parameterized constructor
public OrderRequest(String name, int seats)
{
    this.name = name;
    this.seats_needed = seats;
    count++;
    this.order_id = count;
}

//getter for order_id
public int getId()
{
    return order_id;
}

public String getStatus()
{
  return status;
}

public void setStatus(String status)
{
	this.status = status;
}

public int getSeatsNeeded()
{
	return seats_needed;
}

public String getName()
{
	return name;
}

public int getSectionAllotted()
{
	return section_num_allotted;
}

public void setSectionAllotted(int s)
{
	 section_num_allotted = s;
}

public int getRowAllotted()
{
	return row_allotted;
}

public void setRowAllotted(int r)
{
	row_allotted = r;
}

}
