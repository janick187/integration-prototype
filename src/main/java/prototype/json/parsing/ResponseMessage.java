package prototype.json.parsing;

import java.sql.Timestamp;

public class ResponseMessage {
	
	private int responsecode;
	private String status; 
	private String message;
	private Timestamp timestamp;
	
	public ResponseMessage (int responsecode, String status, String message) {
		this.responsecode = responsecode;
		this.status = status;
		this.message = message;
		this.timestamp = new Timestamp(System.currentTimeMillis()); 		
	}
	
}
