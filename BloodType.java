import java.io.Serializable;

/**
 * contain a String member variable bloodType to denote a specific blood type, and a static method which can be invoked to determine if two blood types are compatible with each other.
 */
public class BloodType implements Serializable {
    private String bloodType;

    /**
     * Getter for blood type
     * @return
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * setter for bloodtype
     * @param bloodType
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * No arg constructor
     */
    public BloodType(){}

    /**
     * Constructor that accepts bloodtype
     * @param bloodType
     */
    public BloodType(String bloodType){this.bloodType = bloodType;}

    /**
     * Determines whether two blood types are compatible
     * @param recipient
     * Accepts the recipient's blood type
     * @param donor
     * Accepts the donor's blood type
     * @return
     * Returns true if the blood type is compatible, and false otherwise
     */
    public static boolean isCompatible(BloodType recipient, BloodType donor){
        if(donor.bloodType.equals("O") || recipient.bloodType.equals("AB")){
            return true;
        }
        else if(recipient.bloodType.equals("A") && donor.bloodType.equals("A")){
            return true;
        }
        else if(recipient.bloodType.equals("B") && donor.bloodType.equals("B")){
            return true;
        }
        else{
            return false;
        }
    }

}
