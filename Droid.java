import java.lang.Thread;
import java.util.Random;
import java.util.List;

public class Droid{
    public static  String RESET = "\u001B[0m";        // Reset to default
    public static  String RED = "\u001B[31m";         // Red for damage
    public static  String GREEN = "\u001B[32m";       // Green for droid names
    
    
    String name;
    int health;
    int damage;
    String series;
    boolean dead = false;
    int minDamage;
    String COLLOR;

    Random rand = new Random();

    public Droid(String name,int health, int damage, String series, String COLLOR){
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.series = series;
        this.COLLOR = COLLOR;
        minDamage = (int)(0.75 * damage);
    }

    public String getName(){
        return name;
    }

    public int getHealth(){
        return health;
    }   

    public int getDamage(){
        return damage;
    }

    public String getSeries(){
        return series;
    }

    public boolean isDead(){
        return dead;
    }

    public void takeDamage(int damage){
        health = health - damage;
        if (health < 0){
            health = 0;
            dead = true;
        } 
    }

    public void attack(Droid target){
        if(dead)
            return;
        try{
            System.out.println(COLLOR + name + RESET + " is attacking " + target.COLLOR + target.getName() + RESET + "...");
            Thread.sleep(1000); 
            int randomDamage = rand.nextInt((damage - minDamage) + 1) + minDamage;
            System.out.println(COLLOR + name + RESET + " attacks " + target.COLLOR + target.getName() + RESET + " for " + RED + randomDamage + RESET + " damage.");            
            target.takeDamage(randomDamage);  
            
        }catch(InterruptedException e) {
            System.out.println("Battle interrupted");
        }
    }

    public static void printList(List<Droid> list){
        for(Droid droid : list)
            System.out.println(droid.COLLOR + droid.getName() + RESET);
    }
}
