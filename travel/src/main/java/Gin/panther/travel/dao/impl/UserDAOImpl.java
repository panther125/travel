package Gin.panther.travel.dao.impl;

import Gin.panther.travel.dao.UserDAO;
import Gin.panther.travel.domain.User;
import Gin.panther.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDAOImpl implements UserDAO {

    private JdbcTemplate jdbc = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public void addUser(User user) {
        String sql = "insert into tab_user values(0,?,?,?,?,?,?,?,null,null)";
        jdbc.update(sql,user.getUsername(),user.getPassword(),user.getName(),
                user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail());

    }

    @Override
    public User getUser(String username) {
        User user = null;

        try {
            String sql = "select * from tab_user where username=?";
            user = jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (DataAccessException e) {
    //            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findUser(String uname, String pwd) {
        User user = null;
        try {
            String sql = "select * from tab_user where username=? and password=?";
            user = jdbc.queryForObject(sql,new BeanPropertyRowMapper<>(User.class)
            , uname,pwd);
        } catch (DataAccessException e) {
//            e.printStackTrace();
        }
        return user;
    }
}
