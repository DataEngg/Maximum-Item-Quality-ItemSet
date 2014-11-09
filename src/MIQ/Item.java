package MIQ;
import java.util.*;
public class Item {
    String name=null;
	int utility=0;
	
	public Item(String name,int utility)
	{
		this.name=name;
		this.utility=utility;
	}
	public void setUtility(int utility)
	{
		this.utility=utility;
	}
	public int getUtility()
	{
		return this.utility;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return this.name;
	}
	public static Comparator<Item> com=new Comparator<Item>(){
		
	

	@Override
	public int compare(Item arg0, Item arg1) {
		// TODO Auto-generated method stub
		return 0;
	}};
}
