package prototype.json.parsing;

/**
 * If an error occurred while calculating the distance using the Google API.
 */
public class GoogleApiException extends Exception {

private ErrorResponseMessage errorresponsemessage;
	
	public GoogleApiException (Exception e, String message) {
		int exceptioncode = 48;
		String exceptionmessage = message;
		String exceptiondetails = e.getMessage();
		errorresponsemessage = new ErrorResponseMessage(exceptioncode, exceptionmessage, exceptiondetails);
	}

	public ErrorResponseMessage getErrorResponseMessage () {
		return errorresponsemessage;
	}
}
