package stock;

public abstract class Publisher {

	public Publisher() {
	}

	protected void publish(Event e){
		EventService service = EventService.getInstance();
		service.publish(e);
	}

}
