package Gin.panther.travel.dao;

import Gin.panther.travel.domain.User;

public interface UserDAO {

    // 注册判断
    public void addUser(User user);

    public User getUser(String username);

    // 登录判断
    public User findUser(String uname,String pwd);

}
