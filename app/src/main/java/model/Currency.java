package model;

/**
 * Created by Roman on 13-Mar-17.
 */

public class Currency {

    private String currencyName;
    private boolean visited;

    public Currency(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
