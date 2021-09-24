import java.io.*;
import java.util.Scanner;

/**
 * main driver for the application
 */
public class TransplantDriver {
    public static final String DONOR_FILE = "donors.txt";
    public static final String RECIPIENT_FILE = "recipients.txt";
    public static void main(String[] args) throws IOException {
        TransplantGraph graph = new TransplantGraph();
        File file = new File("transplant.obj");

        if(file.exists()){
            graph = loadFromFile();
            System.out.println("\nLoading data from transplant.obj... \n");
        }
        else{
            graph = graph.buildFromFiles("donors.txt", "recipients.txt");
        }

        graph.updateConnections();
        //graph.printConnections();

        Scanner stdin = new Scanner(System.in);
        stdin.useDelimiter(System.lineSeparator());

        String ans = "";

        while(!ans.equals("Q")){
            printMenu();
            System.out.print("Please select an option: ");
            ans = stdin.nextLine();
            if(ans.equals("LR")){
                graph.printAllRecipients();
            }
            if(ans.equals("LO")){
                graph.printAllDonors();
            }
            if(ans.equals("AO")){
                System.out.print("\nPlease enter the organ donor name: ");
                String name = stdin.nextLine();
                System.out.print("Please enter the organs "  + name + " is donating: " );
                String organ = stdin.nextLine();
                System.out.print("Please enter the blood type of " + name + ": ");
                String blood = stdin.nextLine();
                System.out.print("Please enter the age of " + name + ": ");
                int age = Integer.parseInt(stdin.nextLine());
                BloodType bloodType = new BloodType(blood);
                Patient donor = new Patient(true,name,organ,age,bloodType);
                graph.addDonor(donor);
                System.out.println("\nThe organ donor with ID " + donor.getID() + " was successfully added to the donor list!");
            }
            if(ans.equals("AR")){
                System.out.print("\nPlease enter new recipient's name: ");
                String name = stdin.nextLine();
                System.out.print("Please enter the recipient's blood type: ");
                String blood = stdin.nextLine();
                System.out.print("Please enter the recipient's age: ");
                int age = stdin.nextInt();
                System.out.print("Please enter the organ needed: ");
                String organ = stdin.nextLine();
                BloodType bloodType = new BloodType(blood);
                Patient recipient = new Patient(false, name, organ, age, bloodType);
                graph.addRecipient(recipient);
            }
            if(ans.equals("RO")){
                System.out.print("Please enter the name of the organ donor to remove:");
                String name = stdin.nextLine();
                graph.removeDonor(name);
            }
            if(ans.equals("RR")){
                System.out.print("Please enter the name of the organ recipient to remove:");
                String name = stdin.nextLine();
                graph.removeRecipient(name);

            }
            if(ans.equals("SR")){
                while(!ans.equals("Q")){
                    printRecipientMenu();
                    System.out.print("Please select an option: ");
                    ans = stdin.nextLine();
                    if(ans.equals("I")){
                        graph.printAllRecipients();
                    }
                    if(ans.equals("N")){
                        graph.printRecipientSortedByConnections();
                    }
                    if(ans.equals("B")){
                        graph.printRecipientsSortedByBloodType();
                    }
                    if(ans.equals("O")){
                        graph.printRecipientsSortedByOrgan();
                    }
                }
                ans = "SR";
            }
            if(ans.equals("SO")){
                while(!ans.equals("Q")){
                    printDonorMenu();
                    System.out.print("Please select an option: ");
                    ans = stdin.nextLine();
                    if(ans.equals("I")){
                        graph.printAllDonors();
                    }
                    if(ans.equals("N")){
                        graph.printDonorsSortedByConnections();
                    }
                    if(ans.equals("B")){
                        graph.printDonorsSortedByBloodType();
                    }
                    if(ans.equals("O")){
                        graph.printDonorsSortedByOrgan();
                    }
                }
                ans = "SO";
            }
        }
        graph.saveInFile();
        System.out.println("\nWriting data to transplant.obj...\n");
        System.out.println("Program terminating normally...");
    }
    public static void printMenu(){
        System.out.println("\nMenu:\n" +
                "    (LR) - List all recipients\n" +
                "    (LO) - List all donors\n" +
                "    (AO) - Add new donor\n" +
                "    (AR) - Add new recipient\n" +
                "    (RO) - Remove donor\n" +
                "    (RR) - Remove recipient\n" +
                "    (SR) - Sort recipients\n" +
                "    (SO) - Sort donors\n" +
                "    (Q) - Quit\n");
    }

    /**
     * prints Donor Meny
     */
    public static void printDonorMenu(){
        System.out.println("\n    (I) Sort by ID\n" +
                "    (N) Sort by Number of Donors\n" +
                "    (B) Sort by Blood Type\n" +
                "    (O) Sort by Organ Donated\n" +
                "    (Q) Back to Main Menu\n");
    }

    /**
     * Prints recipient meny
     */
    public static void printRecipientMenu(){
        System.out.println("\n    (I) Sort by ID\n" +
                "    (N) Sort by Number of Recipients\n" +
                "    (B) Sort by Blood Type\n" +
                "    (O) Sort by Organ Needed\n" +
                "    (Q) Back to Main Menu");
    }

    /**
     * Loads graph  back up file
     * @return graph
     * @throws IOException
     */
    public static TransplantGraph loadFromFile() throws IOException {
        TransplantGraph t = new TransplantGraph();
        try {
            FileInputStream file = new FileInputStream("transplant.obj");
            ObjectInputStream fin  = new ObjectInputStream(file);
            return (TransplantGraph) fin.readObject();
        } catch(IOException | ClassNotFoundException ignored) {
        }
        return t;
    }
}
