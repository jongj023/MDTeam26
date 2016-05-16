package com.locker.model;

/**
 * Created by randyr on 5/16/16.
 */
public class HistoryLimit {

    int limit;

    public HistoryLimit() {
    }
    public HistoryLimit(int limit){this.limit = limit;}

    public int getLimit() {return limit;}
    public void setLimit(int limit) {this.limit = limit;}
}
