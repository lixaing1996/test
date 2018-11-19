package servlet;

import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserService;
import service.impl.UserServiceExcelImpl;
import service.impl.UserServiceImpl;
import service.impl.UserServiceXmlImpl;

/**
 *分页的Servlet
 */
public class PageServlet extends HttpServlet
{

    private static final long serialVersionUID = 4743820091021024051L;
    UserService us = new UserServiceImpl();
    UserService use = new UserServiceExcelImpl();
    UserService usx = new UserServiceXmlImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

}
