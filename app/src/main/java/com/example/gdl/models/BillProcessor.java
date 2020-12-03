package com.example.gdl.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BillProcessor {
    private List<Bill> billList;
    private List<String> memberMasterList;
    private Map<String, Integer> memberIndexMap;
    private double[][] optimisedComputationalGraph;
    private double[][] computationalGraph;
    private ArrayList<String> announcements = new ArrayList<>();


    public BillProcessor(List<Bill> billList){
        this.billList = billList;
        this.readBillList();
    }
    public BillProcessor(Event event){
        this.billList = event.getBillsList();
        this.readBillList();
    }

    public List<String> getMemberMasterList() {
        return memberMasterList;
    }

    public Map<String, Integer> getMemberIndexMap() {
        return memberIndexMap;
    }


    public double[][] getComputationalGraph(){
        return this.computationalGraph;
    }

    public ArrayList<String> getAnnouncements(){
        return this.announcements;
    }

    private void readBillList(){
        consolidateMembers();
        indexMembers();
        createComputationalGraph();
    }

    private void consolidateMembers(){
        ArrayList<String> allMembers = new ArrayList<>();
        for(int bill = 0; bill < this.billList.size(); bill++){
            Bill b = this.billList.get(bill);
            Member payer = b.getPayer();
            String payerID = payer.getId();
            if(!allMembers.contains(payerID)){
                allMembers.add(payerID);
            }

            List<String> billMembersList = b.getMembersList();
            for(int member = 0; member < billMembersList.size(); member++){
                String payeeID = billMembersList.get(member);
                if(!allMembers.contains(payeeID)){
                    allMembers.add(payeeID);
                }
            }
        }
        memberMasterList = allMembers;
    }

    private void indexMembers(){
        Map<String, Integer> indexMap = new HashMap<>();
        for(int member = 0; member < memberMasterList.size(); member++){
            indexMap.put(memberMasterList.get(member), member);
        }
        memberIndexMap = indexMap;
    }

    private void createComputationalGraph(){
        int len = memberMasterList.size();
        computationalGraph = new double[len][len];
        for(int bill = 0; bill < billList.size(); bill++){
            Bill currentBill = billList.get(bill);
            currentBill.calculateSplit();
            Map<String, Double> expMap = currentBill.getExpensesMap();
            String payerID = currentBill.getPayer().getId();

            Set<String> payeeIDs = expMap.keySet();
            Iterator<String> it = payeeIDs.iterator();
            while(it.hasNext()){
                String currentPayee = it.next();
                computationalGraph[memberIndexMap.get(currentPayee)][memberIndexMap.get(payerID)] = expMap.get(currentPayee);
            }
        }
    }

    public void optimiseTransactions(GraphOptimiser opt){
        opt.setComputationalGraph(computationalGraph);
        opt.preProcessGraph();
        opt.optimise();
        optimisedComputationalGraph = opt.getOptimisedComputationalGraph();

//        int len = memberMasterList.size();
//        for(int i = 0; i < len; i++){
//            for(int j = 0; j < len; j++){
//                double sumOfMoney = optimisedComputationalGraph[i][j];
//                if(sumOfMoney != -0.0 && sumOfMoney != 0.0){
//                    announcements.add(memberMasterList.get(i).getName() + " owes "
//                            + orderedMemberList[j].getName() + " $" + sumOfMoney);
//                }
//            }
//        }
    }

    public double[][] getOptimisedComputationalGraph() {
        return optimisedComputationalGraph;
    }





}