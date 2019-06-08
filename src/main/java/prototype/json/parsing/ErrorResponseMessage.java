package prototype.json.parsing;

import java.sql.Timestamp;

public class ErrorResponseMessage {

	private int exceptioncode;
	private String custommessage;
	private String exceptionmessage;
	private String exceptiondetails;
	private Timestamp timestamp;
	
	public ErrorResponseMessage (int exceptioncode, String exceptionmessage, String exceptiondetails) {
		this.exceptioncode = exceptioncode;
		this.exceptionmessage = exceptionmessage;
		this.exceptiondetails = exceptiondetails;
		this.timestamp = new Timestamp(System.currentTimeMillis()); 	
	}

	public void setCustommessage(String custommessage) {
		this.custommessage = custommessage;
	}

	public void setExceptionmessage(String exceptionmessage) {
		this.exceptionmessage = exceptionmessage;
	}

	public String getExceptionMessage () {
		return exceptionmessage;
	}
	
}
