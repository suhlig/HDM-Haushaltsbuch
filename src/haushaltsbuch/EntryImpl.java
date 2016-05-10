package haushaltsbuch;

import java.util.Date;

public class EntryImpl implements Entry {
	
	@Override
	public long getId() {
		return _id;
	}
	@Override
	public Date getEntryDate() {
		return _entryDate;
	}
	@Override
	public String getSrcDst() {
		return _srcDst;
	}
	@Override
	public String getDescription() {
		return _description;
	}
	@Override
	public double getValue() {
		return _value;
	}
	@Override
	public String getCategory() {
		return _category;
	}
	@Override
	public String getPaymentType() {
		return _paymentType;
	}
	
	private long _id;
	private Date _entryDate;
	private String _srcDst;
	private String _description;
	private double _value;
	private String _category;
	private String _paymentType;
}