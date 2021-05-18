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
    if (item.getWeight() + currentWeight <= maxWeight)
      return items.add(item);
    else {
      System.out.println("There is no room to add the item.");
      return false;
    }
  }

  public void listInventory() {
    if(items.size() > 0){
      for(int i = 0; i < items.size(); i++){
        System.out.println(i+1 + ". " + items.get(i).getName());
      }
    }else{
      System.out.println("You have nothing in your pockets.");
    }
  }

  public String getId(int i) {
    return items.get(i).getId();
  }

}
