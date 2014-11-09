package MIQ;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class MIQ_Algo {
	    private int min_util=27;
	    public static HashMap<String,Integer> TWU=new HashMap<String,Integer>();
	    public ArrayList<ItemSet> ItemSet=new ArrayList<ItemSet>();
	    public HashMap<String,Integer> min=new HashMap<String,Integer>();
	    public HashMap<String,Integer> max=new HashMap<String,Integer>();
	    public HashMap<String,Integer> real=new HashMap<String,Integer>();
	   public void first_scan(String args)
	   {String read;
    try {
		BufferedReader br=new BufferedReader(new FileReader(args));
		while((read=br.readLine())!=null)
		{
			String[] split=read.split(":");
			String itemset[]=split[0].split(" ");
			String utility[]=split[2].split(" ");
			
			ItemSet i=new ItemSet(itemset,Integer.parseInt(split[1]));
			ItemSet.add(i);
			for(int l=0;l<itemset.length;l++)
			{
				 Integer t=TWU.get(itemset[l]);
				if(t==null)
				{
					t=Integer.parseInt(split[1]);
				}
				else
				{
					t=t+Integer.parseInt(split[1]);
				}
				TWU.put(itemset[l],t);
			}
		}
	} catch (Exception e) {
		
		e.printStackTrace();
	}
    try {
    	
		BufferedReader br=new BufferedReader(new FileReader(args));
		Tree r=new Tree();
		while((read=br.readLine())!=null)
		{
			String[] split=read.split(":");
			String itemset[]=split[0].split(" ");
			String utility[]=split[2].split(" ");
			String count[]=split[3].split(" ");
			ArrayList<Item> insert=new ArrayList<Item>();
			for(int l=0;l<itemset.length;l++)
			{
				String item=itemset[l]; 
				Integer u=Integer.parseInt(utility[l]);
				if(TWU.get(item)>=min_util)
				{
					Item i=new Item(item,u);
					insert.add(i);
					Integer minutil=min.get(itemset[l]);
					Integer maxutil=max.get(itemset[l]);
					Integer realutil=real.get(itemset[l]);
					if(minutil == null)
					{
						minutil=u;
					}
					else
					{
						if(minutil<u)
						{
							minutil=u;
						}
					}
					min.put(itemset[l], minutil);
					if(maxutil == null)
					{
						maxutil=u;
					}
					else
					{
						if(maxutil<u)
						{
							maxutil=u;
						}
					}
					max.put(itemset[l], maxutil);
					if(realutil == null)
					{
						realutil=u;
					}
					else
					{
						realutil=realutil+u;
					}
					real.put(itemset[l], realutil);
					
					
				}
			}
		
			r.insert_transaction(insert, count);
			/*Collections.sort(insert, new Comparator<Item>() {
				public int compare(Item o1, Item o2) {
					return compareAnItem(o1.name, o2.name,TWU);
				}
			});*/
		}
	     r.createHeaderList(TWU);
	     //r.local_tree();
	} catch (Exception e) {
		
		e.printStackTrace();
	}

	   }
	   public int compareAnItem(String item1, String item2, HashMap<String, Integer> tWU) {
			int c = tWU.get(item2) - tWU.get(item1);
			return c;
		}
}
