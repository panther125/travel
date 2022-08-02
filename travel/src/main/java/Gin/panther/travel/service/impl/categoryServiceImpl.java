package Gin.panther.travel.service.impl;

import Gin.panther.travel.dao.categoryDAO;
import Gin.panther.travel.dao.impl.categoryDAOImpl;
import Gin.panther.travel.domain.Category;
import Gin.panther.travel.service.categoryService;
import Gin.panther.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class categoryServiceImpl implements categoryService {

    private categoryDAO categoryDAO = new categoryDAOImpl();

    @Override
    public List<Category> findNav() {
        // 获取jedis
        Jedis jedis = JedisUtil.getJedis();
        //判断集合是否为空
//        Set<String> nav = jedis.zrange("nav", 0, -1);
        Set<Tuple> nav = jedis.zrangeWithScores("nav", 0, -1);
        List<Category> result = null;
        if(nav == null || nav.size() == 0){
//            System.out.println("查询数据库");
            // 若为空从数据库获取信息 否则存入redis
            result = categoryDAO.findNav();
            for(Category temp : result){
                jedis.zadd("nav",temp.getCid(),temp.getCname());
            }
        }else {
//            System.out.println("redis有缓存");
            result = new ArrayList<Category>();
            for(Tuple temp : nav){
                Category category = new Category();
                category.setCname(temp.getElement());
                category.setCid((int)temp.getScore());
                result.add(category);
            }
        }

        return result;
    }
}
