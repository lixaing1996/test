package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import service.UserService;
import service.impl.UserServiceExcelImpl;

/**
 * 
 * Excel方式的主界面（展示信息界面）
 *
 */
public class UserExcelServlet extends HttpServlet
{
    private static final long serialVersionUID = -2992913072098866516L;

    private UserService us = new UserServiceExcelImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String flag = request.getParameter("flag");
        /**
         * 进入主界面、修改后返回主界面、增加后返回主界面和名字模糊查询返回主界面都会返回一个flag
         * 如果是null或者update或者是add就查询全部数据展示
         * 如果是search就返回符合查询条件的结果
         */
        if (flag == null || flag.equals("add") || flag.equals("update"))
        {
            List<User> l = us.queryAll();
            request.getSession().setAttribute("userExcel", l);
            /**
             * 初始化界面的当前页为第1页和总页数
             */
            request.getSession().setAttribute("pageNosExcel", 1);
            request.getSession().setAttribute("countPageExcel", (int) Math.ceil((double) us.queryAll().size() / 5));
            request.getRequestDispatcher("/WEB-INF/jsp/userExcel.jsp").forward(request, response);
        } else if (flag.equals("search"))
        {
            String username = request.getParameter("username");
            List<User> list = us.queryByName(username);           
            request.getSession().setAttribute("userExcel", list);
            /**
             * 初始化查询后界面的当前页为第1页和总页数
             */
            request.getSession().setAttribute("pageNosExcel", 1);
            request.getSession().setAttribute("countPageExcel", (int) Math.ceil((double) list.size() / 5));
            request.getRequestDispatcher("/WEB-INF/jsp/userExcel.jsp").forward(request, response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

}
