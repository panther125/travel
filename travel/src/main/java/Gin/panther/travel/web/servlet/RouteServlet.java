package Gin.panther.travel.web.servlet;

import Gin.panther.travel.domain.PageBean;
import Gin.panther.travel.domain.Route;
import Gin.panther.travel.domain.User;
import Gin.panther.travel.service.FavoriteService;
import Gin.panther.travel.service.RouteService;
import Gin.panther.travel.service.impl.FavoriteServiceImpl;
import Gin.panther.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    protected void pageQuery(HttpServletRequest req , HttpServletResponse resp) throws IOException {
        // 参数处理
        String currentPagestr = req.getParameter("currentPage");
        String pageSizestr = req.getParameter("pageSize");
        String cidstr = req.getParameter("cid");

        // 搜索线路名称
        String rname = req.getParameter("rname");
        if(rname != null){
            rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        }


        int cid = 0;
        if(cidstr != null && !cidstr.equals("null") && cidstr.length() > 0){
            cid = Integer.parseInt(cidstr);
        }else{
            cid=5;
        }
        int pageSize = 0;
        if(pageSizestr != null && pageSizestr.length() > 0){
            pageSize = Integer.parseInt(pageSizestr);
        }else{
            pageSize = 8;
        }
        int currentPage = 0;
        if(currentPagestr != null && currentPagestr.length() > 0){
            currentPage = Integer.parseInt(currentPagestr);
        }else{
            currentPage = 1;
        }

        // 封装数据
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize,rname);
        ToJSon(pb,resp);
    }

    // 查看旅游详细信息
    protected void findOne(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rid = req.getParameter("rid");
        Route one = routeService.findOne(rid);

        ToJSon(one,resp);

    }

    protected void isFavorite(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // 判断当前用户是否收藏过该线路
        String rid = req.getParameter("rid");
        User user = (User)req.getSession().getAttribute("user");
        int uid;
        if(user == null){
            // 未登录
            uid = 0;
        }else{
            uid = user.getUid();
        }

        boolean flag = favoriteService.isFavorite(rid, uid);
        ToJSon(flag,resp);
    }

    protected void addFavorite(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rid = req.getParameter("rid");
        User user = (User)req.getSession().getAttribute("user");
        int uid;
        if(user == null){
            // 未登录
            return;
        }else{
            uid = user.getUid();
        }

        favoriteService.addFavorite(rid,uid);
    }


    protected void myFavorite(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uid = req.getParameter("uid");
        List<Route> routes = favoriteService.myFavorite(uid);

        ToJSon(routes,resp);
    }
}
