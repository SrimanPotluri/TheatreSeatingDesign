import java.util.ArrayList;
import java.util.List;

//class that is responsible to create and maintain list of orderRequest
// this is made singleton, since we just want once instance of it

class OrderRequestList{

private static OrderRequestList orderRequestList = null;
private List<OrderRequest> order_list;

// private constructor
private OrderRequestList()
{
    order_list = new ArrayList<OrderRequest>();
}

// static method to implement it as a singleton pattern
public static OrderRequestList getInstance()
{
  if(orderRequestList==null)
  {
    orderRequestList = new OrderRequestList();
  }
  return orderRequestList;
}

//
public OrderRequest createOrder(String name, int seats)
{
    //creating order and adding it to the list
    OrderRequest order = new OrderRequest(name,seats);
    order_list.add(order);
    return order;
}

public List<OrderRequest> getOrderList()
{
	return order_list;
}


}
