package util;

/**
 * 关闭流的工具类
 *
 */
public class CloseUtil
{
    public CloseUtil()
    {
    };

    public static void closeStream(AutoCloseable... streams)
    {
        if (streams != null && streams.length > 0)
        {
            for (AutoCloseable stream : streams)
            {
                if (stream != null)
                {
                    try
                    {
                        stream.close();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
