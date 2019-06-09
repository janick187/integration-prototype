package prototype.services;

import java.util.*;

import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import prototype.config.Config;
import prototype.json.parsing.Results;
import prototype.json.parsing.PlacesSearch;

/**
 * <b>Purpose:</b> Create a global configuration object over which all other services can be accessed so that not every execution new service objects are created as this would use a lot of computer resources. Also it stores all configuration details from the config file. This Service will be initialised one time at the beginning.
 * @author Janick Spirig
 */
public class GlobalConfigService extends HelperService {

	// Variables
	private OkHttpClient okhttpclient;
	private Builder reqbuilder;
	private Timer timer;
	private TimerTask hourlymatching;
	private TimerTask dailyemailsend;
	private TimerTask hourlydbtoken;
		
	// Maps
	private HashMap<String, Class> classMap;
	private HashMap<String, String> config;
	
	
	// Services
	private RestConsumeService restconsumeservice;
	private GoogleRestService googlerestservice;
	
	// create singleton object so that it is ensured that this object only exist once -> Help ID: 16, Source: https://www.geeksforgeeks.org/singleton-class-java/
	
	private static GlobalConfigService instance = null;
	private GlobalConfigService() {}
	
    public static synchronized GlobalConfigService getInstance() {
        if (GlobalConfigService.instance == null) {
            GlobalConfigService.instance = new GlobalConfigService();
        }
        return GlobalConfigService.instance;
    }
	
    /**
	 * <b>Purpose:</b> Initialise all global variables. A variable is global when it is used within multiple other classes.
	 */
	public void setupVariables() {
		this.config = new Config().getAllProperties();
		this.okhttpclient = new OkHttpClient();
		this.reqbuilder = new Builder();
		this.timer = new Timer ();
		this.restconsumeservice = new RestConsumeService();
		this.googlerestservice = new GoogleRestService();
	}

	/**
	 * <b>Purpose:</b> Initialise all global Maps. A map is global when it is used within multiple other classes.
	 */
	public void setupMaps () {
		// create class maps for GSON methods -> Help ID: 17, Source: apfelbaum-mockbackend
		this.classMap = new HashMap<String,Class>();
		classMap.put("placessearcharray", PlacesSearch[].class);
		classMap.put("placessearch", PlacesSearch.class);
		classMap.put("candidatesarray", Results[].class);
		classMap.put("candidates", Results.class);
	}
	public void setupLists() {
		
	}
	
	public HashMap<String, String> getConfig() {
		return config;
	}

	public OkHttpClient getOkhttpclient() {
		return okhttpclient;
	}

	public Builder getReqbuilder() {
		return reqbuilder;
	}

	public HashMap<String, Class> getClassMap() {
		return classMap;
	}

	public RestConsumeService getRestconsumeservice() {
		return restconsumeservice;
	}

	public GoogleRestService getGooglerestservice() {
		return googlerestservice;
	}
	
}
