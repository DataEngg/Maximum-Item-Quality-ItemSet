package MIQ;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Tree {
	static Node root=new Node();
	public static HashMap<String, Node> second = new HashMap<String, Node>();
	public static HashMap<String, Node> first = new HashMap<String, Node>();
	public static ArrayList<String> headerList = null;
	public HashMap<Integer,ArrayList<Node>> paths=new HashMap<Integer,ArrayList<Node>>();
	public static HashMap<String,Integer> profit=new HashMap<String,Integer>();
	public HashMap<Integer,ArrayList<Item>> final_candidates=new HashMap<Integer,ArrayList<Item>>();
	public ArrayList<Path> local_path=new ArrayList<Path>();
	public HashMap<Node,Integer> local_twu=new HashMap<Node,Integer>();
	public HashMap<String,Integer> local_support=new HashMap<String,Integer>();
	public void insert_transaction(ArrayList<Item> trans, String count[])
	
	{
		Node current=root;
		int size=trans.size();
		for(int i=0;i<size;i++)
		{
			String item=trans.get(i).getName();
			int q=Integer.parseInt(count[i]);
			Node child=current.getChild(item);
			if(child==null)
			{
				current=insert(current,item,q);
			}
			else
			{
				child.count++;
				if(child.max<q)
				{
					child.max=q;
				}
				current=child;
			}
		}
		
	}
	public Node insert(Node current,String item,int q)
	{
		Node n=new Node();
		n.itemname=item;
		n.parent=current;
		current.childs.add(n);
		n.count=1;
		n.max=q;
		
		return n;
		
	}
    public void display(Node root)
    {   
    	if(root.childs==null)
    	{
    		return;
    	}
    	else
    	{
    		for(Node childs:root.childs)
    		{
    		   System.out.println(childs.itemname+" "+childs.count+" "+childs.max+" "+childs.parent.itemname+" "+childs.nodeUtility);
    		   display(childs);
    		}
    	}
    }
    public void display_link_header()
    {   System.out.println();
    	System.out.println(" ----------------------------------First Node link header in tree-----------------------------------------------------------------");
    	for(Map.Entry<String, Node> s:first.entrySet())
    	{
    		System.out.println("Item Name is "+s.getKey());
    		System.out.println("Node "+s.getValue().count+" "+s.getValue().max);
    	}
    	System.out.println(" ----------------------------------End of links-----------------------------------------------------------------");
    	System.out.println();
    	System.out.println(" ----------------------------------Last Node of Link header-----------------------------------------------------------------");
    	for(Map.Entry<String, Node> s:second.entrySet())
    	{
    		System.out.println("Item Name is "+s.getKey());
    		System.out.println("Node "+s.getValue().count+" "+s.getValue().max);
    	}
    	System.out.println(" ----------------------------------End of Links----------------------------------------------------------------");
    }
    
    void createHeaderList(final Map<String, Integer> TWU) {
		headerList = new ArrayList<String>(first.keySet());
		Collections.sort(headerList, new Comparator<String>() {
			public int compare(String name1, String name2) {
				int compare = TWU.get(name2)- TWU.get(name1);	
				return compare;
			}
		});
	}
    
    public void display_heder()
    {
    	for(String s:headerList)
    	{
    		System.out.println(s);
    	}
    }
    
    public  void path_Extraction(Node root)
    {
    	  int i=0;
    		while(root.childs.size()!=0)
    		{   
    			Node childs=root.childs.get(0);
    			while(childs.count!=0)
    			{   ArrayList<Node> path=new ArrayList<Node>();
    			    ArrayList<Node> remove=new ArrayList<Node>();
    			    i++;
    				extract(childs,path,remove);
    				paths.put(i,path);
    				Removing_node.remove.put(i, remove);
    			}
    	
    		    }
			}
    		
    public void extract(Node childs,ArrayList<Node> path,ArrayList<Node> remove)
    {
        if(childs.count>1)
        {
        	if(childs.childs.size()>0)
        	{
        		extract(childs.childs.get(0),path,remove);
        		childs.count--;
        		if(childs.count==0)
        		{
        			
        			childs.parent.childs.remove(childs);
        			childs.parent=null;
        			path.add(childs);		
        			remove.add(childs);
        		}
        		else
        		{
        			
        			path.add(childs);
        		}
        		
        	}
        }
        else
        {
        	if(childs.childs.size()>0)
        	{
        		extract(childs.childs.get(0),path,remove);
        		childs.count--;
        		
        		childs.parent.childs.remove(childs);
        		childs.parent=null;
        		path.add(childs);
        		remove.add(childs);
        	}
        	else
        	{
        		(childs.count)--;
        		
        		childs.parent.childs.remove(childs);
        		childs.parent=null;
        		path.add(childs);
        		remove.add(childs);
        		
        	}
        	}
        }
    
    public void arrangement(HashMap<String,Integer> TWU)
    {   int count=1;
    	for(Map.Entry<Integer, ArrayList<Node>> d:paths.entrySet())
    	{
    		ArrayList<Node> n=d.getValue();
    		Collections.sort(n, new Comparator<Node>() {
    			public int compare(Node name1, Node name2) {
    				int compare = TWU.get(name2.itemname)- TWU.get(name1.itemname);	
    				return compare;
    			}
    		});
    		paths.put(count,n);
    		count++;
    	}
    }
    public void display_path()
    {
    	int count=1;
		for(Map.Entry<Integer, ArrayList<Node>> d:paths.entrySet())
		{   
			System.out.println(" ----------------------------------Path "+count+"-----------------------------------------------------------------");
			System.out.println();
		    for(Node n:d.getValue())
		    {
		    	System.out.println("Name->"+n.itemname+" ; Max value of it-> "+n.max+" ; Node Utility of it-> "+n.nodeUtility);
		    }
		    System.out.println(" ----------------------------------End of Path "+count+"-----------------------------------------------------------------");
		    System.out.println();
		    count++;
		}
    }
    public void local_tree()
    {
    	path_Extraction(root);
    	display_path();
    	arrangement(MIQ_Algo.TWU);
    	display_path();
    	//display_removed_node();
        
    	for(Map.Entry<Integer, ArrayList<Node>> d:paths.entrySet())
		{   
			Integer [] pathUtility=new Integer[d.getValue().size()];
    		//System.out.println(" ----------------------------------Path "+count+"-----------------------------------------------------------------");
			//System.out.println();
			int i=0;
		    for(Node n:d.getValue())
		    {   if(i==0)
		        {
		    	pathUtility[i]=profit.get(n.itemname)*n.max;
		        }
		    else
		    {
		    	pathUtility[i]=profit.get(n.itemname)*n.max+pathUtility[i-1];
		    }
		    i++;
		    }
		    insert_local_transaction(d.getValue(), pathUtility);
		    //System.out.println(" ----------------------------------End of Path "+count+"-----------------------------------------------------------------");
		    //System.out.println();
		    //count++;
		}
    	createHeaderList(MIQ_Algo.TWU);
    }
    
    public void display_removed_node()
    {
    	int count=1;
		for(Map.Entry<Integer, ArrayList<Node>> d:Removing_node.remove.entrySet())
		{   
			System.out.println(" ----------------------------------Remove "+count+"-----------------------------------------------------------------");
			System.out.println();
		    for(Node n:d.getValue())
		    {
		    	System.out.println(n.itemname);
		    }
		    System.out.println(" ----------------------------------End of Remove "+count+"-----------------------------------------------------------------");
		    System.out.println();
		    count++;
		}
    }
    
    
    
    public void insert_local_transaction(ArrayList<Node> trans,Integer [] path_utility)
	{
		Node current=root;
		int size=trans.size();
		for(int i=0;i<size;i++)
		{
			Node item=trans.get(i);
			int q=path_utility[i];
			Node child=current.getChild(item.itemname);
			if(child==null)
			{
				current=insert_node(current,item,q);
			}
			else
			{
				child.count++;
				child.nodeUtility=child.nodeUtility+q;
				current=child;
			}
		}
		
	}
    
    public void read_profit(String args)
    { String read;
    	 try {
			BufferedReader r=new BufferedReader(new FileReader(args));
			while((read=r.readLine())!=null)
			{
				String[]split=read.split(" ");
				profit.put(split[0],Integer.parseInt(split[1]));
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    public Node insert_node(Node current,Node item,int q)
	{
		
		item.parent=current;
		current.childs.add(item);
		item.count=1;
		item.nodeUtility=q;
		Node localheadernode = first.get(item.itemname);
		if (localheadernode == null) { 
			first.put(item.itemname, item);
			second.put(item.itemname, item);
		} 
		else 
		{ 
			Node lastNode = second.get(item.itemname);
			lastNode.nodeLink = item;
			second.put(item.itemname, item);
		}
		return item;
		
	}
    public void display_local_tree(Node root)
    {   
    	if(root.childs==null)
    	{
    		return;
    	}
    	else
    	{
    		for(Node childs:root.childs)
    		{
    		   System.out.println(childs.itemname+" "+childs.count+" "+childs.max+" "+childs.parent.itemname+" "+childs.nodeUtility);
    		   display_local_tree(childs);
    		}
    	}
    }
    
    
    
    
    
    
    public void pruning_first()
    {   MIQ_Algo s=new MIQ_Algo();
    	/*for(Map.Entry<String, Integer> f:MIQ_Algo.TWU.entrySet())
    	{
    		String item=f.getKey();
    		int util=f.getValue();
    		int i=0;
    		if(util>=s.min_util())
    		{   
    			if(MIQ_Algo.real.get(item)>=s.min_util())
    			{   i++;
    			    ArrayList<Item> can=new ArrayList<Item>(); 
    			    can.add(new Item(item,0));
    				final_candidates.put(i,can);
    			}
    			else
    			{
    				candidates.add(item);
    			}
    		}
    	}*/
        int k=0;
        int m=k;
    	for(int i=headerList.size()-1;i>=0;i--)
    	{   //int j=0;
    	    HashMap<Integer,ArrayList<Item>> final_candidate=new HashMap<Integer,ArrayList<Item>>();
    		String item=headerList.get(i);
    
    		if(MIQ_Algo.real.get(item)>=s.min_util())
    		{
    			
			    ArrayList<Item> can=new ArrayList<Item>(); 
			    can.add(new Item(item,0));
			    k++;
				final_candidate.put(k,can);
				final_candidates.put(k,can);
    		}
    		int twu=MIQ.MIQ_Algo.TWU.get(item);
    		if(twu>=s.min_util())
    		{
    	            conditional_pattern_base(item);	
    	           /* for(Path p:local_path)
    	            {   System.out.println("----------------------------------Path from Conditional-----------------------------");
    	            	for(Node c:p.path)
    	            	{   
    	            		System.out.println(c.itemname);
    	            	}
    	            	System.out.println("----------------------------------End of Path from Conditional-----------------------------");
    	            }*/
    		}
    		for(Path p: local_path)
    		{
    			for(Node c:p.path)
    			{
    				if(local_twu.containsKey(c))
    				{
    					int util=local_twu.get(c);
    					util=util+p.utility;
    					local_twu.put(c,util);
    					
    				}
    				else
    				{
    					local_twu.put(c, p.utility);
    				}
    				
    				if(local_support.containsKey(c.itemname))
    				{
    					int value=local_support.get(c.itemname);
    					value++;
    					local_support.put(c.itemname,value);
    				}
    				else
    				{
    					local_support.put(c.itemname,1);
    				}
    			}
    		}
    	    //StringBuffer it=new StringBuffer(item);
    		ArrayList<Item> arr=new ArrayList<Item>();
    		arr.add(new Item(item,0));
    		for(Map.Entry<Node, Integer> f:local_twu.entrySet())
    		{
    			if(f.getValue()>s.min_util())
    			{
    			    //it.append(" "+f.getKey().itemname);
    				Item t=new Item(f.getKey().itemname);
    				arr.add(t);
    			}
    		}
    		
    		if(k!=m)
    		{
    			final_candidate.put(k,arr);
    			final_candidates.put(k,arr);
    			k++;
    			m=k;
    		}
    		else
    		{
    			
    			final_candidate.put(k,arr);
    			final_candidates.put(k,arr);
    			k++;
    			m=k;
    		}
    		
    		emicu_calculation(final_candidate);
    		//System.out.println(it);
    		local_path.clear();
    		local_twu.clear();
    		//local_support.clear();
    	}
    	//display_Candidate_itemset();
    	
    }
    
    
    
    
    public void conditional_pattern_base(String item)
    {  Node temp=root;
    	for(Node childs:temp.childs)
    	{   
    	    if(childs.itemname.equals(item))
    	    {
    	    	Path p=new Path();
    	    	p.path.add(childs);
    	    	p.support++;
    	        p.utility=childs.nodeUtility;
    	        local_path.add(p);
    	    }
    	    else
    	    {
    	    	ArrayList<Node> s=new ArrayList<Node>();
    	    	s.add(childs);
    	    	path_Extraction_local(childs,item,s);
    	    	
    	    }
    	   
    	}
    }
    
    
    
    
    
    public void path_Extraction_local(Node childs,String item,ArrayList<Node> s)
    {
    	if(childs.childs.size()>0)
    	{    
    		for(Node child:childs.childs)
    		{      
    		    if(child.itemname.equals(item))
    		    {
    		    	Path p=new Path();
    	    	    p.path=(ArrayList<Node>) s.clone();
    	    	    p.support++;
    	    	    p.utility=child.nodeUtility;
    	    	    local_path.add(p);
    	    	    return;
    		    }
    		    else
    		    {
    		    	s.add(child);
        	    	path_Extraction_local(child,item,s);
        	    	s.remove(child);
    		    }
    		}
    	}
    	/*else
    	{
    		if(childs.itemname.equals(item))
    		{
    			Path p=new Path();
	    	    p.path=s;
	    	    p.support++;
	    	    p.utility=childs.nodeUtility;
	    	    local_path.add(p);
	    	    return;
    		}
    	}*/
    	
    }
    
    public void display_Candidate_itemset()
    {   System.out.println("All these itemset are before performing Final step");
        System.out.println();
    	for(Map.Entry<Integer, ArrayList<Item>> f:final_candidates.entrySet())
    		
    	{   System.out.println("--------------------------------------------------------Start of Candidate ItemSet---------------------------------------------------------------");
    		for(Item o:f.getValue())
    		{
    			System.out.println(o.name);
    		}
    		
    		System.out.println("--------------------------------------------------------End of Candidate ItemSet---------------------------------------------------------------");
    	    System.out.println();
    	}
    }
    
    
    
    
    public void emicu_calculation(HashMap<Integer,ArrayList<Item>> final_candidate)
    {   MIQ_Algo s=new MIQ_Algo();
         
    	for(Map.Entry<Integer, ArrayList<Item>> f:final_candidate.entrySet())
    	{
    		
    		
    		
    		Item first=f.getValue().get(0);
    		Item last=f.getValue().get(f.getValue().size()-1);
    		int emigu=MIQ_Algo.real.get(first.name)-
    				(MIQ_Algo.min.get(first.name)*
    						(MIQ_Algo.global_support.get(first.name)-
    								local_support.get(last.name)));
    		int emiu=0;
    		for(Item o:f.getValue())
    		{   if(o!=first)
    		   {
    			 emiu=emiu+MIQ_Algo.max.get(o.name)*local_support.get(o.name);
    		   }
    		}
    		int emicu=emigu+emiu;
    		if(emicu>=s.min_util())
    		{
    			System.out.println("--------------------------------------------------------Start of Final Candidate ItemSet---------------------------------------------------------------");
    			for(Item o:f.getValue())
    			{   
    				System.out.println(o.name);
    				
    			}
    			
    			System.out.println("--------------------------------------------------------End of Final Candidate ItemSet---------------------------------------------------------------");
    			System.out.println();
    		
    		}
    		
    	}
    }
    
}

