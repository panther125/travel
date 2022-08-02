package Gin.panther.travel.web.servlet;

import Gin.panther.travel.domain.Category;
import Gin.panther.travel.service.categoryService;
import Gin.panther.travel.service.impl.categoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    private categoryService categoryService = new categoryServiceImpl();

    public void findNav(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Category> nav = categoryService.findNav();

        ToJSon(nav,resp);

    }


}
