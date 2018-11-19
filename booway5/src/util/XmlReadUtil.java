package util;

import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * 获取根节点下id符合要求的子节点的工具类
 *
 */
public class XmlReadUtil
{
    @SuppressWarnings("unchecked")
    public static Element searchNodeById(Element ele, int id)
    {
        //从Users根节点开始遍历，像【属性=值】的形式存为一个Attribute对象存储在List集合中
        List<Attribute> attrList = ele.attributes();
        Element et = null;
        for (Attribute attr : attrList)
        {
            String name = attr.getName();
            String value = attr.getValue();
            if (name.equals("id") && value.equals(String.valueOf(id)))
            {
                et = ele;
                break;
            }
        }
        if (et != null)
        {
            return et;
        } else
        {
            List<Element> eleList = ele.elements();
            //递归遍历父节点下的所有子节点
            for (Element e : eleList)
            {
                if (searchNodeById(e, id) != null)
                {
                    return searchNodeById(e, id);
                }
            }
        }
        return null;
    }
}
