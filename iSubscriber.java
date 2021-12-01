package AvailabilityDemand;
import java.util.Date;

public abstract interface iSubscriber {

	public abstract boolean subscribe(String location, Date from, Date to);

	public abstract boolean unSubscribe(String location, Date from, Date to);

}
