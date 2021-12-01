package AvailabilityDemand;
import java.util.Date;

//This class is the subscription that each user can have

public class Subscription {

	private String location;

	private Date from;

	private Date to;

	// private Date from;

	// private Date to;

	public Subscription(String location, Date from, Date to) 
	{
		this.location = location;
		this.from = from;
		this.to = to;
	}

	public String getLocation() {
		//return null;
		return this.location;
	}

	public Date getTo() {
		// return null;
		return this.to;
	}

	public Date getFrom() {
		//return null;
		return this.from;
	}

}
