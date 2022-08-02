package Gin.panther.travel.dao;

import Gin.panther.travel.domain.Route;
import Gin.panther.travel.domain.RouteImg;
import Gin.panther.travel.domain.Seller;

import java.util.List;

public interface RouteDAO {

    // 根据cid查询查询总纪录数
    public int getTotalCount(int cid,String rname);

    // 根据cid，start，pagesize分页，查询到对应页码的数据
    public List<Route> findByPage(int cid, int start,int pageSize,String rname);

    // 根据id查看旅游详细信息
    public Route findOne(int rid);

    // 详细信息图片集合
    public List<RouteImg> findImg(int rid);

    // 查询店家id
    public Seller findSeller(int id);

    // 用户收藏后更新count的值
    public void updateCount(int rid, int newCount);

    // 获取收藏最高的4个旅游路线
    public List<Route> popularity();

    // 随机获取六个路线
    public List<Route> getSix();

}
