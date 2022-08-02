package Gin.panther.travel.dao;

import Gin.panther.travel.domain.Favorite;
import Gin.panther.travel.domain.Route;

import java.util.List;

public interface FavoriteDAO {

    public Favorite findByRidandUid(int rid, int uid);

    public int findCountByRid(int rid);

    public void addFavorite(int rid, int uid);

    // 根据uid查询收藏路线的rid
    public List<Integer> myFavorite(int uid);
    // 根据rid查出路线集合
    public List<Route> favorite(List<Integer> rids);

}
