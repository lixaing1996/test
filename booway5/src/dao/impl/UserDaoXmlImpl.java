package dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import dao.UserDao;
import entity.User;
import util.XmlReadUtil;
import util.XmlWriteUtil;

/**
 * DAO层Xml方式的实现
 *
 */
public class UserDaoXmlImpl implements UserDao
{
    /**
     * 单利模式保证Document和root的唯一性
     */
    private static SAXReader s = new SAXReader();
    private static Document d = null;
    private static File file = new File("E:\\workspace\\booway5\\src\\test.xml");
    private static Element root = null;
    /**
     * 静态代码块保证方法调用之前做初始化工作
     * 判断xml文件存不存在，存在就直接读给Document，不存在就要创建新的Document并写入进一个新的文件
     * XmlWriteUtil是把Document写入文件的工具类。
     */
    static
    {
        if (!file.exists())
        {
            d = DocumentHelper.createDocument();
            root = d.addElement("User");
            XmlWriteUtil.write(d, file);
        } else
        {
            try
            {
                d = s.read(file);
                root = d.getRootElement();
            } catch (DocumentException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean add(User u)
    {
        @SuppressWarnings("unchecked")
        Iterator<Element> i = root.elementIterator();
        List<Integer> list = new ArrayList<Integer>();
        while (i.hasNext())
        {
            Element e = i.next();
            @SuppressWarnings("unchecked")
            List<Attribute> l = e.attributes();
            /**
             * 和Excel一样 增加用户时id同样是取最大值id然后自增1的方式
             */
            for (Attribute a : l)
            {
                if ("id".equals(a.getName()))
                {
                    list.add(Integer.parseInt(a.getValue()));
                    break;
                }
            }
        }
        int maxId = Collections.max(list);
        Element e = root.addElement("User");
        e.addAttribute("id", String.valueOf(maxId + 1));
        e.addAttribute("name", u.getName());
        e.addAttribute("age", String.valueOf(u.getAge()));
        return XmlWriteUtil.write(d, file);
    }

    @Override
    public boolean delete(int id)
    {
        Element user = XmlReadUtil.searchNodeById(root, id); //XmlReadUtil是查找根节点下子节点id为某个值的子节点的工具类
        user.getParent().remove(user);
        return XmlWriteUtil.write(d, file);
    }

    @Override
    public boolean update(User u)
    {
        Element user = XmlReadUtil.searchNodeById(root, u.getId());
        @SuppressWarnings("unchecked")
        List<Attribute> l = user.attributes();
        for (Attribute a : l)
        {
            if ("name".equals(a.getName()))
            {
                a.setValue(u.getName());
            } else if ("age".equals(a.getName()))
            {
                a.setValue(String.valueOf(u.getAge()));
            }
        }
        return XmlWriteUtil.write(d, file);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> queryAll()
    {
        Iterator<Element> i = root.elementIterator();
        List<User> list = new ArrayList<User>();
        List<Attribute> l = null;
        User u = null;
        while (i.hasNext())
        {
            Element e = i.next();
            l = e.attributes();
            u = new User();
            for (Attribute a : l)
            {
                if ("name".equals(a.getName()))
                {
                    u.setName(a.getValue());
                } else if ("id".equals(a.getName()))
                {
                    u.setId(Integer.parseInt(a.getValue()));
                } else if ("age".equals(a.getName()))
                {
                    u.setAge(Integer.parseInt(a.getValue()));
                }
            }
            list.add(u);
        }
        return list;
    }

    @Override
    public User queryById(int id)
    {
        Element user = XmlReadUtil.searchNodeById(root, id);
        @SuppressWarnings("unchecked")
        List<Attribute> l = user.attributes();
        User u = new User();
        for (Attribute a : l)
        {
            if ("name".equals(a.getName()))
            {
                u.setName(a.getValue());
            } else if ("id".equals(a.getName()))
            {
                u.setId(Integer.parseInt(a.getValue()));
            } else if ("age".equals(a.getName()))
            {
                u.setAge(Integer.parseInt(a.getValue()));
            }
        }
        return u;
    }

    @Override
    public List<User> queryByName(String username)
    {
        @SuppressWarnings("unchecked")
        Iterator<Element> i = root.elementIterator();
        List<User> list = new ArrayList<User>();
        User u = null;
        while (i.hasNext())
        {
            Element e = i.next();
            @SuppressWarnings("unchecked")
            List<Attribute> l = e.attributes();
            for (Attribute a : l)
            {
                if ("name".equals(a.getName()) && a.getValue().contains(username))
                {
                    u = new User();
                    for (Attribute a2 : l)
                    {
                        if ("name".equals(a2.getName()))
                        {
                            u.setName(a2.getValue());
                        } else if ("id".equals(a2.getName()))
                        {
                            u.setId(Integer.parseInt(a2.getValue()));
                        } else if ("age".equals(a2.getName()))
                        {
                            u.setAge(Integer.parseInt(a2.getValue()));
                        }
                    }
                    list.add(u);
                }
            }
        }
        return list;
    }
}
