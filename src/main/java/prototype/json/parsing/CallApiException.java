package prototype.json.parsing;
/**
 *  In case an error occurred during API call.
 */
public class CallApiException extends Exception {

	private ErrorResponseMessage errorresponsemessage;
	private int responsecode;
	
	public CallApiException (Exception e, String message, int responsecode) {
		int exceptioncode = 44;
		this.responsecode = responsecode;
		String exceptionmessage = message;
		String exceptiondetails = e.getMessage();
		errorresponsemessage = new ErrorResponseMessage(exceptioncode, exceptionmessage, exceptiondetails);
	}

	public ErrorResponseMessage getErrorResponseMessage () {
		return errorresponsemessage;
	}
	public int getResponseCode () {
		return responsecode;
	}
}
