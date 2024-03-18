package projet;

import java.util.ArrayList;

public class Noeud {

	ArrayList<Offre> offres=new ArrayList<>();
	double prixTotal;
	double score;
	ArrayList<String> Acheteurs;
	
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public ArrayList<Offre> getOffres() {
		return offres;
	}
	public void setOffres(ArrayList<Offre> offres) {
		this.offres = offres;
	}
	public double getPrixTotal() {
		double prixTotal=0;
		for(int i=0;i<offres.size();i++)
		{
			prixTotal =+ offres.get(i).prix;
		}
		return prixTotal;
	}
	public void setPrixTotal(double prixTotal) {
		this.prixTotal = prixTotal;
	}
	
	public void add(Offre offre) {
		//this.offres.add(offre);
		for(int i=0;i<offre.offre.size();i++)
		{
		this.offres.add(offre);
		}
	}
	
}
