package projet;

import java.util.ArrayList;

public class Offre {

	ArrayList<Integer> offre =new ArrayList<>();
	double prix;
	
	public double getPrix() {
		return prix;
	}

	public Offre(ArrayList<Integer> offre, double prix) {
		super();
		this.offre = offre;
		this.prix = prix;
	}
	
	
	public Offre() {
		super();
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public ArrayList<Integer> getOffre() {
		return offre;
	}

	public void setOffre(ArrayList<Integer> offre) {
		this.offre = offre;
	}
	
	
	
}
