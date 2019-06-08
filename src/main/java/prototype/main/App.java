package prototype.main;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import prototype.services.GlobalConfigService;
import prototype.services.RestConsumeService;
import spark.Spark;

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
		
		//get("/hello", (req, res) -> restconsumeservice.consumeRestCall(req, res, "search"));
		get("/hello", (req, res) -> "Hello you nice guy!!!");

    }
}