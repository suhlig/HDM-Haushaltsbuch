package test.helpers;

import java.math.BigDecimal;
import java.util.Date;
import haushaltsbuch.persistence.AbstractEntry;

public final class TestEntry extends AbstractEntry
{
  public static final String DEFAULT_ID = "4711";
  public static final int DEFAULT_VALUE = 42;
  public static final String DEFAULT_SOURCE_DESTINATION = "default-source-destination";
  public static final String DEFAULT_PAYMENT_TYPE = "default-payment-type";
  public static final String DEFAULT_DESCRIPTION = "default-description";
  public static final String DEFAULT_CATEGORY = "default-category";

  private String _id = DEFAULT_ID;
  private int _value = DEFAULT_VALUE;
  private Date _entryDate = null;
  private String _srcDest = DEFAULT_SOURCE_DESTINATION;
  private String _paymentType = DEFAULT_PAYMENT_TYPE;
  private String _description = DEFAULT_DESCRIPTION;
  private String _category = DEFAULT_CATEGORY;

  @Override
  public String getCategory()
  {
    return _category;
  }

  @Override
  public String getDescription()
  {
    return _description;
  }

  @Override
  public Date getEntryDate()
  {
    return _entryDate;
  }

  @Override
  public String getId()
  {
    return _id;
  }

  @Override
  public String getPaymentType()
  {
    return _paymentType;
  }

  @Override
  public String getSrcDst()
  {
    return _srcDest;
  }

  @Override
  public BigDecimal getValue()
  {
    return BigDecimal.valueOf(_value);
  }

  public void setCategory(String category)
  {
    _category = category;
  }

  public void setDescription(String description)
  {
    _description = description;
  }

  public void setEntryDate(Date entryDate)
  {
    _entryDate = entryDate;
  }

  public void setId(String id)
  {
    _id = id;
  }

  public void setPaymentType(String paymentType)
  {
    _paymentType = paymentType;
  }

  public void setSrcDest(String srcDest)
  {
    _srcDest = srcDest;
  }

  public void setValue(int value)
  {
    _value = value;
  }

}