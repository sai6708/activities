package mrhot.in.mrhotforbusiness.activities.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jaideepsingh on 30/05/17.
 */

public class Orders implements Serializable {
        @SerializedName("hotcode")
        String hotcode;
        @SerializedName("name")
        String name;
        @SerializedName("itemlist")
        String itemlist;
        @SerializedName("quantity")
        String quantity;
        @SerializedName("COD")
        String COD;
        @SerializedName("contact")
        String contact;
        @SerializedName("roomNo")
        String roomNo;
        @SerializedName("address")
        String address;
        @SerializedName("slot")
        String slot;

        public Orders(String hotcode, String name, String itemlist, String quantity, String COD, String contact, String roomNo, String address, String slot) {
            this.hotcode = hotcode;
            this.name = name;
            this.itemlist = itemlist;
            // this.password = password;
            this.quantity = quantity;
            this.COD = COD;
            this.contact = contact;
            this.roomNo = roomNo;
            this.address = address;

            this.slot = slot;
        }

        public String gethotcode() {
            return hotcode;
        }

        public void setHotcode(String hotcode) {
            this.hotcode = hotcode;
        }

        public String getSlot() {
            return slot;
        }

        public void setSlot(String slot) {
            this.slot = slot;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getItemlist() {
            return itemlist;
        }

        public void setItemlist(String itemlist) {
            this.itemlist = itemlist;
        }
        /*
         public String getPassword() {
             return password;
         }
     
         public void setPassword(String password) {
             this.password = password;
         }
     */
        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getCOD() {
            return COD;
        }

        public void setCOD(String COD) {
            this.COD = COD;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getRoomNo() {
            return roomNo;
        }

        public void setRoomNo(String roomNo) {
            this.roomNo = roomNo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

