package Gin.panther.travel.service;

import Gin.panther.travel.domain.Route;

import java.util.List;

public interface FavoriteService {

    public boolean isFavorite(String rid,int uid);

    public void addFavorite(String rid, int uid);

    // uid ->rid -> route
    public List<Route> myFavorite(String uid);
}
