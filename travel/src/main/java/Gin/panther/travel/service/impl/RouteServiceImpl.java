package Gin.panther.travel.service.impl;

import Gin.panther.travel.dao.FavoriteDAO;
import Gin.panther.travel.dao.RouteDAO;
import Gin.panther.travel.dao.impl.FavoriteDAOImpl;
import Gin.panther.travel.dao.impl.RouteDAOImpl;
import Gin.panther.travel.domain.PageBean;
import Gin.panther.travel.domain.Route;
import Gin.panther.travel.domain.RouteImg;
import Gin.panther.travel.domain.Seller;
import Gin.panther.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDAO routeDAO = new RouteDAOImpl();
    private FavoriteDAO favoriteDAO = new FavoriteDAOImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currPage, int pageSize,String rname) {
        PageBean<Route> pb = new PageBean<Route>();
        pb.setCurrentPage(currPage);
        pb.setPageSize(pageSize);

        int totalCount = routeDAO.getTotalCount(cid,rname);
        pb.setTotalCount(totalCount);

        // 数据集合
        List<Route> page = routeDAO.findByPage(cid, (currPage - 1) * pageSize, pageSize,rname);
        pb.setList(page);
        int totalpage = totalCount % pageSize == 0 ?
                totalCount/pageSize : (totalCount/pageSize) + 1;
        pb.setTotalPage(totalpage);

        return pb;
    }

    @Override
    public Route findOne(String rid) {
        Route route = routeDAO.findOne(Integer.parseInt(rid));

        // 根据route的id查询图片的id
        List<RouteImg> img = routeDAO.findImg(Integer.parseInt(rid));
        route.setRouteImgList(img);
        // 根据rid查询卖家信息sid
        Seller seller = routeDAO.findSeller(route.getSid());
        route.setSeller(seller);

        // 查询收藏次数


        return route;
    }

    @Override
    public List<Route> getpopularity() {
        return routeDAO.popularity();
    }

    @Override
    public List<Route> shufferRoute() {
        return routeDAO.getSix();
    }


}
