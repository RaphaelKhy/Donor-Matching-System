import java.util.Comparator;

/**
 * comparator class for organs
 */
public class OrganComparator implements Comparator<Patient>{

    @Override
    public int compare(Patient p1, Patient p2) {
        return (p1.getOrgan().compareTo(p2.getOrgan()));
    }
}
