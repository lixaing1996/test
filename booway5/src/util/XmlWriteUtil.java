package util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * 将Document写入File的工具类
 *
 */
public class XmlWriteUtil
{
    public static boolean write(Document document, File file)
    {
        Writer out = null;
        XMLWriter writer = null;
        try
        {
            // 创建输出流
            out = new PrintWriter(file, "utf-8");
            //创建格式化器
            OutputFormat format = new OutputFormat("\t", true);
            format.setTrimText(true);
            writer = new XMLWriter(out, format);
            writer.write(document);
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        } finally
        {
            try
            {
                writer.close();
                out.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
}
