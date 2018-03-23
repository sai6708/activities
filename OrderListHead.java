package mrhot.in.mrhotforbusiness.activities;

import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jaideepsingh on 28/06/17.
 */

public class OrderListHead {

    private String cost;
    private String orderTime;
    private String id;
    private String name;
    private String slot;
    private String cod;
    private String itms;
    private String cntct;
    private String hotcode;
    private String qty;
    private String roomNo;
    private String adrs;
    private String accepted;




    public OrderListHead(String id, String hotcode, String name, String itms, String qty, String cod, String cost, String cntct, String roomNo, String adrs, String slot, String orderTime, String accepted ) {
        this.name = name;
        this.slot = slot;
        this.cod = cod;
        this.itms = itms;
        this.cntct = cntct;
        this.hotcode = hotcode;
        this.qty = qty;
        this.roomNo = roomNo;
        this.adrs = adrs;
        this.cost = cost;
        this.orderTime = orderTime;
        this.id = id;
        this.accepted = accepted;

    }

    public String getName() {
        return name;
    }

    public String getCost() {
        return cost;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getId() {
        return id;
    }

    public String getSlot() {
        return slot;
    }

    public String getCod() {
        return cod;
    }

    public String getItms() {
        return itms;
    }

    public String getCntct() {
        return cntct;
    }
    public String getHotcode() {
        return hotcode;
    }

    public String getQty() {
        return qty;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public String getAdrs() {
        return adrs;
    }

    public String getAccepted() {
        return accepted;
    }
}
