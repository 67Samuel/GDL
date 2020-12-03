package com.example.gdl;

import com.example.gdl.models.GreedyAlgorithm;

import org.junit.Test;
import org.junit.Before;
import org.junit.FixMethodOrder;
import static org.junit.Assert.*;

public class GreedyAlgorithmUnitTest {
    @Test
    public void test1() {
        double[][] expectedBeforeOptimisation = {{0,4,6,4,18},
                                                {10,0,6,4,18},
                                                {10,4,0,4,18},
                                                {10,4,6,0,18},
                                                {10,4,6,4,0}};
        double[][] expectedAfterOptimisation = {{0,0,0,0,0},
                                            {0,0,0,0,22},
                                            {8,0,0,0,4},
                                            {0,0,0,0,22},
                                            {0,0,0,0,0}};

        GreedyAlgorithm ga = new GreedyAlgorithm(expectedBeforeOptimisation);
        ga.preProcessGraph();
        ga.optimise();
        double[][] ocg = ga.getOptimisedComputationalGraph();
        for(int row = 0; row < 5; row++){
            assertArrayEquals(expectedAfterOptimisation[row], ocg[row], 0.0001);
        }
    }

    @Test
    public void test2() {
        double[][] expectedBeforeOptimisation = {{0,0,0,0,30},
                {50.0/3.0,0,0,5,30},
                {50.0/3.0,20.0/3.0,0,5,0},
                {0,20.0/3.0,10,0,0},
                {0,0,10.0,0,0}};
        double[][] expectedAfterOptimisation = {{0,0,0,0,0},
                {0,0,0,0,38.3333},
                {0,0,0,0,8.3333},
                {3.3333,0,0,0,3.3333},
                {0,0,0,0,0}};

        GreedyAlgorithm ga = new GreedyAlgorithm(expectedBeforeOptimisation);
        ga.preProcessGraph();
        ga.optimise();
        double[][] ocg = ga.getOptimisedComputationalGraph();
        for(int row = 0; row < 5; row++){
            assertArrayEquals(expectedAfterOptimisation[row], ocg[row], 0.0001);
        }
    }

    @Test
    public void zeroInput() {
        double[][] expectedBeforeOptimisation = {{0,0,0,0,0},
                                            {0,0,0,0,0},
                                            {0,0,0,0,0},
                                            {0,0,0,0,0},
                                            {0,0,0,0,0}};
        double[][] expectedAfterOptimisation = {{0,0,0,0,0},
                                                {0,0,0,0,0},
                                                {0,0,0,0,0},
                                                {0,0,0,0,0},
                                                {0,0,0,0,0}};

        GreedyAlgorithm ga = new GreedyAlgorithm(expectedBeforeOptimisation);
        ga.preProcessGraph();
        ga.optimise();
        double[][] ocg = ga.getOptimisedComputationalGraph();
        for(int row = 0; row < 5; row++){
            assertArrayEquals(expectedAfterOptimisation[row], ocg[row], 0.0001);
        }
    }

}
