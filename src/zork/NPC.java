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
/**
 *  Returns the dialogue of the character
 * @param x takes the second of word of command and sees if the name matches with the name of the character
 * @returns  who if the name does not match
 */
   public String talkTo(String x){
    if(x.equalsIgnoreCase(name))   
    return dialogue; 
    else 
    return "who?";
   }

   




}




