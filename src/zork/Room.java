package zork;

import java.util.ArrayList;


public class Room {

  private String roomName;
  private String description;
  private ArrayList<Exit> exits;
  private ArrayList<Item> items;
  private ArrayList<NPC> NPCS;
  private ArrayList<Item> temporary = new ArrayList<Item>(1);
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

    return "Room: " + roomName + "\n\n" + description + "\n" + exitString() + "\n" + itemString() + "\n" + NPCString();
  }

  /**
   * Return a string describing the room's exits, for example "Exits: north west
   * ".
   */
  private String exitString() {
    String returnString = "Exits: ";
    for (Exit exit : exits) {
      returnString += exit.getDirection() + " ";
    }

    return returnString;
  }

  public Item takeForInventory(){ //This is also just temporary and will be changed into a for loop later (probably)
    temporary.clear();
    temporary.add(items.get(0));
    items.remove(0);
    return temporary.get(0);
  }

  /**
   * Return a description of the items in the current room. 
   * 
   * @return
   */
  private String itemString(){
      String returnString = "Items: ";
      System.out.println(items);
      for(Item item : items){
       // if(item.getRoom().equals(roomName)){
          returnString += item.getName() + "";
        //}
      }
      return returnString;
  }

  private String NPCString() {
    String returnString = "Characters: ";
    System.out.println(NPCS);
    for (NPC npc : NPCS) {
      returnString += npc.getName() + " ";
    }

    return returnString;
  }

  /**
   * Return the room that is reached if we go from this room in direction
   * "direction". If there is no room in that direction, return null.
   */
  public Room nextRoom(String direction) {
    try {
      for (Exit exit : exits) {

        if (exit.getDirection().equalsIgnoreCase(direction)) {
          if (!exit.isLocked){
            String adjacentRoom = exit.getAdjacentRoom();
            return Game.roomMap.get(adjacentRoom);
          }
          System.out.println(exit.getKeyId());
          System.out.println("It's locked so like find some key");
          return null;
        }

      }
    } catch (IllegalArgumentException ex) {
      System.out.println(direction + " is not a valid direction.");
      return null;
    }

    System.out.println(direction + " is not a valid direction.");
    return null;
  }

  public void unlockRoom(String direction, String id) {
    try {
      for (Exit exit : exits) {

        if (exit.getDirection().equalsIgnoreCase(direction)) {
          if (exit.isLocked){
            if (exit.keyId.equals(id)){
              exit.isLocked = false;
              System.out.println("Door unlocked");
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
}
