package haushaltsbuch;

import java.math.BigDecimal;
import java.util.Date;

public interface Entry
{
  String getCategory();

  String getDescription();

  Date getEntryDate();

  String getId();

  String getPaymentType();

  String getSrcDst();

  BigDecimal getValue();
}
