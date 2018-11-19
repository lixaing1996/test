package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import service.UserService;
import service.impl.UserServiceXmlImpl;
/**
 * Xml方式删除用户的Servlet
 *
 */
public class DeleteXmlServlet extends HttpServlet
{

    private static final long serialVersionUID = -8674890285082398310L;

    private UserService us = new UserServiceXmlImpl();

    /**
     * 用ajax传回数据给后台，完成删除操作后把数据转换成json字符串传回前台，前台用js隐藏删除的那行数据
     * 这样可以做到删除数据不用刷新页面就能看到结果
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        int id = Integer.parseInt(request.getParameter("id"));
        us.delete(id);
        String json = JSON.toJSONString(id);
        PrintWriter out = response.getWriter();
        out.println(json);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

}
