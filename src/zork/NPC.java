package zork;

public class NPC {

    /**
     * name 
     * location : type - room
     * method for dialogue/ talking
     * give method - gives player items
     */
   private String location; 
   private String name;
   private String dialogue; 
   

   public NPC( String location, String name, String dialogue){
        this.location = location;
        this.name = name; 
        this.dialogue = dialogue; 

   }

   public NPC(){
    this.location = "DEFAULT LOCATION";
    this.name = "DEFAULT NAME"; 
    this.dialogue = "DEFAULT DIALOGUE"; 

}

   public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getLocation() {
    return location;
}

public void setLocation(String location) {
    this.location = location;
}

public String getDialogue() {
    return dialogue;
}

public void setDialogue(String dialogue){
    this.dialogue = dialogue;
}
/*
public void talk(){
       System.out.println(dialogue);
   }
*/
   public String talkTo(){
       
    return dialogue; 
   }




}




