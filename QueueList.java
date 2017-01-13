import java.util.*;
import java.util.concurrent.Semaphore;

public class QueueList {

	private Queue<Customer> _list;
	Customer customer; 
	private Semaphore full; 
	private Semaphore empty;


	public QueueList(){
		empty = new Semaphore(5); // each queue will have a maximum of 5 Customers
		full = new Semaphore(0);
		_list = new LinkedList<Customer>();
	}	

	public void processCustomer()
	{
		Customer beingProcessed = null;
		try {
			full.acquire();
		} catch (InterruptedException e1) {}

		beingProcessed = _list.poll();
		System.out.println("the size after processing is: " + _list.size());

		try {
			Thread.sleep(beingProcessed.getNumberOfItems()*100);
		} catch (InterruptedException e) {}

		empty.release();

		try{
			Thread.sleep(100);
		} catch (InterruptedException e){}
	}


	public void addCustomer(Customer customer){
		try	{
			Thread.sleep( (new Random()).nextInt(100) + 1);
		} catch (InterruptedException e){}

		this.customer = customer;

		try {
			empty.acquire();
		} catch (InterruptedException e){}

		_list.add(this.customer);
		System.out.println("the size after adding is: " + _list.size());
		full.release();
	}

	public Customer processAndGetCustomer()
	{
		Customer beingProcessed = null;
		try{
			full.acquire();
		} catch (InterruptedException e1) {}


		beingProcessed = _list.poll();
		System.out.println("the size after processing is: " + _list.size());

		try {
			Thread.sleep(beingProcessed.getNumberOfItems()*25);
		} 
		catch (InterruptedException e) {}

		empty.release();

		try{
			Thread.sleep(1000);
		} catch (InterruptedException e){}

		return beingProcessed;
	}

	public int size() {
		return _list.size();
	}

}