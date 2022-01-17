package com.jampetz.sequenceSearchGA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {
    Random random = new Random();

    /**
     * Method that runs genetic algorithm
     *
     * @param inputData initialization info
     * @param filename  path to results file
     */
    void runEvolution(InputData inputData, String filename) {
        Population population = generateInitialPopulation(inputData);

        cleanup(population);

        quickSort(population.getCurrentPopulation(), 0, population.getCurrentPopulation().size() - 1);

        System.out.println("(Fit count, best result): ["
                + population.getCurrentPopulation().size() + ", "
                + population.getCurrentPopulation().get(0).getFitnessScore() + "]");

        boolean success = false;
        int generationCount = 1;

        if (population.getCurrentPopulation().get(0).getFitnessScore() >= population.getTarget()) {
            success = true;
            displayResult("Success!!!", generationCount, population, filename);
        } else {
            while (generationCount < population.getGenerationCount() + 1) {
                generationCount++;

                population.setCurrentPopulation(crossoverProcess(selection(population), population));

                cleanup(population);

                quickSort(population.getCurrentPopulation(), 0, population.getCurrentPopulation().size() - 1);

                System.out.println("[" + population.getCurrentPopulation().size() + ", " + population.getCurrentPopulation().get(0).getFitnessScore() + "]");

                if (population.getCurrentPopulation().get(0).getFitnessScore() >= population.getTarget()) {
                    success = true;
                    displayResult("Success!!!", generationCount, population, filename);
                    break;
                }
            }
        }

        if (!success) {
            displayResult("Iteration count exceeded", population.getGenerationCount() + 1, population, filename);
        }

    }

    /**
     * @param inputData initialization info
     * @return population's first generation
     */
    Population generateInitialPopulation(InputData inputData) {
        Population population = new Population(inputData);

        int chromosomeSize = 0;
        List<List<Integer>> randomRange = new ArrayList<>();

        for (int i = 0; i < inputData.getMiningAreaProperties().size(); i++) {
            randomRange.add(new ArrayList<>());
            for (int j = 0; j < inputData.getMiningAreaProperties().get(i).getCharge().size(); j++) {
                randomRange.get(i).add((int)
                        (inputData.getMiningAreaProperties().get(i).getLengthOfMiningArea()
                                / inputData.getMiningAreaProperties().get(i).getCharge().get(j).getRange()));
                chromosomeSize++;
            }
        }
        population.setRandomRange(randomRange);
        population.setChromosomeSize(chromosomeSize);

        for (int i = 0; i < population.getPopulationSize(); i++) {
            Property chromosome = new Property();
            for (List<Integer> rdmRange : population.getRandomRange()) {
                List<Integer> segment = new ArrayList<>();
                for (Integer value : rdmRange) {
                    segment.add(random.nextInt(1 + (int) (value * population.getInitialRandomWeights())));
                }
                chromosome.getChromosome().add(segment);
            }
            chromosome.setFitnessScore(getFitnessValue(chromosome.getChromosome(), population));
            population.getCurrentPopulation().add(chromosome);
        }

        return population;
    }

    /**
     * @param chromosome whose fitness is evaluated
     * @param population contains info needed for fitness evaluation
     * @return fitness score for specified chromosome
     */
    double getFitnessValue(List<List<Integer>> chromosome, Population population) {
        double sumWeightValue = 0;
        double sumOreValue = 0;
        double sumOversizeChunk = 0;

        for (int i = 0; i < population.getMiningAreaProperties().size(); i++) {
            double sectionCovered = 0;
            for (int j = 0; j < population.getMiningAreaProperties().get(i).getCharge().size(); j++) {
                sectionCovered += chromosome.get(i).get(j) * population.getMiningAreaProperties().get(i).getCharge().get(j).getRange();
                sumWeightValue += chromosome.get(i).get(j) * population.getMiningAreaProperties().get(i).getCharge().get(j).getWeight();
                sumOreValue += chromosome.get(i).get(j) * population.getMiningAreaProperties().get(i).getCharge().get(j).getAmountOfOre();
                sumOversizeChunk += chromosome.get(i).get(j) * population.getMiningAreaProperties().get(i).getCharge().get(j).getAmountOfOre() * population.getMiningAreaProperties().get(i).getCharge().get(j).getOversizeChunkPercentage();
            }
            if (sectionCovered > population.getMiningAreaProperties().get(i).getLengthOfMiningArea() || sumWeightValue > population.getWeightLimit()) {
                return 0;
            }
        }

        if (sumOversizeChunk / sumOreValue > population.getOversizeChunkLimit()) {
            return 0;
        }

        return sumOreValue;
    }

    /**
     * Chromosome whose fitness value is zero gets deleted
     *
     * @param population list of chromosomes with evaluated fitness score
     */
    void cleanup(Population population) {
        population.setCurrentPopulation(population.getCurrentPopulation().stream().filter(list -> list.getFitnessScore() != 0d).toList());
    }

    /**
     * com.jampetz.sequenceSearchGA.Main sorting method
     *
     * @param population list of chromosomes
     * @param low        starting index
     * @param high       ending index
     */
    void quickSort(List<Property> population, int low, int high) {
        if (low < high) {
            int pi = partition(population, low, high);
            quickSort(population, low, pi - 1);
            quickSort(population, pi + 1, high);
        }
    }

    /**
     * Method takes last element as pivot,
     * places the pivot element at its correct
     * position in sorted list
     *
     * @return next element to partition around
     */
    int partition(List<Property> population, int low, int high) {
        double pivot = population.get(high).getFitnessScore();
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {
            if (population.get(j).getFitnessScore() > pivot) {
                i++;
                swap(population, i, j);
            }
        }
        swap(population, i + 1, high);
        return (i + 1);
    }

    /**
     * Method to swap two elements
     */
    void swap(List<Property> population, int i, int j) {
        double tempFitness = population.get(i).getFitnessScore();
        population.get(i).setFitnessScore(population.get(j).getFitnessScore());
        population.get(j).setFitnessScore(tempFitness);

        List<List<Integer>> tempPopulation = population.get(i).getChromosome();
        population.get(i).setChromosome(population.get(j).getChromosome());
        population.get(j).setChromosome(tempPopulation);
    }

    /**
     * Method to create selection list,
     * where order and occurrence frequency of
     * each element is based on fitness score
     *
     * @param population list of chromosomes
     * @return selection list
     */
    List<Integer> selection(Population population) {
        List<Integer> selectionCount = new ArrayList<>();
        List<Integer> selectionPool = new ArrayList<>();

        for (int i = 0; i < population.getCurrentPopulation().size(); i++) {
            selectionCount.add((int) ((population.getCurrentPopulation().get(i).getFitnessScore() / population.getCurrentPopulation().get(0).getFitnessScore()) * population.getMultiply()));
            for (int j = 0; j < selectionCount.get(i); j++) {
                selectionPool.add(i);
            }
        }

        return selectionPool;
    }

    /**
     * @param selectionPool list, where order and occurrence frequency of each element is based on fitness score
     * @param population    list of chromosomes
     * @return new population after crossover
     */
    List<Property> crossoverProcess(List<Integer> selectionPool, Population population) {
        List<Property> newPopulation = new ArrayList<>();

        if (population.getElitism() > 0) {
            for (int i = 0; i < population.getElitism(); i++) {
                newPopulation.add(population.getCurrentPopulation().get(i));
            }
        }

        for (int i = population.getElitism(); i < (population.getPopulationSize() / 2) + population.getElitism() / 2; i++) {
            int firstParent = random.nextInt(selectionPool.size());
            int secondParent = random.nextInt(selectionPool.size());
            int crossoverPoint = 1 + random.nextInt(population.getChromosomeSize() - 1);
            newPopulation.addAll(crossover(selectionPool.get(firstParent), selectionPool.get(secondParent), crossoverPoint, population));
        }
        return newPopulation;
    }

    /**
     * Crossover method that represents mating between chromosomes
     *
     * @param firstParent    randomly chosen chromosome
     * @param secondParent   randomly chosen chromosome
     * @param crossoverPoint point where genes swapped
     * @param population     list of chromosomes
     * @return two new offsprings
     */
    List<Property> crossover(int firstParent, int secondParent, int crossoverPoint, Population population) {
        List<Property> offspring = new ArrayList<>();
        offspring.add(new Property());
        offspring.add(new Property());

        int crossoverCounter = 0;
        for (int i = 0; i < population.getCurrentPopulation().get(firstParent).getChromosome().size(); i++) {
            List<List<Integer>> section = new ArrayList<>();
            section.add(new ArrayList<>());
            section.add(new ArrayList<>());
            for (int j = 0; j < population.getCurrentPopulation().get(firstParent).getChromosome().get(i).size(); j++) {
                if (crossoverCounter < crossoverPoint) {
                    recombination(i, j, firstParent, secondParent, section, population);
                    crossoverCounter++;
                } else {
                    recombination(i, j, secondParent, firstParent, section, population);
                }
            }
            offspring.get(0).getChromosome().add(section.get(0));
            offspring.get(1).getChromosome().add(section.get(1));
        }
        offspring.get(0).setFitnessScore(getFitnessValue(offspring.get(0).getChromosome(), population));
        offspring.get(1).setFitnessScore(getFitnessValue(offspring.get(1).getChromosome(), population));

        return offspring;
    }

    /**
     * Method to swap or mutate selected genes with random chance
     *
     * @param section contains elements for each type of area
     */
    void recombination(int i, int j, int chromosomeOne, int chromosomeTwo, List<List<Integer>> section, Population population) {
        if (random.nextDouble() <= population.getMutationChance()) {
            section.get(0).add(random.nextInt(1 + population.getRandomRange().get(i).get(j)));
            section.get(1).add(random.nextInt(1 + population.getRandomRange().get(i).get(j)));
        } else {
            section.get(0).add(population.getCurrentPopulation().get(chromosomeOne).getChromosome().get(i).get(j));
            section.get(1).add(population.getCurrentPopulation().get(chromosomeTwo).getChromosome().get(i).get(j));
        }
    }

    /**
     * Method to display the results
     *
     * @param message         text to display
     * @param generationCount generationCount amount of generations passed
     * @param population      list of chromosomes
     * @param filename        path to results file
     */
    void displayResult(String message, int generationCount, Population population, String filename) {
        System.out.println(message);
        System.out.println("Generations passed: " + generationCount);
        double areaCovered = 0;
        double sumWeightValue = 0;
        double sumOreValue = 0;
        double sumOversizeChunk = 0;

        for (int i = 0; i < population.getCurrentPopulation().get(0).getChromosome().size(); i++) {
            for (int j = 0; j < population.getCurrentPopulation().get(0).getChromosome().get(i).size(); j++) {
                areaCovered += population.getCurrentPopulation().get(0).getChromosome().get(i).get(j) * population.getMiningAreaProperties().get(i).getCharge().get(j).getRange();
                sumWeightValue += population.getCurrentPopulation().get(0).getChromosome().get(i).get(j) * population.getMiningAreaProperties().get(i).getCharge().get(j).getWeight();
                sumOreValue += population.getCurrentPopulation().get(0).getChromosome().get(i).get(j) * population.getMiningAreaProperties().get(i).getCharge().get(j).getAmountOfOre();
                sumOversizeChunk += population.getCurrentPopulation().get(0).getChromosome().get(i).get(j) * population.getMiningAreaProperties().get(i).getCharge().get(j).getAmountOfOre() * population.getMiningAreaProperties().get(i).getCharge().get(j).getOversizeChunkPercentage();
            }
        }

        System.out.println(population.getCurrentPopulation().get(0).getChromosome() + ";  "
                + population.getCurrentPopulation().get(0).getFitnessScore().intValue() + " tons of ore;  "
                + areaCovered + " meters;  " + (int) sumWeightValue + " kg of charges; "
                + String.format("%.2f", sumOversizeChunk / sumOreValue) + " oversize chunk percentage.");
        Result result = new Result(population.getCurrentPopulation().get(0).getChromosome(),
                population.getCurrentPopulation().get(0).getFitnessScore(),
                areaCovered,
                sumWeightValue, Math.round((sumOversizeChunk / sumOreValue) * 100d) / 100d);
        DataManager dataManager = new DataManager();
        dataManager.writeToJson(filename, result);
    }
}
