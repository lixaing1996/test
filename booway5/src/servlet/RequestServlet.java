package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import entity.User;
import service.UserService;
import service.impl.UserServiceExcelImpl;
import service.impl.UserServiceImpl;
import service.impl.UserServiceXmlImpl;

public class RequestServlet extends HttpServlet
{

    private static final long serialVersionUID = -3304809769459271227L;

    private static Map<String, String> mapping = new HashMap<String, String>();

    public RequestServlet()
    {
        super();
    }

    static
    {
        Properties p = new Properties();
        InputStream in = RequestServlet.class.getClassLoader().getResourceAsStream("properties.properties");
        try
        {
            p.load(in);
            for (Object key : p.keySet())
            {
                mapping.put((String) key, (String) p.getProperty((String)key));
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    //前面经过了一个字符过滤器 这里就不写字符编码了
        String url = request.getRequestURI();
        url = url.substring(url.lastIndexOf("/"));
        System.out.println(url);
        try
        {
            Class<?> c = Class.forName("servlet." + mapping.get(url));
            service service = null;
            service = (service) c.newInstance();
            service.doservice(request, response);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
       

    }

}

interface service
{

    public void doservice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}

class AddService implements service
{

    
    @Override
    public void doservice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        UserService us = new UserServiceImpl();
        String flag = request.getParameter("flag");
        /**
         * 点击增加之后和点击确认增加之后都会传回一个标志flag 如果是tiaozhuan就跳转到修改信息界面
         * 如果是add，就执行增加用户操作
         */
        if (flag.equals("tiaozhuan"))
        {
            request.getRequestDispatcher("/WEB-INF/jsp/addUser.jsp").forward(request, response);
        } else if (flag.equals("add"))
        {
            String username = request.getParameter("username");
            int age = Integer.parseInt(request.getParameter("age"));
            User u = new User();
            u.setName(username);
            u.setAge(age);
            us.add(u);
            request.getRequestDispatcher("/user").forward(request, response);
        }
    }
}

class SelectService implements service
{

    @Override
    public void doservice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        UserService us = new UserServiceImpl();

        String flag = req.getParameter("flag");
        /**
         * 进入主界面、修改后返回主界面、增加后返回主界面和名字模糊查询返回主界面都会返回一个flag
         * 如果是null或者update或者是add就查询全部数据展示
         * 如果是search就返回符合查询条件的结果
         */
        if (flag == null || flag.equals("add") || flag.equals("update"))
        {
            List<User> list2 = us.queryAll();
            req.getSession().setAttribute("users", list2);
            /**
             * 初始化界面的当前页为第1页和总页数
             */
            req.getSession().setAttribute("pageNos", 1);
            req.getSession().setAttribute("countPage", (int) Math.ceil((double) us.queryAll().size() / 5));
            req.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(req, resp);
        } else if (flag.equals("search"))
        {
            String username = req.getParameter("username");
            List<User> list = us.queryByName(username);
            req.getSession().setAttribute("users", list);
            /**
             * 初始化查询后界面的当前页为第1页和总页数
             */
            req.getSession().setAttribute("pageNos", 1);
            req.getSession().setAttribute("countPage", (int) Math.ceil((double) list.size() / 5));
            req.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(req, resp);
        }

    }

}

class UpdateService implements service
{
    
    @Override
    public void doservice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        UserService us = new UserServiceImpl();
        String flag = req.getParameter("flag");
        /**
         * 点击修改和点击修改完成之后都会传回一个flag
         * 如果是id，就传回被修改者的id并把id放到session里面传回到修改用户界面
         * 如果是update，就执行修改操作，最后返回到主界面
         */
        if (flag.equals("id"))
        {
            int id = Integer.parseInt(req.getParameter("id"));
            req.getSession().setAttribute("userId", id);
            req.getRequestDispatcher("/WEB-INF/jsp/updateUser.jsp").forward(req, resp);
        } else if (flag.equals("update"))
        {
            int userId = Integer.parseInt(req.getParameter("userId"));
            User u = us.queryById(userId);
            String username = req.getParameter("username");
            if (!req.getParameter("age").equals(""))
            {
                int age = Integer.parseInt(req.getParameter("age"));
                if (username.equals(""))
                {
                    u.setAge(age);
                } else
                {
                    u.setAge(age);
                    u.setName(username);
                }
            } else
            {
                if (!username.equals(""))
                {
                    u.setName(username);
                }
            }
            us.update(u);
            req.getRequestDispatcher("/user").forward(req, resp);
        }

    }
    
    }
    
class DeleteService implements service
{

    @Override
    public void doservice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        UserService us = new UserServiceImpl();
        int id = Integer.parseInt(req.getParameter("id"));
        us.delete(id);
        String json = JSON.toJSONString(id);
        PrintWriter out = resp.getWriter();
        out.println(json);

    }
}

class PageService implements service
{

    @Override
    public void doservice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        UserService us = new UserServiceImpl();
        UserService use = new UserServiceExcelImpl();
        UserService usx = new UserServiceXmlImpl();          
        int pageNos = Integer.parseInt(request.getParameter("pageNos"));

        String flag = request.getParameter("flag");
        int count = 0;
        int countPage = 0;
        /**
        * 三种方式都能分页，JDBC传回的值是1，Excel是2，Xml是3
        * 通过各自的queryAll方法获得总条数
        * 规定每页只有5行，所以总页数等于总条数除以5，余数要进1（调用Math的ceil方法就能得出答案）
        * pageNos代表的是当前页，count代表总行数，countPage代表总页数
        * 当前显示的起始行是（pageNos-1）*5 末尾行是pageNos*5-1，前台分别可以用c:foreach的属性begin和end表示
        */
        if ("1".equals(flag))
        {
            request.getSession().setAttribute("pageNos", pageNos);
            count = us.queryAll().size();
            countPage = (int) Math.ceil((double) count / 5);
            request.getSession().setAttribute("countPage", countPage);
            request.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(request, response);
        } else if ("2".equals(flag))
        {
            request.getSession().setAttribute("pageNosExcel", pageNos);
            count = use.queryAll().size();
            countPage = (int) Math.ceil((double) count / 5);
            request.getSession().setAttribute("countPageExcel", countPage);
            request.getRequestDispatcher("/WEB-INF/jsp/userExcel.jsp").forward(request, response);
        } else if ("3".equals(flag))
        {
            request.getSession().setAttribute("pageNosXml", pageNos);
            count = usx.queryAll().size();
            countPage = (int) Math.ceil((double) count / 5);
            request.getSession().setAttribute("countPageXml", countPage);
            request.getRequestDispatcher("/WEB-INF/jsp/userXml.jsp").forward(request, response);
        }
        
    }
}