package Gin.panther.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求路径
        String uri = req.getRequestURI();
        String methodName = uri.split("/")[3];
        try {
            // this指向继承baseServlet的子类，谁调用方法this就是谁
            Method method = this.getClass().getDeclaredMethod(methodName
                    ,HttpServletRequest.class,HttpServletResponse.class);
//           System.out.println(method);
            method.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 序列化为json
    protected void ToJSon(Object obj,HttpServletResponse resp) throws IOException {
        // 序列化返回
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json; charset=utf-8");
        mapper.writeValue(resp.getOutputStream(),obj);
    }

    protected String ToJsonAsStream(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return  mapper.writeValueAsString(obj);
    }
}
