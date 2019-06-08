package prototype.json.parsing;
/**
 * If an error occurred during parsing of a JSON string to a Java Object.
 */
public class JsonParseException extends Exception {
	
	private ErrorResponseMessage errorresponsemessage;
	
	public JsonParseException (Exception e, String message) {
		int exceptioncode = 45;
		String exceptionmessage = message;
		String exceptiondetails = e.getMessage();
		errorresponsemessage = new ErrorResponseMessage(exceptioncode, exceptionmessage, exceptiondetails);
	}

	public ErrorResponseMessage getErrorResponseMessage () {
		return errorresponsemessage;
	}
}
