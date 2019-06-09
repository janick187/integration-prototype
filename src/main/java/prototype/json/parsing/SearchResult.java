package prototype.json.parsing;

public class SearchResult implements Comparable {
	
	private String id;
	private String name;
	private String address;
	private String lat;
	private String lng;
	private double rating;
	private String open_now;

	public SearchResult (Results result) {
		try {
			this.id = result.getPlace_Id();
			this.name = result.getName();
			this.address = result.getFormatted_address();
			this.lat = result.getGeometry().getLocation().getLat();
			this.lng = result.getGeometry().getLocation().getLng();
			this.rating = Double.parseDouble(result.getRating());
			this.open_now = result.getOpening_hours().getOpen_now();
		} catch (NullPointerException excep) {
			System.out.println("Could not process the place " + this.id + " because at least one attribute is empty.");
		}
	}
	
	public double getRating() {
		return this.rating;
	}

	@Override
	public int compareTo(Object o) {
		return new Double(((SearchResult) o).getRating()).compareTo(rating);
	}
}
