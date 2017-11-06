package stock;

import java.util.HashMap;
import java.util.Map;

public class StockBroker extends Subscriber {
	private StockBroker instance = new StockBroker();
	private Map<String, Stock> stocks;

	private StockBroker() {
		stocks = new HashMap<String, Stock>();
	}

	public StockBroker getInstance(){
		if(instance == null){
			instance = new StockBroker();
		}
		return instance;
	}
	
	public StockStatus getCurrentStockStatus(String symbol){
		Stock s = stocks.get(symbol);
		if (s == null) return null;
		return s.getCurrentStatus();
	}
	
	public void addStock(Stock s){
		String key = s.getSymbol();
		stocks.put(key, s);
	}
	
	public Stock getStock(String symbol){
		return stocks.get(symbol);
	}
	
	@Override
	public void inform(Event event) {
		try{
			if(StockCreationEvent.class.isInstance(event)){
				StockCreationEvent stockCreationEvent = (StockCreationEvent) event;
				addStock(stockCreationEvent.getStock());
			}
		}catch(ClassCastException e){
			e.printStackTrace();
		}
	}

}
