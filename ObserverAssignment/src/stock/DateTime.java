package stock;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
	private String dateTime;
	public DateTime() {
		dateTime = new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss").format(new Date());
	} 
	public String getTime(){
		return dateTime;
	}

}
