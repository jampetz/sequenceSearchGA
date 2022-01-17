package com.jampetz.sequenceSearchGA;

public class Main {
    public static void main(String[] args) {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

        DataManager dataManager = new DataManager();
        InputData inputData = dataManager.readDataFromJson
                ("/inputData/inputData.json", InputData.class);

        long start = System.currentTimeMillis();
        geneticAlgorithm.runEvolution(inputData,"/result/result.json");
        long end = System.currentTimeMillis();
        System.out.println("Elapsed Time in milli seconds: " + (end - start));
    }
}
