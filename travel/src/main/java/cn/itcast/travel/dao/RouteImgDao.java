package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {

    /**
     * 根据rid去tab_route_img中查询List<RouteImgDao>
     * @param rid
     * @return
     */
    public List<RouteImg> findByRid(int rid);
}
