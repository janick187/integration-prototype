package prototype.json.parsing;

public class SearchResult {
	
	private String name;
	private String address;
	private String lat;
	private String lng;
	private String rating;
	private String open_now;

	public SearchResult (Candidates cand) {
		this.name = cand.getName();
		this.address = cand.getFormatted_address();
		this.lat = cand.getGeometry().getLocation().getLat();
		this.lng = cand.getGeometry().getLocation().getLng();
		this.rating = cand.getRating();
		this.open_now = cand.getOpening_hours().getOpen_now();
	}
}
