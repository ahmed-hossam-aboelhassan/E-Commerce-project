package com.ecommerceproject.Dto;

import com.ecommerceproject.Entity.Address;
import jakarta.persistence.Column;

public class AddressDto {
    private int addressno;
    private String country;
    private String city;
    private String addressLine;
    private String postalCode;

    public AddressDto(Address address ){

        this.country=address.getCountry();
        this.city=address.getCity();
        this.addressLine=address.getAddressLine();
        this.postalCode=address.getPostalCode();
    }




    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "addressno=" + addressno +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", addressLine='" + addressLine + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
