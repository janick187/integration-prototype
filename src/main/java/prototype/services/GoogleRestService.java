package prototype.services;

import java.util.HashMap;
import java.util.Map.Entry;

import prototype.json.parsing.CallApiException;
import prototype.config.*;

/**
 * <b>Purpose:</b> Get certain information from one of the Google API's.
 * @author Janick Spirig
 */
public class GoogleRestService extends RestCallService {
	
	// url over which the google api is accessible
	private String googleapiurl;
	
	// string key which is necessary for the authentication
	private String googleapikey; 
	
	/**
	 * <b>Purpose:</b> Constructor which creates the GoogleRestService object as well as the parent object RestClient
	 */
	public GoogleRestService () {
		// extract google credentials from Config file
		googleapiurl = GlobalConfigService.getInstance().getConfig().get("GoogleApiUrl");
		googleapikey = GlobalConfigService.getInstance().getConfig().get("GoogleApiKey");
	}
	
	/**
	 * <b>Purpose:</b> Get information from google by a call to specific Google API
	 * @param type the Google API which should provide the information
	 * <br>example<br>
	 * type: distancematrix
	 * @param params a hashmap which contains all parameters that should be included in the call of the Google API (type)
	 * <br>example<br>
	   params: origins (e.g. 31+Fuhrstrasse+Waedenswil), destinations (e.g. 124a+Hardturmstrasse+Zuerich)
	   <br>	
	 * @return a <code>string</code> which contains the answer from the Google API in JSON format
	 */
	public String callGoogleApi (String type, HashMap<String, String> params) throws CallApiException {
		
		
		// create substring of URL -> Help ID: 7, Source: https://developers.google.com/maps/documentation/distance-matrix/intro
		String url = googleapiurl + type + "/json?";
		
		// loop over params hashmap and add each parameter to the URL
		for (Entry<String, String> entry : params.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    String parameter = key + "=" + value;
		    url = url + parameter + "&";
		}
		
		// add the Google API key at the end for proper authentication -> Help ID: 7, Source: https://developers.google.com/maps/documentation/distance-matrix/intro
		url = url + "key=" + googleapikey;
		
		// send REST call to google
		String answer = this.sendGetRequest(url, false, "");
		
		return answer;
	}
}
