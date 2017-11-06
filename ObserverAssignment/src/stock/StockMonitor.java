package stock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class StockMonitor extends Subscriber {
	private Map<Stock, StockStatus> stockMap;
	private static StockMonitor instance = new StockMonitor();

	private StockMonitor() {
		stockMap = new HashMap<Stock, StockStatus>();
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
					display();
				}
				break;
			}
			case PRICEUPDATE:{
				if(PriceUpdateEvent.class.isInstance(event)){
					PriceUpdateEvent priceUpdateEvent = (PriceUpdateEvent) event;
					addStatus(priceUpdateEvent.getUpdatedStatus());
					display();
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
		System.out.println("**************Stock Board**************");
		System.out.println("*-------Stock------|-------Price------");
		Iterator<Entry<Stock, StockStatus>> iterator = stockMap.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<Stock, StockStatus> pair = iterator.next();
			Stock stock = pair.getKey();
			StockStatus status = pair.getValue();
			System.out.println("*       "+stock.getSymbol()+"       |       "+status.getPrice().getAmount());
		}
		System.out.println("****************************************");
	}
	
	public float getStockLatestPrice(String symbol){
		StockStatus updatedStatus = stockMap.get(symbol);
		return updatedStatus.getPrice().getAmount();
	}

	private void addStatus(StockStatus updatedStatus) {
		this.stockMap.put(updatedStatus.getStock(), updatedStatus);
	}

	private void addStock(Stock stock) {
		this.stockMap.put(stock, stock.getCurrentStatus());
	}

	public static void main(String[] args) {
		StockBroker stockBroker = StockBroker.getInstance();
		StockMonitor stockMonitor = StockMonitor.getInstance();
		EventService eventService =  EventService.getInstance();
		eventService.subscribe(EventType.STOCKCREATION, stockBroker);
		eventService.subscribe(EventType.STOCKCREATION, stockMonitor);
		eventService.subscribe(EventType.PRICEUPDATE, stockMonitor);
		stockMonitor.display();
		
		Stock appleStock = new Stock("AAPL", new Money(275.5f));
		Stock amazonStock = new Stock("AMZN", new Money(919.07f));
		Stock teslaStock = new Stock("TSLA", new Money(400.33f));
		appleStock.addStatus(new Money(173.5f));
		amazonStock.addStatus(new Money(1119.07f));
		teslaStock.addStatus(new Money(300.33f));		
		
	}
	
}
