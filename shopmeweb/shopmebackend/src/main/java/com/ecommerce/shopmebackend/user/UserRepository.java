package com.ecommerce.shopmebackend.user;

import com.ecommerce.shopmecommon.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

    Long countById(Long id);

    @Modifying
    @Transactional
    @Query(
            value = "update users set enabled = ?2 where id = ?1",
            nativeQuery = true
    )
    void updateUserStatus(Long id, boolean enabled);
}
