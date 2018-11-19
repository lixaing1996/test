package entity;

import java.io.Serializable;

public class User implements Serializable
{

    /**
     *  User实体类 有序号、姓名、年龄三个属性
     *  空参和全参两个构造方法
     *  各属性get set方法
     */
    private static final long serialVersionUID = -3363120264501521428L;
    
    private int id;
    
    private String name;
    
    private int age;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public User()
    {
        super();
    }

    public User(int id, String name, int age)
    {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString()
    {
        return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
    

}
