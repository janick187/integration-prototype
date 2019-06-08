package prototype.json.parsing;

/**
 * If from and/or until dates submitted in the searchrequest or stored on the product are wrongly formatted.
 */
public class DateConvertionException extends Exception {

	private ErrorResponseMessage errorresponsemessage;
	
	public DateConvertionException (Exception e, String message) {
		int exceptioncode = 47;
		String exceptionmessage = message;
		String exceptiondetails = e.getMessage();
		errorresponsemessage = new ErrorResponseMessage(exceptioncode, exceptionmessage, exceptiondetails);
	}

	public ErrorResponseMessage getErrorResponseMessage () {
		return errorresponsemessage;
	}
}
