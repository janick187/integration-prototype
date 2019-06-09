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

	// create singleton object so that it is ensured that this object only exist once -> Help ID: 16, Source: https://www.geeksforgeeks.org/singleton-class-java/
	
	private static GlobalExecutionService instance = null;
	private GlobalExecutionService() {}
	
    public static synchronized GlobalExecutionService getInstance() {
    	if (GlobalExecutionService.instance == null) {
            GlobalExecutionService.instance = new GlobalExecutionService();
        }
        return GlobalExecutionService.instance;
    }
    
    public String searchPlace (Request req) throws CallApiException, JsonParseException {
    	
    	// create map to store all the  parameters which are required to call the Google API
    	HashMap<String, String> params = new HashMap<String, String>();
    	
    	// String formatedsearchterm = req.params("searchterm").replaceAll("\\s+","%20");
    	// add parameters to map
    	params.put("query", req.params("searchterm"));
    	params.put("fields", "formatted_address,name,rating,opening_hours,geometry");
    	String apitype = "place/textsearch";
 
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
    		// sort list based on rating
    		Collections.sort(allsearchresults);
    	} catch (Exception e) {
    		System.out.println("Could not sort list");
    	}
    	
    	// create a stringbuilder so that multiple product object data can be returned in the same response to the user later -> Help ID: , Source: https://stackoverflow.com/questions/12899953/in-java-how-to-append-a-string-more-efficiently
    	StringBuilder stringbuilder = new StringBuilder().append("[");
    	
    	// add each product which fulfils the matching criteria to the response
    	for (SearchResult sr : allsearchresults) {
    		// add search result object to the response
    		stringbuilder.append(this.toJson(sr) + ",");
    	}
    			
    	// close response content so that it is in valid format
    	String jsonresponse = closeJsonResponse(stringbuilder);
    	
    	return jsonresponse;
    }

    public String getPlaceDetails (Request req) throws JsonParseException, CallApiException {
    	
    	// create map to store all the  parameters which are required to call the Google API
    	HashMap<String, String> params = new HashMap<String, String>();
    	    	
    	// add parameters to map
    	params.put("placeid", req.params("placeid"));
    	params.put("fields", "formatted_address,name,rating,opening_hours,rating,formatted_phone_number,opening_hours,address_component,scope,url,price_level,user_ratings_total");
    	String apitype = "place/details";
 
    	SinglePlaceResponse googleanswer = (SinglePlaceResponse) this.fromJsonObject(GlobalConfigService.getInstance().getGooglerestservice().callGoogleApi(apitype, params), "singleplaceresponse");
    	
    	PlaceDetailsSearchResult pdsr = new PlaceDetailsSearchResult (googleanswer.getResult());
    	
    	return this.toJson(pdsr);
    }
}