package prototype.json.parsing;

import prototype.services.GlobalExecutionService;

public class PlaceDetailsSearchResult {

	private String name;
	private String phone;
	private String rating;
	private String number_of_ratings;
	private String price_level;
	private String url;
	private String address;
	
	private String open_now;
	
	private String monday_open;
	private String monday_close;
	private String tuesday_open;
	private String tuesday_close;
	private String wednesday_open;
	private String wednesday_close;
	private String thursday_open;
	private String thursday_close;
	private String friday_open;
	private String friday_close;
	private String saturday_open;
	private String saturday_close;
	private String sunday_open;
	private String sunday_close;
	
	public PlaceDetailsSearchResult (SinglePlaceResult result) {
			try {
				this.name = result.getName();
				this.phone = result.getFormatted_phone_number();
				this.rating = result.getRating();
				this.number_of_ratings = result.getUser_ratings_total();
				this.price_level = result.getPrice_level();
				this.url = result.getUrl();
				this.address = result.getFormatted_address();
				
				this.open_now = result.getOpening_hours().getOpen_now();
				
				setPeriods(result);
			} catch (Exception exc) {
				System.out.println("At least one attribute is empty");
			}
	}
	
	public void setPeriods (SinglePlaceResult result) {
		
		for (Periods period : result.getOpening_hours().getPeriods()) {
		
			switch (period.getOpen().getDay()) {
			
				case "0":
					monday_open = period.getOpen().getTime();
				break;
				case "1":
					tuesday_open = period.getOpen().getTime();
				break;
				case "2":
					wednesday_open = period.getOpen().getTime();
				break;
				case "3":
					thursday_open = period.getOpen().getTime();
				break;
				case "4":
					friday_open = period.getOpen().getTime();
				break;
				case "5":
					saturday_open = period.getOpen().getTime();
				break;
				case "6":
					sunday_open = period.getOpen().getTime();
			}
			
			switch (period.getClose().getDay()) {
				
				case "0":
					monday_close = period.getClose().getTime();
				break;
				case "1":
					tuesday_close = period.getClose().getTime();
				break;
				case "2":
					wednesday_close = period.getClose().getTime();
				break;
				case "3":
					thursday_close = period.getClose().getTime();
				break;
				case "4":
					friday_close = period.getClose().getTime();
				break;
				case "5":
					saturday_close = period.getClose().getTime();
				break;
				case "6":
					sunday_close = period.getClose().getTime();
			}	
		}
	}
}
