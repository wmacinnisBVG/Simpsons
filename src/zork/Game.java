package zork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Game {

  public static HashMap<String, Room> roomMap = new HashMap<String, Room>();

  private Parser parser;
  private Room currentRoom;
  private Room lastRoom; 
  private Inventory currentInventory = new Inventory(10);
  private boolean chase;
  

  public ArrayList<Item> gameItems = new ArrayList<Item>();
  /**
   * Create the game and initialise its internal map.
   */
  public Game() {
    try {
      initRooms("Simpsons\\src\\zork\\data\\rooms.json");
      currentRoom = roomMap.get("Main-House-TV-Room");
      initItems("Simpsons\\src\\zork\\data\\items.json");
      initCharacters("Simpsons\\src\\zork\\data\\characters.json");
    } catch (Exception e) {
      e.printStackTrace();
    }
    parser = new Parser();
  }

  private void initRooms(String fileName) throws Exception {
    Path path = Path.of(fileName);
    String jsonString = Files.readString(path);
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(jsonString);

    JSONArray jsonRooms = (JSONArray) json.get("rooms");

    for (Object roomObj : jsonRooms) {
      Room room = new Room();
      String roomName = (String) ((JSONObject) roomObj).get("name");
      String roomId = (String) ((JSONObject) roomObj).get("id");
      String roomDescription = (String) ((JSONObject) roomObj).get("description");
      Boolean isDark = (Boolean) ((JSONObject) roomObj).get("isDark");
      room.setDescription(roomDescription);
      room.setRoomName(roomName);
      room.setDark(isDark);

      JSONArray jsonExits = (JSONArray) ((JSONObject) roomObj).get("exits");
      ArrayList<Exit> exits = new ArrayList<Exit>();
      for (Object exitObj : jsonExits) {
        String direction = (String) ((JSONObject) exitObj).get("direction");
        String adjacentRoom = (String) ((JSONObject) exitObj).get("adjacentRoom");
        String keyId = (String) ((JSONObject) exitObj).get("keyId");
        Boolean isLocked = (Boolean) ((JSONObject) exitObj).get("isLocked");
        Boolean isOpen = (Boolean) ((JSONObject) exitObj).get("isOpen");
        Exit exit = new Exit(direction, adjacentRoom, isLocked, keyId, isOpen);
        exits.add(exit);
      }
      room.setExits(exits);
      roomMap.put(roomId, room);
    }
  }
  /**
   * Takes items.json file and brings all the items into the game
   */
  private void initItems(String fileName) throws Exception {
    Path path = Path.of(fileName);
    String jsonString = Files.readString(path);
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(jsonString);

    JSONArray jsonItems = (JSONArray) json.get("items");

    for (Object roomObj : jsonItems) {
      Item item = new Item();
      String itemName = (String) ((JSONObject) roomObj).get("name");
      item.setName(itemName);
      long itemWeight = (Long) ((JSONObject) roomObj).get("weight");
      item.setWeight(itemWeight);
      boolean itemIsOpenable = (boolean) ((JSONObject) roomObj).get("isOpenable");
      item.setOpen(itemIsOpenable);
      String id = (String) ((JSONObject) roomObj).get("id");
      item.setId(id);
      boolean itemIsUseable = (boolean) ((JSONObject) roomObj).get ("isUseable");
      item.setUseable(itemIsUseable);
      String roomId = (String) ((JSONObject) roomObj).get("room");
      roomMap.get(roomId).addItem(item);
      //gameItems.add(item); 
    }
    

  }
  private void initCharacters(String fileName) throws Exception {
    Path path = Path.of(fileName);
    String jsonString = Files.readString(path);
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(jsonString);

    JSONArray jsonCharacters = (JSONArray) json.get("characters");

    for (Object roomObj : jsonCharacters) {
       NPC character = new NPC();
      String characterName = (String) ((JSONObject) roomObj).get("name");
      character.setName(characterName);
      String characterLocation = (String) ((JSONObject) roomObj).get("location");
      character.setLocation(characterLocation);
      String characterDialogue = (String) ((JSONObject) roomObj).get("dialogue");
      character.setDialogue(characterDialogue);
      roomMap.get(characterLocation).addNPC(character);
      
    }
  }

  /**
   * Main play routine. Loops until end of play.
   */
  public void play() {
    printWelcome();
   
    boolean finished = false;
    while (!finished) {
      Command command;
      lastRoom = currentRoom;
      
     
      try {
        command = parser.getCommand();
        finished = processCommand(command);
      } catch (IOException e) {
        e.printStackTrace();
      }
      endChase();
    }
    System.out.println("Thank you for playing.  Good bye.");
  }

  /**
   * Print out the opening message for the player.
   */
  private void printWelcome() {
    System.out.println("The Simpsons");
    System.out.println();
    System.out.println("Welcome to Springfield!");
    System.out.println("Simpsons is a new, incredibly fun murder mystery game.");
    System.out.println("Type 'help' if you need help.");
    System.out.println();
    System.out.println(currentRoom.longDescription());
    System.out.println("Hey Bart, you are in the Simpson's house, start exploring. \n You hear a strange noise from the living room. Go through the kitchen and turn west in the entryway.");
  }

  /**
   * Given a command, process (that is: execute) the command. If this command ends
   * the game, true is returned, otherwise false is returned.
   */
  private boolean processCommand(Command command) {
    if (command.isUnknown()) {
      System.out.println("I don't know what you mean...");
      return false;
    }

    String commandWord = command.getCommandWord().toLowerCase();
    if (commandWord.equals("help"))
      printHelp();
    else if (commandWord.equals("go"))
      goRoom(command);
    else if (commandWord.equals("quit")) {
      if (command.hasSecondWord())
        System.out.println("Quit what?");
      else
        return true; // signal that we want to quit
    } else if (commandWord.equals("eat")) {
      System.out.println("Do you really think you should be eating at a time like this?");
    } else if (commandWord.equals("pick up")) {
      
        
      Item item = currentRoom.takeItem(command.getSecondWord());
      if(currentRoom.getRoomName().equals("Apu's store")){
        System.out.println("Apu: please don't try to steal from my store, Talk to me first!");
      }else if (item == null)
        System.out.println("What " + command.getSecondWord() + "?");
      else
        currentInventory.addItem(item); 
    } else if(commandWord.equals("inventory")) {
      currentInventory.listInventory();
    }else if(commandWord.equals("talk to")){
      for(NPC npc: currentRoom.getNPC()){

        if(npc.getName().equals("Apu")){
          System.out.println(npc.talkTo());
          System.out.println(" 1. Chips \t$1 \n 2. Soda \t$1 \n 3. Cookie \t$1 \n please select your choice by using the word \"buy\"");
        }else
        System.out.println(npc.talkTo());
      }
    }else if(commandWord.equals("buy")){
      if(currentRoom.getRoomName().equals("Apu's store") && currentInventory.checkInventory("Bill") ){
        Item item = currentRoom.takeItem(command.getSecondWord());
      if (item == null)
        System.out.println("Please choose from the three options in the store");
      else
        currentInventory.addItem(item); 
        //remove bill from inventory
      }else{
        System.out.println("Oh no!, you need to have money to buy something, please come back with the right amount of money\n Hint: check your house for spage change");
      }
      
      //here
    }else if(commandWord.equals("drive to")){
      if(currentRoom.getRoomName().equals("Car"))
      goRoom(command);
      else{
      System.out.println("You cannot drive when you are not in a car!");
      }
      //here
    }else if(commandWord.equals("unlock")){
      String direction = command.getSecondWord();
      for(int i = 0; i < currentInventory.getSize(); i++){
        currentRoom.unlockRoom(direction, currentInventory.getId(i));
      } 
    } else if(commandWord.equals("hide")) {
      
      
        } else if(commandWord.equals("drop")){

      Item item = currentInventory.removeItem(command.getSecondWord());
      if (item == null){
          System.out.println("You do not have the " + command.getSecondWord());
      }else
        currentRoom.addItem(item);
     

    }else if(commandWord.equals("use")){
      if(command.hasSecondWord()){
        String item = command.getSecondWord().toLowerCase();
        for(int i = 0; i < currentInventory.getSize(); i++){ //put other useable items in this loop if you want
          if(item.equals("flashlight") && currentInventory.getName(i).equals("Flashlight")){
            if(currentRoom.getDark()){
              System.out.println("You can now see your surrounding area.");
              currentRoom.setDark(false);
              System.out.println(currentRoom.longDescription());
            }else{
              System.out.println("You can already see everything around you.");
            }
          }
        }
      }
    }
    return false;
  }

  // implementations of user commands:
  
  /**
   * Print out some help information. Here we print some stupid, cryptic message
   * and a list of the command words.
   */
  private void printHelp() {
    System.out.println("You are trying to find Homer's killer.");
    System.out.println("Around Springfield.");
    System.out.println();
    System.out.println("Your command words are:");
    parser.showCommands();
  }


  private void endChase(){
    String bobLast = roomMap.get("Mall3").getNPC().get(0).getLocation();

    if(currentRoom.getRoomName().equals("Springfield Mall East Wing")){
     chase = true;
     System.out.println(("\n !!!! QUICK Run away from Sideshow bob and report him to the police station as quick as possible !!!"));

     for(NPC npc: currentRoom.getNPC()){
       System.out.println(npc.talkTo());
     }
    }

    
   
    if(chase && !currentRoom.getRoomName().equals("Bush")){

     if( currentRoom.checkRoom("Sideshow Bob")||bobLast.equals(currentRoom.getRoomName()))
     System.out.println("I CAUGHT U");
      roomMap.get("Mall3").getNPC().get(0).setLocation(lastRoom.getRoomName());
       
          
        
      
    }
   
    }
    
  

  /**
   * Try to go to one direction. If there is an exit, enter the new room,
   * otherwise print an error message.
   */
  private void goRoom(Command command) {
    if (!command.hasSecondWord()) {
      // if there is no second word, we don't know where to  go...
      System.out.println("Go where?");
      return;
    }

    String direction = command.getSecondWord();

    // Try to leave current room.
    Room nextRoom = currentRoom.nextRoom(direction, currentRoom);

    if (nextRoom == null)
      System.out.println("There is no door!");
    else if(nextRoom == currentRoom){
      currentRoom = nextRoom;
    }else{
      currentRoom = nextRoom;
      System.out.println(currentRoom.longDescription());
    }
  }
}
