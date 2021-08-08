package cn.itcast.travel.service;

public interface FavoriteService {

    /**
     * 判断用户手否收藏路线
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(String rid,int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    public  void add(String rid, int uid);
}
