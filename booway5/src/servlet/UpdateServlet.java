package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import service.UserService;
import service.impl.UserServiceImpl;

/**
 * JDBC方式修改用户的Servlet
 *
 */
public class UpdateServlet extends HttpServlet
{

    private static final long serialVersionUID = -1901090128684121108L;

    private UserService us = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        super.doPost(req, resp);
    }
}
