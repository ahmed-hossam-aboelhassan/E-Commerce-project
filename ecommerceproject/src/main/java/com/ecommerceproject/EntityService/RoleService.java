package com.ecommerceproject.EntityService;

import com.ecommerceproject.Dao.RoleDao;
import com.ecommerceproject.Entity.Role;
import com.ecommerceproject.Entity.RoleId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements RoleServiceInterface {
    @Autowired
    private RoleDao roleDao;


    @Override
    public Role findById(RoleId roleId) {
        return roleDao.findById(roleId).get();
    }
}
