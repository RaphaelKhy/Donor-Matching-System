import java.util.Comparator;

/**
 * Comparator class for Bloodtype
 */
public class BloodTypeComparator implements Comparator<Patient>{

    @Override
    public int compare(Patient p1, Patient p2) {
        return (p1.getBloodtype().getBloodType().compareTo(p2.getBloodtype().getBloodType()));
    }
}