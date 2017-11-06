package stock;

public class StockStatus {
	private Stock stock;
	private Money price;
	private DateTime date;
	public StockStatus(Stock stock, Money price, DateTime date) {
		this.stock = stock;
		this.price = price;
		this.date = date;
	}

	public Stock getStock() {
		return stock;
	}

	public Money getPrice() {
		return price;
	}
	
	public DateTime getDateTime(){
		return date;
	}

}
