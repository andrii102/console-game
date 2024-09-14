import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public static List <Droid> droidList = new ArrayList<>();

    public static final String RESET = "\u001B[0m";        // Reset to default
    public static final String MAGENTA = "\u001B[35m"; 
    public static final String BLUE = "\u001B[34m";
    public static String RED = "\u001B[31m";
    public static String COLLORS[] = {RED, BLUE, MAGENTA};
    
    private static final Random rand = new Random();

    public static void Duel(Droid droid1, Droid droid2) {
        System.out.println("Duel between " + droid1.COLLOR + droid1.getName() + RESET + " and " + droid2.COLLOR + droid2.getName() + RESET +" is starting...");
        while (!droid1.isDead() && !droid2.isDead()) {
            droid1.attack(droid2); 
            if (droid2.isDead()) {
                System.out.println(droid2.COLLOR + droid2.getName() + RESET + " is DEFEATED!");
            }
    
            droid2.attack(droid1);  
            if (droid1.isDead()) {
                System.out.println(droid1.COLLOR + droid1.getName() + RESET + " is DEFEATED!");
            }
        }
    }

    public static void TeamBattle(String team1_name, List<Droid> team1, String team2_name, List<Droid> team2) {
        System.out.print("\nTeam battle between " + RED + team1_name + RESET + " and " +BLUE + team2_name + RESET + " is starting...\n");
        
        System.out.print("\n" + RED + team1_name + RESET + ": \n");
        for (Droid droid : team1) 
            System.out.println(RED + droid.getName() + RESET);
        System.out.print("\n" + BLUE + team2_name + RESET + ": \n");
        for (Droid droid : team2)
            System.out.println(BLUE + droid.getName() + RESET);
        System.out.println();
        while (!team1.isEmpty() && !team2.isEmpty()) {
            List<Integer> attacksT1 = new ArrayList<>();
            List<Integer> attacksT2 = new ArrayList<>();
    
            populateIndexList(attacksT1, team1.size());
            populateIndexList(attacksT2, team2.size());
    
            int randIndexT2 = rand.nextInt(team2.size());   //attacsT2.size()
            team1.get(randAttacker(attacksT1)).attack(team2.get(randIndexT2));
            if(team2.get(randIndexT2).isDead()){
                team2.remove(randIndexT2);
                // Update attacksT2 after removal (new size)
                attacksT2.clear();
                populateIndexList(attacksT2, team2.size());
                // Check if team2 is empty
                if (team2.isEmpty()) {
                    System.out.println(RED + team1_name + RESET + " wins!");
                    return;
                }
            }
    
            int randIndexT1 = rand.nextInt(team1.size()); // +1 because we removed one element from attacksT1 attacksT1.size()+1
            team2.get(randAttacker(attacksT2)).attack(team1.get(randIndexT1));
            if(team1.get(randIndexT1).isDead()){
                team1.remove(randIndexT1);
                // Update attacksT1 after removal (new size)
                attacksT1.clear();
                populateIndexList(attacksT1, team1.size());
                // Check if team1 is empty
                if (team1.isEmpty()) {
                    System.out.println(BLUE + team2_name + RESET + " wins!");
                    return;
                }
            }
        }
    }
    public static void populateIndexList(List<Integer> list, int indexes) {
        for(int i = 0; i < indexes; i++) 
            list.add(i);
    }

    public static int randAttacker(List<Integer> list) {
        int index = (int)(Math.random() * list.size());
        list.remove(index);
        return index;
    }

    public static void consoleMenu(){
        System.out.println("Menu:\n1. Create droid\n2. Print droids\n3. Duel\n4. Team battle\n5.Write to file\n6. Read from file\n7. Exit\n");
        System.out.println("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch(choice){
            case 1:
                System.out.println("Choose droid type: 1. Rseries, 2. Hseries, 3.TankDroid");
                int droidType = scanner.nextInt();
                switch(droidType){
                    case 1:
                        System.out.println("Enter droid name: ");
                        String name = scanner.next();
                        System.out.println("Choose color:1. RED, 2. BLUE, 3. MAGENTA");
                        int color = scanner.nextInt();
                        Droid droid = new Rseries(10, 35, name, 100, 30, COLLORS[color - 1]);
                        droidList.add(droid);
                break;}
            case 2:
                System.out.println("Droids: ");
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            default:
                System.out.println("Wrong choice");
                break;
        }
        scanner.close();
    }
    
    public static void main(String[] args) {
        
        Droid droid1 = new Rseries(15, 50, "CROW", 100, 35, MAGENTA);
        Droid droid3 = new Rseries(0, 30, "AMBER", 100, 20, MAGENTA);
        Droid[] teammates = {droid1, droid3};
        Droid droid2 = new Hseries(30, "medic", 150, 30, MAGENTA, teammates);
        // Duel(droid1, droid2);

        List<Droid> team1 = new ArrayList<>();
        List<Droid> team2 = new ArrayList<>();

        team1.add(droid2);
        team1.add(droid1);
        team1.add(droid3);
        team2.add(new Rseries(15, 50, "3p0", 100, 35, BLUE));
        team2.add(new TankDroid("Tank", 150, 45, 50, BLUE));
        TeamBattle("Team 1", team1, "Team 2", team2);
    }
}