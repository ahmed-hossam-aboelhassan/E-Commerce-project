package com.ecommerceproject.EntityService;

import com.ecommerceproject.Entity.Role;
import com.ecommerceproject.Entity.RoleId;

public interface RoleServiceInterface {

    Role findById(RoleId roleId);

}
