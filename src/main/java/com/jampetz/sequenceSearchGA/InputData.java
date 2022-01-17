package com.jampetz.sequenceSearchGA;

import java.util.List;

public class InputData {
    private int populationSize;
    private double target;
    private double initialRandomWeights;
    private double weightLimit;
    private double oversizeChunkLimit;
    private int generationCount;
    private int elitism;
    private double mutationChance;
    private int multiply; //adjusting the size of selection pool array, where occurrence is based upon fitness score
    private List<Detail> miningAreaProperties;

    public List<Detail> getMiningAreaProperties() {
        return miningAreaProperties;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public double getTarget() {
        return target;
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public double getOversizeChunkLimit() {
        return oversizeChunkLimit;
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

    public double getInitialRandomWeights() {
        return initialRandomWeights;
    }
}
