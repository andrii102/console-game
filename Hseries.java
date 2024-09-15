import java.util.List;
import java.util.ArrayList;
public class Hseries extends Droid{
    int heal;   //heals teammates
    int count;  //every 3 atacks opens heal ability
    public List<Droid> teammates;
    public Hseries(String name,String COLLOR, List<Droid> teammates){
        super(name, 120, 35,"Cseries", COLLOR);
        heal = 15;
        count = -1;
        if(teammates == null)
            teammates = new ArrayList<>();
        else
            teammates = new ArrayList<>(teammates);
    }

    @Override
    public void recover() {
        health = fullHealth;
        count = -1;
        teammates = null;
    }

    public int getHeal(){
        return heal;
    }   

    public int getCount(){  
        return count;
    }   
    public void healMates() {


        try{
            health += heal;
            System.out.println(COLLOR + name + RESET + " heals itself for " + GREEN + heal + RESET + " health. Current health: " + GREEN + health + RESET);     
            Thread.sleep(1000);              
            if(teammates == null)
                return;  
            int t = 0;
            System.out.println(COLLOR + name + RESET + " is starting to" + GREEN + " heal " + RESET + "teammates...");
            Thread.sleep(1000);
            for (Droid teammate : teammates) {
                if (!teammate.isDead()) {
                    System.out.print(GREEN + teammate.name + " ");
                    Thread.sleep(500);
                    t++;
                    teammate.takeDamage(-heal); // Heal by applying negative damage
                }
            }
            if(t>1)
                System.out.println(RESET + "have been" + GREEN + " healed!" + RESET); 
            if(t==1)
                System.out.println(RESET + "has been" + GREEN + " healed!" + RESET); 
            Thread.sleep(1000);      
        }catch(InterruptedException e) {
            System.out.println("Battle interrupted");
        }
    }

    @Override
    public void attack(Droid target){
        try{
        if(dead)
            return;
        if(count%3 == 0){
            healMates();
        }
        System.out.println(COLLOR + name + RESET + " is attacking " + target.COLLOR + target.getName() + RESET + "...");
        Thread.sleep(1000);
        int randomDamage = rand.nextInt((damage - minDamage) + 1) + minDamage;
        target.takeDamage(randomDamage);
        System.out.println(COLLOR + name + RESET + " attacks " + target.COLLOR + target.getName() + RESET + " for " + RED + randomDamage + RESET + " damage.");   
        count++;            
        }catch(InterruptedException e) {
            System.out.println("Battle interrupted");
        }

    }
}
