/********
 * Author: Kylie Wang
 * Date: 2023/4/19
 * Purpose: A program with recursive and iterative solutions to the 
 * knapsack problem. Each solution is timed to see the speed it takes 
 * to find the optimal bag.
 */
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Groceries {

	private static int counter = 0;

	// create grocery items and put into an array
	private static GroceryItem[] groceryItems = { new GroceryItem(1, 2.5, "flour"), new GroceryItem(0.25, 2.4, "beans"),
			new GroceryItem(0.5, 1.20, "cereal"), new GroceryItem(0.3, 3.99, "cookies"),

			new GroceryItem(0.3, 4.99, "Spam"), new GroceryItem(2, 15, "chicken"), new GroceryItem(1, 0.99, "soda"),
			new GroceryItem(100, 100000, "MyItem") };

	/*
	 * Recursive solution that counts method calls Precondition: b is an arraylist
	 * of groceries already in the bag Postcondition: returns the optimized bag
	 */
	private static ArrayList<GroceryItem> bestBagRecursive(ArrayList<GroceryItem> b, int n, double c) {
		// counts recursive calls
		counter++;
		if (counter % 10000000 == 0)
			System.out.println("# method calls : " + counter);

		// creates possible bags
		ArrayList<GroceryItem> temp = new ArrayList<GroceryItem>(); // a copy of the bag passed in to not alter the
																	// original
		ArrayList<GroceryItem> temp1 = new ArrayList<GroceryItem>(); // will be bag without adding current item
		ArrayList<GroceryItem> temp2 = new ArrayList<GroceryItem>(); // will be bag adding one of current item
		ArrayList<GroceryItem> temp3 = new ArrayList<GroceryItem>(); // will be bag adding two of current item
		temp.addAll(b);

		if (n == -1 || c == 0) {
			return b; // base case, if we go through all the items or if capacity is at limit
		} else {

			// skips item if initially too big to fit in bag
			if (groceryItems[n].getWeight() > c) {
				return temp1 = bestBagRecursive(temp, n - 1, c);
			}

			// create temporary bag1 where the item is skipped
			
			temp1 = bestBagRecursive(temp, n - 1, c);
			
			// create temporary bag2 where one of the item is added
			temp.add(groceryItems[n]);
			temp2 = bestBagRecursive(temp, n - 1, c - groceryItems[n].getWeight());

			// create temporary bag3 if conditions allow that have 2 of the same items added
			if (2 * groceryItems[n].getWeight() < c) {
				temp.add(groceryItems[n]);
				temp3 = bestBagRecursive(temp, n - 1, c - 2 * groceryItems[n].getWeight());
			}
		}

		// find and return the current best bag out of the three
		if (bagCost(temp1) > bagCost(temp2) && bagCost(temp1) > bagCost(temp3)) {
			return temp1;
		} else if (bagCost(temp2) > bagCost(temp3)) {
			return temp2;
		} else {
			return temp3;
		}

	} // bestBagRecursive

	/*
	 * Iterative solution Postcondition: returns an optimized bag
	 */
	private static ArrayList<GroceryItem> bestBagIterative(double capacity) {

		ArrayList<GroceryItem> bestBag = new ArrayList<GroceryItem>(); // to be returned

		// total weight of the items currently in bag
		double totWeight = 0;

		// sorts grocery items by their value/kg
		Arrays.sort(groceryItems);

		int i = 0;
		do {

			// if have space for two, put the two items with max value inside
			if (2 * groceryItems[i].getWeight() <= capacity - totWeight) {
				totWeight += 2 * groceryItems[i].getWeight();
				bestBag.add(groceryItems[i]);
				bestBag.add(groceryItems[i]);

				// if space for only one, put one in
			} else if (groceryItems[i].getWeight() <= capacity - totWeight) {
				totWeight += groceryItems[i].getWeight();
				bestBag.add(groceryItems[i]);

				// item initially too big to fit in bag
			}

			i++;
		} while (i < groceryItems.length);

		return bestBag;
	} // bestBagIterative

	// returns the weight of bag b
	public static double bagWeight(ArrayList<GroceryItem> b) {
		double weight = 0;
		for (int i = 0; i < b.size(); i++) {
			weight += b.get(i).getWeight();

		}

		DecimalFormat df = new DecimalFormat("#.0000000000"); // format away the double rounder error
																// ex. 8.4 + 0.3 = 8.700000000000001
		return Double.parseDouble(df.format(weight));

	}

	// returns the value of bag b
	public static double bagCost(ArrayList<GroceryItem> b) {
		double cost = 0;
		for (int i = 0; i < b.size(); i++) {
			cost += b.get(i).getCost();
		}

		DecimalFormat df = new DecimalFormat("#.00"); // format away the double rounder error 
														// ex. 8.4 + 0.3 = 8.700000000000001
		return Double.parseDouble(df.format(cost));
	}

	// returns number of items of i in bag b
	public static int bagCount(ArrayList<GroceryItem> b, String item) {
		int count = 0;
		for (int i = 0; i < b.size(); i++) {
			if (b.get(i).getItem().equals(item)) {
				count++;
			}
		}

		return count;
	}

	// prints contents of bag b
	public static void printBag(ArrayList<GroceryItem> b) {
		System.out.println("The contents of this bag include: ");

		for (int i = 0; i < b.size(); i++) {
			System.out.println(b.get(i).getItem());
		}
	}

	public static void main(String[] args) {
		
		// declare capacity
		double capacity = 8.8;
		
		System.out.println("************Iterative Solution******\n");

		long startTime = System.nanoTime();
		ArrayList<GroceryItem> perfectBag = bestBagIterative(capacity);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);

		System.out.println("Time: " + duration / 1000 + " ms");
		System.out.println("# items = " + perfectBag.size());
		System.out.println("weight items = " + bagWeight(perfectBag) + " kg.");
		System.out.println("cost items = $" + bagCost(perfectBag));
		printBag(perfectBag);

		System.out.println("\n************Recursive Solution******\n");

		ArrayList<GroceryItem> temp = new ArrayList<GroceryItem>();
		startTime = System.nanoTime();
		perfectBag = bestBagRecursive(temp, groceryItems.length - 1, capacity);
		endTime = System.nanoTime();
		duration = (endTime - startTime);

		System.out.println("Time: " + duration / 1000 + " ms");
		System.out.println("# method calls = " + counter);
		System.out.println("# items = " + perfectBag.size());
		System.out.println("weight items = " + bagWeight(perfectBag) + " kg.");
		System.out.println("cost items = $" + bagCost(perfectBag));
		printBag(perfectBag);

	}

} // Groceries