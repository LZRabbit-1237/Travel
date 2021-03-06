package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategotyServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        //1.从redis中查询
        //1.1读取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.2.判断查询的结果是否为空
//        Set<String> categorys = jedis.zrange("category", 0, -1);
        //1.3查询sortedSet中的分数(cid)和值(cname)
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

        List<Category> cs = null;
        if(categorys == null || categorys.size() == 0){

//            System.out.println("从数据库查询。。。");

            //3.如果为空，需要从数据库中查询，再将数据库存入redis
            //3.1从数据库中查询
            cs = categoryDao.findAll();
            //3.2将几何数据存储到redis中的 category key中
            //3.3遍历集合
            for(int i = 0; i < cs.size(); i++){
                int cid = cs.get(i).getCid();
                String cname = cs.get(i).getCname();
                jedis.zadd("category",cid,cname);
            }
        }else{
//            System.out.println("从redis查询。。。");
            //4.如果不为空,将set的数据存入list
            cs = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                cs.add(category);
            }

        }
//        System.out.println(cs);
        return cs;
    }
}
