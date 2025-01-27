package com.ecommerceproject.EntityService;

import com.ecommerceproject.Dao.AddressDao;
import com.ecommerceproject.Entity.Address;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService implements AddressServiceInterface{
    @Autowired
    private AddressDao addressDao;

    public Address findById(int id){
        return addressDao.findById(id).orElse(null);
    }
    @Transactional
    public void deleteAddress(int addressId){
        addressDao.deleteByAddressId(addressId);
    }
}
