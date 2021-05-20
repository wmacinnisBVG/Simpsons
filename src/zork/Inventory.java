package zork;

import java.util.ArrayList;

public class Inventory {
  private ArrayList<Item> items;
  private int maxWeight = 10;
  private int currentWeight;

  public Inventory(int maxWeight) {
    this.items = new ArrayList<Item>();
    this.maxWeight = maxWeight;
    this.currentWeight = 0;
  }

  public int getMaxWeight() {
    return maxWeight;
  }

  public int getCurrentWeight() {
    return currentWeight;
  }

  public boolean addItem(Item item) {
    if (item.getWeight() + currentWeight <= maxWeight)
    
      return items.add(item);
    else {
      System.out.println("There is no room to add the item.");
      return false;
    }
  }

  public void listInventory() {
    System.out.println("Inventory current weight: "+currentWeight);
    for(int i=0; i<=items.size()-1; i++){
      System.out.println(items.get(i).getName());
    }
  }

  public String getId() {
    return items.get(0).getId();
  }

}
