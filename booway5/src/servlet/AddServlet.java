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
 * JDBC方式增加用户的Servlet
 */
public class AddServlet extends HttpServlet
{
    private static final long serialVersionUID = 6739203285919462536L;

    private UserService us = new UserServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

}
