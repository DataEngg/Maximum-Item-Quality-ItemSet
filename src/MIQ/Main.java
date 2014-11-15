package MIQ;

public class Main {
	public static void main(String args[])
	{
	   MIQ_Algo obj=new MIQ_Algo();
	   obj.first_scan(args[0]);
	   Tree r=new Tree();
	   System.out.println(" ----------------------------------Welcome TO Global Tree of Algorithm-----------------------------------------------------------------");
	   r.display(Tree.root);
	   System.out.println(" ----------------------------------End Of Global Tree-----------------------------------------------------------------");
	   System.out.println();
	   
	   System.out.println();
	   
	   r.read_profit(args[1]);
	   r.local_tree();
	   System.out.println();
	   System.out.println(" ----------------------------------Welcome TO Local Tree of Algorithm-----------------------------------------------------------------");
	   r.display_local_tree(Tree.root);
	   System.out.println(" ----------------------------------End Of Local Tree-----------------------------------------------------------------");
	   System.out.println(" ----------------------------------Welcome TO HeaderList-----------------------------------------------------------------");
	   r.display_link_header();
	   System.out.println(" ----------------------------------End Of HeaderList-----------------------------------------------------------------");
	   System.out.println(" ----------------------------------Welcome TO SortHeaderList by TWU-----------------------------------------------------------------");
	   r.display_heder();
	   System.out.println(" ----------------------------------End of SortHeaderList By TWU-----------------------------------------------------------------");
	   System.out.println();
	   r.pruning_first();
	}
	
}
