package AvailabilityDemand;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Customer implements iSubscriber {

	private String customerName; //customer's name

	private List<Subscription> subscriptions; //list of users subscriptions

	// private Broker broker;

	// private Subscription[] subscription;

	public Customer(String customerName) 
	{
		this.customerName = customerName;
		subscriptions = new ArrayList<Subscription>();
	}

	@Override
	/***
	 * This function calls the createSubscription method which checks if this is a valid subscription
	 */
	public boolean subscribe(String location, Date from, Date to) 
	{
		return createSubscription(location, from, to);
	}
	@Override
	public boolean unSubscribe(String location, Date from, Date to) {
		return deleteSubscription(location, from, to);
	}

	/***
	 * 
	 * @param location the location of the subscription
	 * @param from the Date from which subscription should start
	 * @param to the Date to which the subscription should end
	 * @return true or false depending on whether sub can be created
	 */
	public boolean createSubscription(String location, Date from, Date to)
	{
		Calendar cal = new GregorianCalendar(2021, Calendar.NOVEMBER, 27);
		Date d1 = cal.getTime();

		//check if its before epoch (11/27/2021)
		if(from.before(d1) || to.before(d1))
		{
			return false;
		}
		//so date can't be before 11/27

		if(from.after(to) || to.equals(from))
		{
			return false;
		}

		if(subscriptions.isEmpty())
		{
			subscriptions.add(new Subscription(location, from, to));
			return true;
		}
		// we've checked both empty and if wrong date, now we're looking for:
		// 	******* subscription doesn't exist or overlaps? ******

		//assumption : customer can have multiple overlapping stays at same location
		// boolean locationSame = false;

		for(Subscription s : this.subscriptions)
		{
			if(s.getLocation().equals(location)) //same location
			{
				// locationSame = true;
				if(s.getFrom().equals(from) && s.getTo().equals(to)) //same dates
				{
					return false;
				}
			}
		}
		subscriptions.add(new Subscription(location, from, to));
		return true;
	}

	/**
	 * 
	 * @param location the location of the unsubscription
	 * @param fromthe Date from which unsubscription should start
	 * @param to the Date to which the subscription should end
	 * @return true or false depending on whether user can be unsubscribed
	 */
	public boolean deleteSubscription(String location, Date from, Date to) 
	{
		int i=0;
		for(Subscription s : subscriptions)
		{
			if(s.getLocation().equals(location) && s.getFrom().equals(from) && s.getTo().equals(to))
			{
				i = subscriptions.indexOf(s);
				subscriptions.remove(i);
				return true;
			}
			//i++;
		}
		return false;
	}

	/**
	 * 
	 * @return List of subscriptions that the user has
	 */
	public List<Subscription> getSubscriptions() {
		return subscriptions;
		//return null
	}
	/**
	 * 
	 * @return customer's name
	 */
	public String getCustomer() {
		return this.customerName;
		//return null;
	}

}
