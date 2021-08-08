package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    public User findByUsername(String userName);

    /**
     * 用户保存
     */
    public void save(User user);

    User findByCode(String code);

    void updateStatus(User uer);

    User findByUsernameAndPassword(String username, String password);
}
