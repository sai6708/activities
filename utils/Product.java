package mrhot.in.mrhotforbusiness.activities.utils;

/**
 * Created by Jasbir_Singh on 09-06-2017.
 */

public class Product {
    String  id;
    String item_name;
    String sold;
    String sold_avail;
    String avail;


    //Constructor

    public  Product(String id, String item_name, String sold, String sold_avail, String avail) {
        this.id = id;
        this.item_name = item_name;
        this.sold = sold;
        this.sold_avail = sold_avail;
        this.avail = avail;

    }

    /*public void sold(int id,String avail)
    {
        this.avail=avail;
        this.id=id;
        sold="";
        sold_avail="";
        item_name="";
    }*/


    public String getId() {
        return id;
    }

    public void setId(String id) {
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
