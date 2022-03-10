package com.ecommerce.shopmebackend.admin.user;

import com.ecommerce.shopmecommon.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
