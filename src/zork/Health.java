package zork;
/**
 * Damage system. Can take damage from various sources.
 */
public class Health {
    private static int harts = 3;
    public int getHarts(){
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
    public static void addHealth(){
        if(harts < 5){
            harts += 1;
            System.out.println("You have picked up a hart!");
        } else {
            System.out.println("Your health is already full!");
        }
    }
    public void playerDead(){
        System.out.println("You have died.");
        System.exit(0);
    }
}
