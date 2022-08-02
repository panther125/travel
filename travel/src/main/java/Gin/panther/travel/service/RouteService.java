package Gin.panther.travel.service;

import Gin.panther.travel.domain.PageBean;
import Gin.panther.travel.domain.Route;

import java.util.List;

public interface RouteService {

    public PageBean<Route> pageQuery(int cid, int currPage, int pageSize,String rname);

    public Route findOne(String rid);
    // 人气旅游
    public List<Route> getpopularity();

    // 随机路线
    public List<Route> shufferRoute();

}
