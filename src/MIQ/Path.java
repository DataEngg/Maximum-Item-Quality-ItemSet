package MIQ;

import java.util.ArrayList;
import java.util.List;

public class Path {
	public ArrayList<Node> path=new ArrayList<Node>();
	public int utility=0;
	public int support=0;
	public int getSupport()
	{
		return support;
	}
    public int getUtility()
    {
    	return utility;
    }
    public List<Node> getList()
    {
    	return path;
    }
}
