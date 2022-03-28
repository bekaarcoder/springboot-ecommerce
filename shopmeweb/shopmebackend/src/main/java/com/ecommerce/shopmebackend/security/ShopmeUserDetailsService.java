package com.ecommerce.shopmebackend.security;

import com.ecommerce.shopmebackend.user.UserRepository;
import com.ecommerce.shopmecommon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ShopmeUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return new ShopmeUserDetails(user);
        }
        throw new UsernameNotFoundException("Could not found user with email: " + email);
    }
}
