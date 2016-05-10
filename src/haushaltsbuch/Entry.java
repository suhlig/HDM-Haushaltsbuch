package haushaltsbuch;

import java.util.Date;

public interface Entry {

	String getPaymentType();
	String getCategory();
	double getValue();
	String getDescription();
	String getSrcDst();
	Date getEntryDate();
	long getId();
}
