package zork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

//import org.json.simple.ItemList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Game {

  public static HashMap<String, Room> roomMap = new HashMap<String, Room>();

  private Parser parser;
  private Room currentRoom;
  private Room lastRoom; 
  private Inventory currentInventory = new Inventory(10);
  public Health harts = new Health();
  private boolean chase;
  boolean finished = false;
  

  public ArrayList<Item> gameItems = new ArrayList<Item>();
  /**
   * Create the game and initialise its internal map.
   */
  public Game() {
    try {
      initRooms("src\\zork\\data\\rooms.json");
      currentRoom = roomMap.get("Main-House-TV-Room");
      initItems("src\\zork\\data\\items.json");
      initCharacters("src\\zork\\data\\characters.json");
    } catch (Exception e) {
      e.printStackTrace();
    }
    parser = new Parser();
  }
  /**
   * Takes rooms.json file and brings all rooms into the game
   * Grabs all attributes from rooms.json 
   * 
   */
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
      boolean itemIsUseable = (boolean) ((JSONObject) roomObj).get("isUseable");
      item.setUseable(itemIsUseable);
      String roomId = (String) ((JSONObject) roomObj).get("room");
      roomMap.get(roomId).addItem(item);
      //gameItems.add(item); 
    }
    

  }
  /**
   * Takes characters.json file and brings all the characters into the game
   */
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
    System.out.println("Hey Bart, you are in the Simpson's house, start exploring. \n\n You hear a strange noise from the living room. Go through the kitchen and turn west in the entryway.");
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
    if(!currentRoom.getRoomName().equals("Car"))
      goRoom(command);
    else
    System.out.println("Use \"drive to \" to travel in the car");
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
        if(command.getSecondWord() == "Hart" || command.getSecondWord() == "hart"){
          currentInventory.addItem(item);
          Health.addHealth();
          currentInventory.removeItem(command.getSecondWord());
        } else if (item.getName().equalsIgnoreCase("Note")){
            System.out.println("The note reads, \" You need to give the money, do your part and then I will kill homer \n  meet me at the Kwik-E-Mart Store\"");

        }else {
          currentInventory.addItem(item);
        }
         
    } else if(commandWord.equals("inventory")) {
      currentInventory.listInventory();
    }else if(commandWord.equals("talk to")){

      if(command.getSecondWord() == null)
        System.out.println("\nWho? please enter the character you want to talk to");
        else{
      for(NPC npc: currentRoom.getNPC()){
        if(npc.getName().toLowerCase().equals(command.getSecondWord().toLowerCase()));
        if(npc.getName().equals("Apu")){
          System.out.println(npc.talkTo("Apu"));
          System.out.println(" 1. Chips \t$1 \n 2. Soda \t$1 \n 3. Cookie \t$1 \n please select your choice by using the word \"buy\"");
        }else

        System.out.println(npc.talkTo(command.getSecondWord()));
      }
    }
    }else if(commandWord.equals("buy")){
      if(currentRoom.getRoomName().equals("Apu's store") && currentInventory.checkInventory("Dollar Bill") ){
        Item item = currentRoom.takeItem(command.getSecondWord());
        if(item != null){
          currentInventory.addItem(item); 
          System.out.println("Thanks for your purchase, Bart. I saw some sketchy guy go to the mall, you should check it out.");
          currentInventory.removeItem("Dollar Bill");
        }else{
          System.out.println("Please choose from the three options in the store");
        }
      }else{
        System.out.println("Oh no!, you need to have money to buy something, please come back with the right amount of money\n Hint: check your house for some spare change");
      }
      
      //drive to is only used when in the car
    }else if(commandWord.equals("drive to")){ //Alternate command for go, makes it more grammatically correct
      if(currentRoom.getRoomName().equals("Car"))
      goRoom(command);
      else{
      System.out.println("\nYou cannot drive when you are not in a car!");
      }
      //here
    }else if(commandWord.equals("unlock")){
      String direction = command.getSecondWord();
      for(int i = 0; i < currentInventory.getSize(); i++){ //Checks to see if any of the items currently in the inventory will unlock the door at direction
        currentRoom.unlockRoom(direction, currentInventory.getId(i));
      } 
      
        } else if(commandWord.equals("drop")){

      Item item = currentInventory.removeItem(command.getSecondWord());
      if (item == null){
          System.out.println("You do not have the " + command.getSecondWord());
      }else{
        currentRoom.addItem(item);
      }

    } else if(commandWord.equals("health")){
      System.out.println("You are currently at "+harts.getHarts()+" harts.") ; 
    }else if(commandWord.equals("use")){
      if(command.hasSecondWord()){
        String item = command.getSecondWord().toLowerCase();
        for(int i = 0; i < currentInventory.getSize(); i++){ //put other useable items in this loop if you want
          if(item.equals("flashlight") && currentInventory.getName(i).equals("Flashlight")){
            if(currentRoom.getDark()){
              System.out.println("\n You can now see your surrounding area.");
              currentRoom.setDark(false);
              System.out.println(currentRoom.longDescription());
            }else{
              System.out.println("\nYou can already see everything around you.");
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
    if(currentRoom.getDark()){
      System.out.println("\ntry finding a flashlight somewhere and using it in the dark room");
    }else if(chase){
        System.out.println("Be careful of what inputs you type, one mistake can lead to Sideshow Bob catching you. Make sure you also make sure you follow the path and don't fall into dead ends ");
    }else{
    System.out.println("You are trying to find Homer's killer.");
    System.out.println("Around Springfield.");
    System.out.println();
    System.out.println("Your command words are:");
    parser.showCommands();
    }
  }

  /**
   * The final chase sequence upon entering the mall
   * Sideshow Bob will chase you around the mall. If you make a wrong step, you lose and the game is over.
   *  The method uses the global variable lastRoom which is assigned the value of currentRoom before currentRoom is updated with command
   * the method will only start CHASING when the player enters mall3 and exits the player cannot be caught when the player is in
   * a bush or in the original chase point
   * if the player reaches the police station - the player wins
   */
  private void endChase(){
    String bobLast = lastRoom.getRoomName();
    if(currentRoom.getRoomName().equals("Springfield Mall East Wing")){
     chase = true;
     System.out.println(("\n !!!! QUICK, Run away from Sideshow bob and report him to the police station as quick as possible !!! \n \n There is a path south of the mall that leads to the police station... make your decisions quick or Sideshow Bob will catch you\n\n"));

     for(NPC npc: currentRoom.getNPC()){
       System.out.println("SideShow Bob:" + npc.talkTo("SideShow Bob"));
     }

     

  }
    
    if(chase && !currentRoom.getRoomName().equals("Bush")&& !currentRoom.getRoomName().equals("Springfield Mall East Wing")){
     
      
     if( currentRoom.checkRoom("Sideshow Bob")||bobLast.equals(currentRoom.getRoomName())|| currentRoom.getRoomName().equals("Car")){
     System.out.println("Sideshow Bob: I CAUGHT YOU, now no one will know I killed homer\n\n Unfortunately, you took the wrong path and Sideshow Bob has caught you and will kill you, try again next time");

     
     finished = true; 
     }else if(currentRoom.getRoomName().equals("Police Station")){

       finished = true; 
     }
      
       
          
      
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

    // Try to leave current room. Passes currentRoom so that if player runs into a locked door, it does not return null and instead goes to the else if statement. 
    Room nextRoom = currentRoom.nextRoom(direction, currentRoom);

    if (nextRoom == null)
      System.out.println("There is no door!");
    else if(nextRoom == currentRoom){
      currentRoom = nextRoom;
    }else{
      currentRoom = nextRoom;
      System.out.println("\n \n " + currentRoom.longDescription());
    }
  }
}
