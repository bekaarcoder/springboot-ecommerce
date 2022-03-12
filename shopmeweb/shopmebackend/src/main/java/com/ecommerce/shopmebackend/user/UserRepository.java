package com.ecommerce.shopmebackend.user;

import com.ecommerce.shopmecommon.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
