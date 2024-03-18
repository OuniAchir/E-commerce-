package projet;
import java.util.*;

public class EncheresCombinatoires {

    private static final int TAILLE_POPULATION = 50;
    private static final int TAILLE_INDIVIDU = 10;
    private static final int NB_GENERATIONS = 100;
    private static final double TAUX_MUTATION = 0.1;

    private static final int[][] OFFRES = {
            {10, 20, 30, 40, 50, 60, 70, 80, 90, 100},
            {15, 25, 35, 45, 55, 65, 75, 85, 95, 105},
            {17, 27, 37, 47, 57, 67, 77, 87, 97, 107},
            {8, 18, 28, 38, 48, 58, 68, 78, 88, 98},
            {12, 22, 32, 42, 52, 62, 72, 82, 92, 102}
    };

   

    private static List<int[]> genererPopulation() {
        List<int[]> population = new ArrayList<>();

        for (int i = 0; i < TAILLE_POPULATION; i++) {
            int[] individu = new int[TAILLE_INDIVIDU];
            Set<Integer> objetsAttribues = new HashSet<>();

            for (int j = 0; j < TAILLE_INDIVIDU; j++) {
                int objet = (int) (Math.random() * OFFRES[j].length);

                // Vérifier si l'objet a déjà été attribué
                while (objetsAttribues.contains(objet)) {
                    objet = (int) (Math.random() * OFFRES[j].length);
                }

                individu[j] = objet;
                objetsAttribues.add(objet);
            }

            population.add(individu);
        }

        return population;
    }

    private static void evaluerPopulation(List<int[]> population) {
        for (int[] individu : population) {
            int offreTotale = calculerOffreTotale(individu);
            double score = 1.0 / (offreTotale + 1);
            individu[individu.length -1] = (int) (score * 100);
        }
    }

    private static int calculerOffreTotale(int[] individu) {
        int offreTotale = 0;
        for (int i = 0; i < individu.length; i++) {
            offreTotale += OFFRES[i][individu[i]];
        }
        return offreTotale;
    }

    private static List<int[]> selectionnerParents(List<int[]> population) {
        List<int[]> parents = new ArrayList<>();

        for (int i = 0; i < TAILLE_POPULATION; i++) {
            int index1 = (int) (Math.random() * TAILLE_POPULATION);
            int index2 = (int) (Math.random() * TAILLE_POPULATION);

            int[] parent1 = population.get(index1);
            int[] parent2 = population.get(index2);

            if (parent1[parent1.length - 1] > parent2[parent2.length - 1]) {
                parents.add(parent1);
            } else {
                parents.add(parent2);
            }
        }

        return parents;
    }

    private static List<int[]> reproduire(List<int[]> parents) {
        List<int[]> enfants = new ArrayList<>();

        for (int i = 0; i < TAILLE_POPULATION; i += 2) {
            int[] parent1 = parents.get(i);
            int[] parent2 = parents.get(i + 1);

            int[] enfant1 = new int[TAILLE_INDIVIDU];
            int[] enfant2 = new int[TAILLE_INDIVIDU];

            // Point de croisement
            int pointCroisement = (int) (Math.random() * (TAILLE_INDIVIDU - 1)) + 1;

            // Création des enfants
            for (int j = 0; j < TAILLE_INDIVIDU; j++) {
                if (j < pointCroisement) {
                    enfant1[j] = parent1[j];
                    enfant2[j] = parent2[j];
                } else {
                    enfant1[j] = parent2[j];
                    enfant2[j] = parent1[j];
                }
            }

            enfants.add(enfant1);
            enfants.add(enfant2);
        }

        return enfants;
    }

    private static void muter(List<int[]> population) {
        for (int[] individu : population) {
            if (Math.random() < TAUX_MUTATION) {
                int indexMutation = (int) (Math.random() * (TAILLE_INDIVIDU - 1));
                individu[indexMutation] = (int) (Math.random() * OFFRES[indexMutation].length);
            }
        }
    }

    private static int[] trouverMeilleurIndividu(List<int[]> population) {
        int[] meilleurIndividu = population.get(0);
        int meilleurScore = meilleurIndividu[meilleurIndividu.length - 1];

        for (int[] individu : population) {
            int score = individu[individu.length - 1];
            if (score > meilleurScore) {
                meilleurIndividu = individu;
                meilleurScore = score;
            }
        }

        return Arrays.copyOf(meilleurIndividu, TAILLE_INDIVIDU);
    }
    
    public static void main(String[] args) {
        // Générer la population initiale
        List<int[]> population = genererPopulation();

        for (int generation = 1; generation <= NB_GENERATIONS; generation++) 
        {
            System.out.println("Génération " + generation);

            // Évaluer la population
            evaluerPopulation(population);

            // Sélectionner les parents
            List<int[]> parents = selectionnerParents(population);

            // Reproduction
            List<int[]> enfants = reproduire(parents);

            // Mutation
            muter(enfants);

            // Remplacer la population par les enfants
            population = enfants;
        }

        // Évaluer la population finale
        evaluerPopulation(population);

        // Trouver la meilleure solution
        int[] meilleurIndividu = trouverMeilleurIndividu(population);
        int meilleureOffre = calculerOffreTotale(meilleurIndividu);

        System.out.println("\nMeilleure offre trouvée : " + meilleureOffre);
        System.out.println("Répartition des enchères : " + Arrays.toString(meilleurIndividu));
    }
}

