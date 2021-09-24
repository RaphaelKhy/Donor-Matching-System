import java.util.Comparator;

/**
 * Comparator class for num connections
 */
public class NumConnectionsComparator implements Comparator<Patient> {

    @Override
    public int compare(Patient p1, Patient p2) {
        return p1.getNumConnections() - (p2.getNumConnections());
    }
}
