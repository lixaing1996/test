package dao.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dao.UserDao;
import entity.User;
import util.CloseUtil;
import util.JdbcUtil;

/**
 * DAO层JDBC方式的实现
 *
 */
public class UserDaoImpl implements UserDao
{
    /**
     * 最基本的数据库对数据的增删改查，数据库操作完后调用util工具包中的关闭流方法。
     */
    private Connection c = null;
    private PreparedStatement p = null;
    private String sql = null;
    private ResultSet rs = null;

    public boolean add(User u)
    {
        sql = "insert into User(id,name,age) values(?,?,?)";
        try
        {
            c = JdbcUtil.getConnection();
            p = c.prepareStatement(sql);
            p.setInt(1, u.getId());
            p.setString(2, u.getName());
            p.setInt(3, u.getAge());
            return p.executeUpdate() > 0;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        } finally
        {
            CloseUtil.closeStream(p, c);
        }    
    }

    public boolean delete(int id)
    {
        sql = "delete from User where id = ?";
        try
        {
            c = JdbcUtil.getConnection();
            p = c.prepareStatement(sql);
            p.setInt(1, id);

            return p.executeUpdate() > 0;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        } finally
        {
            CloseUtil.closeStream(p, c);
        }
    }

    public boolean update(User u)
    {
        sql = "update User set name = ? , age = ? where id = ?";
        try
        {
            c = JdbcUtil.getConnection();
            p = c.prepareStatement(sql);
            p.setString(1, u.getName());
            p.setInt(2, u.getAge());
            p.setInt(3, u.getId());
            return p.executeUpdate() > 0;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        } finally
        {
            CloseUtil.closeStream(p, c);
        }
   }

    public List<User> queryAll()
    {
        sql = "select * from User";
        List<User> l = new ArrayList<User>();
        try
        {
            c = JdbcUtil.getConnection();
            p = c.prepareStatement(sql);
            rs = p.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                User u = new User(id, name, age);
                l.add(u);
            }
            return l;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            CloseUtil.closeStream(rs, p, c);
        }
    }

    public User queryById(int id)
    {
        User u = null;
        sql = "select * from User where id =?";
        try
        {
            c = JdbcUtil.getConnection();
            p = c.prepareStatement(sql);
            p.setInt(1, id);
            rs = p.executeQuery();
            if (rs.next())
            {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                u = new User(id, name, age);
            }
            return u;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            CloseUtil.closeStream(rs, p, c);
        }
    }

    @Override
    public List<User> queryByName(String username)
    {
        {
            sql = "select * from User where name like ?"; //使用like语句进行模糊查询
            List<User> l = new ArrayList<User>();
            try
            {
                c = JdbcUtil.getConnection();
                p = c.prepareStatement(sql);
                p.setString(1, "%" + username + "%");
                rs = p.executeQuery();
                while (rs.next())
                {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    User u = new User(id, name, age);
                    l.add(u);
                }
                return l;
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                CloseUtil.closeStream(rs, p, c);
            }
            return null;
        }
    }

}
