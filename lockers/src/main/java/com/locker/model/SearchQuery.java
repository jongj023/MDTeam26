package com.locker.model;

/**
 * Created by randyr on 5/18/16.
 *
 * Used as model for the submitted search query.
 */
public class SearchQuery {
    String searchFloor;
    String searchTower;

    public SearchQuery() {}
    public SearchQuery(String searchFloor, String searchTower) {
        this.searchFloor = searchFloor;
        this.searchTower = searchTower;
    }

    public String getSearchFloor(){return searchFloor;}
    public String getSearchTower(){return searchTower;}

    public void setSearchFloor(String searchFloor) {this.searchFloor = searchFloor;}
    public void setSearchTower(String searchTower) {this.searchTower = searchTower;}

    public String toString() {
        return searchTower + " " + searchFloor;
    }
}
