package prototype.json.parsing;

public class SearchResult {
	
	private String id;
	private String name;
	private String address;
	private String lat;
	private String lng;
	private String rating;
	private String open_now;

	public SearchResult (Results result) {
		try {
			this.id = result.getId();
			this.name = result.getName();
			this.address = result.getFormatted_address();
			this.lat = result.getGeometry().getLocation().getLat();
			this.lng = result.getGeometry().getLocation().getLng();
			this.rating = result.getRating();
			this.open_now = result.getOpening_hours().getOpen_now();
		} catch (NullPointerException excep) {
			System.out.println("Could not process the place " + this.id + " because at least one attribute is empty.");
		}
	}
}
