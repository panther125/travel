package Gin.panther.travel.dao.impl;

import Gin.panther.travel.dao.FavoriteDAO;
import Gin.panther.travel.domain.Favorite;
import Gin.panther.travel.domain.Route;
import Gin.panther.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;


public class FavoriteDAOImpl implements FavoriteDAO {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findByRidandUid(int rid, int uid) {
        Favorite favorite= null;
        try {
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            favorite = template.queryForObject(sql,
                    new BeanPropertyRowMapper<>(Favorite.class),rid,uid);
        } catch (DataAccessException e) {
//            e.printStackTrace();
        }

        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        return template.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public void addFavorite(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        String date = "2022-8-26";
        template.update(sql,rid,date,uid);
    }

    @Override
    public List<Integer> myFavorite(int uid) {
        String sql = "select rid from tab_favorite where uid = ?";

        List<Integer> rids = template.queryForList(sql, Integer.class, uid);
        return rids;
    }

    @Override
    public List<Route> favorite(List<Integer> rids) {
        String sql = "select * from tab_route where rid = ?";

        List<Route> favoriteRoute = new ArrayList<>();

        for (int i = 0; i < rids.size(); i++) {
            Route route = template.queryForObject(sql,new BeanPropertyRowMapper<>(Route.class),rids.get(i));
            favoriteRoute.add(route);
        }

        return favoriteRoute;
    }
}
