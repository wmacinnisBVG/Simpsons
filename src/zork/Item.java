package zork;

import java.util.ArrayList;

public class Item extends OpenableObject {
  private int weight;
  private String name;
  private boolean isOpenable;
  private String room;
  public Item(int weight, String room, String name, boolean isOpenable) {
    this.weight = weight;
    this.room = room; 
    this.name = name;
    this.isOpenable = isOpenable;
  }
  public Item() {
    name = "DEFAULT ITEM";
    room = "DEFAULT ROOM";
    weight = 0;
    isOpenable = false;
  }
  public void open() {
    if (!isOpenable)
      System.out.println("The " + name + " cannot be opened.");

  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public String getName() {
    return name;
  }

  public String getRoom(){
    return room;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isOpenable() {
    return isOpenable;
  }

  public void setOpenable(boolean isOpenable) {
    this.isOpenable = isOpenable;
  }

  public void setRoom(String room){
    this.room = room;
  }
}
