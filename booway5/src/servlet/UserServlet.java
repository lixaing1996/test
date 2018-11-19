package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import service.UserService;
import service.impl.UserServiceImpl;

/**
 * 
 * JDBC方式的主界面（展示信息界面）
 *
 */

public class UserServlet extends HttpServlet
{
    private UserService us = new UserServiceImpl();
    private static final long serialVersionUID = -384554213505312705L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

        super.doPost(req, resp);
    }

}
