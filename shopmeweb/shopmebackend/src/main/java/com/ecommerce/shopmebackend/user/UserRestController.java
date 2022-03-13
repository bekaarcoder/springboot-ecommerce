package com.ecommerce.shopmebackend.user;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(@Param("email") String email) {
        return userService.isEmailUnique(email) ? "OK" : "Duplicate";
    }
}
