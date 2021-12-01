package AvailabilityDemand;
import java.util.Date;

public class StayPeriod {

	private Date startDate;

	private Date endDate;

	// private Date endDate;

	// private Date startDate;

	public StayPeriod(Date startDate, Date endDate) 
	{
		this.startDate = startDate;
		this.endDate = endDate;
	}
	/**
	 * 
	 * @return date that stay starts
	 */
	public Date getFrom() {
		//return null;
		return this.startDate;
	}

	/**
	 * 
	 * @return date that stay ends
	 */
	public Date getTo() {
		//return null;
		return this.endDate;
	}

}
