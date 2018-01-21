package driver;
import order.OrderRequestList;
import theatre.Theater;

//this class is a placeholder for objects Theatre and OrderRequestList
// this creates the objects
class ObjectHolderClass {
	
	private Theater theatre = null;
	private OrderRequestList orderRequestList = null;
	
	public ObjectHolderClass()
	{
		theatre = Theater.getInstance();
		orderRequestList = OrderRequestList.getInstance();
	}
	
	public Theater getTheatre()
	{
		return theatre;
	}
	
	public OrderRequestList getOrderRequestList()
	{
		return orderRequestList;
	}

}
