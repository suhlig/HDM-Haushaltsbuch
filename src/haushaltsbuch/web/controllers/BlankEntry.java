package haushaltsbuch.web.controllers;

import java.math.BigDecimal;
import java.util.Date;
import haushaltsbuch.Entry;

public class BlankEntry implements Entry
{
  @Override
  public String getCategory()
  {
    return "";
  }

  @Override
  public String getDescription()
  {
    return "";
  }

  @Override
  public Date getEntryDate()
  {
    throw new RuntimeException("Will be set by the repository");
  }

  @Override
  public String getId()
  {
    throw new RuntimeException("Will be set by the repository");
  }

  @Override
  public String getPaymentType()
  {
    return "";
  }

  @Override
  public String getSrcDst()
  {
    return "";
  }

  @Override
  public BigDecimal getValue()
  {
    return BigDecimal.ZERO;
  }
}
