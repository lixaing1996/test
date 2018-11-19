
package dao;

import java.util.List;

import entity.User;

/**
 * DAO层接口
 *
 */
public interface UserDao
{
    public boolean add(User u);

    public boolean delete(int id);

    public boolean update(User u);

    public List<User> queryAll();

    public User queryById(int id);

    public List<User> queryByName(String username);
}
