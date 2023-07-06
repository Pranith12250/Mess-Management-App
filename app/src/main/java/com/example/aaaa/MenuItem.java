package com.example.aaaa;

public class MenuItem
{
    private int id;
    private String name;
    private String description;
    private String others;
    public MenuItem(int id, String name,String description,String others)
    {
        this.id=id;
        this.name=name;
        this.description=description;
        this.others=others;
    }
    public int getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public String getDescription()
    {
        return description;
    }
    public String getOthers()
    {
        return others;
    }
}
