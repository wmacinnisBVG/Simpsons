package zork;

import java.util.ArrayList;


public class Room {
  private String roomName;
  private String description;
  private boolean isDark;
  private ArrayList<Exit> exits;
  private ArrayList<Item> items;
  private ArrayList<NPC> NPCS;
  private ArrayList<Item> temporary = new ArrayList<Item>(1);
  public int storySection = 0;

  public ArrayList<Exit> getExits() {
    return exits;
  }

  public void setExits(ArrayList<Exit> exits) {
    this.exits = exits;
  }

  public ArrayList<Item> getItems() {
    return items;
  }
  public void setItems(ArrayList<Item> items){
    this.items = items; 
  }
  public ArrayList<NPC> getNPC() {
    return NPCS;
  }
  public void setNPC(ArrayList<NPC> NPCS){
    this.NPCS = NPCS;
  }
  /**
   * Create a room described "description". Initially, it has no exits.
   * "description" is something like "a kitchen" or "an open court yard".
   */
  public Room(String description) {
    this.description = description;
    exits = new ArrayList<Exit>();
    items = new ArrayList<Item>();
    NPCS = new ArrayList<NPC>();
    

  }

  public Room() {
    roomName = "DEFAULT ROOM";
    description = "DEFAULT DESCRIPTION";
    exits = new ArrayList<Exit>();
    items = new ArrayList<Item>();
    NPCS = new ArrayList<NPC>();
  }

  public void addExit(Exit exit) throws Exception {
    exits.add(exit);
  }

  /**
   * Return the description of the room (the one that was defined in the
   * constructor).
   */
  public String shortDescription() {
    return "Room: " + roomName + "\n\n" + description;
  }

  /**
   * Return a long description of this room, on the form: You are in the kitchen.
   * Exits: north west
   */
  public String longDescription() {
    if(roomName.equals("Living Room")){
      return "\n" + description +"\n \nRoom: " + roomName+  "  ||  " + exitString() + "  ||  " + itemString() + "\n" + NPCString() + "\n You see Homer lying in a puddle of blood on the floor. The wound is still fresh, the killer must be close by.\n\n However, Ned's Glasses lie on the floor. Homer is still alive, talk to him to find out what happened ";
    } else if(roomName.equals("Car")){
      return"Hello Bart, you are now in the car. To drive to car, please use the command word \" drive to\" and enter the location you want to reach" + 
      "\n\n" + exitString() ;
    }else if(!isDark){
      return "\n" + description +"\n \n Room: " + roomName+  "  ||  " + exitString() + "  ||  " + itemString() + "\n" + NPCString();
    } else {
      return "\n This room is very dark, you can't see anything right now. Try to find some light";
    }
    

  }

  /**
   * Return a string describing the room's exits, for example "Exits: north west
   * ".
   */
  private String exitString() {
    String returnString = "Exits: ";
    if(roomName == "Car"){
      if(storySection == 0){
        returnString = "PowerPlant";
      } else if(storySection == 1) {
        returnString = "Store";
      } else if(storySection == 2){
        returnString = "Mall";
      } else if(storySection == 3){
        returnString =  "Home";
      }
    } else {
      for (Exit exit : exits) {
        returnString += exit.getDirection() + " ";
      }
  
    }

    return returnString;
  }

  public Item takeForInventory(){ //This is also just temporary and will be changed into a for loop later (probably)
    temporary.clear();
    temporary.add(items.get(0));
    items.remove(0);
    return temporary.get(0);
  }

  public Item takeItem(String item){
   
    for (int i =0; i<items.size(); i++){
      if (items.get(i).getName().equalsIgnoreCase(item)){
        System.out.println("\n You picked up " + items.get(i).getName() + ".");
        return items.remove(i);
      }
    }

    return null;
  }

  /**
   * Return a description of the items in the current room. 
   * 
   * @return
   */
  private String itemString(){
    String returnString = "";
    if (items.isEmpty()) {
      
    } else {
      returnString = "Items: ";
    }
      for(Item item : items){
          returnString += item.getName() + ", ";
      }
      if(returnString.length() >= 2)
      return returnString.substring(0,returnString.length()-2);
      else
      return returnString;
  }

  private String NPCString() {
    String returnString = "";
    if (NPCS.isEmpty()) {
      
    } else {
      returnString = "Characters: ";
    }
    
    for (NPC npc : NPCS) {
      returnString += npc.getName() + " ";
    }

    return returnString;
  }

  /**
   * Return the room that is reached if we go from this room in direction
   * "direction". If there is no room in that direction, return null.
   */
  public Room nextRoom(String direction, Room lockedRoom) {
    try {
      for (Exit exit : exits) {

        if (exit.getDirection().equalsIgnoreCase(direction)) {
          if (!exit.isLocked){
            String adjacentRoom = exit.getAdjacentRoom();
            return Game.roomMap.get(adjacentRoom);
          }
          //System.out.println(exit.getKeyId());
          System.out.println("\n The area you want to enter appears locked.... If you have keys use the unlock function + the direction of what door you want to unlock.");
          return lockedRoom;
        }

      }
    } catch (IllegalArgumentException ex) {
      System.out.println(direction + " is not a valid direction.");
      return null;
    }

    System.out.println(direction + " is not a valid direction.");
    return null;
  }
  /**
   * Tries to unlock the room given a specific direction. Will first match an exit to the direction, and then for that exit will see if the keyId is equal to the id of any item in the players inventory.
   * @param direction
   * @param id
   */
  public void unlockRoom(String direction, String id) {
    try {
      for (Exit exit : exits) {

        if (exit.getDirection().equalsIgnoreCase(direction)) {
          if (exit.isLocked){
            if (exit.keyId.equals(id)){
              exit.isLocked = false;
              System.out.println("You managed to unlock the door. \n");
            }
          }
        }

      }
    } catch (IllegalArgumentException ex) {
      System.out.println(direction + " is not a valid direction.");
    }
  }

  /*
   * private int getDirectionIndex(String direction) { int dirIndex = 0; for
   * (String dir : directions) { if (dir.equals(direction)) return dirIndex; else
   * dirIndex++; }
   * 
   * throw new IllegalArgumentException("Invalid Direction"); }
   */
  public String getRoomName() {
    return roomName;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void addItem(Item item) {
    items.add(item);
  }

  public void addNPC (NPC npc) {
    NPCS.add(npc);
  }

  public boolean checkRoom(String Name){
    for(NPC npc: NPCS){
      if(npc.getName().equals(Name))
      return true;
    }
    return false; 
  }
 
  
  public void setDark(boolean isDark) {
    this.isDark = isDark;
  }

  public boolean getDark() {
    return isDark;
  }

}
