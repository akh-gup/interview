package se.akh.gup.klarna.questions;

import java.util.*;
import java.util.stream.Collectors;


public class Transactions {

    public static List<String> findRejectedTransactions(List<String> transactions, int creditLimit) {

        Transactions transactionsObj = new Transactions();
        ArrayList<String> rejectedTxns = new ArrayList<>();

        // Converts all transactions to user specific transactions
        HashMap<String, ArrayList<String>> userTxnMap = transactionsObj.userTransactionMap(transactions);

        // Find rejected transactions for each user and group them in a list.
        for(Map.Entry<String, ArrayList<String>> userTxnEntryMap: userTxnMap.entrySet()){

            rejectedTxns.addAll(transactionsObj.fetchRejectedTransactions(userTxnEntryMap.getValue(), creditLimit));
        }

        return transactionsObj.sortTransactions(rejectedTxns);
    }

    private List<String> sortTransactions(ArrayList<String> txns){

        Comparator<String> comparator = new Comparator<String>(){
            @Override
            public int compare(String s1, String s2){

                Integer txnId1 = Integer.parseInt(s1.replace("TR",""));
                Integer txnId2 = Integer.parseInt(s2.replace("TR",""));

                return txnId1.compareTo(txnId2);
            }

        };
        return txns.stream().sorted(comparator).collect(Collectors.toList());
    }

    /***
     Fetch rejected transactions:
     When sum of amounts exceeds the credit limit, transactions are going to be rejected after that.
     @param userTransactions: List of transaction for a user
     @param creditLimit: Limit allowed for the user
     @return List of rejected transactions id
     */
    public ArrayList<String> fetchRejectedTransactions(ArrayList<String> userTransactions, int creditLimit){

        ArrayList<String> rejectedTxns = new ArrayList<>();
        int totalSpentAmount = 0;

        for(String txn: userTransactions){

            String[] transactionDetail = txn.split(",");
            int txnAmount = Integer.parseInt(transactionDetail[3]);

            if (totalSpentAmount + txnAmount > creditLimit){
                rejectedTxns.add(transactionDetail[4]);

            } else {
                totalSpentAmount += txnAmount;
            }
        }
        return rejectedTxns;
    }

    /***
     Converts all transaction list to specific users transactions and stores them in map using unique user identifier.
     @param transactions: List of all transactions.
     @return Map of user transactions in list as value and unique user identifier as key
     */
    public HashMap<String, ArrayList<String>> userTransactionMap(List<String> transactions){

        HashMap<String, ArrayList<String>> userTxnMap = new HashMap<>();

        // Converts list of all transactions to user specific.
        for(String txn: transactions){

            String[] transactionDetail = txn.split(",");
            String userIdentifier = userIdentifier(transactionDetail);

            // If user specific transactions already exists,
            // add them in existing list or
            // add a new list with the transaction.
            if (userTxnMap.containsKey(userIdentifier)){

                ArrayList<String> userTxnList = userTxnMap.get(userIdentifier);
                userTxnList.add(txn);
                userTxnMap.put(userIdentifier, userTxnList);

            } else {
                userTxnMap.put(userIdentifier, new ArrayList<>(Arrays.asList(txn)));
            }
        }

        return userTxnMap;
    }

    /***
     Fetches identifier from transaction i.e. first name + last name + email address.
     @@param transactionInfo: Transaction details
     @return Return user identification
     */
    private String userIdentifier(String[] transactionInfo){
        return transactionInfo[0] + "_" +  transactionInfo[1] + "_" + transactionInfo[2];
    }
}