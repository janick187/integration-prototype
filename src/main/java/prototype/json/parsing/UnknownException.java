package prototype.json.parsing;

	public class UnknownException extends Exception {
		
		private ErrorResponseMessage errorresponsemessage;
		
		public UnknownException (Exception e, String message) {
			int exceptioncode = 50;
			String exceptionmessage = message;
			String exceptiondetails = e.getMessage();
			errorresponsemessage = new ErrorResponseMessage(exceptioncode, exceptionmessage, exceptiondetails);
		}

		public ErrorResponseMessage getErrorResponseMessage () {
			return errorresponsemessage;
		}
	}