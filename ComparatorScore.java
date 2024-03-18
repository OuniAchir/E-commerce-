package projet;

import java.util.Comparator;

public class ComparatorScore implements Comparator<Noeud> {
	    public int compare(Noeud r1, Noeud r2) {
	        if (r1.getScore() < r2.getScore()) {
	            return -1;
	        } else if (r1.getScore() > r2.getScore()) {
	            return 1;
	        } else {
	            return 0;
	        }
	    }
	
}