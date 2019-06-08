package prototype.json.parsing;
public class PlacesSearch
{
    private Candidates[] candidates;

    private String status;

    public Candidates[] getCandidates ()
    {
        return candidates;
    }

    public void setCandidates (Candidates[] candidates)
    {
        this.candidates = candidates;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [candidates = "+candidates+", status = "+status+"]";
    }
}