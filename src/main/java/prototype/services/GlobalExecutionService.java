package prototype.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import prototype.json.parsing.CallApiException;
import prototype.json.parsing.Results;
import prototype.json.parsing.JsonParseException;
import prototype.json.parsing.PlaceDetailsSearchResult;
import prototype.json.parsing.PlacesSearch;
import prototype.json.parsing.SearchResult;
import prototype.json.parsing.SinglePlaceResponse;
import spark.Request;

/**
 * <b>Purpose:</b> Create a global execution object over which all executions can be triggered so that execution methods are reusable as they are not part of the main class. This Service will be initialised one time at the beginning.
 * @author Janick Spirig
 */
public class GlobalExecutionService extends HelperService {

	// create singleton object so that it is ensured that this object only exist once -> Source: https://www.geeksforgeeks.org/singleton-class-java/
	
	private static GlobalExecutionService instance = null;
	private GlobalExecutionService() {}
	
    public static synchronized GlobalExecutionService getInstance() {
    	if (GlobalExecutionService.instance == null) {
            GlobalExecutionService.instance = new GlobalExecutionService();
        }
        return GlobalExecutionService.instance;
    }
    
    /**
     * <b>Purpose:</b> Search for places which match with the search-term received
     * @param req the reques received
     * @return a <code>string</code> which contains all places that have been found in the appropriate format
     * @throws CallApiException
     * @throws JsonParseException
     */
    public String searchPlace (Request req) throws CallApiException, JsonParseException {
    	
    	// create map to store all the  parameters which are required to call the Google API
    	HashMap<String, String> params = new HashMap<String, String>();
    	
    	// add all required parameters to the map -> Source: https://developers.google.com/places/web-service/search
    	params.put("query", req.params("searchterm"));
    	params.put("fields", "formatted_address,name,rating,opening_hours,geometry");
    	String apitype = "place/textsearch";
 
    	// get data from google by calling the google api
    	PlacesSearch googleanswer = (PlacesSearch) this.fromJsonObject(GlobalConfigService.getInstance().getGooglerestservice().callGoogleApi(apitype, params), "placessearch");
    	
    	Results[] results = googleanswer.getResults();
    	
    	// list of all searchresults
    	ArrayList<SearchResult> allsearchresults = new ArrayList<SearchResult>();
    	
    	// add each result to the resultlist
    	for (Results result : results) {
    		SearchResult sr = new SearchResult(result);
    		allsearchresults.add(sr);
    	}
    	
    	try {
    		// sort list based on rating (highest rating first as most relevant)
    		Collections.sort(allsearchresults);
    	} catch (Exception e) {
    		// print exception only as the sorting is not a must-criteria
    		e.printStackTrace();
    		System.out.println("Could not sort list");
    	}
    	
    	// create a stringbuilder so that multiple searchresult object data can be returned in the same response to the user later -> Help ID: , Source: https://stackoverflow.com/questions/12899953/in-java-how-to-append-a-string-more-efficiently
    	StringBuilder stringbuilder = new StringBuilder().append("[");
    	
    	// add each searchresult to the response
    	for (SearchResult sr : allsearchresults) {
    		// add search result object to the response
    		stringbuilder.append(this.toJson(sr) + ",");
    	}
    			
    	// close response content so that it is in valid format
    	String jsonresponse = closeJsonResponse(stringbuilder);
    	
    	return jsonresponse;
    }

    /**
     * <b>Purpose:</b> Get details for a single place
     * @param req the request received
     * @return a <code>string</code> which contains all the relevant place details
     * @throws CallApiException
     * @throws JsonParseException
     */
    public String getPlaceDetails (Request req) throws JsonParseException, CallApiException {
    	
    	// create map to store all the  parameters which are required to call the Google API
    	HashMap<String, String> params = new HashMap<String, String>();
    	    	
    	// add all required parameters to the map -> Source: https://developers.google.com/places/web-service/search
    	params.put("placeid", req.params("placeid"));
    	params.put("fields", "formatted_address,name,rating,opening_hours,rating,formatted_phone_number,opening_hours,address_component,scope,url,price_level,user_ratings_total");
    	String apitype = "place/details";
 
    	// get data from google by calling the google api
    	SinglePlaceResponse googleanswer = (SinglePlaceResponse) this.fromJsonObject(GlobalConfigService.getInstance().getGooglerestservice().callGoogleApi(apitype, params), "singleplaceresponse");
    	
    	// create searchresult object which can be returned to the user and only contains relevant attributes
    	PlaceDetailsSearchResult pdsr = new PlaceDetailsSearchResult (googleanswer.getResult());
    	
    	return this.toJson(pdsr);
    }
}