package Gin.panther.travel.dao.impl;

import Gin.panther.travel.dao.categoryDAO;
import Gin.panther.travel.domain.Category;
import Gin.panther.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class categoryDAOImpl implements categoryDAO {

    private JdbcTemplate jdbc = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Category> findNav() {
        String sql = "select * from tab_category";
        return jdbc.query(sql,new BeanPropertyRowMapper<>(Category.class));
    }
}
