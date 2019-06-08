package prototype.json.parsing;

public class NoResultsMessage extends ResponseMessage {
	
	public NoResultsMessage (String message) {
		super(80, "noresults", message);
	}
}