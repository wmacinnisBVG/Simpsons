package zork;

import java.util.ArrayList;

public class Inventory {
  private ArrayList<Item> items;
  private int maxWeight;
  private int currentWeight;
  private long itemWeight;
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
      System.out.println("\nYou can't pick up this item because you have reached the weight limit for your inventory.");
      return false;
    }
  }

  /*
  public boolean removeItem(Item item){
    return items.remove(item);
  }
*/
  public Item removeItem(String item){
    item = item.toLowerCase();
    //Get Item weight
    for(Item x : items){
      if(x.getName().toLowerCase().equals(item)){
        itemWeight  = x.getWeight();
      }
        
    }

    for(int i = 0; i< items.size(); i++){
    if(items.get(i).getName().toLowerCase().equals(item)){
        currentWeight -= itemWeight; 
        System.out.println("You dropped " + items.get(i).getName() + ". \n");
        return items.remove(i);
      }
    }
    return null; 
  }
  public boolean checkInventory(String item){
    for(Item x : items){
      if(x.getName().equals(item))
      return true;
    }
    return false; 
  }

  public void listInventory() {
    System.out.println("Your inventory max weight is: "+maxWeight + "\t");
    System.out.print("||\t Inventory current weight: "+currentWeight);
    for(int i=0; i<=items.size()-1; i++){
      System.out.println("");
      System.out.println(items.get(i).getName());
    }
  }

  public String getId(int i) {
    return items.get(i).getId();
  }

  public String getName(int i) {
    return items.get(i).getName();
  }
 
}
