package stock;

import java.util.Vector;

public class Stock extends Publisher {
	private String stockSymbol;
	private Vector<StockStatus> stockStats;
	
	public Stock(String symbol, Money price) {
		this.stockSymbol = symbol;
		stockStats = new Vector<StockStatus>();
		StockStatus creationStat = new StockStatus(this, price, new DateTime());
		stockStats.add(0, creationStat); // acts like a stack
		// create stock creation event and publish notification.
		StockCreationEvent scEvent = new StockCreationEvent(this);
		super.publish(scEvent);
	}

	public StockStatus getCurrentStatus() {
		return stockStats.firstElement(); // always get the one at index 0
	}
	
	/*
	 * getStatus by DateTime may be problematic!
	 */
	public StockStatus getStatus(DateTime d){
		for (StockStatus ss : stockStats) {
	        if (ss.getDateTime().getTime().equals(d.getTime())) {
	            return ss;
	        }
	    }
		return null;
	}
	
	public void addStatus(Money price){
		StockStatus ss = new StockStatus(this, price, new DateTime());
		stockStats.add(0, ss); // always add at index 0 for it to behave like a stack
		// create price update event and publish notification.
		PriceUpdateEvent puEvent = new PriceUpdateEvent(ss);
		super.publish(puEvent);
	}

	public String getSymbol() {
		return this.stockSymbol;
	}
	
	public String toString(){
		String print = "Stock info: "
		+ stockSymbol + System.lineSeparator() + 
		"with Latest Status: " + getCurrentStatus().toString();
		return print;
	}

}
