package com.example.gdl.models;

import java.util.Arrays;

public class GreedyAlgorithm extends GraphOptimiser{
    private double[] credit;

    public GreedyAlgorithm(double[][] computationalGraph){
        this.computationalGraph = computationalGraph;

    }

    public GreedyAlgorithm(){}

    @Override
    public void preProcessGraph(){
        calculateCreditPerNode();
        int N = computationalGraph.length;
        this.optimisedComputationalGraph = new double[N][N];
        checkInputs();
    }

    private void minimiseGraphArrows(double[] creditList){
        int mxCredit = getMax(creditList);
        int mxDebit = getMin(creditList);

        // If both amounts are 0, then
        // all amounts are settled
        if (creditList[mxCredit] == 0 || creditList[mxDebit] == 0)
            return;

        // Find the minimum of two amounts
        double min = Math.min(-creditList[mxDebit], creditList[mxCredit]);
        creditList[mxCredit] -= min;
        creditList[mxDebit] += min;

        this.optimisedComputationalGraph[mxDebit][mxCredit] = min;

//        System.out.println("Person " + mxDebit + " pays " + min
//                + " to " + "Person " + mxCredit);


        minimiseGraphArrows(creditList);
    }

    @Override
    public void optimise(){
        minimiseGraphArrows(this.credit);
    }

    private double[][] transpose(double[][] input){
        int len = input[0].length;
        double[][] output = new double[len][len];
        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                output[j][i] = input[i][j];
            }
        }
        return output;
    }

    private double[][] negate(double[][] input){
        int len = input[0].length;
        double[][] output = new double[len][len];
        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                output[i][j] = -input[i][j];
            }
        }
        return output;
    }

    private static int getMin(double arr[]) {
        int minInd = 0;
        for (int i = 1; i < arr.length; i++)
            if (arr[i] < arr[minInd])
                minInd = i;
        return minInd;
    }

    // A utility function that returns
    // index of maximum value in arr[]
    private static int getMax(double arr[]) {
        int maxInd = 0;
        for (int i = 1; i < arr.length; i++)
            if (arr[i] > arr[maxInd])
                maxInd = i;
        return maxInd;
    }

    private void calculateCreditPerNode() {
        // Create an array amount[],
        // initialize all value in it as 0.
        int N = this.computationalGraph[0].length;
        double amount[] = new double[N];

        // Calculate the net amount to
        // be paid to person 'p', and
        // stores it in amount[p]. The
        // value of amount[p] can be
        // calculated by subtracting
        // debts of 'p' from credits of 'p'
        for (int p = 0; p < N; p++)
            for (int i = 0; i < N; i++)
                amount[p] += (this.computationalGraph[i][p] - this.computationalGraph[p][i]);

        this.credit = amount;
    }

    public double[] getCredit() {
        return credit;
    }

    private void checkInputs(){
        int len = computationalGraph[0].length;
        double[][] output = new double[len][len];
        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                if(computationalGraph[i][j] < 0 && computationalGraph[i][j] != -0.0){
                    throw new IllegalArgumentException("Cannot have negative numbers in the computational graph matrix");
                }
            }
        }
    }





}
