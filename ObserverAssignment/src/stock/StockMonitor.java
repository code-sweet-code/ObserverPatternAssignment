package stock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class StockMonitor extends Subscriber {
	private Map<String, StockStatus> stockMap;
	private StockMonitor instance = new StockMonitor();

	private StockMonitor() {
		stockMap = new HashMap<String, StockStatus>();
	}

	public StockMonitor getInstance(){
		if(instance == null){
			instance = new StockMonitor();
		}
		return instance;
	}
	
	@Override
	public void inform(Event event) {
		try{
			switch(event.getEventType()){
			case STOCKCREATION:{
				if(StockCreationEvent.class.isInstance(event)){
					StockCreationEvent stockCreationEvent = (StockCreationEvent) event;
					addStock(stockCreationEvent.getStock());
				}
				break;
			}
			case PRICEUPDATE:{
				if(PriceUpdateEvent.class.isInstance(event)){
					PriceUpdateEvent priceUpdateEvent = (PriceUpdateEvent) event;
					addStatus(priceUpdateEvent.getUpdatedStatus());
				}
				break;
			}
			default: break;
			}
		}catch(ClassCastException e){
			e.printStackTrace();
		}
	}
	
	public void display(){
		System.out.println("Stock|Price");
		Iterator<Entry<String, StockStatus>> iterator = stockMap.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, StockStatus> pair = iterator.next();
			String symbol = pair.getKey();
			Money price = pair.getValue().getPrice();
			System.out.println(symbol+"|"+price.getAmount());
		}
	}
	
	public float getStockLatestPrice(String symbol){
		StockStatus updatedStatus = stockMap.get(symbol);
		return updatedStatus.getPrice().getAmount();
	}

	private void addStatus(StockStatus updatedStatus) {
		this.stockMap.put(updatedStatus.getStock().getSymbol(), updatedStatus);
	}

	private void addStock(Stock stock) {
		this.stockMap.put(stock.getSymbol(), stock.getCurrentStatus());
	}

	public static void main(String[] args) {
		
	}
}
