package stock;

public abstract class Event {
	protected EventType type;
	
	protected Event(EventType type) {
		this.type = type;
	}

	public EventType getEventType(){
		return type;
	}
}
