package com.jampetz.sequenceSearchGA;

import java.util.ArrayList;
import java.util.List;

public class Population {
    private List<Property> currentPopulation;
    private List<List<Integer>> randomRange;
    private int chromosomeSize;
    private int populationSize;
    private int generationCount;
    private int elitism;
    private double mutationChance;
    private int multiply;
    private double target;
    private double initialRandomWeights;
    private double weightLimit;
    private double oversizeChunkLimit;
    private List<Detail> miningAreaProperties;

    public Population(InputData inputData) {
        this.populationSize = inputData.getPopulationSize();
        this.generationCount = inputData.getGenerationCount();
        this.elitism = inputData.getElitism();
        this.mutationChance = inputData.getMutationChance();
        this.multiply = inputData.getMultiply();
        this.target = inputData.getTarget();
        this.weightLimit = inputData.getWeightLimit();
        this.oversizeChunkLimit = inputData.getOversizeChunkLimit();
        this.miningAreaProperties = inputData.getMiningAreaProperties();
        this.initialRandomWeights = inputData.getInitialRandomWeights();
        this.currentPopulation = new ArrayList<>();
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getChromosomeSize() {
        return chromosomeSize;
    }

    public void setChromosomeSize(int chromosomeSize) {
        this.chromosomeSize = chromosomeSize;
    }

    public int getGenerationCount() {
        return generationCount;
    }

    public int getElitism() {
        return elitism;
    }

    public double getMutationChance() {
        return mutationChance;
    }

    public int getMultiply() {
        return multiply;
    }

    public double getTarget() {
        return target;
    }

    public List<Detail> getMiningAreaProperties() {
        return miningAreaProperties;
    }

    public List<List<Integer>> getRandomRange() {
        return randomRange;
    }

    public void setRandomRange(List<List<Integer>> randomRange) {
        this.randomRange = randomRange;
    }

    public List<Property> getCurrentPopulation() {
        return currentPopulation;
    }

    public void setCurrentPopulation(List<Property> currentPopulation) {
        this.currentPopulation = currentPopulation;
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public double getOversizeChunkLimit() {
        return oversizeChunkLimit;
    }

    public double getInitialRandomWeights() {
        return initialRandomWeights;
    }
}
