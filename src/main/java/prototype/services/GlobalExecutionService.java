package prototype.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import prototype.json.parsing.CallApiException;
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
    
    public String searchPlace (Request req) throws CallApiException {
    	
    	// create map to store all the  parameters which are required to call the Google API
    	HashMap<String, String> params = new HashMap<String, String>();
    	
    	// String formatedsearchterm = req.params("searchterm").replaceAll("\\s+","%20");
    	// add parameters to map
    	params.put("input", req.params("searchterm"));
    	params.put("inputtype", "textquery");
    	params.put("fields", "photos,formatted_address,name,rating,opening_hours,geometry");

    	String apitype = "place/findplacefromtext";
 
    	String answer = GlobalConfigService.getInstance().getGooglerestservice().callGoogleApi(apitype, params);
    	
    	
    	return answer;
    }
    
}