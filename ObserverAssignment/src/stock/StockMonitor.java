package stock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class StockMonitor extends Subscriber {
	private Map<String, StockStatus> stockMap;
	private static StockMonitor instance = new StockMonitor();

	private StockMonitor() {
		stockMap = new HashMap<String, StockStatus>();
	}

	public static StockMonitor getInstance(){
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
		StockBroker stockBroker = StockBroker.getInstance();
		StockMonitor stockMonitor = StockMonitor.getInstance();
		EventService eventService =  EventService.getInstance();
		eventService.subscribe(EventType.STOCKCREATION, stockBroker);
		eventService.subscribe(EventType.STOCKCREATION, stockMonitor);
		eventService.subscribe(EventType.PRICEUPDATE, stockMonitor);
		
		Stock appleStock = new Stock("AAPL", new Money(172.5f));
		Stock amazonStock = new Stock("AMZN", new Money(1118.07f));
		Stock teslaStock = new Stock("TSLA", new Money(299.33f));
		
		/* Should we use thread to update stock pricing in a loop and then print out
		 * all test price changes?
		 */
		appleStock.addStatus(new Money(173.5f));
		amazonStock.addStatus(new Money(1119.07f));
		teslaStock.addStatus(new Money(300.33f));
		
		/* check to see if StockBroker and StockMonitor were informed or not
		 * 
		 */
		System.out.println("Broker's Apple stock: " + stockBroker.getCurrentStockStatus("AAPL").toString());
		System.out.println("Broker's Amazon stock: " + stockBroker.getCurrentStockStatus("AMZN").toString());
		System.out.println("Broker's Tesla stock: " + stockBroker.getCurrentStockStatus("TSLA").toString());
		
		System.out.println("Monitor's Apple price stock: " + stockMonitor.getStockLatestPrice("AAPL"));
		System.out.println("Monitor's Amazon price stock: " + stockMonitor.getStockLatestPrice("AMZN"));
		System.out.println("Monitor's Tesla price stock: " + stockMonitor.getStockLatestPrice("TSLA"));
		
		
		
	}
	
}
