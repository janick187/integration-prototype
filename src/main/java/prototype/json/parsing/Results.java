package prototype.json.parsing;

public class Results
{
    private String formatted_address;

    private String name;

    private Opening_hours opening_hours;

    private String rating;

    private Geometry geometry;
    
    private String place_id;

    private Photos[] photos;

    public String getFormatted_address ()
    {
        return formatted_address;
    }

    public void setFormatted_address (String formatted_address)
    {
        this.formatted_address = formatted_address;
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

    public Geometry getGeometry ()
    {
        return geometry;
    }

    public void setGeometry (Geometry geometry)
    {
        this.geometry = geometry;
    }

    public Photos[] getPhotos ()
    {
        return photos;
    }

    public void setPhotos (Photos[] photos)
    {
        this.photos = photos;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [formatted_address = "+formatted_address+", name = "+name+", opening_hours = "+opening_hours+", rating = "+rating+", geometry = "+geometry+", photos = "+photos+"]";
    }

	public String getPlace_Id() {
		return place_id;
	}

}
