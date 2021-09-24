import java.io.Serializable;

/**
 * represents an active organ donor or recipient in the database.
 */
public class Patient implements Comparable, Serializable {
    private String name;
    private String organ;
    private int age;
    private BloodType bloodtype;
    private int ID;
    private boolean isDonor;
    private int numConnections = 0;

    /**
     * setter for name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * setter for organ
     * @param organ
     */
    public void setOrgan(String organ) {
        this.organ = organ;
    }

    /**
     * getter for organ
     * @return
     */
    public String getOrgan() {
        return organ;
    }

    /**
     * setter for age
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * getter for age
     * @return
     */
    public int getAge(){
        return age;
    }

    /**
     * setter for bloodtype
     * @param bloodtype
     */
    public void setBloodtype(BloodType bloodtype) {
        this.bloodtype = bloodtype;
    }

    /**
     * getter for bloodtype
     * @return
     */
    public BloodType getBloodtype() {
        return bloodtype;
    }

    /**
     * setter for id
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * getter for id
     * @return
     */
    public int getID() {
        return ID;
    }

    /**
     * setter for donor
     * @param donor
     */
    public void setDonor(boolean donor) {
        isDonor = donor;
    }

    /**
     * Getter for donor
     * @return
     */
    public boolean getDonor(){
        return isDonor;
    }

    /**
     * Setter for num connections
     * @param numConnections
     */
    public void setNumConnections(int numConnections) {
        this.numConnections = numConnections;
    }

    /**
     * getter for num connections
     * @return
     */
    public int getNumConnections() {
        return numConnections;
    }

    /**
     * no arg constructor
     */
    public Patient(){}

    /**
     * Constructor for Patient with all paramaters
     * @param isDonor
     * @param name
     * @param organ
     * @param age
     * @param bloodType
     */
    public Patient( boolean isDonor, String name, String organ, int age, BloodType bloodType){
        this.isDonor = isDonor;
        this.name = name;
        this.organ = organ;
        this.age = age;
        this.bloodtype = bloodType;
    }

    /**
     *  Compares this Patient object to o, comparing by ID such that the values should be sorted in ascending order.
     * @param o
     * @return
     */
    public int compareTo(Object o){
        return 0;
    }

    /**
     * return string representation of patient object
     * @return
     */
    public String toString(){
        return "Name:" + name;
    }
}
