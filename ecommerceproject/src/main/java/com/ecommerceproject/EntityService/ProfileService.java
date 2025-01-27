package com.ecommerceproject.EntityService;

import com.ecommerceproject.Dao.ProfileDao;
import com.ecommerceproject.Entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService implements ProfileServiceInterface{
    @Autowired
    private ProfileDao profileDao;

    @Override
    public Profile findById(String id) {
        return profileDao.findById(id).get();
    }
}
