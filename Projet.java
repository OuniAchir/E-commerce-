package projet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Projet {

	
	

    private static final int TAILLE_POPULATION = 50;
    private static final int TAILLE_NOEUD = 2;
    private static final int NB_GENERATIONS = 100;
    private static final double TAUX_MUTATION = 0.1;
    static ArrayList<Offre> Noeud =new ArrayList<>();
    static ArrayList<Offre> Offres =new ArrayList<>();
    
  
    
    ArrayList<Integer> offree=new ArrayList<>();
	ArrayList<Integer> offre1=new ArrayList<>();
	
	public Projet()
	{
		ArrayList<Integer> offree=new ArrayList<>();
		ArrayList<Integer> offre1=new ArrayList<>();
		
		offree.add(2);
		offree.add(6);
		offree.add(4);
		offree.add(9);
		
		offre1.add(8);
		offre1.add(5);
		offre1.add(1);
		offre1.add(3);
		
		Offre o=new Offre(offree, 90);
		/*for(int i=0; i<offree.size();i++)
		{
			o.offre.add(offree.get(i));
		}	*/
	
		Offre l=new Offre(offre1,17);
		
	
		

		
		Projet.Offres.add(o);
		Projet.Offres.add(l);
		

	}


    
	public static ArrayList<Noeud> generationPop ()
	{
		
		ArrayList<Noeud> pop = new ArrayList<>();
		 Noeud e=new Noeud();
		 
		 
		for(int i=0;i<TAILLE_POPULATION;i++) 
		{
			
			//Offre individu = new Offre();
			Set<Integer> objetsAttribues = new HashSet<>();
			ArrayList<Integer> bin=new ArrayList<>();
			
			
			for (int j = 0; j < TAILLE_NOEUD; j++) 
			{
				//Controle des objets proposés par chaque acheteurs
				for(int k=0;k<8;k++) {bin.add(0);}
				
                int objet =  (int) (Math.random() * Offres.size()-1);

                // Verifier si l'objet a déja été attribué
                while (objetsAttribues.contains(objet)) {
                	  objet = (int) (Math.random() *Offres.size()-1);
                }
               
               //Mettre les objets de  l'offre choisie a 1  
               for(int k=0;k<4;k++) 
               {
            	   bin.add(Offres.get(objet).offre.get(k),1);
               }
                
               //Tester par rapport aux objets de chaque acheteur
               int k=0;
               while( k < Offres.get(objet).offre.size())
                {
                	if(bin.get(Offres.get(objet).offre.get(k)) == 1)
                	{
                		objet = (int) (Math.random() * Offres.size() -1);  
                	}
                	k++;
                }
               System.out.println("%%%%%%%%%%%%%%%%%%%%%%%"+Offres.size());
               e.add(Offres.get(objet));
               System.out.println("%%%%%%%%%%%%%%%%%%%%%%%"+Offres.size());

               
                //ajouter l'objet attribué a la liste pour qu'il ne soit pas attribué une autre fois au meme noeud 
                objetsAttribues.add(objet);
        		System.out.println("genere");
    			System.out.println("ppppppppppppppp");

            }
			System.out.println("ppppppppppppppp");

			pop.add(i,e);
			System.out.println("ppppppppppppppp");

			//System.out.println(pop);
		}
		System.out.println("*********************");
		return pop;
	}
	
	public static void evaluerPopulation(ArrayList<Noeud> pop ) {
        for (int i=0;i< pop.size();i++) {
            double offreTotale = calculerOffreTotale(pop.get(i));
            double score = 1.0 / (offreTotale + 1);
            pop.get(i).score=score * 100;
        }
    }

	public static double calculerOffreTotale(Noeud d) {
        double offreTotale = 0;
        for (int i = 0; i <TAILLE_NOEUD; i++) {
            offreTotale += d.getPrixTotal();
        }
        return offreTotale;
    }
	
	public static ArrayList<Noeud> selectionnerParents(ArrayList<Noeud> pop) {
    	ArrayList<Noeud>  parents=new ArrayList<>();
    	Collections.sort(pop, new ComparatorScore());
    	
        for (int i = 0; i < TAILLE_POPULATION/2; i++) {
           parents.add(pop.get(i));
        }
        return parents;
    }
    
	public static ArrayList<Noeud> croisement(ArrayList<Noeud> parents)
	{
		
		ArrayList<Noeud> enfants = new ArrayList<>();

	        for (int i = 0; i < parents.size(); i += 2) {
	            Noeud enfant1 = parents.get(i);
	            Noeud enfant2 = parents.get(i + 1);

	            // Point de croisement
	            
	            int pointCroisement = (int) (Math.random() * (TAILLE_NOEUD - 1));

	            
	            while(pointCroisement == 0) 
	            {
	            	pointCroisement = (int) (Math.random() * (TAILLE_NOEUD - 1));
	            }
	            
	            // Création des enfants
	        	   Offre o1=new Offre();
	        	   Offre o2=new Offre();
	        	   
	        	   
	        	   for(int k=0; k<enfant1.offres.get(pointCroisement).offre.size();k++)
	        	   {
	        		   o1.offre.add(enfant1.offres.get(pointCroisement).offre.get(k));
	        		   o1.prix=enfant1.offres.get(pointCroisement).prix;
	        	   }
	        	   
	        	   for(int k=0; k<enfant2.offres.get(pointCroisement).offre.size();k++)
	        	   {
	        		   o2.offre.add(enfant2.offres.get(pointCroisement).offre.get(k));
	        		   o2.prix=enfant2.offres.get(pointCroisement).prix;
	        	   }
	        	   
	        	   
	        	   enfant1.offres.remove(pointCroisement);
	        	   enfant1.offres.add(pointCroisement,o2);
	        	   enfant1.setPrixTotal(calculerOffreTotale(enfant1));
	        	   
	        	   
	        	   enfant2.offres.remove(pointCroisement);
	        	   enfant2.offres.add(pointCroisement,o1);
	        	   enfant2.setPrixTotal(calculerOffreTotale(enfant2));
	        	   
	        	   enfants.add(enfant1);
	        	   enfants.add(enfant2);
	        	   
	        }
	        return enfants;
	}
    
	public static ArrayList<Noeud> mutation(ArrayList<Noeud> enfantsC)
	{
		ArrayList<Noeud> enfantsM=new ArrayList<>();
		ArrayList<Integer> bin=new ArrayList<>();
		Set<Integer> objetsAttribues = new HashSet<>();
		
		//Controle des objets proposés par chaque acheteurs
		for(int l=0;l<100;l++) {bin.add(0);}
		
		for(int i=0; i<enfantsC.size();i++)
		{
			for(int j=0; j<enfantsC.get(i).offres.size();j++)
			{
				//Générer deux indices Aléatoirement pour chaque  noeud
				Random rand1=new Random();
				int r1= rand1.nextInt(enfantsC.size() -1);
				
				int objet =  (int) (Math.random() * Offres.size());
				
				while (objet == r1) { objet =  (int) (Math.random() * Offres.size()); }
				
				for (int k = 0; k < TAILLE_NOEUD; k++) 
				{
					//Mettre les objets de  l'offre choisie a 1  
		               for(int l=0;l<100;l++) 
		               {
		            	   bin.add(Offres.get(objet).offre.get(l),1);
		               }
	                // Verifier si l'objet a déja été attribué
	                while (objetsAttribues.contains(objet)) 
	                {
	                	  objet = (int) (Math.random() * Offres.size());
	                } 
				}

				//Tester par rapport aux objets de chaque acheteur
	               int k=0;
	               while( k < Offres.get(objet).offre.size())
	                {
	                	if(bin.get(Offres.get(objet).offre.get(k)) == 1)
	                	{
	                		objet = (int) (Math.random() * Offres.size());  
	                	}
	                	k++;
	                }

				enfantsC.get(i).offres.remove(r1);
				enfantsC.get(i).offres.add(r1,Offres.get(objet));
			
			}
			
			enfantsM.add(enfantsC.get(i));
		}
		
		return enfantsM;
	}
	
	
	public static Noeud MeilleureIndividue (ArrayList<Noeud> pop)
	{
		Noeud meilleur =pop.get(0);
		evaluerPopulation(pop);
		
		for(int i=1; i<pop.size();i++)
		{
			if(meilleur.getScore() < pop.get(i).getScore())
			{
				meilleur=pop.get(i);
			}
		}
		
		System.out.println("La meilleure solution est = " +meilleur.offres);
		
		return meilleur ;
		
	}
	
	public ArrayList<Noeud> remplacement (ArrayList<Noeud> pop ,ArrayList<Noeud> enfantsC,ArrayList<Noeud> enfantsM)
	{
		ArrayList<Noeud> nouveauxIndividus = new ArrayList<>();
	    nouveauxIndividus.addAll(enfantsC);
	    nouveauxIndividus.addAll(enfantsM);
	    
	    // Trie les nouveaux individus par ordre croissant de leur valeur de score
	    Collections.sort(nouveauxIndividus, new ComparatorScore());
	    
	    int taille=enfantsC.size()+enfantsM.size();
	    // Remplace les individus les moins performants par les nouveaux individus
	    for (int i = 0; i < taille; i++) {
	        pop.set(i, nouveauxIndividus.get(i));
	    }
		return pop;
		
	}
	
	public Noeud algo_genetique()
	{
		
		//Noeud d=null;
		ArrayList<Noeud> pop=new ArrayList<>();
		ArrayList<Noeud> parents=new ArrayList<>();
		ArrayList<Noeud> enfantsC=new ArrayList<>();
		ArrayList<Noeud> enfantsM;//=new ArrayList<>();

		pop=generationPop();
		
		System.out.println("genere");
		evaluerPopulation(pop);
		System.out.println("evaluer");

		
		for(int i=1;i<NB_GENERATIONS;i++)
		{
			parents=selectionnerParents(pop);
			System.out.println("************************");
			enfantsC=croisement(parents);
			System.out.println("************************");

			enfantsM=mutation(enfantsC);
			System.out.println("************************");

			evaluerPopulation(enfantsC);
			evaluerPopulation(enfantsM);
			pop=remplacement(pop,enfantsC,enfantsM);
		}
		
		return MeilleureIndividue(pop);
	}
	
	public static void main (String[] args) 
	{
		Projet p=new Projet();
		
		p.algo_genetique();
		
	}
	
}
