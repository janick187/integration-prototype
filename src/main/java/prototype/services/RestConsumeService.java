package prototype.services;

import spark.Request;
import spark.Response;

/**
 * <b>Purpose:</b> Centralised service which processes REST requests, returns responses and handles occurring exceptions in a structured way.
 * @author Janick Spirig
 */
public class RestConsumeService extends HelperService {
	
	/**
	 * <b>Purpose:</b> Process different incoming REST requests
	 * @param request the Spark request object
	 * @param response the Spark response object
	 * @param method the method which defines which execution should be started
	 * @return a <code>string</code> value
	 */
	public String consumeRestCall (Request request, Response response, String method) {
		
		System.out.println("Received request with command " + method +  ". From " + request.raw().getRemoteAddr());
		
		String result ="";
		
		switch (method) {
			// check if method equals "search". This means that a search request has been submitted by the user.
			case "search": 
				try {
					
				} catch (Exception e) {			
					// inform caller about internal server error by setting the HTTP response code to 500
					response.status(500);
					// return custom-friendly exception message
					result = this.returnExceptionMessage(e);
				} finally {
					request = null;
				}
			break;
		}			
		return result;
	}
}