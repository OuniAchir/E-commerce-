package projet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Agent {
    private String name;
    private int[] offers;

    public Agent(String name, int numItems) {
        this.name = name;
        this.offers = new int[numItems];
    }

    public String getName() {
        return name;
    }

    public int[] getOffers() {
        return offers;
    }

    public void generateRandomOffers(int maxOffer) {
        Random random = new Random();
        for (int i = 0; i < offers.length; i++) {
            offers[i] = random.nextInt(maxOffer + 1);
        }
    }
}

class Seller {
    private int[] items;
    private List<Agent> buyers;

    public Seller(int numItems, List<Agent> buyers) {
        this.items = new int[numItems];
        this.buyers = buyers;
    }

    public void generateRandomItems(int maxItemQuantity) {
        Random random = new Random();
        for (int i = 0; i < items.length; i++) {
            items[i] = random.nextInt(maxItemQuantity + 1);
        }
    }

    public void runAuctions(int numIterations) {
        for (int iteration = 1; iteration <= numIterations; iteration++) {
            System.out.println("Iteration: " + iteration);
            System.out.println("Current items: " + arrayToString(items));

            for (Agent buyer : buyers) {
                int[] offers = buyer.getOffers();
                int[] winningOffers = new int[offers.length];

                for (int i = 0; i < offers.length; i++) {
                    winningOffers[i] = items[i] >= offers[i] ? offers[i] : items[i];
                    items[i] -= winningOffers[i];
                }

                System.out.println("Buyer: " + buyer.getName());
                System.out.println("Offers: " + arrayToString(offers));
                System.out.println("Winning offers: " + arrayToString(winningOffers));
                System.out.println("Remaining items: " + arrayToString(items));
                System.out.println();
            }
        }
    }

    private String arrayToString(int[] array) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }

	public int[] getItems() {
		// TODO Auto-generated method stub
		return null;
	}
}

class GeneticAlgorithm {
    private static final int POPULATION_SIZE = 50;
    private static final int MAX_GENERATIONS = 100;
    private static final double MUTATION_RATE = 0.1;

    public static void main(String[] args) {
        int numItems = 3;
        int numBuyers = 5;
        int maxItemQuantity = 10;
        int maxOffer = 5;

        List<Agent> buyers = new ArrayList<>();
        for (int i = 1; i <= numBuyers; i++) {
            buyers.add(new Agent("Buyer " + i, numItems));
        }

        Seller seller = new Seller(numItems, buyers);
        seller.generateRandomItems(maxItemQuantity);

        for (Agent buyer : buyers) {
            buyer.generateRandomOffers(maxOffer);
        }

        List<Agent> bestSolution = runGeneticAlgorithm(seller, buyers);

        System.out.println("Best solution:");
        for (Agent buyer : bestSolution) {
            System.out.println("Buyer: " + buyer.getName());
            System.out.println("Offers: " + arrayToString(buyer.getOffers()));
        }
    }

    private static List<Agent> runGeneticAlgorithm(Seller seller, List<Agent> buyers) {
        List<List<Agent>> population = generateInitialPopulation(buyers, POPULATION_SIZE);
        int generation = 0;

        while (generation < MAX_GENERATIONS) {
            List<List<Agent>> newPopulation = new ArrayList<>();

            for (int i = 0; i < POPULATION_SIZE; i++) {
                List<Agent> parent1 = selectParent(population);
                List<Agent> parent2 = selectParent(population);

                List<Agent> offspring = crossover(parent1, parent2);
                mutate(offspring, MUTATION_RATE);

                newPopulation.add(offspring);
            }

            population = newPopulation;
            generation++;
        }

        return getBestSolution(population, seller);
    }

    private static List<List<Agent>> generateInitialPopulation(List<Agent> buyers, int populationSize) {
        List<List<Agent>> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            List<Agent> solution = new ArrayList<>(buyers);
            Collections.shuffle(solution);
            population.add(solution);
        }

        return population;
    }

    private static List<Agent> selectParent(List<List<Agent>> population) {
        Random random = new Random();
        int index = random.nextInt(population.size());
        return population.get(index);
    }

    private static List<Agent> crossover(List<Agent> parent1, List<Agent> parent2) {
        int crossoverPoint = parent1.size() / 2;
        List<Agent> offspring = new ArrayList<>(parent1.subList(0, crossoverPoint));

        for (Agent agent : parent2) {
            if (!offspring.contains(agent)) {
                offspring.add(agent);
            }
        }

        return offspring;
    }

    private static void mutate(List<Agent> solution, double mutationRate) {
        Random random = new Random();

        for (int i = 0; i < solution.size(); i++) {
            if (random.nextDouble() < mutationRate) {
                int j = random.nextInt(solution.size());
                Collections.swap(solution, i, j);
            }
        }
    }

    private static List<Agent> getBestSolution(List<List<Agent>> population, Seller seller) {
        List<Agent> bestSolution = null;
        int bestFitness = Integer.MAX_VALUE;

        for (List<Agent> solution : population) {
            seller.runAuctions(1); // Run the auction for one iteration using the current solution

            int fitness = calculateFitness(seller);

            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestSolution = new ArrayList<>(solution);
            }
        }

        return bestSolution;
    }

    private static int calculateFitness(Seller seller) {
        int totalUnsoldItems = 0;

        for (int item : seller.getItems()) {
            totalUnsoldItems += item;
        }

        return totalUnsoldItems;
    }

    private static String arrayToString(int[] array) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
