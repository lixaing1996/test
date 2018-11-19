package service;

import java.util.List;

import entity.User;

/**
 * Service层接口 由于没有具体业务，所以和DAO层一样
 *
 */
public interface UserService
{
    public boolean add(User u);

    public boolean delete(int id);

    public boolean update(User u);

    public List<User> queryAll();

    public User queryById(int id);

    public List<User> queryByName(String username);
}
