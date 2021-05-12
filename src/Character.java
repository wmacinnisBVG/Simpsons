import zork.Room;

public class Character {

    /**
     * name 
     * location : type - room
     * method for dialogue/ talking
     * give method - gives player items
     */
   private Room location; 
   private String name;
   private String dialogue; 
   

   public Character( Room location, String name, String dialogue){
        this.location = location;
        this.name = name; 
        this.dialogue = dialogue; 

   }

   public void talk(){
       System.out.println(dialogue);
   }

   public void giveItem(){

   }

}
