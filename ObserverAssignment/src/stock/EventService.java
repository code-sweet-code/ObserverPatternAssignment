package stock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventService {
	private Map<EventType, List<Subscriber>> subscribedMap;
	private static EventService instance = new EventService();
	
	private EventService() {
		subscribedMap = new HashMap<EventType, List<Subscriber>>();
	}

	public synchronized static EventService getInstance(){
		if(instance == null){
			instance = new EventService();
		}
		return instance;
	}
	
	public void subscribe(EventType type, Subscriber subscriber){
		List<Subscriber> subscriberList = getSubscriberList(type);
		addSubscriber(subscriber, subscriberList);
	}
	

	public void unsubscribe(EventType type, Subscriber subscriber){
		List<Subscriber> subscriberList = getSubscriberList(type);
		removeSubscriber(subscriber, subscriberList);
	}
	
	
	public void publish(Event event){
		List<Subscriber> subscriberList = getSubscriberList(event.getEventType());
		notifySubscribers(subscriberList, event);
	}
	
	private void notifySubscribers(List<Subscriber> subscriberList, Event event) {
		Iterator<Subscriber> iterator = subscriberList.iterator();
		while(iterator.hasNext()){
			Subscriber subscriber = iterator.next();
			subscriber.inform(event);
		}
	}

	private void addSubscriber(Subscriber subscriber, List<Subscriber> subscriberList) {
		if(!subscriberList.contains(subscriber)){
			subscriberList.add(subscriber);
		}
	}

	private void removeSubscriber(Subscriber subscriber, List<Subscriber> subscriberList) {
		if(subscriberList.contains(subscriber)){
			subscriberList.remove(subscriber);
		}
	}
	
	private List<Subscriber> getSubscriberList(EventType type) {
		if(!subscribedMap.containsKey(type)){
			addEventType(type);
		}
		List<Subscriber> list = subscribedMap.get(type);
		return list;
	}

	private void addEventType(EventType type){
		List<Subscriber> list = new LinkedList<Subscriber>();
		subscribedMap.put(type, list);
	}
}
