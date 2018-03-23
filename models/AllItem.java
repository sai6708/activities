package mrhot.in.mrhotforbusiness.activities.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anuran on 3/23/2017.
 */

public class AllItem {
    @SerializedName("allItems")
    List<Item> allItems=new ArrayList<>();

    public AllItem(List<Item> allItems) {
        this.allItems = allItems;
    }

    public List<Item> getAllItems() {
        return allItems;
    }

    public void setAllItems(List<Item> allItems) {
        this.allItems = allItems;
    }
}
