package zork;

public class Item extends OpenableObject {
  private long weight;
  private String name;
  private boolean isOpenable;
  private String id;
  private boolean isUseable;
 

  public Item(int weight, String name, boolean isOpenable) {
    this.weight = weight;
    
    this.name = name;
    this.isOpenable = isOpenable;
  }
  public Item() {
    name = "DEFAULT ITEM";
    weight = 0;
    isOpenable = false;
  }
  public void open() {
    if (!isOpenable)
      System.out.println("The " + name + " cannot be opened.");
    
  }
 

  public long getWeight() {
    return weight;
  }

  public void setWeight(long weight) {
    this.weight = weight;
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
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

  public void setId(String id) {
    this.id = id;
  }

  public void setUseable(boolean isUseable) {
    this.isUseable = isUseable;
  }

  public boolean getUseable() {
    return isUseable;
  }


}
