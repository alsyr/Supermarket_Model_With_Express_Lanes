

import java.util.Random;

public class Customer {
	private int _limitItems;
	private int _numberItems;

	public Customer(int limitItems){
		_limitItems = limitItems;
		_numberItems = (new Random()).nextInt(_limitItems) + 1;
	}

	public int getNumberOfItems(){
		return _numberItems;
	}
}
