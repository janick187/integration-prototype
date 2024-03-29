package prototype.main;

import static spark.Spark.*;
import spark.Spark;

import prototype.services.GlobalConfigService;
import prototype.services.RestConsumeService;

public class App 
{
    public static void main( String[] args )
    {
    	
		// default configuration
		int hp = 8080;    
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			hp   = Integer.parseInt(processBuilder.environment().get("PORT"));      
		}
		Spark.port(hp);
		
		// setup global config service
		GlobalConfigService.getInstance().setupMaps();
		GlobalConfigService.getInstance().setupVariables();
		GlobalConfigService.getInstance().setupLists();
		
		RestConsumeService restconsumeservice = GlobalConfigService.getInstance().getRestconsumeservice();
		
		// search for places by search term in text format
		get("/searchplaces/:searchterm", (req, res) -> restconsumeservice.consumeRestCall(req, res, "searchplace"));
		
		get("/placedetails/:placeid", (req, res) -> restconsumeservice.consumeRestCall(req, res, "getplace")); 
		
		get("/hello", (req, res) -> "Hello you nice guy!!!");
    }
}