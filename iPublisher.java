package AvailabilityDemand;
import java.util.Date;

public abstract interface iPublisher {

	public abstract boolean publish(String providerName, String location, Date availableFrom, Date availableDate);

}
