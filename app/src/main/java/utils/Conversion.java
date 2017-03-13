package utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import model.ConversionRateTo;
import model.Currency;

/**
 * Created by Roman on 13-Mar-17.
 */

public class Conversion {

    /**What we have here is a directed and weighted (different rates from and to) graph problem,
     we can chose to solve it via dijkstra, dfs or bfs. My choice of action will be dfs (Depth-first search)
     My goal is that the conversion will run once and travers the graph and map the rates from all the currencies to GBP,
     thus eliminating the need to call separately for each currency conversion*/

    private  int gbpCurrencyPosition;
    private  ArrayList<Currency> currencyArrayList =new ArrayList<>();
    private  HashMap<String,Double> currencyToGBP = new HashMap<>();
    private  HashMap<String, ArrayList<ConversionRateTo>> currencyRatesFromAssets;


    public Conversion(HashMap<String, ArrayList<ConversionRateTo>> currencyRatesFromAssets) {
        this.currencyRatesFromAssets = currencyRatesFromAssets;
    }

    public HashMap<String, Double> getCurrencyRateToGBPRate() {
        //populate adjacency matrix
        int[][] adjacency_matrix = populateAdjacencyMatrix();

        //populate the nodes of the graph, which in our case is the different currencies
        populateCurrenciesNodes();

        //initialize the conversion map
        populateCurrencyToGBPHash();

        //return the hash with the converted rate after the algorithm completion.
        return dfsConversionAlgo(adjacency_matrix,currencyArrayList.get(gbpCurrencyPosition),currencyRatesFromAssets);

    }


    private  int[][] populateAdjacencyMatrix() {
        int[][] adjacency_matrix = new int[currencyRatesFromAssets.size()][currencyRatesFromAssets.size()];
        int i =0;
        for (String keySetI : currencyRatesFromAssets.keySet()) {
            int j = 0;
            for (String keySetJ : currencyRatesFromAssets.keySet()){
                for (ConversionRateTo conversionRateTo : currencyRatesFromAssets.get(keySetI)) {
                    if(keySetJ.equals(conversionRateTo.getCurrencyTarget())){
                        adjacency_matrix[i][j] = 1;
                        break;
                    }
                }
                j++;
            }

            i++;
        }
        return adjacency_matrix;
    }


    private  void populateCurrenciesNodes() {
        for (String currenciesName : currencyRatesFromAssets.keySet()) {
            if(currenciesName.equals("GBP")) {
                // indication of the GBP currency position, we would like to traverse the graph from the main currency
                gbpCurrencyPosition = currencyArrayList.size();
            }
            currencyArrayList.add(new Currency(currenciesName));}

    }

    private  void populateCurrencyToGBPHash() {
        for (String keySet : currencyRatesFromAssets.keySet()){
            currencyToGBP.put(keySet, 1.0);
        }
    }

    private  HashMap<String,Double> dfsConversionAlgo(int adjacency_matrix[][], Currency currency, HashMap<String, ArrayList<ConversionRateTo>> currencyRatesFromAssets) {
        Stack<Currency> currencyStack = new Stack<>();
        currencyStack.add(currency);//add GBP currency to the stack
        currency.setVisited(true);
        while (!currencyStack.isEmpty()) {
            Currency element = currencyStack.pop();

            ArrayList<Currency> connectedCurrencies = findConnectedCurrencies(adjacency_matrix, element);
            for (int i = 0; i < connectedCurrencies.size(); i++) {
                Currency cur = connectedCurrencies.get(i);
                if (cur != null && !cur.isVisited()) {

                    if(element.getCurrencyName().equals("GBP")) {
                        for (ConversionRateTo conversionRateTo : currencyRatesFromAssets.get(cur.getCurrencyName())) {
                            if (conversionRateTo.getCurrencyTarget().equals("GBP")){
                                currencyToGBP.put(cur.getCurrencyName(),conversionRateTo.getRate());
                            }
                        }
                    }else{
                        for (ConversionRateTo conversionRateTo : currencyRatesFromAssets.get(cur.getCurrencyName())) {
                            if (conversionRateTo.getCurrencyTarget().equals(element.getCurrencyName())){
                                currencyToGBP.put(cur.getCurrencyName(),conversionRateTo.getRate()*currencyToGBP.get(element.getCurrencyName()));
                            }
                        }
                    }

                    currencyStack.add(cur);
                    cur.setVisited(true);
                }
            }
        }
        return currencyToGBP;
    }


    private  ArrayList<Currency> findConnectedCurrencies(int adjacency_matrix[][], Currency x) {
        int nodeIndex = -1;
        ArrayList<Currency> neighbours = new ArrayList<>();
        for (int i = 0; i < currencyArrayList.size(); i++) {
            if (currencyArrayList.get(i).equals(x)) {
                nodeIndex = i;
                break;
            }
        }
        if (nodeIndex != -1) {
            for (int j = 0; j < adjacency_matrix[nodeIndex].length; j++) {
                if (adjacency_matrix[nodeIndex][j] == 1) {
                    neighbours.add(currencyArrayList.get(j));
                }
            }
        }
        return neighbours;
    }

}
