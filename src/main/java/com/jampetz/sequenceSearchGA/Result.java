package com.jampetz.sequenceSearchGA;

import java.util.List;

public class Result {
    private List<List<Integer>> chromosome;
    private double fitnessScore;
    private double areaCovered;
    private double weightOfCharges;
    private double oversizeChunkPercentage;

        public Result(List<List<Integer>> chromosome, double fitnessScore, double areaCovered, double weightOfCharges, double oversizeChunkPercentage) {
                this.chromosome = chromosome;
                this.fitnessScore = fitnessScore;
                this.areaCovered = areaCovered;
                this.weightOfCharges = weightOfCharges;
                this.oversizeChunkPercentage = oversizeChunkPercentage;
        }
}
