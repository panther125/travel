package Gin.panther.travel.web.servlet;

import Gin.panther.travel.domain.Route;
import Gin.panther.travel.service.RouteService;
import Gin.panther.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/index/*")
public class IndexServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();
    // 精选
    protected void getpopularity(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Route> popularity = routeService.getpopularity();

        ToJSon(popularity,resp);
    }
    // 国内游
    protected void getchina(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Route> china = routeService.shufferRoute();

        ToJSon(china,resp);
    }
    // 境外游
    protected void getforeign(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Route> foreign = routeService.shufferRoute();

        ToJSon(foreign,resp);
    }


}
