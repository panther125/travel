package Gin.panther.travel.dao.impl;

import Gin.panther.travel.dao.RouteDAO;
import Gin.panther.travel.domain.Route;
import Gin.panther.travel.domain.RouteImg;
import Gin.panther.travel.domain.Seller;
import Gin.panther.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("all")
public class RouteDAOImpl implements RouteDAO {

    private JdbcTemplate jdbc = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int getTotalCount(int cid,String rname) {
//        String sql = "select count(*) from tab_route where cid = ?";
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if(cid != 0){
            sb.append("and cid=? ");
            params.add(cid);
        }
        if(rname != null && rname.length() > 0){
            sb.append("and rname like ?");
            params.add("%"+rname+"%");
        }

        sql = sb.toString();

        return jdbc.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
//        String sql = "select * from tab_route where cid = ? limit ?,?";
        String sql = "select * from tab_route where 1=1 ";

        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if(cid != 0){
            sb.append("and cid=? ");
            params.add(cid);
        }
        if(rname != null && rname.length() > 0){
            sb.append("and rname like ?");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ?,?");
        sql = sb.toString();
        params.add(start);
        params.add(pageSize);

        return jdbc.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return jdbc.queryForObject(sql,new BeanPropertyRowMapper<>(Route.class),rid);
    }

    @Override
    public List<RouteImg> findImg(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        return jdbc.query(sql,new BeanPropertyRowMapper<>(RouteImg.class),rid);
    }

    @Override
    public Seller findSeller(int id) {
        String sql = "select * from tab_seller where sid = ?";

        return  jdbc.queryForObject(sql,new BeanPropertyRowMapper<>(Seller.class),id);
    }

    @Override
    public void updateCount(int rid,int newCount) {
        String sql = "update tab_route set count = ? where rid = ?";
        jdbc.update(sql,newCount,rid);
    }

    @Override
    public List<Route> popularity() {
        String sql = "select * from tab_route order by count desc limit 4";

        List<Route> popular = jdbc.query(sql, new BeanPropertyRowMapper<>(Route.class));

        return popular;
    }

    @Override
    public List<Route> getSix() {
        String sql = "select * from tab_route limit ? , 6";
        Random ran = new Random();
        int start = ran.nextInt(513);

        List<Route> shufferRoute = jdbc.query(sql, new BeanPropertyRowMapper<>(Route.class), start);

        return shufferRoute;
    }


}
