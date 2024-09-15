import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.IOException;

public class Game {
    public static List <Droid> droidList = new ArrayList<>();

    public static final String RESET = "\u001B[0m";        // Reset to default
    public static final String MAGENTA = "\u001B[35m"; 
    public static final String BLUE = "\u001B[34m";
    public static String RED = "\u001B[31m";
    public static String COLORS[] = {RED, BLUE, MAGENTA};
    
    private static final Random rand = new Random();

    public static Droid Duel(Droid droid1, Droid droid2) {
        System.out.println("Duel between " + droid1.COLLOR + droid1.getName() + RESET + " and " + droid2.COLLOR + droid2.getName() + RESET +" is starting...");
        while (!droid1.isDead() && !droid2.isDead()) {
            droid1.attack(droid2); 
            if (droid2.isDead()) {
                System.out.println(droid2.COLLOR + droid2.getName() + RESET + " is DEFEATED!");
                droid1.recover();
                return droid2;
            }
    
            droid2.attack(droid1);  
            if (droid1.isDead()) {
                System.out.println(droid1.COLLOR + droid1.getName() + RESET + " is DEFEATED!");
                droid2.recover();
                return droid1;
            }
        }
        return null;
    }

    public static void TeamBattle(String team1_name, List<Droid> team1, String team2_name, List<Droid> team2) {
        System.out.print("\nTeam battle between " + RED + team1_name + RESET + " and " +BLUE + team2_name + RESET + " is starting...\n");
        
        System.out.print("\n" + RED + team1_name + RESET + ": \n");
        for (Droid droid : team1){
            System.out.println(RED + droid.getName() + RESET);
            if(droid instanceof Hseries){
                List <Droid> teammates = new ArrayList<>(team1);
                teammates.remove(droid);
                ((Hseries)droid).teammates = teammates;
            }
        }
            
        System.out.print("\n" + BLUE + team2_name + RESET + ": \n");
        for (Droid droid : team2){
            System.out.println(BLUE + droid.getName() + RESET);
            if(droid instanceof Hseries){
                List <Droid> teammates = new ArrayList<>(team1);
                teammates.remove(droid);
                ((Hseries)droid).teammates = teammates;
            }
        }
            
        System.out.println();
        
        while (!team1.isEmpty() && !team2.isEmpty()) {
            List<Integer> attacksT1 = new ArrayList<>();
            List<Integer> attacksT2 = new ArrayList<>();
    
            populateIndexList(attacksT1, team1.size());
            populateIndexList(attacksT2, team2.size());
    
            int randIndexT2 = rand.nextInt(team2.size());   
            team1.get(randAttacker(attacksT1)).attack(team2.get(randIndexT2));
            if(team2.get(randIndexT2).isDead()){
                team2.remove(randIndexT2);
                // Update attacksT2 after removal (new size)
                attacksT2.clear();
                populateIndexList(attacksT2, team2.size());
                // Check if team2 is empty
                if (team2.isEmpty()) {
                    System.out.println(RED + team1_name + RESET + " wins!");
                    for(Droid droid : team1){
                        if(!droid.isDead())
                            droid.recover();
                        }
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
                    for(Droid droid : team1){
                        if(!droid.isDead())
                            droid.recover();
                    }
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
        while(true){
            System.out.println("\nMenu:\n1. Create droid\n2. Print droids\n3. Duel\n4. Team battle\n5.Write to file\n6. Read from file\n7. Exit\n");
            System.out.println("Enter your choice: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch(choice){
                case 1:
                    System.out.println("Choose droid type: 1. Rseries, 2. Hseries, 3. TankDroid");
                    int droidType = scanner.nextInt();
                    Droid droid = null;
                    scanner.nextLine();

                    System.out.println("Enter droid name: ");
                    String name = scanner.nextLine();

                    System.out.println("Choose color: 1. RED, 2. BLUE, 3. MAGENTA");
                    int color = scanner.nextInt();
                    if (color < 1 || color > 3) {
                        System.out.println("Invalid color choice. Defaulting to RED.");
                        color = 1;
                    }                
                    switch(droidType) {
                        case 1:
                            droid = new Rseries(name, COLORS[color - 1]); // Choose color from array
                            break;
                        case 2:
                            droid = new Hseries(name, COLORS[color - 1],null); 
                            break;
                        case 3:
                            droid = new TankDroid(name, COLORS[color - 1]);
                            break;
                        default:
                            System.out.println("Invalid droid type.");
                    }
                    droidList.add(droid);
                    break;
                case 2:
                    System.out.println("Droids: ");
                    printDroids(droidList);
                    break;
                case 3:
                    System.out.println("Duel:");
                    System.out.println("Enter first droid #: ");
                    int index1 = scanner.nextInt();
                    System.out.println("Enter second droid #: ");
                    int index2 = scanner.nextInt();
                    droidList.remove(Duel(droidList.get(index1-1), droidList.get(index2-1)));
                    break;
                case 4:System.out.println("Team battle:");

                    System.out.println("Enter team name: ");
                    String team1_name = scanner.next();
                    List<Droid> team1 = createTeam(droidList, team1_name, scanner);
                    
                    System.out.println("Enter second team name: ");
                    String team2_name = scanner.next();
                    List<Droid> team2 = createTeam(droidList, team2_name, scanner);
                    TeamBattle(team1_name, team1, team2_name, team2);
                    break;
                case 5:
                    System.out.println("Write to file:");
                    System.out.println("Enter filename: ");
                    String filename = scanner.next();
                    try {
                        File file = new File(filename);
                        if (file.createNewFile()) {
                            System.out.println("File created: " + file.getName());
                        } else {
                            System.out.println("File already exists.");
                        }
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                    System.out.println("Duel:");
                    System.out.println("Enter first droid #: ");
                    int pl1 = scanner.nextInt();
                    System.out.println("Enter second droid #: ");
                    int pl2 = scanner.nextInt();
                    PrintStream terminal = System.out;
                    try {
                        // Create a PrintStream that writes to a file
                        PrintStream fileOut = new PrintStream(new FileOutputStream(filename));
                        // Redirect standard output to the file
                        System.setOut(fileOut);
                        droidList.remove(Duel(droidList.get(pl1-1), droidList.get(pl2-1))); 
                        // Restore standard output to the console    
                        System.setOut(terminal);               
                    } catch (IOException e) {
                        System.err.println("Error redirecting output: " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Read from file:");
                    System.out.println("Enter filename: ");
                    String filename2 = scanner.next();
                    ReadFromFile(filename2);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Wrong choice");
                    break;
            }
            scanner.nextLine();
        }
    }

    public static void ReadFromFile(String filename) {
        File file = new File(filename);  // File to read from

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);  // Print each line from the file
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    public static List <Droid> createTeam(List<Droid> list, String team_name, Scanner scanner) {
        System.out.println(("Enter team size: "));
        int team_size = scanner.nextInt();
        System.out.println("Choose " + team_size + " droids for " + team_name + ": ");
        List<Droid> team = new ArrayList<>();
        for (int i = 0; i < team_size; i++) {
            int index = scanner.nextInt();
            team.add(droidList.get(index - 1));
        }
        return team;
    }

    public static void printDroids(List<Droid> list){
        int i = 1;
        for(Droid droid : list)
            System.out.println(i++ + "." + droid.COLLOR + droid.getName() + RESET);
    }
    
    public static void main(String[] args) {
        System.out.println("Welcome to the game!");
        consoleMenu();
    }
}