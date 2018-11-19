package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.User;
import service.UserService;
import service.impl.UserServiceExcelImpl;
/**
 * Excel方式修改用户的Servlet
 *
 */
public class UpdateExcelServlet extends HttpServlet
{

    private static final long serialVersionUID = -4688337081657565603L;
    private UserService us = new UserServiceExcelImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        String flag = request.getParameter("flag");
        /**
         * 点击修改和点击修改完成之后都会传回一个flag
         * 如果是id，就传回被修改者的id并把id放到session里面传回到修改用户界面
         * 如果是update，就执行修改操作，最后返回到主界面
         */
        if (flag.equals("id"))
        {
            int id = Integer.parseInt(request.getParameter("id"));
            request.getSession().setAttribute("userId", id);
            request.getRequestDispatcher("/WEB-INF/jsp/updateUserExcel.jsp").forward(request, response);
        } else if (flag.equals("update"))
        {
            int userId = Integer.parseInt(request.getParameter("userId"));
            User u = us.queryById(userId);
            String username = request.getParameter("username");
            if (!request.getParameter("age").equals(""))
            {
                int age = Integer.parseInt(request.getParameter("age"));
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
            request.getRequestDispatcher("/userExcel").forward(request, response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

}
