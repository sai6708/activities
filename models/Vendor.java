package mrhot.in.mrhotforbusiness.activities.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Anuran on 3/16/2017.
 */

public class Vendor implements Serializable{
    @SerializedName("id")
    String id;
    @SerializedName("city")
    String city;
    @SerializedName("name")
    String name;
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;
    @SerializedName("contact")
    String contact;
    @SerializedName("autokey")
    String autokey;
    @SerializedName("qualityRating")
    String qualityRating;
    @SerializedName("varietyRating")
    String varietyRating;
    @SerializedName("priceRating")
    String priceRating;

    public Vendor(String id, String city, String name, String username, String contact, String autokey, String qualityRating, String varietyRating, String priceRating) {
        this.id = id;
        this.city = city;
        this.name = name;
        this.username = username;
        // this.password = password;
        this.contact = contact;
        this.autokey = autokey;
        this.qualityRating = qualityRating;
        this.varietyRating = varietyRating;
        this.priceRating = priceRating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    /*
     public String getPassword() {
         return password;
     }

     public void setPassword(String password) {
         this.password = password;
     }
 */
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAutokey() {
        return autokey;
    }

    public void setAutokey(String autokey) {
        this.autokey = autokey;
    }

    public String getQualityRating() {
        return qualityRating;
    }

    public void setQualityRating(String qualityRating) {
        this.qualityRating = qualityRating;
    }

    public String getVarietyRating() {
        return varietyRating;
    }

    public void setVarietyRating(String varietyRating) {
        this.varietyRating = varietyRating;
    }

    public String getPriceRating() {
        return priceRating;
    }

    public void setPriceRating(String priceRating) {
        this.priceRating = priceRating;
    }
}
