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
public class BillProcessorUnitTest2 {
    List<String> memberList1;
    List<String> memberList2;
    List<Bill> billList;
    Bill[] tempBill;


    @Before
    public void prepareTestItems(){
        memberList1 = new ArrayList<>();
        memberList1.add("m4");
        memberList1.add("m3");
        memberList1.add("m1");
        memberList1.add("m5");

        memberList2 = new ArrayList<>();
        memberList2.add("m1");
        memberList2.add("m3");
        memberList2.add("m5");

        Member payer2 = new Member("m4","m4");
        Member payer1 = new Member("m2","m2");


        Bill bill1 = new Bill();
        Bill bill2 = new Bill();
        bill1.setPayer(payer1);
        bill2.setPayer(payer2);
        bill1.setTotalCost(10);
        bill2.setTotalCost(20);
        bill1.setMembersList(memberList1);
        bill2.setMembersList(memberList2);
        billList = new ArrayList<>();
        billList.add(bill1);
        billList.add(bill2);
        tempBill = new Bill[]{bill1, bill2};
    }

    @Test
    public void testcase2(){
        List<Bill> billList1 = new ArrayList<>();


        BillProcessor opt = new BillProcessor(billList);
        double[][] cg = opt.getComputationalGraph();
        double[][] expected = {{0,0, 0,0,0},
                {2,0,0, 0,0},
                {2,5,0,0,0},
                {2,5,0,0,0},
                {2,5,0,0,0}};
        System.out.println(opt.getMemberIndexMap().toString());
        System.out.println(opt.getMemberMasterList().toString());
        System.out.println(Arrays.deepToString(cg));
        for(int row = 0; row < 5; row++){
            assertArrayEquals(expected[row], cg[row], 0.0001);
        }

        GraphOptimiser optGraph = new GreedyAlgorithm();
        opt.optimiseTransactions(optGraph);
        double[][] ocg = opt.getOptimisedComputationalGraph();
        double[][] expectedAfterOptimisation = {{0,0,0,0,0},
                {0,0,0,0,0},
                {0,7,0,0,0},
                {7,0,0,0,0},
                {1,6,0,0,0}};

        for(int row = 0; row < 5; row++){
            assertArrayEquals(expectedAfterOptimisation[row], ocg[row], 0.0001);
        }

    }


}
