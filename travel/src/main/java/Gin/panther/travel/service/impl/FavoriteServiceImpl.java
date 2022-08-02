package Gin.panther.travel.service.impl;

import Gin.panther.travel.dao.FavoriteDAO;
import Gin.panther.travel.dao.RouteDAO;
import Gin.panther.travel.dao.impl.FavoriteDAOImpl;
import Gin.panther.travel.dao.impl.RouteDAOImpl;
import Gin.panther.travel.domain.Favorite;
import Gin.panther.travel.domain.Route;
import Gin.panther.travel.service.FavoriteService;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDAO favroiteDAO = new FavoriteDAOImpl();
    private RouteDAO routeDAO = new RouteDAOImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favroiteDAO.findByRidandUid(Integer.parseInt(rid), uid);

        return favorite != null;
    }

    @Override
    public void addFavorite(String rid, int uid) {
        favroiteDAO.addFavorite(Integer.parseInt(rid),uid);

        Route one = routeDAO.findOne(Integer.parseInt(rid));
        int newCount = one.getCount() + 1;
        routeDAO.updateCount(Integer.parseInt(rid),newCount);
    }

    @Override
    public List<Route> myFavorite(String uid) {
        List<Integer> rids = favroiteDAO.myFavorite(Integer.parseInt(uid));

        List<Route> favorite = favroiteDAO.favorite(rids);

        return favorite;
    }

}
