
package com.example.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Theater implements Serializable {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("postalCode")
    @Expose
    private String postalCode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("picture")
    @Expose
    private Picture picture;
    @SerializedName("cinemaChain")
    @Expose
    private CinemaChain cinemaChain;
    @SerializedName("hasPRMAccess")
    @Expose
    private Integer hasPRMAccess;
    @SerializedName("openToSales")
    @Expose
    private Integer openToSales;
    @SerializedName("geoloc")
    @Expose
    private Geoloc geoloc;
    @SerializedName("link")
    @Expose
    private List<TheaterLink> link = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public CinemaChain getCinemaChain() {
        return cinemaChain;
    }

    public void setCinemaChain(CinemaChain cinemaChain) {
        this.cinemaChain = cinemaChain;
    }

    public Integer getHasPRMAccess() {
        return hasPRMAccess;
    }

    public void setHasPRMAccess(Integer hasPRMAccess) {
        this.hasPRMAccess = hasPRMAccess;
    }

    public Integer getOpenToSales() {
        return openToSales;
    }

    public void setOpenToSales(Integer openToSales) {
        this.openToSales = openToSales;
    }

    public Geoloc getGeoloc() {
        return geoloc;
    }

    public void setGeoloc(Geoloc geoloc) {
        this.geoloc = geoloc;
    }

    public List<TheaterLink> getLink() {
        return link;
    }

    public void setLink(List<TheaterLink> link) {
        this.link = link;
    }

}
