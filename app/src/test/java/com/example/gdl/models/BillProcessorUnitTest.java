package com.example.gdl.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BillProcessorUnitTest {
    List<Member> memberList;
//    List<Bill> billList1;
//    List<Bill> billList2;
    Bill[] tempBill;
//    @Test
//    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
//    }
//
//    @Test(expected = AssertionError.class)
//    public void addition_isWrong() {
//        assertEquals(4, 2);
//    }


    @Before
    public void prepareTestItems(){
        Member m1 = new Member("A",1);
        Member m2 = new Member("B",2);
        Member m3 = new Member("C",3);
        Member m4 = new Member("D",4);
        Member m5 = new Member("E",5);
        memberList = new ArrayList<>();
        memberList.add(m1);
        memberList.add(m2);
        memberList.add(m3);
        memberList.add(m4);
        memberList.add(m5);

        Bill bill1 = new Bill("Pizza", "19/9/2019", 50, m1);
        Bill bill2 = new Bill("Taxi", "19/9/2019", 20, m2);
        Bill bill3 = new Bill("Karaoke", "19/9/2019", 30, m3);
        Bill bill4 = new Bill("Prata", "19/9/2019", 20, m4);
        Bill bill5 = new Bill("Trampoline park", "19/9/2019", 90, m5);
        tempBill = new Bill[]{bill1, bill2, bill3, bill4, bill5};

    }

    @Test
    public void allPaysForAll(){
        List<Bill> billList1 = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            Bill currentBill = tempBill[i];
            currentBill.setPayer(memberList.get(i));
            currentBill.setmMembersList(memberList);
            currentBill.calculateSplit();
            billList1.add(currentBill);
        }

        BillProcessor opt = new BillProcessor(billList1);
        double[][] cg = opt.getComputationalGraph();
        double[][] expected = {{0,4,6,4,18},
                                {10,0,6,4,18},
                                {10,4,0,4,18},
                                {10,4,6,0,18},
                                {10,4,6,4,0}};

        for(int row = 0; row < 5; row++){
            assertArrayEquals(expected[row], cg[row], 0.0001);
        }

        GraphOptimiser optGraph = new GreedyAlgorithm();
        opt.optimiseTransactions(optGraph);
        double[][] ocg = opt.getOptimisedComputationalGraph();
        double[][] expectedAfterOptimisation = {{0,0,0,0,0},
                                                {0,0,0,0,22},
                                                {8,0,0,0,4},
                                                {0,0,0,0,22},
                                                {0,0,0,0,0}};

        for(int row = 0; row < 5; row++){
            assertArrayEquals(expectedAfterOptimisation[row], ocg[row], 0.0001);
        }

    }

    @Test
    public void allPaysForSome(){
        List<Bill> billList2 = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            Bill currentBill = tempBill[i];
            currentBill.setPayer(memberList.get(i));
            if(i == 0) currentBill.setmMembersList(new ArrayList<Member>(memberList.subList(1,3)));
            else if(i == 1) currentBill.setmMembersList(new ArrayList<Member>(memberList.subList(2,4)));
            else if(i == 2) currentBill.setmMembersList(new ArrayList<Member>(memberList.subList(3,5)));
            else if(i == 3) currentBill.setmMembersList(new ArrayList<Member>(memberList.subList(1,4)));
            else currentBill.setmMembersList(new ArrayList<Member>(memberList.subList(0,2)));

            currentBill.addMember(memberList.get(i));
            currentBill.calculateSplit();
            billList2.add(currentBill);
        }

        BillProcessor opt2 = new BillProcessor(billList2);
        double[][] cg = opt2.getComputationalGraph();

        double[][] expected = {{0,0,0,0,30},
                                {50.0/3.0,0,0,5,30},
                                {50.0/3.0,20.0/3.0,0,5,0},
                                {0,20.0/3.0,10,0,0},
                                {0,0,10.0,0,0}};

        for(int row = 0; row < 5; row++){
            assertArrayEquals(expected[row], cg[row], 0.0001);
        }


        GraphOptimiser optGraph = new GreedyAlgorithm();
        opt2.optimiseTransactions(optGraph);
        double[][] ocg = opt2.getOptimisedComputationalGraph();
        double[][] expectedAfterOptimisation = {{0,0,0,0,0},
                                                {0,0,0,0,38.3333},
                                                {0,0,0,0,8.3333},
                                                {3.3333,0,0,0,3.3333},
                                                {0,0,0,0,0}};

        for(int row = 0; row < 5; row++){
            assertArrayEquals(expectedAfterOptimisation[row], ocg[row], 0.0001);
        }

    }

    @Test
    public void somePaysForSome(){
        List<Bill> billList2 = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            Bill currentBill = tempBill[i];
            currentBill.setPayer(memberList.get(i));
            if(i == 0) currentBill.setmMembersList(new ArrayList<Member>(memberList.subList(1,3)));
            else if(i == 2) currentBill.setmMembersList(new ArrayList<Member>(memberList.subList(3,5)));
            else if(i == 3) currentBill.setmMembersList(new ArrayList<Member>(memberList.subList(1,4)));

            currentBill.addMember(memberList.get(i));
            currentBill.calculateSplit();
            billList2.add(currentBill);
        }

        BillProcessor opt2 = new BillProcessor(billList2);
        double[][] cg = opt2.getComputationalGraph();

        double[][] expected = {{0,0,0,0,30},
                {50.0/3.0,0,0,5,30},
                {50.0/3.0,20.0/3.0,0,5,0},
                {0,20.0/3.0,10,0,0},
                {0,0,10.0,0,0}};

        for(int row = 0; row < 5; row++){
            assertArrayEquals(expected[row], cg[row], 0.0001);
        }


        GraphOptimiser optGraph = new GreedyAlgorithm();
        opt2.optimiseTransactions(optGraph);
        double[][] ocg = opt2.getOptimisedComputationalGraph();
        double[][] expectedAfterOptimisation = {{0,0,0,0,0},
                {0,0,0,0,38.3333},
                {0,0,0,0,8.3333},
                {3.3333,0,0,0,3.3333},
                {0,0,0,0,0}};

        for(int row = 0; row < 5; row++){
            assertArrayEquals(expectedAfterOptimisation[row], ocg[row], 0.0001);
        }

    }


}
