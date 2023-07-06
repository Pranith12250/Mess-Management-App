package com.example.aaaa;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Menu
{
    @PrimaryKey
    @NonNull
    private String date;
    @Embedded(prefix = "breakfast_")
    private BreakfastItem breakfastItem;
    @Embedded(prefix = "lunch_")
    private LunchItem lunchItem;
    @Embedded(prefix = "snack_")
    private SnacksItem snacksItem;
    @Embedded(prefix = "dinner_")
    private DinnerItem dinnerItem;
    public Menu(@NonNull String date, BreakfastItem breakfastItem, LunchItem lunchItem,
                SnacksItem snacksItem, DinnerItem dinnerItem)
    {
        this.date=date;
        this.breakfastItem=breakfastItem;
        this.lunchItem=lunchItem;
        this.snacksItem=snacksItem;
        this.dinnerItem=dinnerItem;
    }
    public String getDate()
    {
        return date;
    }
    public BreakfastItem getBreakfastItem()
    {
        return breakfastItem;
    }
    public LunchItem getLunchItem()
    {
        return lunchItem;
    }
    public SnacksItem getSnacksItem()
    {
        return snacksItem;
    }
    public DinnerItem getDinnerItem()
    {
        return dinnerItem;
    }
    public void setDate(@NonNull String date)
    {
        this.date = date;
    }
    public void setBreakfastItem(BreakfastItem breakfastItem)
    {
        this.breakfastItem = breakfastItem;
    }
    public void setLunchItem(LunchItem lunchItem)
    {
        this.lunchItem = lunchItem;
    }
    public void setSnacksItem(SnacksItem snacksItem)
    {
        this.snacksItem = snacksItem;
    }
    public void setDinnerItem(DinnerItem dinnerItem)
    {
        this.dinnerItem = dinnerItem;
    }
}
