import java.util.ArrayList;
import java.util.List;

//class that is responsible to create and maintain list of orders
// this is made singleton, since we just want once instance of it

class OrderRequest{

private static OrderRequest orderRequest = null;
private List<Order> order_list;

// private constructor
private OrderRequest()
{
    order_list = new ArrayList();
}

// static method to implement it as a singleton pattern
public static OrderRequest getInstance()
{
  if(orderRequest==null)
  {
    orderRequest = new OrderRequest();
  }
  return orderRequest;
}

//
public Order createOrder(String name, int seats)
{
    //creating order and adding it to the list
    Order order = new Order(name,seats);
    order_list.add(order);
    return order;
}

public List<Order> getOrderList()
{
	return order_list;
}


}
