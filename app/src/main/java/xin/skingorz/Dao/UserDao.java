package xin.skingorz.Dao;

import java.util.List;

import xin.skingorz.Bean.BaseApplication;
import xin.skingorz.Bean.User;

public class UserDao {

    /**
     * 向数据库中添加用户数据，如有重复则覆盖
     *
     * @param user 带添加的用户
     */
    public static void insertUser(User user) {
        BaseApplication.getDaoInstant().getUserDao().insertOrReplace(user);
    }

    /**
     * 根据主键删除数据
     *
     * @param id 主键
     */
    public static void deleteUser(long id) {
        BaseApplication.getDaoInstant().getUserDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param user
     */
    public static void updateUser(User user) {
        BaseApplication.getDaoInstant().getUserDao().update(user);
    }

    /**
     * 加载数据库的全部数据
     * @return 数据库的数据
     */
    public static List<User> queryAll(){
        return BaseApplication.getDaoInstant().getUserDao().loadAll();
    }
}