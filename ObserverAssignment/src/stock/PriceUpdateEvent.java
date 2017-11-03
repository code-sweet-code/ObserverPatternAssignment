package stock;

public class PriceUpdateEvent extends Event {
	private StockStatus updatedStatus;

	public PriceUpdateEvent(StockStatus status) {
		super(EventType.PRICEUPDATE);
		this.updatedStatus = status;
	}

	public StockStatus getUpdatedStatus(){
		return updatedStatus;
	}
}
