package MIQ;

public class ItemSet {
	
String[] itemset;
int transaction_utility=0;
           public ItemSet(String[] itemset,int transutility)
          {
	          this.itemset=itemset;
	          this.transaction_utility=transutility;
          }
           public String [] getItemset()
           {
        	   return this.itemset;
           }
           
           public int getUtility()
           {
        	   return this.transaction_utility;
           }
           
           public int size() {
       		return itemset.length;
       	}
}
