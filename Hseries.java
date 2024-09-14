public class Hseries extends Droid{
    int heal;   //heals teammates
    int count;  //every 3 atacks opens heal ability
    Droid[] teammates;
    public Hseries(int heal, String name, int health, int damage, String COLLOR, Droid[] teammates){
        super(name, health, damage,"Cseries", COLLOR);
        this.heal = heal;
        this.count = -1;
        this.teammates = teammates;
    }

    public int getHeal(){
        return heal;
    }   

    public int getCount(){  
        return count;
    }   
    public void healMates() {
        if(teammates == null)
            return;
        try{
            int t = 0;
            System.out.println(COLLOR + name + RESET + " is starting to" + GREEN + " heal " + RESET + "teammates...");
            Thread.sleep(1000);
            for (int i = 0; i < teammates.length; i++) {
                if (!teammates[i].isDead()) {
                    System.out.print(GREEN + teammates[i].name + " ");
                    Thread.sleep(500);
                    t++;
                    teammates[i].takeDamage(-heal); // Heal by applying negative damage
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
            health += heal;
            System.out.println(COLLOR + name + RESET + " heals itself for " + GREEN + heal + RESET + " health. Current health: " + GREEN + health + RESET);
            Thread.sleep(1000);
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
