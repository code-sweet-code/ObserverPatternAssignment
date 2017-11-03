package stock;

import java.util.LinkedList;
import java.util.List;

public class StockBroker extends Subscriber {
	private StockBroker instance = new StockBroker();
	private List<Stock> stocks;

	private StockBroker() {
		stocks = new LinkedList<Stock>();
	}

	public StockBroker getInstance(){
		if(instance == null){
			instance = new StockBroker();
		}
		return instance;
	}
	
	@Override
	public void inform(Event event) {
		// TODO Auto-generated method stub
		
	}

}
