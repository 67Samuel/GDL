package com.example.gdl.models;

public abstract class GraphOptimiser {
    protected double[][] computationalGraph;
    protected double[][] optimisedComputationalGraph;


    public abstract void optimise();
    public abstract void preProcessGraph();

    public double[][] getComputationalGraph() {
        return computationalGraph;
    }

    public void setComputationalGraph(double[][] inputGraph){
        computationalGraph = inputGraph;
    }

    public double[][] getOptimisedComputationalGraph() {
        return optimisedComputationalGraph;
    }
}
