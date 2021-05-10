package zork;

import java.util.ArrayList;

public class Room {

  private String roomName;
  private String description;
  private ArrayList<Exit> exits;
  private ArrayList<Item> items;

  public ArrayList<Exit> getExits() {
    return exits;
  }

  public void setExits(ArrayList<Exit> exits) {
    this.exits = exits;
  }

  public ArrayList<Item> getItems() {
    return items;
  }
  public void setItems(ArrayList<Item> exists){
    this.items = items; 
  }
  /**
   * Create a room described "description". Initially, it has no exits.
   * "description" is something like "a kitchen" or "an open court yard".
   */
  public Room(String description) {
    this.description = description;
    exits = new ArrayList<Exit>();
    items = new ArrayList<Item>();
  }

  public Room() {
    roomName = "DEFAULT ROOM";
    description = "DEFAULT DESCRIPTION";
    exits = new ArrayList<Exit>();
    items = new ArrayList<Item>();
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

    return "Room: " + roomName + "\n\n" + description + "\n" + exitString() + "\n" + itemString();
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

  /**
   * Return a description of the items in the current room. 
   * 
   * @return
   */
  private String itemString(){
      String returnString = "Items: ";
      for(Item item : items){
        if(item.getRoom().equals(roomName)){
          returnString += item.getName() + "";
        }
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
          String adjacentRoom = exit.getAdjacentRoom();

          return Game.roomMap.get(adjacentRoom);
        }

      }
    } catch (IllegalArgumentException ex) {
      System.out.println(direction + " is not a valid direction.");
      return null;
    }

    System.out.println(direction + " is not a valid direction.");
    return null;
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
}
