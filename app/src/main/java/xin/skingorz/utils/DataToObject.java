package xin.skingorz.utils;

import java.util.List;

import xin.skingorz.Bean.User;
import xin.skingorz.Dao.UserDao;

public class DataToObject {
    private UserDao userDao = new UserDao();

    /**
     * 获取包含自己在内的所有好友的信息
     * @return
     */
    public List<User> getAllfriends(){
        return userDao.queryAll();
    }
}
