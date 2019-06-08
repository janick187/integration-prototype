package prototype.services;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import prototype.json.parsing.*;

/**
 * <b>Purpose:</b> This class is consisting of several small methods which provide simple functionality which are re-used across the whole matching application
 */
public abstract class HelperService  {

	/**
	 * <b>Purpose:</b> Convert a string in ISO format into a DateTime object
	 * @param iso the ISO <code>string</code> which should be converted
	 * @return a <code>DateTime</code> object
	 * @throws DateConvertionException
	 */
	public DateTime convertISOtoDate (String iso) throws DateConvertionException  {
		DateTime datetime = null;
		try {
			// convert String to DateTime object -> Help ID: 9, Source: https://stackoverflow.com/questions/6252678/converting-a-date-string-to-a-datetime-object-using-joda-time-library
			org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
			datetime = formatter.parseDateTime(iso);
		} catch (IllegalArgumentException | UnsupportedOperationException exc) {
			DateConvertionException dce = new DateConvertionException(exc, "One of the dates provided is not in correct format! (correct format: yyyy-MM-dd'T'HH:mm:ss.SSSZZ)");
			handleException(dce);
			throw dce;
		}
		return datetime;
	}
	
	/**<br>
	 * <b>Purpose:</b> Round a number to a certain number of decimal digits.
	 * @param value the number which should be rounded
	 * @param precision amount of digits after the point
	 * @return the rounded <code>integer</code> value
	 */
	public double roundDecimal (double value, int precision) {
		// round number -> Help ID: 10, Source: https://stackoverflow.com/questions/22186778/using-math-round-to-round-to-one-decimal-place
		int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}
	
	/**
	 * <b>Purpose:</b> Serialization of <code>java object</code> to <code>string</code>
	 * @param obj the java object
	 * @return a serialized <code>string</code>
	 */
	public String toJson (Object obj) {
		// convert java object to string -> Help ID: 11, Source: apfelbaum-mockbackend
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		gson = null;
		return json;
	}
	
	/**
	 * <b>Purpose:</b> Serialization of multiple json objects in one single json string to an array of java objects
	 * @param json the json string which should be serialized
	 * @param type the name of the object class of which an array should be created
	 * @return an array of java objects
	 * @throws JsonParseException
	 */
	public Object[] fromJsonArray (String json, String type) throws JsonParseException {
		Gson gson = new Gson();
		try {
			// convert multiple json objects in single json string to an array of java objects -> Help ID: 13, Source: http://zetcode.com/java/gson/
			Object[] obj = (Object[]) gson.fromJson(json, GlobalConfigService.getInstance().getClassMap().get(type));
			return obj;
		} catch (JsonSyntaxException jse) {
			JsonParseException jpe = new JsonParseException(jse, "Could not parse json string to object of type " + type);
			handleException(jpe);
			throw jpe;
		} finally {
			gson = null;
		}
	}
	
	/**
	 * <b>Purpose:</b> Deserialization of <code>string</code> to <code>java object</code>
	 * @param json the json string which should be deserialized
	 * @param type the name of the object class to which the json string should be converted to
	 * @return a <code>java object</code>
	 * @throws JsonParseException
	 */
	public Object fromJsonObject(String json, String type) throws JsonParseException {
		Gson gson = new Gson();
		try {
			// convert json string to java object -> Help ID: 12, Source: apfelbaum-mockbackend
			Object obj = gson.fromJson(json, GlobalConfigService.getInstance().getClassMap().get(type));
			return obj;
		} catch (JsonSyntaxException jse) {
			JsonParseException jpe = new JsonParseException(jse, "Could not parse json string to object of type " + type);
			handleException(jpe);
			throw jpe;
		} finally {
			gson = null;
		}
	}
	
	/**
	 * <b>Purpose:</b> Standardised approach to process any kind of Exception that occurred in the matching-application
	 * @param e the Exception object
	 * @param email a boolean value. If true the application owner will be informed about error. If false no information will be sent to the application owner.
	 */
	public void handleException(Exception e) {
		// get the full error message
		String json = returnExceptionMessage(e);
		
		// print full Exception message to console
		System.out.println(json);
		e.printStackTrace();
	}
	
	/**
	 * <b>Purpose:</b> Transform an exception which has been thrown to a json string which can be returned to the calling system / user / printed to the console
	 * @param e the exception object
	 * @return a json string which can be returned to the user
	 */
	public String returnExceptionMessage (Exception e) {
		String jsonmessage = null;
		
		if (e instanceof JsonParseException) {
			jsonmessage = toJson(((JsonParseException) e).getErrorResponseMessage());
		} else if (e instanceof GoogleApiException) {
			jsonmessage = toJson(((GoogleApiException) e).getErrorResponseMessage());
		} else if (e instanceof DateConvertionException) {
			jsonmessage = toJson(((DateConvertionException) e).getErrorResponseMessage());
		} else if (e instanceof IllegalArgumentException) {
			jsonmessage = toJson(((IllegalArgumentException) e).getMessage());
		} else if (e instanceof CallApiException) {
			jsonmessage = toJson(((CallApiException) e).getErrorResponseMessage());
		}
		else {
			// create unknown Exception if exception type is unknown
			e.printStackTrace();
			UnknownException une = new UnknownException(e, "An unknown exception occured. Please check the Log file");
			jsonmessage = toJson(une.getErrorResponseMessage());
		}
		return jsonmessage;
	}
	
	/**
	 * <b>Purpose:</b> Close a JSON array so that it can be used as valid JSON response.
	 * @param stringbuilder the result in JSON format which should be returned
	 * @return a <code>string</code> in JSON format
	 * @throws JsonParseException 
	 */
	public String closeJsonResponse (StringBuilder stringbuilder) {
		
		// delete last element of string which is a comma
		stringbuilder.deleteCharAt(stringbuilder.length()-1);
		
		// check if string contained a comma to ensure that string has content
		if (!stringbuilder.toString().equals("")) {
			// add closing bracket to JSON array
			stringbuilder.append("]");
		} else {
			// create message that no results have been found
			stringbuilder.append(toJson(new NoResultsMessage("No products available for the provided searchrequest!")));
		}
		// return a string in json format, this is either the searchresult or the no results message
		return stringbuilder.toString();
	}
	
	/**
	 * <b>Purpose:</b> Convert a single object and the corresponding value into json format
	 * @param name the attribute name
	 * @param value the attribute value
	 * @return a <code>string</code> in json format
	 */
	public String convertSingleValueToJson (String name, String value) {
		return "{"+ toJson(name) + ":" + toJson(value) + "}";
	}
}