package prototype.services;

import java.util.ArrayList;
import java.util.Collections;

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
    
}