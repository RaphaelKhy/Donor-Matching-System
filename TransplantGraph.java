import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * contains an adjacency matrix for the organ donors and recipients
 */
public class TransplantGraph implements Serializable{
    private ArrayList<Patient> donors = new ArrayList<Patient>(100);
    private ArrayList<Patient> recipients = new ArrayList<Patient>(100);
    private boolean connections[][] = new boolean[MAX_PATIENTS][MAX_PATIENTS];
    public static int MAX_PATIENTS = 100;

    /**
     * Default constructor
     */
    public TransplantGraph(){}

    /**
     * Adds the specified Patient to the recipients list.
     * This method must also update the connections adjacency matrix, as necessary.
     * @param patient
     * Accepts a {@link Patient} object
     */
    public void addRecipient(Patient patient){
        Patient recipient = patient;
        recipients.add(recipient);
        int recipientID = recipients.indexOf(recipient);
        recipient.setID(recipientID);

        //add recipient to connections
        BloodType recipientBlood = recipient.getBloodtype();

        for(int i = 0; i < donors.size(); i++){
            Patient donor = donors.get(i);
            BloodType donorBlood = donor.getBloodtype();
            int donorID = donor.getID();
            if(BloodType.isCompatible(recipientBlood, donorBlood)){
                if(donor.getOrgan().equals(recipient.getOrgan())){
                    connections[donorID][recipientID] = true;
                }
            }
            if(!BloodType.isCompatible(donorBlood, recipientBlood)){
                connections[donorID][recipientID] = false;
            }
        }
        //updateConnections();
    }

    /**
     * Adds the specified Patient to the donors list.
     * This method must also update the connections adjacency matrix, as necessary.
     * @param patient
     * Accepts a {@link Patient} object
     */
    public void addDonor(Patient patient){
        Patient donor = patient;
        donors.add(donor);
        int donorID = donors.indexOf(donor);
        donor.setID(donorID);

        //add donor to connections
        BloodType donorBlood = donor.getBloodtype();

        for(int i = 0; i < recipients.size(); i++){
            Patient recipient = recipients.get(i);
            BloodType recipientBlood = recipient.getBloodtype();
            int recipientID = recipient.getID();
            if(BloodType.isCompatible(recipientBlood, donorBlood)){
                if(donor.getOrgan().equals(recipient.getOrgan())){
                    connections[donorID][recipientID] = true;
                }
            }
            if(!BloodType.isCompatible(recipientBlood, donorBlood)){
                connections[donorID][recipientID] = false;
            }
        }
        //updateConnections();
    }

    /**
     * Removes the specified Patient from the recipients list.
     * This method must also update the connections adjacency matrix,
     * removing all connections to this recipient,
     * and removing the column associated with the patient from the matrix.
     * @param name
     * Accepts a string name
     */
    public void removeRecipient(String name){
        int recipientID = 100;

        //find recipient ID
        for(int i = 0; i < recipients.size(); i++){
            Patient recipient = recipients.get(i);
            if(recipient.getName().equals(name)){
                recipientID = recipient.getID();
                break;
            }
        }

        if(recipientID != 100){
            //remove recipient from connections and shift columns
            for(int row = 0; row < connections.length; row++){
                for(int col = recipientID; col < connections[row].length-1; col++){
                    connections[row][col] = connections[row][col+1];
                }
                //set last column to false;
                connections[row][MAX_PATIENTS-1] = false;
            }

            //remove from recipients array
            recipients.remove(recipientID);
            updateIDs();
            System.out.println(name + " was removed from the organ transplant waitlist.");
        }
    }

    /**
     * Removes the specified Patient from the donors list.
     * This method must also update the connections adjacency matrix,
     * removing all connections to this donor,
     * and removing the row associated with the patient from the matrix.
     * @param name
     * Accepts a string name
     */
    public void removeDonor(String name){
        int donorID = 100;

        //find donor ID
        for(int i = 0; i < donors.size(); i++){
            Patient donor = donors.get(i);
            if(donor.getName().equals(name)){
                donorID = donor.getID();
                break;
            }
        }

        if(donorID != 100){
            //remove donor from connections and shift columns
            for(int row = donorID; row < connections.length - 1; row++){
                for(int col = 0; col < connections[row].length; col++){
                    connections[row][col] = connections[row+1][col];
                }
            }

            //remove from donor array
            donors.remove(donorID);
            updateIDs();
            System.out.println(name + " was removed from the organ donor list.");
        }
    }

    /**
     * Prints all organ recipients' information in a neatly formatted table.
     */
    public void printAllRecipients(){
        String str = "Index | Recipient Name     | Age | Organ Needed  | Blood Type | Donor IDs\n" +
                "=============================================================================";
        for(int i = 0; i < recipients.size(); i++){
            Patient recipient = recipients.get(i);
            String bloodtype = recipient.getBloodtype().getBloodType();
            int recipientID = recipient.getID();

            //find all connections
            String connectionIDs = "";
            for(int j = 0; j < MAX_PATIENTS; j++){
                if (connections[j][recipientID]){
                    connectionIDs += Integer.toString(j) + " ";
                }
            }
            str += String.format("\n%-6d| %-19s| %-4d| %-14s| %-11s| %-13s", i, recipient.getName(),recipient.getAge(), recipient.getOrgan(), bloodtype, connectionIDs);
        }
        System.out.println(str + "\n");
    }

    /**
     * Prints all organ donors' information in a neatly formatted table.
     */
    public void printAllDonors(){
        String str = "Index | Donor Name         | Age | Organ Donated | Blood Type | Recipient IDs\n" +
                "=============================================================================";
        for(int i = 0; i < donors.size(); i++){
            Patient donor = donors.get(i);
            String bloodtype = donor.getBloodtype().getBloodType();
            int donorID = donor.getID();

            String connectionIDs = "";
            for(int j = 0; j < MAX_PATIENTS; j++){
                if (connections[donorID][j]){
                    connectionIDs += Integer.toString(j) + " ";
                }
            }
            str += String.format("\n%-6d| %-19s| %-4d| %-14s| %-11s| %-13s", i, donor.getName(),donor.getAge(), donor.getOrgan(), bloodtype, connectionIDs);
        }
        System.out.println(str + "\n");
    }

    /**
     * Updates the IDs of patients in the recipient and donor arrays
     */
    public void updateIDs(){
        for(int i = 0; i < recipients.size(); i++){
            if(recipients.get(i) != null){
                Patient recipient = recipients.get(i);
                recipient.setID(i);
            }
        }
        for(int i = 0; i < donors.size(); i++){
            if(donors.get(i) != null){
                Patient donor = donors.get(i);
                donor.setID(i);
            }
        }
    }

    /**
     * Creates and returns a new TransplantGraph object
     * @param donorFile
     * Accepts a donorFile as a string
     * @param recipientFile
     * Accepts a recipientFile as a string
     * @return
     * Returns a new {@link TransplantGraph} object
     */
    public static TransplantGraph buildFromFiles(String donorFile, String recipientFile) throws IOException {
        TransplantGraph backupGraph = new TransplantGraph();
        backupGraph.readDonorFile(donorFile);
        backupGraph.readRecipientFile(recipientFile);
        return backupGraph;
    }

    /**
     * reads donor file
     * @param filename
     * @throws IOException
     */
    public void readDonorFile(String filename) throws IOException {
        FileInputStream fis = new FileInputStream(filename);
        InputStreamReader instream = new InputStreamReader(fis);
        BufferedReader reader = new BufferedReader(instream);
        String data;
        while((data = reader.readLine()) != null){
            Patient patient = new Patient();
            String[] dataArray = data.split(", ");
            patient.setID(Integer.parseInt(dataArray[0]));
            patient.setName(dataArray[1]);
            patient.setAge(Integer.parseInt(dataArray[2]));
            String organ = dataArray[3].toLowerCase();
            patient.setOrgan(organ);
            BloodType bloodType = new BloodType();
            bloodType.setBloodType(dataArray[4]);
            patient.setBloodtype(bloodType);
            addDonor(patient);
        }
    }

    /**
     * saves graph object in file
     * @throws IOException
     */
    public void saveInFile() throws IOException {
        FileOutputStream file = new FileOutputStream("transplant.obj");
        ObjectOutputStream fout = new ObjectOutputStream(file);
        fout.writeObject(this);
        fout.close();
    }

    /**
     * reads recipient file
     * @param filename
     * @throws IOException
     */
    public void readRecipientFile(String filename) throws IOException {
        FileInputStream fis = new FileInputStream(filename);
        InputStreamReader instream = new InputStreamReader(fis);
        BufferedReader reader = new BufferedReader(instream);
        String data;
        while((data = reader.readLine()) != null){
            Patient patient = new Patient();
            String[] dataArray = data.split(", ");
            patient.setID(Integer.parseInt(dataArray[0]));
            patient.setName(dataArray[1]);
            patient.setAge(Integer.parseInt(dataArray[2]));
            String organ = dataArray[3].toLowerCase();
            patient.setOrgan(organ);
            BloodType bloodType = new BloodType();
            bloodType.setBloodType(dataArray[4]);
            patient.setBloodtype(bloodType);
            addRecipient(patient);
        }
    }

    /**
     * updates connections 2d array
     */
    public void updateConnections(){
        if(donors.size() == 0 || recipients.size() == 0){
            return;
        }
        Patient donor = new Patient();
        Patient recipient = new Patient();

        for(int i = 0; i < donors.size(); i++){
            donor = donors.get(i);
            String donorOrgan = donor.getOrgan();
            BloodType donorBlood = donor.getBloodtype();
            for(int j = 0; j < recipients.size(); j++){
                 recipient = recipients.get(j);
                 String recipientOrgan = recipient.getOrgan();
                 BloodType recipientBlood = recipient.getBloodtype();

                //check connection
                boolean connection = false;
                if(BloodType.isCompatible(recipientBlood,donorBlood)){
                    if(donorOrgan.equals(recipientOrgan)){
                        connection = true;
                    }
                }
                connections[i][j] = connection;
            }
        }
    }

    /**
     * prints connections for testing
     */
    public void printConnections(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                System.out.print(connections[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * prints recipients sorted by blood type
     */
    public void printRecipientsSortedByBloodType(){
        String str = "Index | Recipient Name     | Age | Organ Needed  | Blood Type | Donor IDs\n" +
                "=============================================================================";

        //create new array sorted by blood type
        ArrayList<Patient> RbloodType = new ArrayList<>(recipients);
        RbloodType.sort(new BloodTypeComparator());

        for(int i = 0; i < RbloodType.size(); i++){
            Patient recipient = RbloodType.get(i);
            String bloodtype = recipient.getBloodtype().getBloodType();
            int recipientID = recipient.getID();

            //find all connections
            String connectionIDs = "";
            for(int j = 0; j < MAX_PATIENTS; j++){
                if (connections[j][recipientID]){
                    connectionIDs += Integer.toString(j) + " ";
                }
            }
            str += String.format("\n%-6d| %-19s| %-4d| %-14s| %-11s| %-13s", recipientID, recipient.getName(),recipient.getAge(), recipient.getOrgan(), bloodtype, connectionIDs);
        }
        System.out.println(str + "\n");
    }

    /**
     * prints recipients sorted by organ
     */
    public void printRecipientsSortedByOrgan(){
        String str = "Index | Recipient Name     | Age | Organ Needed  | Blood Type | Donor IDs\n" +
                "=============================================================================";

        //create new array sorted by blood type
        ArrayList<Patient> RbloodType = new ArrayList<>(recipients);
        RbloodType.sort(new OrganComparator());

        for(int i = 0; i < RbloodType.size(); i++){
            Patient recipient = RbloodType.get(i);
            String bloodtype = recipient.getBloodtype().getBloodType();
            int recipientID = recipient.getID();

            //find all connections
            String connectionIDs = "";
            for(int j = 0; j < MAX_PATIENTS; j++){
                if (connections[j][recipientID]){
                    connectionIDs += Integer.toString(j) + " ";
                }
            }
            str += String.format("\n%-6d| %-19s| %-4d| %-14s| %-11s| %-13s", recipientID, recipient.getName(),recipient.getAge(), recipient.getOrgan(), bloodtype, connectionIDs);
        }
        System.out.println(str + "\n");
    }

    /**
     * prints recipients sorted by connections
     */
    public void printRecipientSortedByConnections(){
        checkNumConnectionsForRecipients();
        String str = "Index | Recipient Name     | Age | Organ Needed  | Blood Type | Donor IDs\n" +
                "=============================================================================";

        //create new array sorted by blood type
        ArrayList<Patient> Rconnections = new ArrayList<>(recipients);
        Rconnections.sort(new NumConnectionsComparator());

        for(int i = 0; i < Rconnections.size(); i++){
            Patient recipient = Rconnections.get(i);
            String bloodtype = recipient.getBloodtype().getBloodType();
            int recipientID = recipient.getID();

            //find all connections
            String connectionIDs = "";
            for(int j = 0; j < MAX_PATIENTS; j++){
                if (connections[j][recipientID]){
                    connectionIDs += Integer.toString(j) + " ";
                }
            }
            str += String.format("\n%-6d| %-19s| %-4d| %-14s| %-11s| %-13s", recipientID, recipient.getName(),recipient.getAge(), recipient.getOrgan(), bloodtype, connectionIDs);
        }
        System.out.println(str + "\n");
    }

    /**
     * prints donors sorted by blood type
     */
    public void printDonorsSortedByBloodType(){
        String str = "Index | Donor Name         | Age | Organ Donated | Blood Type | Recipient IDs\n" +
                "=============================================================================";

        //create new array sorted by blood type
        ArrayList<Patient> DbloodType = new ArrayList<>(donors);
        DbloodType.sort(new BloodTypeComparator());

        for(int i = 0; i < DbloodType.size(); i++){
            Patient donor = DbloodType.get(i);
            String bloodtype = donor.getBloodtype().getBloodType();
            int donorID = donor.getID();

            String connectionIDs = "";
            for(int j = 0; j < MAX_PATIENTS; j++){
                if (connections[donorID][j]){
                    connectionIDs += Integer.toString(j) + " ";
                }
            }
            str += String.format("\n%-6d| %-19s| %-4d| %-14s| %-11s| %-13s", donorID, donor.getName(),donor.getAge(), donor.getOrgan(), bloodtype, connectionIDs);
        }
        System.out.println(str + "\n");
    }

    /**
     * prints donors sorted by organ
     */
    public void printDonorsSortedByOrgan(){
        String str = "Index | Donor Name         | Age | Organ Donated | Blood Type | Recipient IDs\n" +
                "=============================================================================";

        //create new array sorted by blood type
        ArrayList<Patient> DbloodType = new ArrayList<>(donors);
        DbloodType.sort(new OrganComparator());

        for(int i = 0; i < DbloodType.size(); i++){
            Patient donor = DbloodType.get(i);
            String bloodtype = donor.getBloodtype().getBloodType();
            int donorID = donor.getID();

            String connectionIDs = "";
            for(int j = 0; j < MAX_PATIENTS; j++){
                if (connections[donorID][j]){
                    connectionIDs += Integer.toString(j) + " ";
                }
            }
            str += String.format("\n%-6d| %-19s| %-4d| %-14s| %-11s| %-13s", donorID, donor.getName(),donor.getAge(), donor.getOrgan(), bloodtype, connectionIDs);
        }
        System.out.println(str + "\n");
    }

    /**
     * prints donors sorted by connections
     */
    public void  printDonorsSortedByConnections(){
        checkNumConnectionsForDonors();
        String str = "Index | Donor Name         | Age | Organ Donated | Blood Type | Recipient IDs\n" +
                "=============================================================================";

        //create new array sorted by blood type
        ArrayList<Patient> Dconnections = new ArrayList<>(donors);
        Dconnections.sort(new NumConnectionsComparator());

        for(int i = 0; i < Dconnections.size(); i++){
            Patient donor = Dconnections.get(i);
            String bloodtype = donor.getBloodtype().getBloodType();
            int donorID = donor.getID();

            String connectionIDs = "";
            for(int j = 0; j < MAX_PATIENTS; j++){
                if (connections[donorID][j]){
                    connectionIDs += Integer.toString(j) + " ";
                }
            }
            str += String.format("\n%-6d| %-19s| %-4d| %-14s| %-11s| %-13s", donorID, donor.getName(),donor.getAge(), donor.getOrgan(), bloodtype, connectionIDs);
        }
        System.out.println(str + "\n");
    }

    /**
     * counts the number of connections for each recipient
     */
    public void checkNumConnectionsForRecipients() {
        for (int i = 0; i < recipients.size(); i++) {
            int numConnections = 0;
            Patient recipient = recipients.get(i);
            int recipientID = recipient.getID();

            //find all connections
            for (int j = 0; j < MAX_PATIENTS; j++) {
                if (connections[j][recipientID]) {
                   numConnections++;
                }
            }
            recipient.setNumConnections(numConnections);
        }
    }

    /**
     * counts the number of connections for each donor
     */
    public void checkNumConnectionsForDonors() {
        for (int i = 0; i < donors.size(); i++) {
            int numConnections = 0;
            Patient donor = donors.get(i);
            int donorID = donor.getID();

            String connectionIDs = "";
            for (int j = 0; j < MAX_PATIENTS; j++) {
                if (connections[donorID][j]) {
                    numConnections++;
                }
            }
            donor.setNumConnections(numConnections);
        }
    }


}
