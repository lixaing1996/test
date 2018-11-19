package service.impl;

import java.util.List;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import entity.User;
import service.UserService;

/**
 * Service接口JDBC方式的实现
 *都是直接调用DAO层的实现
 */

public class UserServiceImpl implements UserService
{
    private UserDao ud = new UserDaoImpl();

    public boolean add(User u)
    {

        return ud.add(u);
    }

    public boolean delete(int id)
    {

        return ud.delete(id);

    }

    public boolean update(User u)
    {

        return ud.update(u);

    }

    public List<User> queryAll()
    {
        List<User> l = ud.queryAll();
        return l;
    }

    public User queryById(int id)
    {
        User u = ud.queryById(id);
        return u;
    }

    @Override
    public List<User> queryByName(String username)
    {
        List<User> l = ud.queryByName(username);
        return l;

    }
}