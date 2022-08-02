package Gin.panther.travel.service.impl;

import Gin.panther.travel.dao.UserDAO;
import Gin.panther.travel.dao.impl.UserDAOImpl;
import Gin.panther.travel.domain.User;
import Gin.panther.travel.service.UserService;


public class UserServiceImpl implements UserService {

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public boolean addUser(User user) {
        User user1 = userDAO.getUser(user.getUsername());

        if(user1 != null){
            return false;
        }
        // 设置一个唯一激活码 最好加上时间变成唯一
//        user.setCode(UuidUtil.getUuid());
//        user.setStatus("N");
        userDAO.addUser(user);

        // 设置邮件正文
//        String content = "<a href='http://localhost/travel/activeServlet?code="+user.getCode()
//                +"'><a>点击激活琴酒旅游网</a>";
//        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    @Override
    public User login(String uname, String pwd) {
        return userDAO.findUser(uname,pwd);
    }
}
