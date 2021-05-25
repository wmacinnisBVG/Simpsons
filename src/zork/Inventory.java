package zork;

import java.util.ArrayList;

public class Inventory {
  private ArrayList<Item> items;
  private int maxWeight;
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

  public int getSize() {
    return items.size();
  }

  public boolean addItem(Item item) {
    if (item.getWeight() + currentWeight <= maxWeight){
      currentWeight += item.getWeight();
      return items.add(item);
    } else {
      System.out.println("There is no room to add the item.");
      return false;
    }
  }

  public boolean checkInventory(String item){
    for(Item x : items){
      if(x.getName().equals(item))
      return true;
    }
    return false; 
  }

  public void listInventory() {
    System.out.println("Your inventory max weight is: "+maxWeight);
    System.out.println("Inventory current weight: "+currentWeight);
    for(int i=0; i<=items.size()-1; i++){
      System.out.println(items.get(i).getName());
    }
  }

  public String getId(int i) {
    return items.get(i).getId();
  }

}