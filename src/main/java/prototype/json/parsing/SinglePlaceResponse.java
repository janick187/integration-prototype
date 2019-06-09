package prototype.json.parsing;

public class SinglePlaceResponse
{
    private SinglePlaceResult result;

    private String status;

    public SinglePlaceResult getResult ()
    {
        return result;
    }

    public void setResult (SinglePlaceResult result)
    {
        this.result = result;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }
}
