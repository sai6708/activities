package mrhot.in.mrhotforbusiness.activities.models;

/**
 * Created by Jasbir_Singh on 18-06-2017.
 */

public class sold_data {

    String itemname;

    int success,item;

    public sold_data( int success,int item,String itemname) {

        this.item = item;
        this.itemname=itemname;
        this.success=success;
    }

    public int getsuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }
}
