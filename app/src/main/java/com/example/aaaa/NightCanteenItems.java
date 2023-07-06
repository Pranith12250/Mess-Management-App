package com.example.aaaa;

public class NightCanteenItems
{
    private String name;
    private int price;
    private String desc;
    public NightCanteenItems(){}
    public NightCanteenItems(String name, int price, String desc)
    {
        this.name = name;
        this.price = price;
        this.desc=desc;
    }

    public String getName()
    {
        return name;
    }

    public int getPrice()
    {
        return price;
    }

    public String getDescription()
    {
        return desc;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public void setDescription(String desc)
    {
        this.desc = desc;
    }
}

