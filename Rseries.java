
public class Rseries extends Droid{
    int heal;   //heal itself
    int count;  //every 3 atacks opens super atack
    int superAtack; 
    public Rseries(String name, String COLLOR){
        super(name, 100, 20, "Rseries",COLLOR);
        heal = 15;
        count = -1;
        superAtack = 50;
    }

    @Override
    public void recover() {
        health = fullHealth;
        count = -1;
    }

    public int getHeal(){
        return heal;
    }   

    public int getCount(){  
        return count;
    }   

    public int getSuperAtack(){ 
        return superAtack;
    }
    
    @Override
    public void attack(Droid target){
        if(dead)
            return;
            try {
                if (count % 3 == 0) {
                    System.out.print("\n"+COLLOR + name + RESET + " is preparing a SUPER ATTACK...\n\n");
                    Thread.sleep(1500);
                    target.takeDamage(superAtack);
                    System.out.println(COLLOR + name + RESET + " performs a SUPER ATTACK on " + target.COLLOR + target.getName() + RESET + " for " + RED + superAtack + RESET + " damage!");
                    Thread.sleep(1000);
                    health += heal;
                    System.out.println(COLLOR + name + RESET + " heals itself for " + GREEN + heal + RESET + " health. Current health: " + GREEN + health + RESET);
                } else {
                    System.out.println(COLLOR + name + RESET + " is attacking " + target.COLLOR + target.getName() + RESET + "...");
                    Thread.sleep(1000);
                    int randomDamage = rand.nextInt((damage - minDamage) + 1) + minDamage;
                    target.takeDamage(randomDamage);
                    System.out.println(COLLOR + name + RESET + " attacks " + target.COLLOR + target.getName() + RESET + " for " + RED + randomDamage + RESET + " damage.");                           
            }
            count++;  
        }catch(InterruptedException e) {
            System.out.println("Battle interrupted");
        }
    }

}

