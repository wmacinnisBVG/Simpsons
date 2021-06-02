package zork;

public class Health {
    private int harts = 5;
    public int getHearts(){
        return harts;
    }
    public void takeDamage(){
        harts -= 1;
        if(harts <= 0){
            playerDead();
        }
    }
    public void getFullHealth(){
        System.out.println("Your full health is: "+harts+" harts");
    }
    public void addHealth(){
        if(harts < 5){
            harts += 1;
        } else {
            System.out.println("Your health is already full!");
        }
    }
    public void playerDead(){
        System.out.println("You have died.");
        System.exit(0);
    }
}
