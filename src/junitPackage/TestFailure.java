package junitPackage;

import org.junit.Before;
import org.junit.Test;

import driver.ExecuteInputs;
import driver.ObjectHolderClass;
import order.OrderRequestList;
import theatre.Theater;


public class TestFailure {
	
	private Theater theater = null;
	private OrderRequestList  orderRequestList = null;
	private ObjectHolderClass obj = new ObjectHolderClass();
	private ExecuteInputs exe = null;

	
	@Before
	public void setUp() {
		theater = obj.getTheatre();
		orderRequestList = obj.getOrderRequestList();
		exe = new ExecuteInputs(theater,orderRequestList);
    }
 
	
	
	/*** test cases for Theater Layout inputs ***/

	@Test
	public void testExecuteLayout1() {
		
		exe.executeLayout("6 6");
	}
	
	@Test(expected = NumberFormatException.class)
	public void testExecuteLayout2() {
		
		exe.executeLayout("-6 6");
	}
	
	@Test(expected = NumberFormatException.class)
	public void testExecuteLayout3() {
		
		exe.executeLayout("Miller 6");
	}
	
	@Test(expected = NumberFormatException.class)
	public void testExecuteLayout4() {
		
		exe.executeLayout("6 6.2");
	}
	
	@Test(expected = NumberFormatException.class)
	public void testExecuteLayout5() {
		
		exe.executeLayout("0 6");	
	}
	
	
	/*** test cases for Customer Order inputs ***/
	
	@Test(expected = NumberFormatException.class)
	public void testExecuteRequest1() {
		
		exe.executeRequests("Miller Randy");
	}
	
	
	@Test(expected = NumberFormatException.class)
	public void testExecuteRequest2() {
		
		exe.executeRequests("Miller Randy");
	}
	
	@Test(expected = NumberFormatException.class)
	public void testExecuteRequest3() {
		
		exe.executeLayout("Ken 0");
	}
	
	@Test(expected = NumberFormatException.class)
	public void testExecuteRequest4() {
		
		exe.executeLayout("Bob 6.2");
		
	}
	
	@Test(expected = NumberFormatException.class)
	public void testExecuteRequest5() {
		
		exe.executeLayout("Frank -3");
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testExecuteRequest6() {
		
		exe.executeLayout("Frank 3 4");	
	}
	
	

}
