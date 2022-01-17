package com.jampetz.sequenceSearchGA;

import java.util.ArrayList;
import java.util.List;

public class Property {
    private List<List<Integer>> chromosome;
    private Double fitnessScore;

    public Property() {
        this.chromosome = new ArrayList<>();
        this.fitnessScore = 0d;
    }

    public List<List<Integer>> getChromosome() {
        return chromosome;
    }

    public void setChromosome(List<List<Integer>> chromosome) {
        this.chromosome = chromosome;
    }

    public Double getFitnessScore() {
        return fitnessScore;
    }

    public void setFitnessScore(Double fitnessScore) {
        this.fitnessScore = fitnessScore;
    }
}
