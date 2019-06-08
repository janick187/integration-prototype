package prototype.json.parsing;

public class SuccessMessage extends ResponseMessage {

	public SuccessMessage (String message) {
		super(20, "ok", message);
	}
}