package com.example.gdl;

import com.example.gdl.models.Bill;
import com.example.gdl.models.BillProcessor;
import com.example.gdl.models.GraphOptimiser;
import com.example.gdl.models.GreedyAlgorithm;
import com.example.gdl.models.Member;


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
    List<String> memberList1;
    List<String> memberList2;
    List<Bill> billList;
    Bill[] tempBill;


    @Before
    public void prepareTestItems(){
        memberList1 = new ArrayList<>();
        memberList1.add("m2");

        memberList2 = new ArrayList<>();
        memberList2.add("m4");
        memberList2.add("m3");

        Member payer1 = new Member("m1","m1");
        Member payer2 = new Member("m2","m2");


        Bill bill1 = new Bill();
        Bill bill2 = new Bill();
        bill1.setPayer(payer1);
        bill2.setPayer(payer2);
        bill1.setTotalCost(20);
        bill2.setTotalCost(30);
        bill1.setMembersList(memberList1);
        bill2.setMembersList(memberList2);
        billList = new ArrayList<>();
        billList.add(bill1);
        billList.add(bill2);
        tempBill = new Bill[]{bill1, bill2};
    }

    @Test
    public void testcase1(){
        List<Bill> billList1 = new ArrayList<>();


        BillProcessor opt = new BillProcessor(billList);
        double[][] cg = opt.getComputationalGraph();
        double[][] expected = {{0,0,0,0},
                                {10,0,0,0},
                                {0,10,0,0},
                                {0,10,0,0}};
//        System.out.println(opt.getMemberIndexMap().toString());
//        System.out.println(opt.getMemberMasterList().toString());
//        System.out.println(Arrays.deepToString(cg));
        for(int row = 0; row < 4; row++){
            assertArrayEquals(expected[row], cg[row], 0.0001);
        }

        GraphOptimiser optGraph = new GreedyAlgorithm();
        opt.optimiseTransactions(optGraph);
        double[][] ocg = opt.getOptimisedComputationalGraph();
        double[][] expectedAfterOptimisation = {{0,0,0,0},
                                                {0,0,0,0},
                                                {10,0,0,0},
                                                {0,10,0,0}};

        for(int row = 0; row < 4; row++){
            assertArrayEquals(expectedAfterOptimisation[row], ocg[row], 0.0001);
        }

    }
/*
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
*/

}
