package Gin.panther.travel.dao;

import Gin.panther.travel.domain.Category;

import java.util.List;

public interface categoryDAO {

    // 查询导航条
    public List<Category> findNav();
}
