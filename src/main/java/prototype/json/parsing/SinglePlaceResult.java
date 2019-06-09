package prototype.json.parsing;

public class SinglePlaceResult
{
    private String user_ratings_total;

    private String formatted_address;
    
    private String price_level;
    
    private String scope;

    private String name;

    private Opening_hours opening_hours;

    private String rating;

    private Address_components[] address_components;

    private String formatted_phone_number;

    private String url;

    public String getUser_ratings_total ()
    {
        return user_ratings_total;
    }

    public void setUser_ratings_total (String user_ratings_total)
    {
        this.user_ratings_total = user_ratings_total;
    }

    public String getScope ()
    {
        return scope;
    }

    public void setScope (String scope)
    {
        this.scope = scope;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Opening_hours getOpening_hours ()
    {
        return opening_hours;
    }

    public void setOpening_hours (Opening_hours opening_hours)
    {
        this.opening_hours = opening_hours;
    }

    public String getRating ()
    {
        return rating;
    }

    public void setRating (String rating)
    {
        this.rating = rating;
    }

    public Address_components[] getAddress_components ()
    {
        return address_components;
    }

    public void setAddress_components (Address_components[] address_components)
    {
        this.address_components = address_components;
    }

    public String getFormatted_phone_number ()
    {
        return formatted_phone_number;
    }

    public void setFormatted_phone_number (String formatted_phone_number)
    {
        this.formatted_phone_number = formatted_phone_number;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public String getPrice_level() {
		return price_level;
	}

	public void setPrice_level(String price_level) {
		this.price_level = price_level;
	}
}
