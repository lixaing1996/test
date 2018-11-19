package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 获取数据库连接工具类
 */
public class JdbcUtil
{
    private static String url = null;
    private static String driver = null;
    private static String username = null;
    private static String password = null;
    static
    {

        Properties p = new Properties();
        InputStream i = null;

        try
        {
            i = JdbcUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
            p.load(i);
            driver = p.getProperty("driver");
            url = p.getProperty("url");
            username = p.getProperty("username");
            password = p.getProperty("password");

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception
    {
        Connection c = null;
        Class.forName(driver);
        c = DriverManager.getConnection(url, username, password);
        return c;
    }

}
