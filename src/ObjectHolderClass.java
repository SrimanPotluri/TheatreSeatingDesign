//this class is a placeholder for objects Theatre and OrderRequestList
// this creates the objects
public class ObjectHolderClass {
	
	private Theatre theatre = null;
	private OrderRequestList orderRequestList = null;
	
	public ObjectHolderClass()
	{
		theatre = Theatre.getInstance();
		orderRequestList = OrderRequestList.getInstance();
	}
	
	public Theatre getTheatre()
	{
		return theatre;
	}
	
	public OrderRequestList getOrderRequestList()
	{
		return orderRequestList;
	}

}
