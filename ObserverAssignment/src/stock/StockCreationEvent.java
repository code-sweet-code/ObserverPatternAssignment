package stock;

public class StockCreationEvent extends Event {
	private Stock stock;

	public StockCreationEvent(Stock s) {
		super(EventType.STOCKCREATION);
		this.stock = s;
	}
	
	public Stock getStock(){
		return stock;
	}

}
