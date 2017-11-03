package stock;

import java.util.HashMap;
import java.util.Map;

public class StockMonitor extends Subscriber {
	private Map<Stock, StockStatus> stockMap;
	private StockMonitor instance = new StockMonitor();

	private StockMonitor() {
		stockMap = new HashMap<Stock, StockStatus>();
	}

	public StockMonitor getInstance(){
		if(instance == null){
			instance = new StockMonitor();
		}
		return instance;
	}
	
	@Override
	public void inform(Event e) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
