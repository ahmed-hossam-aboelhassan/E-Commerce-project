package com.ecommerceproject.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="user")
public class User   {

    @Id
    @Column(name = "user_name")
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at" ,updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name="default_address_id")
    private int defaultAddress;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Role> roles;

    @OneToOne( mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Address> addresses;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(cascade ={ CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH})
    @JoinTable(name = "user_order",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="order_id"))
    private List<Order> orders;


    public int getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(int defaultAddress) {
        System.out.println("kaka");
        this.defaultAddress = defaultAddress;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        this.getCart().setUser(this);
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;

    }

    public void addaddress(Address address){
        if(this.addresses==null)
            this.addresses=new ArrayList<>();

        this.addresses.add(address);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    public void addOrder(Order order){
        if(this.orders==null)
            this.orders=new ArrayList<>();

        this.orders.add(order);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonIgnore
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @JsonIgnore
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    @JsonIgnore
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<Role> getRoles() {
        return roles;
    }

    @JsonIgnore
    public void setRoles(List<Role> roles) {
        this.roles = roles;
        for(Role r:roles){
            r.setUser(this);
        }
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
        profile.setUser(this);

    }
    public void addrole(Role r){
        if(this.roles==null)
            this.roles=new ArrayList<>();

        this.roles.add(r);
        r.setUser(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}


