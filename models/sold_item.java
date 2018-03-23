package mrhot.in.mrhotforbusiness.activities.models;

/**
 * Created by Jasbir_Singh on 16-06-2017.
 */

public class sold_item {
    String itemId,itemName;
    int id;
    String item_name;
    String sold;
    String sold_avail;
    String avail;

    public sold_item(String itemId, String itemName,int id,String item_name,String sold,String sold_avail,String avail) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.id=id;
        this.item_name=item_name;
        this.sold=sold;
        this.sold_avail=sold_avail;
        this.avail=avail;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getAvail() {
        return avail;
    }

    public void setAvail(String avail) {
        this.avail = avail;
    }

    public String getSold_avail() {
        return sold_avail;
    }

    public void setSold_avail(String sold_avail) {
        this.sold_avail = sold_avail;
    }
}
