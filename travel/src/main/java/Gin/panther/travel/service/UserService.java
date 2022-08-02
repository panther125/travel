package Gin.panther.travel.service;

import Gin.panther.travel.domain.User;

public interface UserService {

    public boolean addUser(User user);

    public User login(String uname, String pwd);
}
