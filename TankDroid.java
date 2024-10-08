public class TankDroid extends Droid{
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";

    int shield;

    public TankDroid(String name,String COLLOR) {
        super(name, 150, 40, "TankDroid", COLLOR);
        shield = 50;
    }

    @Override
    public void recover() {
        health = fullHealth;
        shield = 50;
    }

    public int getShield(){
        return shield;
    }

    @Override
    public void takeDamage(int damage) {
        try {
            if(shield > 0){
                if(shield < damage){
                    System.out.println(COLLOR + name + RESET + " shield absorbed " +  BLUE + shield + RESET + " damage.");
                    System.out.println("Remaining shield: " + YELLOW + 0 + RESET + " Shield is depleted.");
                    Thread.sleep(1000);                      
                    super.takeDamage(damage-shield);
                    shield = 0;
                }
                shield = shield - damage;
                System.out.println(COLLOR + name + RESET + " shield absorbed all damage. Remaining shield: " + BLUE + shield + RESET);
                Thread.sleep(1000);          
            } else {
                super.takeDamage(damage); 
            }            
        }catch(InterruptedException e) {
            System.out.println("Battle interrupted");
        }

    }
}