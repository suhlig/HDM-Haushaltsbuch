package haushaltsbuch;

import java.math.BigDecimal;
import java.util.Date;

public interface Entry {
	String getPaymentType();
	String getCategory();
	BigDecimal getValue();
	String getDescription();
	String getSrcDst();
	Date getEntryDate();
	String getId();
}
