package MIQ;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
		Node localheadernode = first.get(item);
		if (localheadernode == null) { 
			first.put(item, n);
			second.put(item, n);
		} 
		else 
		{ 
			Node lastNode = second.get(item);
			lastNode.nodeLink = n;
			second.put(item, n);
		}
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
		    	System.out.println(n.itemname+" "+n.count+" "+n.parent+" "+n.max+" "+n.nodeUtility+" "+n.nodeLink );
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
    	display_removed_node();
        
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
}

