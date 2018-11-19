package dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import dao.UserDao;
import entity.User;
import util.CloseUtil;

/**
 * DAO层Excel方式的实现
 *
 */
public class UserDaoExcelImpl implements UserDao
/**
 * 单利模式保证Workbook和Sheet的唯一性
 */
{
    private static File file = new File("G:/excelTest.xlsx");
    private static InputStream is = null;
    private static Workbook workbook = null;
    private static Sheet sheet = null;
    /**
     * 静态代码块保证方法调用之前做初始化工作
     * 判断sheet（“0”）是否存在，不存在就创建新的，
     * 然后将表头写进Excel中，存在就直接赋值。
     */
    static
    {
        try
        {
            is = new FileInputStream(file);
            workbook = new XSSFWorkbook(is);
            CloseUtil.closeStream(is);
            if (workbook.getSheetAt(0) == null)
            {
                sheet = workbook.createSheet("0");
                Row row = sheet.createRow(0);
                Cell c0 = row.createCell(0);
                c0.setCellValue("序号");
                Cell c1 = row.createCell(1);
                c1.setCellValue("姓名");
                Cell c2 = row.createCell(2);
                c2.setCellValue("年龄");
            } else
            {
                sheet = workbook.getSheetAt(0);
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean add(User u)
    {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-MM-dd"));
        int rowLength = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(rowLength);
        FileOutputStream fileoutputStream = null;
        Integer maxId = 0;
        /* 
         * 增加用户时，id采用自增的方式，先取出excel中的用户的最大的id，
         * 然后加1作为新用户的id 缺点是如果id最大的用户被删除，则会导致出错。
         */
        try
        {
            List<Integer> l = new ArrayList<Integer>();
            for (int i = 1; i < rowLength; i++)
            {
                Row row2 = sheet.getRow(i);
                Cell c = row2.getCell(0);
                int id = (int) c.getNumericCellValue();
                l.add(id);
            }
            if (l.size() != 0)
            {
                maxId = Collections.max(l);
            }
            Cell c0 = row.createCell(0);
            c0.setCellValue(maxId + 1);
            Cell c1 = row.createCell(1);
            c1.setCellValue(u.getName());
            Cell c2 = row.createCell(2);
            c2.setCellValue((int) u.getAge());
            fileoutputStream = new FileOutputStream(file);
            workbook.write(fileoutputStream);
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        } finally
        {
            CloseUtil.closeStream(fileoutputStream);
        }

    }

    @Override
    public boolean delete(int id)
    {
        FileOutputStream fileoutputStream = null;
        int rowLength = sheet.getLastRowNum() + 1;
        for (int i = 1; i < rowLength; i++)
        {
            Row row = sheet.getRow(i);
            Cell c = row.getCell(0);
            int cid = (int) c.getNumericCellValue();
            if (cid == id)
            {
                if (i + 1 == rowLength) //删除用的是shiftRows方法，删除数据时下面的数据会自动上移
                { //但是要判断删除的数据是否是最后一行，最后一行删除时用removeRow方法
                    sheet.removeRow(row);
                } else
                {
                    sheet.shiftRows(i + 1, rowLength - 1, -1);
                }
                break;
            }
        }
        try
        {
            fileoutputStream = new FileOutputStream(file);
            workbook.write(fileoutputStream);
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        } finally
        {
            CloseUtil.closeStream(fileoutputStream);
        }
    }

    @Override
    public boolean update(User u)
    {
        FileOutputStream fileoutputStream = null;
        int rowLength = sheet.getLastRowNum() + 1;
        for (int i = 1; i < rowLength; i++)
        {
            Row row = sheet.getRow(i);
            Cell c = row.getCell(0);
            int cid = (int) c.getNumericCellValue();
            if (cid == u.getId())
            {
                Cell c2 = row.getCell(1);
                c2.setCellValue(u.getName());
                Cell c3 = row.getCell(2);
                c3.setCellValue(u.getAge());
                break;
            }
        }
        try
        {
            fileoutputStream = new FileOutputStream(file);
            workbook.write(fileoutputStream);
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        } finally
        {
            CloseUtil.closeStream(fileoutputStream);
        }
    }

    @Override
    public List<User> queryAll()
    {
        List<User> l = new ArrayList<User>();
        int rowLength = sheet.getLastRowNum() + 1;
        for (int i = 1; i < rowLength; i++)
        {
            Row row = sheet.getRow(i);
            User u = new User();
            int id = (int) row.getCell(0).getNumericCellValue();
            u.setId(id);
            String name = row.getCell(1).getStringCellValue();
            u.setName(name);
            int age = (int) row.getCell(2).getNumericCellValue();
            u.setAge(age);
            l.add(u);
        }
        return l;
    }

    @Override
    public User queryById(int id)
    {
        User u = new User();
        int rowLength = sheet.getLastRowNum() + 1;
        for (int i = 1; i < rowLength; i++)
        {
            Row row = sheet.getRow(i);
            Cell c = row.getCell(0);
            int cid = (int) c.getNumericCellValue();
            if (id == cid)
            {
                Cell c2 = row.getCell(1);
                u.setName(c2.getStringCellValue());
                Cell c3 = row.getCell(2);
                u.setAge((int) c3.getNumericCellValue());
                u.setId(id);
                break;
            }
        }
        return u;
    }

    @Override
    public List<User> queryByName(String username)
    {
        int rowLength = sheet.getLastRowNum() + 1;
        List<User> l = new ArrayList<User>();
        for (int i = 1; i < rowLength; i++)
        {
            Row row = sheet.getRow(i);
            Cell c = row.getCell(1);
            String name = c.getStringCellValue();
            if (name.contains(username)) //用excel中的名字通过contains方法和前台传来的值进行比较
            { //可以现实模糊查询
                User u = new User();
                Cell c2 = row.getCell(0);
                u.setId((int) c2.getNumericCellValue());
                Cell c3 = row.getCell(2);
                u.setAge((int) c3.getNumericCellValue());
                u.setName(name);
                l.add(u);
            }
        }
        return l;
    }

}
