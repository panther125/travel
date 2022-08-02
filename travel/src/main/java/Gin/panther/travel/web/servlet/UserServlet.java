package Gin.panther.travel.web.servlet;

import Gin.panther.travel.domain.ResultInfo;
import Gin.panther.travel.domain.User;
import Gin.panther.travel.service.UserService;
import Gin.panther.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet{

    private UserService userService = new UserServiceImpl();

    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 校验验证码
        String check = req.getParameter("check");
        HttpSession session = req.getSession();
        String checkcode = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if(!check.equalsIgnoreCase(checkcode)){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            // 将info序列化成json对象返回前端
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(json);

            return;
        }

        Map<String, String[]> parameterMap = req.getParameterMap();
        // 封装对象
        User user = new User();
//        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        // 到service层校验
        boolean flag = userService.addUser(user);

        // 响应结果
        ResultInfo info = new ResultInfo();
        if(flag){
            info.setFlag(true);
        }else{
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }
        // 将info序列化成json对象返回前端
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uname = req.getParameter("username");
        String pwd = req.getParameter("password");

        User user = userService.login(uname, pwd);

        ResultInfo info = new ResultInfo();
        // 判断
        if(user == null){
            // 登录错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！");
        }else{
            info.setFlag(true);
            req.getSession().setAttribute("user",user);
        }
        // 响应数据
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getOutputStream(),info);
    }

    protected void find(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object user = req.getSession().getAttribute("user");

      ToJSon(user,resp);

    }

    protected void exit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 销毁session函数invalidate();
        req.getSession().invalidate();
        // 跳转登录页面
        resp.sendRedirect(req.getContextPath()+"/login.html");
    }
}
