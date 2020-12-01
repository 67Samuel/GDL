package com.example.gdl.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BillProcessor {
    private List<Bill> billList;
    private Map<String, Member> memberMasterMap;
    private Map<Member, Integer> memberIndexMap;
    private Member[] orderedMemberList;
    private double[][] optimisedComputationalGraph;
    private double[][] computationalGraph;
    private ArrayList<String> announcements = new ArrayList<>();


    BillProcessor(List<Bill> billList){
        this.billList = billList;
        this.readBillList();
    }
    BillProcessor(Event event){
        this.billList = event.getBillsList();
        this.readBillList();
    }


    public double[][] getComputationalGraph(){
        return this.computationalGraph;
    }

    public ArrayList<String> getAnnouncements(){
        return this.announcements;
    }

    public Map<String, Member> memberMasterMap() {
        return memberMasterMap;
    }

    public Member[] getOrderedMemberList() {
        return orderedMemberList;
    }


    private void readBillList(){
        consolidateMembers();
        indexMembers();
        createComputationalGraph();
    }

    private void consolidateMembers(){
        this.memberMasterMap = new HashMap<String, Member>();
//        for(Bill b : billList){
//            for(Member m : b.getmMembersList()){
//                if(!this.memberMasterMap.containsKey(m.mId)){
//                    this.memberMasterMap.put(m.mId, m);
//                }
//            }
//        }
        for(int bill = 0; bill < this.billList.size(); bill++){
            Bill b = this.billList.get(bill);
            List<Member> membersList = b.getMembersList();
            int memberListSize = membersList.size();

            for(int member = 0; member < memberListSize; member++){
                Member m = membersList.get(member);
                if(!this.memberMasterMap.containsKey(m.getId())){
                    this.memberMasterMap.put(m.getId(), m);
                }
            }
        }
    }

    private void indexMembers(){
        List<Member> membersList = new ArrayList<Member>(memberMasterMap.values());
        Map<Member, Integer> membersIndex = new HashMap<>();
        for(int i = 0; i < membersList.size(); i++){
            membersIndex.put(membersList.get(i), i);
        }
        memberIndexMap = membersIndex;

        Set<Member> membersKey = memberIndexMap.keySet();
        orderedMemberList = new Member[memberIndexMap.size()];
        for(Member m : membersKey){
            orderedMemberList[memberIndexMap.get(m)] = m;
        }
    }

    private void createComputationalGraph(){
        int len = orderedMemberList.length;
        computationalGraph = new double[len][len];
        for(Bill b : billList){
            Member payer = b.getPayer();
            Map<Member,Double> billExpensesMap = b.getExpensesMap();
            Set<Member> billPayees = billExpensesMap.keySet();
            for(Member m : billPayees){
                computationalGraph[memberIndexMap.get(m)][memberIndexMap.get(payer)] = billExpensesMap.get(m);
            }
        }
    }

    public void optimiseTransactions(GraphOptimiser opt){
        opt.setComputationalGraph(computationalGraph);
        opt.preProcessGraph();
        opt.optimise();
        optimisedComputationalGraph = opt.getOptimisedComputationalGraph();

        int len = orderedMemberList.length;
        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                double sumOfMoney = optimisedComputationalGraph[i][j];
                if(sumOfMoney != -0.0 && sumOfMoney != 0.0){
                    announcements.add(orderedMemberList[i].getName() + " owes "
                            + orderedMemberList[j].getName() + " $" + sumOfMoney);
                }
            }
        }
    }

    public double[][] getOptimisedComputationalGraph() {
        return optimisedComputationalGraph;
    }





}
