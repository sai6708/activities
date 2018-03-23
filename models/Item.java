package mrhot.in.mrhotforbusiness.activities.models;

/**
 * Created by Anuran on 3/18/2017.
 */

public class Item {
    String itemId,itemName,itemNick,itemPrice,description,category,fastingitem,spicy,jain,quantity;

    public Item(String itemId, String itemName, String itemNick, String itemPrice, String description, String category,
                String fastingitem,String spicy,String jain,String quantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemNick = itemNick;
        this.itemPrice = itemPrice;
        this.description = description;
        this.category = category;
        this.fastingitem=fastingitem;
        this.spicy=spicy;
        this.jain=jain;
        this.quantity=quantity;
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

    public String getItemNick() {
        return itemNick;
    }

    public void setItemNick(String itemNick) {
        this.itemNick = itemNick;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFastingitem() {
        return fastingitem;
    }

    public void setFastingitem(String fastingitem) {
        this.fastingitem = fastingitem;
    }

    public String getSpicy() {
        return spicy;
    }

    public void setSpicy(String spicy) {
        this.spicy = spicy;
    }

    public String getJain() {
        return jain;
    }

    public void setJain(String jain) {
        this.jain = jain;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
