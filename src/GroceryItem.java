/*******
 * Author: Kylie Wang
 * Date: 2023/4/19
 * Purpose: This program constructs a grocery item and includes methods
 * to get the item's weight, cost, and name. It also compares item's 
 * cost/weight to see what is worth more. 
 */


public class GroceryItem implements Comparable <GroceryItem>{
       double weight = 0;
       double cost = 0;
       String item = "";
      
	  public GroceryItem(double w, double c, String n) {
		  weight = w;
		  cost = c;
		  item = n;
	  }
	  
	  public double getWeight() {
		   	return weight;
	  }
	  
	  public double getCost() {
		   	return cost;
	  }
	  
	  public String getItem() {
		   	return item;
	  }
	 
	  // compare grocery items so the item with the bigger cost/kg is bigger
	  @Override 
	  public int compareTo(GroceryItem a) {
	        if (weight / cost > a.getWeight() / a.getCost()) {
	        	return 1;
	        } else if (weight / cost < a.getWeight() / a.getCost()) {
	        	return -1;
	        }
	        return 0;
	       
	    }
}