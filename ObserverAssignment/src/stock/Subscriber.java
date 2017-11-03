package stock;

import java.util.Random;

public abstract class Subscriber {
	protected int id;
	protected Subscriber(){
		Random random = new Random();
		this.id = random.nextInt();
	}
	public abstract void inform(Event e);
	public int getId(){
		return id;
	}

	@Override
	public boolean equals(Object obj){
		if (obj == null || !Subscriber.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
		Subscriber other = (Subscriber) obj;
	    if(this.id != other.id){
	    	return false;
	    }else{
	    	return true;
	    }
		
	}
}
